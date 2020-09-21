import { Component, OnInit, ViewChild, Input, ChangeDetectorRef } from '@angular/core';
import { STComponent, STColumn, STChange } from '@delon/abc';
import { QueryOptions } from '@mt-framework-ng/core';
import { Router , ActivatedRoute } from '@angular/router';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';

import { DataStandardDatasetService } from '@mt-datagov-ng/standard/src/services';
import { CategoryTreeService } from '@mt-datagov-ng/common/src/services/tree/category-tree.service';
import { TreeUtil } from '@mt-datagov-ng/common';
import { NzMessageService } from 'ng-zorro-antd/message';
import { DataStandardDatasetDataitemDto, DataStandardDatasetDto } from '@mt-datagov-ng/standard/src/models';


@Component({
  selector: 'app-data-standard-dataset-edit-editor',
  templateUrl: './data-standard-dataset-edit.component.html',
  styleUrls: ['./data-standard-dataset-edit.component.less'],
})
export class DataStandardDatasetEditComponent implements OnInit {

  validateForm: FormGroup;
  controlArray: Array<{ index: number; show: boolean }> = [];


  dataset: DataStandardDatasetDto = {
      id: null,
      code : '' ,
      datasetName : '',
      cateId : '' ,
      datasSource : '' ,
      tableName : '' ,
      comment  :  '' ,
      status  : true ,
      dataitems : []
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

  dataItemDatas: Array<any> = [];

  dataItemDatasSum: Array<any> = [];



  dataItemDataForm: DataStandardDatasetDataitemDto = {
    id: '',

    orginFieldName: '' ,

    chinessName: '',

    fieldName: '',

    isMajorKey: false ,

    fieldType: '' ,

    typeFormat : ''  ,

    comment: '',

    checked: false,
  };

  stColumns: STColumn[] = [
      { title: '#', width: '3%' },
      { title: '字段名', width: '10%' },
      { title: '中文名', width: '10%'},
      { title: '字段类型', width: '10%'},
      { title: '描述', width: '50%'},
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
      private datasetDatasetService: DataStandardDatasetService,
      private categoryTreeService: CategoryTreeService,
      private messageService: NzMessageService,
  ) { }


  ngOnInit(): void {
      this.activeRoute.params.subscribe(params => {
          // 修改
          if (params && params.id) {
              this.datasetDatasetService.findById(params.id).subscribe(searchDataStandard => {
                  // tslint:disable-next-line: forin
                  for (const key in this.dataset) {
                      // tslint:disable-next-line: no-unused-expression
                      this.dataset[key] = searchDataStandard [key];
                  }
                  this.dataItemDatasSum = [...searchDataStandard.dataitems];
                  this.load();
              });

          // 新增
          } else {
                  this.datasetDatasetService.getCode().subscribe(data => {
                      this.dataset.code = data;
                  });
                  this.load();
          }
      });
      this.initForm(this.dataset ? this.dataset : null);
      this.loadCateTree();
  }


  initForm(dataset?: any) {
      this.validateForm = this.fb.group({
        code: [dataset ? dataset.code : null],
        datasetName: [dataset ? dataset.datasetName : null],
        cateId: [dataset ? dataset.cateId : null],
        datasSource: [dataset ? dataset.datasSource : null],
        comment: [dataset ? dataset.comment : null],
        status: [dataset ? dataset.status : null],
      });
      if (this.validateForm) {
        this.validateForm.updateValueAndValidity();
      }
    }





    /** 加载目录树 */
  loadCateTree() {
      this.categoryTreeService.get('DATA_SET').subscribe(resp => {
          const items = resp;
          this.cateNodes = TreeUtil.populateTreeNodes(items, 'name', 'id', 'children');
      });
  }

  /**
   * 加载
   */
  load() {
        this.loading = true;
        this.dataItemDatas = [];
        // 分页数据处理
        if (this.dataItemDatasSum.length > this.pageSize ) {
            let pageLegth ;
            if (this.dataItemDatasSum.length >= this.pageSize * this.pageIndex) {
                pageLegth = this.pageSize * this.pageIndex;
            } else {
                pageLegth = this.dataItemDatasSum.length;
            }
            // tslint:disable-next-line: no-unused-expression
            for ( let i = this.pageSize * (this.pageIndex - 1); i < pageLegth ; i++){
                this.dataItemDatas.push(this.dataItemDatasSum[i]);
            }
            this.dataItemDatas = [...this.dataItemDatas];
         } else {
            this.dataItemDatas = [...this.dataItemDatasSum];
         }
        // 整体赋值
        this.count = this.dataItemDatas.length;
        this.totalPages = this.dataItemDatasSum.length;
        // 刷新数据
        this.dataItemDatas = [...this.dataItemDatas];
        this.dataItemDatasSum = [...this.dataItemDatasSum];
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
      return this.dataItemDatas.every(item => item.checked);
  }





  /**
   * @param rowData  修改参数
   * 新增校验规则
   */
  openDataItem(rowData?: DataStandardDatasetDataitemDto) {

      this.visible = true;
      if (rowData) {
          this.dataItemDataForm.id = rowData.id;
          this.dataItemDataForm.orginFieldName = rowData.orginFieldName;
          this.dataItemDataForm.chinessName = rowData.chinessName;
          this.dataItemDataForm.isMajorKey = rowData.isMajorKey;
          this.dataItemDataForm.fieldType = rowData.fieldType;
          this.dataItemDataForm.typeFormat = rowData.typeFormat;
          this.dataItemDataForm.comment = rowData.comment;
      } else {
            this.dataItemDataForm.orginFieldName = rowData.orginFieldName;
            this.dataItemDataForm.chinessName = rowData.chinessName;
            this.dataItemDataForm.isMajorKey = rowData.isMajorKey;
            this.dataItemDataForm.fieldType = rowData.fieldType;
            this.dataItemDataForm.typeFormat = rowData.typeFormat;
            this.dataItemDataForm.comment = rowData.comment;
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
          this.dataItemDatas.forEach(item => item.checked = value);
      }
      this.cdr.detectChanges();
  }

  /**
   * 批量删除 规则
   */
  batchDeleteDataItem() {
      const deleteData = [];
      this.dataItemDatasSum.forEach(item => {
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

  deleteDataItem(rowData: DataStandardDatasetDataitemDto) {
      this.sigletonDelete(rowData);
      this.load();
  }

  sigletonDelete(rowData: DataStandardDatasetDataitemDto) {
      const index = this.dataItemDatasSum.indexOf(rowData);
      this.dataItemDatasSum.splice(index, 1);
  }

  handleCancel() {
      this.visible = false;
  }

  batchHandleCancel() {
      this.batchVisible = false;
  }


  saveDataItem() {
        // 新增
        if (this.dataItemDataForm.id === '') {
            const addDataItemDataForm = {
            id: '',
            orginFieldName: '' ,
            chinessName: '',
            fieldName: '',
            isMajorKey: false ,
            fieldType: '' ,
            typeFormat : ''  ,
            comment: '',
            checked: false,
            };
            addDataItemDataForm.orginFieldName = this.dataItemDataForm.orginFieldName;
            addDataItemDataForm.chinessName = this.dataItemDataForm.chinessName;
            addDataItemDataForm.fieldName = this.dataItemDataForm.fieldName;
            addDataItemDataForm.isMajorKey = this.dataItemDataForm.isMajorKey;
            addDataItemDataForm.fieldType = this.dataItemDataForm.fieldType;
            addDataItemDataForm.typeFormat = this.dataItemDataForm.typeFormat;
            addDataItemDataForm.comment = this.dataItemDataForm.comment;
            this.dataItemDatasSum.push(addDataItemDataForm);
        } else {
            // 修改
            for (const item of this.dataItemDatasSum) {
                if ( item.id === this.dataItemDataForm.id) {
                item.orginFieldName = this.dataItemDataForm.orginFieldName;
                item.chinessName = this.dataItemDataForm.chinessName;
                item.fieldName = this.dataItemDataForm.fieldName;
                item.isMajorKey = this.dataItemDataForm.isMajorKey;
                item.fieldType = this.dataItemDataForm.fieldType;
                item.typeFormat = this.dataItemDataForm.typeFormat;
                item.comment = this.dataItemDataForm.comment;
                }
            }
        }
        this.visible = false;
        this.load();
  }



  /**
   * 提交表单
   */
  submit() {
      this.dataset.dataitems = [];
      this.dataset.dataitems = [...this.dataItemDatasSum];

      if (this.dataset.id) {
           // tslint:disable-next-line: radix
           this.datasetDatasetService.update(this.dataset.id  , this.dataset).subscribe(data => {
               if (data) {
                  this.messageService.success('保存成功');
               }
           } );
      } else {
          this.datasetDatasetService.add(this.dataset).subscribe(data => {
              if (data) {
                  this.messageService.success('保存成功');
                  this.dataset.id = data.id;
              }
          });
      }

  }



}
