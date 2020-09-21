import { Component, OnInit, ViewChild } from '@angular/core';
import { NzTreeNode } from 'ng-zorro-antd';
import {  PubSubService, CategoryTreeService } from '../../../../common//src/services/index';
import { TreeUtil } from '../../../../common/src/utils/index';
import { TreeOption } from '../../../../common/src/models/index';
import { DataStandardDatasetListComponent } from '../../components/index';
import { UUID } from '@mt-framework-ng/util';

@Component({
  selector: 'app-data-standard-dataset',
  templateUrl: './data-standard-dataset.component.html',
  styleUrls: ['./data-standard-dataset.component.less']
})
export class DataStandardDatasetComponent implements OnInit {

  @ViewChild('dataStandardDatesetList', { static: false })
  dataStandardDatesetList: DataStandardDatasetListComponent;

  /**组装树 */
  nodes = [];
  /** 左边宽度 */
  leftSize = 350;
  treeId = UUID();
  category: any;
  treeOption: TreeOption = {
    /** 目录树类型（报表管理） */
    treeCodeType: 'DATA_SET',
    /** 是否开启非目录节点拖拽功能 */
    isDraggable: false,
    /** 树节点单击事件 */
    click: (node: NzTreeNode) => {
      this.category = node && <any>node.origin;
      if (this.dataStandardDatesetList) {
        this.dataStandardDatesetList.category = this.category;
        this.dataStandardDatesetList.queryOption.page = 0;
        this.dataStandardDatesetList.load();
      }
    },
    /** 树节点双击事件 */
    dblClick: (node: NzTreeNode) => {
      this.nzDbEvent(node);
    },
    /** 删除叶子节点（非目录节点） */
    deleteLeaf: (node: NzTreeNode) => {
      this.delNodeEvent(node);
    },
    /** 树节点展开事件 */
    expandChange: (node: NzTreeNode, categoryNodes?: NzTreeNode[]) => {
      this.loadResource(node);
    },
    /** 加载最顶层非目录数据 */
    loadNoCategoryOfTop: (category: NzTreeNode[]) => {
      this.loadResource();
    },

    operations: [{
      name: '新建数据标准',
      icon: 'control',
      fn: (isVisible: boolean, id?: string) => {
        this.dataStandardDatesetList.category = this.category;
        this.dataStandardDatesetList.create();
      }
    }],

    /** 下拉菜单操作选项 */
    catOperations: [{
      name: '新建数据标准',
      icon: 'control',
      fn: (isVisible: boolean, id?: string) => {
        this.dataStandardDatesetList.category = this.category;
        this.dataStandardDatesetList.create();
      }
    }],
  };
  constructor(
    private pubsub: PubSubService,
    private categoryTreeService: CategoryTreeService,
    ) { }

    ngOnInit() {
      this.categoryTreeService.get('DATA_SET').subscribe(data => {
        if (data && data.length) {
          // data[0]['nodeType'] = 'expand';
          this.nodes = TreeUtil.populateTreeNodes(data, 'name', 'id', 'children');
          if (this.dataStandardDatesetList) {
            this.dataStandardDatesetList.category = data[0];
            this.dataStandardDatesetList.queryOption.page = 0;
            this.dataStandardDatesetList.load();
          }
        }
      });
    }

    /** 加载目录节点下的资源列表 */
  loadResource(node?: NzTreeNode) {
    /** 如果存在目录，并且该目录未加载过，则加载目录下的数据 */
    // if (node && !node.origin.isLoaded && !node.isLeaf) {
    //      // 仅为通知树目录,当前节点已加载过,改变其loaded状态,不对节点做任何增删操作,有用,勿删!!!
    //       this.pubsub.publish(TreeEventNames.add + this.treeId, { parent: node, nodes: []});
    //     }
  }

   /**
   * 双击树节点展开
   */
  nzDbEvent(node: NzTreeNode) {
    node.isExpanded = !node.isExpanded;
    this.loadResource(node);
  }

  /** 删除叶子节点（非目录节点） */
  delNodeEvent(node: NzTreeNode) {
  }
}
