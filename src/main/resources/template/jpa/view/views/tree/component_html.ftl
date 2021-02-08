<as-split direction="horizontal" unit="pixel" [useTransition]="true"
  (gutterClick)="leftSize = leftSize == 0 ? 350 : 0;">
  <as-split-area [size]="leftSize" maxSize="600">
    <app-insight-category-tree [treeOption]="treeOption" [load]="false"></app-insight-category-tree> 
  </as-split-area>
  <as-split-area [size]="'*'">
    <app-rule-list #ruleList></app-rule-list>
  </as-split-area>
</as-split>