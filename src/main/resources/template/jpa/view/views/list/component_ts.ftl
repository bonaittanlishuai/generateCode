import { SystemInfoDTO } from './../../../models/system-info-dto';
import { SystemEditerComponent } from './../system-editer/system-editer.component';
import { Component, OnInit, ViewChild, Input, ChangeDetectorRef } from '@angular/core';
import { STComponent, STColumn, STChange } from '@delon/abc';
import { QueryOptions } from '@mt-framework-ng/core';
import { Router , ActivatedRoute } from '@angular/router';
import { NzMessageService ,NzTreeNode} from 'ng-zorro-antd';
import { PubSubService , DictCacheService } from '@mt-datagov-ng/common/index';
import { SystemInfoService } from '../../../services/index';
import { RuleFindParamDTO } from '../../../models/index';

@Component({
    selector: 'app-system-list',
    templateUrl: './system-list.component.html',
    styles: [ `
    :host ::ng-deep .matech-toolbar .ant-form-item  {
       justify-content: flex-start;
       flex-wrap: wrap;
     } `]
})
export class SystemListComponent implements OnInit {



    @ViewChild('systemEdit',{static: false})
    systemEdit: SystemEditerComponent

    loading = false;

  
    /**
    * 分页，排序参数
    */
    @Input()
    queryOption: QueryOptions = {
        page: 0,
        size: 20,
        sort: 'id,desc',
    };

    name: string ;

    mapOfExpandedData = [];

    stColumns: STColumn[] = [
        { title: '#', width: '3%' },
        { title: '名称', width: '10%',className:'text-center' },
        { title: '系统地址', width: '15%',className:'text-center'},
        { title: '系统用户', width: '10%',className:'text-center'},
        { title: '数据源类型', width: '10%',className:'text-center' },
        { title: '数据源地址', width: '15%',className:'text-center' },
        { title: '数据源端口', width: '10%',className:'text-center' },
        { title: '数据源用户', width: '10%',className:'text-center' },
        { title: '状态', width: '10%',className:'text-center' },
        { title: '操作', width: '7%',className:'text-center' },
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
        private msg: NzMessageService,
        private router: Router,
        private pubsub: PubSubService,
        private dictCacheService: DictCacheService,
        private cdr: ChangeDetectorRef,
        private sysService: SystemInfoService,
    ) { }

    ngOnInit() {
        this.load();
    }

    /**
     * 加载
     */
    load() {
        this.loading = true;
        this.sysService.findOnePage(this.queryOption,this.name).subscribe(data => {
                if (data) {
                    this.mapOfExpandedData = [];
                    data.data.forEach(d => {
                        this.mapOfExpandedData = [...this.mapOfExpandedData, d
                        ];
                    });
                    this.count = data.totalRecords;
                    this.totalPages = data.totalPages;
                }
                this.loading = false;
            });
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
     * 按条件查找
     */
    search() {
        this.load();
    }

    /**
     * 刷新列表
     */
    reflash() {
        this.load();
    }

    /**
     * 创建系统信息
     */
    create() {
        this.systemEdit.visible =true;
        this.systemEdit.system = {};
    }


    /**
     * 编辑系统信息
     */
    edit(item: SystemInfoDTO) {
        this.systemEdit.visible =true;
        this.systemEdit.system = item;
    }

    /**
     * 删除数据标准
     */
    delete(item) {
        this.sysService.delete(item.id).subscribe(data => {
            this.mapOfExpandedData = this.mapOfExpandedData.filter(e => e.id !== item.id);
            this.load();
        });
    }




    /**
     * 判断是否全选中
     */
    get isAllDisplayDataChecked() {
        return this.mapOfExpandedData.every(item => item.checked);
    }

    /**
     * 点击多选框触发事件
     */
    checkAll(value: boolean, row?: any): void {
        if (row) {
            row.checked = value;
        } else {
            this.mapOfExpandedData.forEach(item => item.checked = value);
        }
        // this.batchObj.rules = this.mapOfExpandedData.filter(item => item.checked);
        this.cdr.detectChanges();
    }

   
}
