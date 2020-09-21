import { Component, OnInit, ViewChild, Input, ChangeDetectorRef } from '@angular/core';
import { STComponent, STColumn, STChange } from '@delon/abc';
import { QueryOptions } from '@mt-framework-ng/core';
import { Router , ActivatedRoute } from '@angular/router';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';

import { DataStandardSimilarWordService } from '@mt-datagov-ng/standard/src/services';
import { CategoryTreeService } from '@mt-datagov-ng/common/src/services/tree/category-tree.service';
import { TreeUtil } from '@mt-datagov-ng/common';
import { NzMessageService } from 'ng-zorro-antd/message';


@Component({
  selector: 'app-data-standard-similar-word-edit-editor',
  templateUrl: './data-standard-similar-word-edit.component.html',
  styleUrls: ['./data-standard-similar-word-edit.component.less'],
})
export class DataStandardSimilarWordEditComponent  implements OnInit {


  validateForm: FormGroup;
  controlArray: Array<{ index: number; show: boolean }> = [];


  dataStandardSimilarWord = {
      id: null,
      code : '' ,
      word : '' ,
      cateId  :  '' ,
      comment  : '' ,
      similarWords :  {},

  } ;




  loading = false;

  /**校验规则窗口 */
  visible = false;
  /**批量删除 窗口 */
  batchVisible = false;

  /**
   * 目录参数
   */
  category: any;
  code = null;
  word = null;

  /** 目录树 */
  cateNodes = [];

  /**
  * 分页，排序参数
  */
  @Input()
  queryOption: QueryOptions = {
      page: 0,
      size: 20,
      sort: 'id,desc',
  };

  mapOfSimilarWordData: Array<{id: number , code: string , comment: string , checked: boolean}> = [];
  /**总条数 */
  mapOfSimilarWordDataSum: Array<{id: number , code: string , comment: string , checked: boolean}> = [];
  similarWordDataLength = 0;

  similarWordTitle: string;

  similarWordForm = {
      id: 0,
      code: '',
      comment: '',
      checked: false
  };

  stColumns: STColumn[] = [
      { title: '#', width: '3%' },
      { title: '词语', width: '40%' },
      { title: '描述', width: '40%'},
      { title: '操作', width: '17%' },
  ];

  /**
   * 列表参数
   */
  pageSizeOptions = [10, 20, 30, 100];
  count: number;
  pageSize = 20;
  pageIndex = 1;
  totalPages: number;


  constructor(
      private router: Router,
      private cdr: ChangeDetectorRef,
      private activeRoute: ActivatedRoute,
      private fb: FormBuilder,
      private dataStandardSimilarWordService: DataStandardSimilarWordService,
      private categoryTreeService: CategoryTreeService,
      private messageService: NzMessageService,
  ) { }


  ngOnInit(): void {
      this.activeRoute.params.subscribe(params => {
          // 修改
          if (params && params.id) {
              this.dataStandardSimilarWordService.findById(params.id).subscribe(searchDataStandard => {
                  // tslint:disable-next-line: forin
                  for (const key in this.dataStandardSimilarWord) {
                      // tslint:disable-next-line: no-unused-expression
                      this.dataStandardSimilarWord[key] = searchDataStandard [key];
                  }

                   // tslint:disable-next-line: forin
                   for (const key in this.dataStandardSimilarWord.similarWords) {
                      this.similarWordDataLength ++;
                      // tslint:disable-next-line: no-unused-expression
                      this.mapOfSimilarWordDataSum.push({id: this.similarWordDataLength , code: key,
                          comment: this.dataStandardSimilarWord.similarWords[key], checked: false } );
                   }
                   this.mapOfSimilarWordDataSum = [...this.mapOfSimilarWordDataSum];
                   this.load();

              });

          // 新增
          } else {
              this.dataStandardSimilarWordService.getCode().subscribe(data => {
                  this.dataStandardSimilarWord.code = data;
              });
              this.load();

          }
      });
      this.initForm(this.dataStandardSimilarWord ? this.dataStandardSimilarWord : null);
      this.loadCateTree();

  }


  initForm(dataStandardSimilarWord?: any) {
      this.validateForm = this.fb.group({
        code: [dataStandardSimilarWord ? dataStandardSimilarWord.code : null],
        word: [dataStandardSimilarWord ? dataStandardSimilarWord.word : null],
        cateId: [dataStandardSimilarWord ? dataStandardSimilarWord.cateId : null],
        comment: [dataStandardSimilarWord ? dataStandardSimilarWord.comment : null],
        similarWords: [dataStandardSimilarWord ? dataStandardSimilarWord.similarWords : ''],
      });
      if (this.validateForm) {
        this.validateForm.updateValueAndValidity();
      }
    }





    /** 加载目录树 */
  loadCateTree() {
      this.categoryTreeService.get('SIMILAR_WORD').subscribe(resp => {
          const items = resp;
          this.cateNodes = TreeUtil.populateTreeNodes(items, 'name', 'id', 'children');
      });
  }

  /**
   * 加载
   */
  load() {
      this.loading = true;
      this.mapOfSimilarWordData = [];
      // 整体赋值
      if (this.mapOfSimilarWordDataSum.length > this.pageSize ) {
          let pageLegth ;
          if (this.mapOfSimilarWordDataSum.length >= this.pageSize * this.pageIndex) {
              pageLegth = this.pageSize * this.pageIndex;
          } else {
              pageLegth = this.mapOfSimilarWordDataSum.length;
          }
          // tslint:disable-next-line: no-unused-expression
          for ( let i = this.pageSize * (this.pageIndex - 1); i < pageLegth ; i++){
              this.mapOfSimilarWordData.push(this.mapOfSimilarWordDataSum[i]);
          }
          this.mapOfSimilarWordData = [...this.mapOfSimilarWordData];
       } else {
          this.mapOfSimilarWordData = [...this.mapOfSimilarWordDataSum];
       }
      // 整体赋值
      this.count = this.mapOfSimilarWordData.length;
      this.totalPages = this.mapOfSimilarWordDataSum.length;
      this.mapOfSimilarWordDataSum = [...this.mapOfSimilarWordDataSum];
      this.mapOfSimilarWordData = [...this.mapOfSimilarWordData];
      this.loading = false;
  }
  /**
   * 页码改变的回调
   * @param $event 选中的页码
   */
  pageIndexChange ($event) {
      this.queryOption.page = $event - 1;
      this.pageIndex = $event;
      this.load();
  }
  /**
   * 每页条数改变的回调
   * @param $event 选中的条数
   */
  pageSizeChange($event) {
      this.pageSize = $event;
      this.queryOption.size = $event;
      const size = this.count / $event;
      if (size === 1 || size < 1) {
          this.queryOption.page = 0;
          this.pageIndex = 1;
      } else {
          if (this.pageIndex > Math.ceil(size)) {
              this.queryOption.page = Math.ceil(size) - 1;
              this.pageIndex = Math.ceil(size);
          }
      }
      this.load();

  }






  /**
   * 判断是否全选中
   */
  get isAllDisplayDataChecked() {
      return this.mapOfSimilarWordData.every(item => item.checked);
  }






  /**
   * @param rowData  修改参数
   * 新增校验规则
   */
  openSimilarWord(rowData?: {id: number, code: string , comment: string , checked: boolean}) {
      this.visible = true;
      if (rowData) {
          this.similarWordForm.id = rowData.id;
          this.similarWordForm.code = rowData.code;
          this.similarWordForm.comment = rowData.comment;
          this.similarWordForm.checked = rowData.checked;
          this.similarWordTitle = '修改';
      } else {
          this.similarWordForm.id = 0 ;
          this.similarWordForm.code = '';
          this.similarWordForm.comment = '';
          this.similarWordForm.checked = false;
          this.similarWordTitle = '新增';
      }
  }

  opeanBatchDeleted() {
      this.batchVisible = true;
  }



   /**
   * 点击多选框触发事件
   */
  checkAll(value: boolean, row?: any): void {
      if (row) {
          row.checked = value;
      } else {
          this.mapOfSimilarWordData.forEach(item => item.checked = value);
      }
      this.cdr.detectChanges();
  }

  /**
   * 批量删除 规则
   */
  batchDelete() {
      const deleteData = [];
      this.mapOfSimilarWordDataSum.forEach(item => {
          if (item.checked) {
              deleteData.push(item);
          }
      }) ;
      if (deleteData.length === 0) {
          this.messageService.warning('请选中行');
          return;
      }
      deleteData.forEach(item => {
          this.sigletonDelete(item);
      }) ;
      this.batchHandleCancel();
      this.load();
  }


  deleteSimilarWord(rowData: {id: number, code: string , comment: string , checked: boolean}) {
      this.sigletonDelete(rowData);
      this.load();
  }

  sigletonDelete (rowData: {id: number, code: string , comment: string , checked: boolean}) {
      const index = this.mapOfSimilarWordDataSum.indexOf(rowData);
      this.mapOfSimilarWordDataSum.splice(index, 1);
      this.count--;
      this.totalPages--;
  }

  handleCancel() {
      this.visible = false;
  }

  batchHandleCancel() {
      this.batchVisible = false;
  }


  saveSimilarWord() {
      // 是否存在相同的 code
      let isEquCode = false;
      this.similarWordDataLength ++ ;

      for (const item of this.mapOfSimilarWordDataSum) {
          // 校验是否存在相同 的 code
          if ( item.code === this.similarWordForm.code && item.id !== this.similarWordForm.id) {
              isEquCode = true;
          }
      }

      if (!isEquCode) {
          // 新增
          if (this.similarWordForm.id === 0) {
              const addForm = {
                  id: 0,
                  code: '',
                  comment: '',
                  checked: false
              };
              this.similarWordDataLength ++;
              addForm.id = this.similarWordDataLength;
              addForm.code = this.similarWordForm.code;
              addForm.comment = this.similarWordForm.comment;
              addForm.checked = false;
              this.mapOfSimilarWordDataSum.push(addForm);
              this.count++;
              this.totalPages++;
          } else {
              // 修改
              for (const item of this.mapOfSimilarWordDataSum) {
                  if ( item.id === this.similarWordForm.id) {
                      item.code = this.similarWordForm.code;
                      item.comment = this.similarWordForm.comment;
                      item.checked = this.similarWordForm.checked;
                  }
              }
          }
          this.visible = false;
      } else {
          this.messageService.info('词语不能相等');
      }

      this.load();
  }


   /**
   * 必填校验
   */
  formRequired (): boolean {
     if (!this.dataStandardSimilarWord.word) {
        this.messageService.warning('词语不能为空');
        return false;
     } else if (this.mapOfSimilarWordDataSum.length === 0) {
        this.messageService.warning('同义词不能为空');
        return false;
    }
     return true;

  }


  /**
   * 提交表单
   */
  submit() {
        if (!this.formRequired()) {
            return;
        }
      this.dataStandardSimilarWord.similarWords = {};
      for (const item of this.mapOfSimilarWordDataSum) {
          this.dataStandardSimilarWord.similarWords[item.code] = item.comment ;
      }

     // 为空默认选第一个
     if (!this.dataStandardSimilarWord.cateId) {
        this.dataStandardSimilarWord.cateId = this.cateNodes[0].id;
      }

      // 修改
      if (this.dataStandardSimilarWord.id) {
           // tslint:disable-next-line: radix
           this.dataStandardSimilarWordService.update(this.dataStandardSimilarWord.id  , this.dataStandardSimilarWord).subscribe(data => {
               if (data) {
                  this.messageService.success('保存成功');
               }
           } );
      } else {
          this.dataStandardSimilarWordService.add(this.dataStandardSimilarWord).subscribe(data => {
              if (data) {
                  this.messageService.success('保存成功');
                  this.dataStandardSimilarWord.id = data.id;
              }
          });
      }

  }

}
