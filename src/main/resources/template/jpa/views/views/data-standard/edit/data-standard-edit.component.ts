import { Component, OnInit, ViewChild, Input, ChangeDetectorRef , ElementRef} from '@angular/core';
import { STComponent, STColumn, STChange } from '@delon/abc';
import { QueryOptions } from '@mt-framework-ng/core';
import { Router , ActivatedRoute } from '@angular/router';
import { FormGroup, FormBuilder, FormControl , Validators  } from '@angular/forms';
import { DataStandardSimilarWordService } from '../../../services/index';
import { DataStandardService } from '@mt-datagov-ng/standard/src/services';
import { CategoryTreeService } from '@mt-datagov-ng/common/src/services/tree/category-tree.service';
import { TreeUtil } from '@mt-datagov-ng/common';
import { NzMessageService } from 'ng-zorro-antd/message';




@Component({
  selector: 'app-data-standard-edit-editor',
  templateUrl: './data-standard-edit.component.html',
  styleUrls: ['./data-standard-edit.component.less'],
})
export class DataStandardEditComponent implements OnInit {

  
    similarWordObj: any;

  validateForm: FormGroup;
  controlArray: Array<{ index: number; show: boolean }> = [];
  /**
   * 参照标准
   */
  refStandards: Array<{lable: string, value: string }> = [
      {
          lable: '数据标准',
          value: 'DATA'
      },
      {
          lable: '数据规则',
          value: 'RULE'
      }
  ] ;

  fieldTypes: Array<{lable: string, value: string }> = [
      {
          lable: '字符类型',
          value: 'STRING'
      },
      {
          lable: '数字类型',
          value: 'INT'
      },
      {
          lable: '日期类型',
          value: 'DATE'
      }
  ] ;

  commonHidden = false;
  dateHidden = true;
  /**参考标准隐藏 */
  refStandardHidden = true;

  /**
   * 数据类型的提示值
   */
  typePlaceholder = '长度';

  dataStandard = {
      id: null,
      code : '' ,
      nameIdentifter : '',
      name : '' ,
      fieldType : '' ,
      typeFormat : '' ,
      refStandard : '' ,
      cateId  :  '' ,
      comment  : '' ,
      similarWord :  undefined,
      similarWordWord :  undefined,
      validateRules : {}
  } ;



  loading = false;

  /**校验规则窗口 */
  visible = false;
  /**批量删除 窗口 */
  batchVisible = false;

  /** 同义词窗口*/
  similarVisible = false;
  /**
   * 目录参数
   */
  category: any;
  code = null;
  name = null;

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

  mapOfValidaetRuleData: Array<{id: number , code: string , value: string , checked: boolean}> = [];

  mapOfValidaetRuleDataSum: Array<{id: number , code: string , value: string , checked: boolean}> = [];

  validaetRuleDataLength = 0;

  validateRuleTitle = '';

  /** */
  regValidateRule = '正则表达式校验';
  /**
   * 正则表达式
   */
  reg: string;

  validateRuleForm = {
      id: 0,
      code: '',
      value: '',
      checked: false
  };

  stColumns: STColumn[] = [
      { title: '#', width: '3%' },
      { title: '代码编号', width: '40%' },
      { title: '代码名称', width: '40%'},
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


  /** 同义词 */

  loadingSimilar = false;
   /**
     * 目录参数
     */
    categorySimilar: any;
    /**
    * 分页，排序参数
    */
    @Input()
    queryOptionSimilar: QueryOptions = {
        page: 0,
        size: 20,
        sort: 'id,desc',
    };

    mapOfExpandedDataSimilar = [];

    stColumnsSimilar: STColumn[] = [
        { title: '#', width: '2%' },
        { title: '标准名称', width: '10%' },
        { title: '标准类型', width: '10%'},
        { title: '数据类型', width: '10%'},
        { title: '描述', width: '30%'},
        { title: '创建时间', width: '14%' },
        { title: '修改时间', width: '15%' },
    ];

    /**
     * 列表参数
     */
    pageSizeOptionsSimilar = [10, 20, 30, 100];
    countSimilar: number;
    pageSizeSimilar = 20;
    pageIndexSimilar = 1;
    totalPagesSimilar: number;


  constructor(
      private router: Router,
      private cdr: ChangeDetectorRef,
      private activeRoute: ActivatedRoute,
      private fb: FormBuilder,
      private dataStandardService: DataStandardService,
      private categoryTreeService: CategoryTreeService,
      private messageService: NzMessageService,
      private dataStandardSimilarWordService: DataStandardSimilarWordService,
      private elementRef: ElementRef,

  ) { }


  ngOnInit(): void {
      this.activeRoute.params.subscribe(params => {
          // 修改
          if (params && params.id) {
              this.dataStandardService.findById(params.id).subscribe(searchDataStandard => {
                  // tslint:disable-next-line: forin
                  for (const key in this.dataStandard) {
                      // tslint:disable-next-line: no-unused-expression
                      this.dataStandard[key] = searchDataStandard [key];
                  }
                  if (this.dataStandard.similarWord) {
                    this.dataStandard.similarWordWord = this.dataStandard.similarWord.word;
                  }
                  // tslint:disable-next-line: forin
                  for (const key in this.dataStandard.validateRules) {
                      this.validaetRuleDataLength ++;
                      this.mapOfValidaetRuleDataSum.push({id: this.validaetRuleDataLength , code: key,
                          value: this.dataStandard.validateRules[key], checked: false } );
                  }

                  this.mapOfValidaetRuleDataSum = [...this.mapOfValidaetRuleDataSum];
                  this.load();
              });

          // 新增
          } else {
                  this.dataStandardService.getCode().subscribe(data => {
                      this.dataStandard.code = data;
                  });
                  this.dataStandard.fieldType = this.fieldTypes[0].value;
                  this.dataStandard.refStandard = this.refStandards[0].value;
                  this.load();
          }
      });
      this.initForm(this.dataStandard ? this.dataStandard : null);
      this.loadCateTree();
      this.refStandardOnChange(this.dataStandard.refStandard);
      // 同义词
      this.categorySimilar['code'] = 'DATA_STANDARD';
      this.loadSimilar();
  }


  initForm(dataStandard?: any) {
      this.validateForm = this.fb.group({
        code: [dataStandard ? dataStandard.code : null],
        nameIdentifter: [dataStandard ? dataStandard.nameIdentifter : null],
        name: [dataStandard ? dataStandard.name : null],
        fieldType: [dataStandard ? dataStandard.fieldType : null],
        typeFormat: [dataStandard ? dataStandard.typeFormat : null],
        refStandard: [dataStandard ? dataStandard.refStandard : null],
        cateId: [dataStandard ? dataStandard.cateId : null],
        similarWord: [dataStandard ? dataStandard.similarWord : ''],
        comment: [dataStandard ? dataStandard.comment : null],
      });
      if (this.validateForm) {
        this.validateForm.updateValueAndValidity();
      }
    }





    /** 加载目录树 */
  loadCateTree() {
      this.categoryTreeService.get('DATA_STANDARD').subscribe(resp => {
          const items = resp;
          this.cateNodes = TreeUtil.populateTreeNodes(items, 'name', 'id', 'children');
      });
  }

  /**
   * 加载
   */
  load() {
        this.loading = true;
        this.mapOfValidaetRuleData = [];
        // 分页数据处理
        if (this.mapOfValidaetRuleDataSum.length > this.pageSize ) {
            let pageLegth ;
            if (this.mapOfValidaetRuleDataSum.length >= this.pageSize * this.pageIndex) {
                pageLegth = this.pageSize * this.pageIndex;
            } else {
                pageLegth = this.mapOfValidaetRuleDataSum.length;
            }
            // tslint:disable-next-line: no-unused-expression
            for ( let i = this.pageSize * (this.pageIndex - 1); i < pageLegth ; i++){
                this.mapOfValidaetRuleData.push(this.mapOfValidaetRuleDataSum[i]);
            }
            this.mapOfValidaetRuleData = [...this.mapOfValidaetRuleData];
         } else {
            this.mapOfValidaetRuleData = [...this.mapOfValidaetRuleDataSum];
         }
        // 整体赋值
        this.count = this.mapOfValidaetRuleData.length;
        this.totalPages = this.mapOfValidaetRuleDataSum.length;
        // 刷新数据
        this.mapOfValidaetRuleData = [...this.mapOfValidaetRuleData];
        this.mapOfValidaetRuleDataSum = [...this.mapOfValidaetRuleDataSum];
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
      return this.mapOfValidaetRuleData.every(item => item.checked);
  }




  /**
   * 
   * @param value 数据类型下拉框选择值
   */
  typeOnChange(value: any) {
      if (value === this.fieldTypes[2].value) {
          this.dateHidden = false;
          this.commonHidden = true;
      } else {
          this.commonHidden = false;
          this.dateHidden = true;
          if (value === this.fieldTypes[0].value) {
              this.typePlaceholder = '长度';
          } else if (value === this.fieldTypes [1].value) {
              this.typePlaceholder = '大小' ;
          }
      }
  }

  /**
   * @param rowData  修改参数
   * 新增校验规则
   */
  openValidateRule(rowData?: {id: number, code: string , value: string , checked: boolean}) {
      this.visible = true;
      if (rowData) {
          this.validateRuleForm.id = rowData.id;
          this.validateRuleForm.code = rowData.code;
          this.validateRuleForm.value = rowData.value;
          this.validateRuleForm.checked = rowData.checked;
          this.validateRuleTitle = '修改标准代码';
      } else {
          this.validateRuleForm.id = 0 ;
          this.validateRuleForm.code = '';
          this.validateRuleForm.value = '';
          this.validateRuleForm.checked = false;
          this.validateRuleTitle = '添加标准代码';
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
          this.mapOfValidaetRuleData.forEach(item => item.checked = value);
      }
      this.cdr.detectChanges();
  }

  /**
   * 批量删除 规则
   */
  batchDeleteValidateRule() {
      const deleteData = [];
      this.mapOfValidaetRuleDataSum.forEach(item => {
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
      this.load();
      this.batchHandleCancel();
  }

  deleteValidateRule(rowData: {id: number, code: string , value: string , checked: boolean}) {
      this.sigletonDelete(rowData);
      this.load();
  }

  sigletonDelete(rowData: {id: number, code: string , value: string , checked: boolean}) {
      const index = this.mapOfValidaetRuleDataSum.indexOf(rowData);
      this.mapOfValidaetRuleDataSum.splice(index, 1);
  }

  handleCancel() {
      this.visible = false;
  }

  batchHandleCancel() {
      this.batchVisible = false;
  }


  saveValidateRule() {
      // 是否存在相同的 code
      let isEquCode = false;
      this.validaetRuleDataLength ++ ;

      for (const item of this.mapOfValidaetRuleDataSum) {
          // 校验是否存在相同 的 code
          if ( item.code === this.validateRuleForm.code && item.id !== this.validateRuleForm.id) {
              isEquCode = true;
          }
      }

      if (!isEquCode) {
          // 新增
          if (this.validateRuleForm.id === 0) {
              const addForm = {
                  id: 0,
                  code: '',
                  value: '',
                  checked: false
              };
              this.validaetRuleDataLength ++;
              addForm.id = this.validaetRuleDataLength;
              addForm.code = this.validateRuleForm.code;
              addForm.value = this.validateRuleForm.value;
              addForm.checked = false;
              this.mapOfValidaetRuleDataSum.push(addForm);
          } else {
              // 修改
              for (const item of this.mapOfValidaetRuleDataSum) {
                  if ( item.id === this.validateRuleForm.id) {
                      item.code = this.validateRuleForm.code;
                      item.value = this.validateRuleForm.value;
                      item.checked = this.validateRuleForm.checked;
                  }
              }
          }
          this.visible = false;
      } else {
          this.messageService.info('代码编号不能相等');
      }
      this.load();
  }


  refStandardOnChange (value: any) {
      if (value === this.refStandards[0].value) {
          this.refStandardHidden = true;
      } else if (value === this.refStandards [1] .value) {
          this.refStandardHidden = false;
      }
  }

  /**
   * 必填校验
   */
  formRequired (): boolean {
    console.log(this.cateNodes);
     if (!this.dataStandard.nameIdentifter) {
        this.messageService.warning('标准标识不能为空');
        return false;
     } else if (!this.dataStandard.name) {
        this.messageService.warning('标准名称不能为空');
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
      this.dataStandard.validateRules = {};

      if (this.dataStandard.refStandard === this.refStandards[0].value) {
          if (this.mapOfValidaetRuleDataSum.length === 0) {
              this.messageService.warning('配置信息不能为空');
              return;
          }
          for (const item of this.mapOfValidaetRuleDataSum) {
              this.dataStandard.validateRules[item.code] = item.value ;
          }
      } else if (this.dataStandard.refStandard === this.refStandards[1].value) {
          if (this.reg) {
              this.messageService.warning('配置信息不能为空');
              return;
          }
          this.dataStandard.validateRules[this.regValidateRule] = this.reg ;
      }
      // 为空默认选第一个
      if (!this.dataStandard.cateId) {
        this.dataStandard.cateId = this.cateNodes[0].id;
      }

      if (this.dataStandard.id) {
           // tslint:disable-next-line: radix
           this.dataStandardService.update(this.dataStandard.id  , this.dataStandard).subscribe(data => {
               if (data) {
                  this.messageService.success('保存成功');
               }
           } );
      } else {
          this.dataStandardService.add(this.dataStandard).subscribe(data => {
              if (data) {
                  this.messageService.success('保存成功');
                  this.dataStandard.id = data.id;
              }
          });
      }

  }

  /**  同义词 */


    /**
     * 加载
     */
    loadSimilar() {
        this.loadingSimilar = true;
        this.dataStandardSimilarWordService.findPage(
            this.queryOption, this.category && this.category.code).subscribe(data => {
                if (data) {
                    this.mapOfExpandedDataSimilar = [];
                    data.data.forEach(d => {
                        this.mapOfExpandedDataSimilar = [...this.mapOfExpandedDataSimilar, d
                        ];
                    });
                    this.count = data.totalRecords;
                    this.totalPages = data.totalPages;
                }
                this.loadingSimilar = false;
            });
    }
    /**
     * 页码改变的回调
     * @param $event 选中的页码
     */
    pageIndexChangeSimilar ($event) {
        this.queryOptionSimilar.page = $event - 1;
        this.pageIndexSimilar = $event;
        this.load();
    }
    /**
     * 每页条数改变的回调
     * @param $event 选中的条数
     */
    pageSizeChangeSimilar($event) {
        this.pageSizeSimilar = $event;
        this.queryOptionSimilar.size = $event;
        const size = this.countSimilar / $event;
        if (size === 1 || size < 1) {
            this.queryOptionSimilar.page = 0;
            this.pageIndexSimilar = 1;
        } else {
            if (this.pageIndexSimilar > Math.ceil(size)) {
                this.queryOptionSimilar.page = Math.ceil(size) - 1;
                this.pageIndexSimilar = Math.ceil(size);
            }
        }
        this.loadSimilar();
    }




    /**
     * 点击选择框触发事件 单选
     */
    checkSimilar(value: boolean, row?: any): void {
        if (row) {
            this.mapOfExpandedDataSimilar.forEach(item => item.checked = false);
            row.checked = value;
        }
        // this.batchObj.rules = this.mapOfExpandedData.filter(item => item.checked);
        this.cdr.detectChanges();
    }

    handleSimilarCancel() {
        this.similarVisible = false;
    }

    clickSimilar() {
        this.similarVisible = true;
        this.loadSimilar();
    }

    // 确定选择同义词
    selectSimilar() {
        let selectSimilar;
        this.mapOfExpandedDataSimilar.forEach(item => {
            if (item.checked) {
                selectSimilar = item;
            }
        });
        if (selectSimilar) {
            this.similarVisible = false;
            this.dataStandard.similarWord = selectSimilar;
            this.dataStandard.similarWordWord = selectSimilar.word;
        } else {
            this.similarVisible = true;
            this.messageService.warning('请选择同义词');
        }
    }

    // 同义词改变的时候 如果清空了值，则进行  similarWord 的清空
    similarChange() {
        console.log('---------');
        if (!this.dataStandard.similarWordWord) {
            this.dataStandard.similarWord = undefined;
        }
    }

}
