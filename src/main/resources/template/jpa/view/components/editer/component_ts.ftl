import { Component, OnInit, ChangeDetectorRef, SimpleChanges, OnChanges, Input, Output, EventEmitter, ViewChild ,AfterViewInit} from '@angular/core';
import { NzMessageService, NzTreeNodeOptions } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import {  SystemInfoService } from '../../../services/index';
import {  SystemInfoDTO } from '../../../models/index';
import { DictCacheService } from '@mt-insight-ng/insight';
import { DictionaryValueDTO} from '@mt-framework-ng/view/src/models/index';


@Component({
  selector: 'app-system-editer',
  templateUrl: './system-editer.component.html',
  styleUrls: ['./system-editer.component.less']
})
export class SystemEditerComponent implements OnInit, OnChanges{

  visible = false;

  @Input()
  system: SystemInfoDTO = {};

  executeTypeList = [];

  _datasourceType: Array<DictionaryValueDTO>;

  @Output()
  onSystemListReload = new EventEmitter<boolean>();

  constructor(
    private systemService: SystemInfoService,
    private msg: NzMessageService,
    public http: _HttpClient,
    private cdr: ChangeDetectorRef,
    private dictCacheService: DictCacheService
  ) { }

  ngOnInit() {
    this.initView();
  }


  /**
   * 监听参数修改
   * @param changes 修改参数集合
   */
  ngOnChanges(changes: SimpleChanges): void {
    if (changes.id) {
      this.initView();
      this.cdr.detectChanges();
    }
  }

  /**
   * 初始化
   */
  initView() {
    // 字段数据类型字典
    this.dictCacheService.getDictByCode("DISPATCH_DATASOURCE_TYPE").subscribe(data => {
      this._datasourceType = data.list;
    });
  }

  save() {
      if(!this.requiredCheck()){
        return;
      }
      if(this.system.id) {
          this.systemService.update(this.system.id,this.system).subscribe(data => {
              this.system ={};
              this.handleCancel(false);
              this.onSystemListReload.emit;
          });
      }else  {
        this.systemService.create(this.system).subscribe(data => {
          this.system ={};
          this.handleCancel(false);
          this.onSystemListReload.emit;
        });
      }
  }


  requiredCheck(): boolean{
    if(!this.system.name) {
      this.msg.warning(this.empty('名称'));
      return false;
    }

    return true;
  }

  empty(field: string): string {
      return field + '是必填项';
  }



  /**
  * 修改窗口状态
  * @param status 状态
  */
  handleCancel(status: boolean): void {
    this.visible = status;
  }
 
}
