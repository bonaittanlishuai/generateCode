import { Component, OnInit, ViewChild } from '@angular/core';
import { NzTreeNode } from 'ng-zorro-antd';
import { TreeOption,PubSubService , CategoryTreeService } from '@mt-insight-ng/insight';
import { RuleListComponent } from '../rule-list/rule-list.component';
import { UUID , TreeUtil } from '@mt-framework-ng/util';
import { AbstractBaseEntity } from '@mt-framework-ng/core';

@Component({
  selector: 'app-rule-tree',
  templateUrl: './rule-tree.component.html',
  styleUrls: ['./rule-tree.component.less']
})
export class RuleTreeComponent implements OnInit {

  @ViewChild('ruleList', { static: false })
  ruleList: RuleListComponent;

  /**组装树 */
  nodes = [];
  /** 左边宽度 */
  leftSize = 350;
  treeId = UUID();
  category: any;
  treeOption: TreeOption = {
    
    /** 目录树类型（报表管理） */
    treeCodeType: 'DISPATCH_RULE',
    /** 树节点里是否包含业务对象数据 */
    hasBusinessObject: false,
    /** 是否开启“搜索”功能 */
    isShowSearch: true,
    /** 是否开启“我的收藏”功能  */
    isShowFavorte: false,
    /** 是否开启非目录节点拖拽功能 */
    isDraggable: false,
    /** 树节点单击事件 */
    click: (node: NzTreeNode) => {
      this.category = node && <any>node.origin;
      if (this.ruleList) {
        this.ruleList.category = this.category;
        this.ruleList.queryOption.page = 0;
        this.ruleList.queryOption.categoryCode =this.ruleList.category.code;
        this.ruleList.load();
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
  expandChange: param => {
    this.loadResource(param.node);
  },
  /** 加载最顶层非目录数据 */
  loadNoCategoryOfTop: param => {
    this.loadResource();
  },
  convertNode: node => {
    return this.convertReportNodes([node])[0];
  },

  operations: [{
    name: '新建规则',
    icon: 'control',
    fn: (isVisible: boolean, id?: string) => {
      this.ruleList.category = this.category;
      this.ruleList.create();
    }
  }],

  /** 下拉菜单操作选项 */
  catOperations: [{
    name: '新建规则',
    icon: 'control',
    fn: (isVisible: boolean, id?: string) => {
      this.ruleList.category = this.category;
      this.ruleList.create();
    }
  }],
};
  constructor(
    private pubsub: PubSubService,
    private categoryTreeService: CategoryTreeService,
    ) { }

    ngOnInit() {
      this.categoryTreeService.get('DISPATCH_RULE').subscribe(data => {
        if (data && data.length) {
          this.nodes = TreeUtil.populateTreeNodes(data, 'id', 'name', 'children');
          if (this.ruleList) {
            this.ruleList.category = data[0];
            this.ruleList.queryOption.page = 0;
            this.ruleList.load();
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


  convertReportNodes(nodes: AbstractBaseEntity[]) {
    return TreeUtil.populateTreeNodes(nodes, 'id', 'name');
  }
}
