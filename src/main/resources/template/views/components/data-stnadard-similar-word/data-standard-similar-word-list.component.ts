import { Component, OnInit, ViewChild, Input, ChangeDetectorRef } from '@angular/core';
import { STComponent, STColumn, STChange } from '@delon/abc';
import { QueryOptions } from '@mt-framework-ng/core';
import { Router , ActivatedRoute } from '@angular/router';
import { DataStandardDto } from '../../models/index';
import { NzMessageService } from 'ng-zorro-antd';
import { DataStandardSimilarWordService } from '../../services/index';
import { PubSubService , DictCacheService } from '../../../../common/src/services/index';

@Component({
    selector: 'app-data-standard-similar-word-list',
    templateUrl: './data-standard-similar-word-list.component.html',
    styles: [ `
    :host ::ng-deep .matech-toolbar .ant-form-item  {
       justify-content: flex-start;
       flex-wrap: wrap;
     } `]
})
export class DataStandardSimilarWordListComponent implements OnInit {




    isRequire = false;
    isDayRequire = false;
    loading = false;

    /**
     * 目录参数
     */
    category: any;
    code = null;
    word = null;


    /**
    * 分页，排序参数
    */
    @Input()
    queryOption: QueryOptions = {
        page: 0,
        size: 20,
        sort: 'id,desc',
    };

    mapOfExpandedData = [];

    stColumns: STColumn[] = [
        { title: '#', width: '3%' },
        { title: '编码', width: '16%' },
        { title: '词语', width: '10%'},
        { title: '描述', width: '8%'},
        { title: '同义词', width: '18%'},
        { title: '创建时间', width: '14%' },
        { title: '修改时间', width: '14%' },
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
        private msg: NzMessageService,
        private router: Router,
        private pubsub: PubSubService,
        private dictCacheService: DictCacheService,
        private dataStandardSimilarWordService: DataStandardSimilarWordService,
        private cdr: ChangeDetectorRef,
        private activeRoute: ActivatedRoute,
    ) { }

    ngOnInit() {
        this.category['code'] = 'DATA_STANDARD_SIMILAR_WORD';
        this.load();
    }

    /**
     * 加载
     */
    load() {
        this.loading = true;
        this.dataStandardSimilarWordService.findPage(
            this.queryOption, this.category && this.category.code,
            this.code, this.word ).subscribe(data => {
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
     * 创建数据标准
     */
    create() {
        this.router.navigate(['../similarWordEdit'], {
            queryParams: { id: null, category: this.category },
            relativeTo: this.activeRoute
        });
    }


    /**
     * 编辑数据标准
     */
    edit(item) {
        this.router.navigate([`../similarWordEdit/${item.id}`], {
                relativeTo: this.activeRoute
            });
    }

    /**
     * 删除数据标准
     */
    delete(item) {
        this.dataStandardSimilarWordService.logicDeleteById(item.id).subscribe(data => {
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
