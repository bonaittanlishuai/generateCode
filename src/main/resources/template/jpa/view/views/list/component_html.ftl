<as-split direction="vertical" unit="pixel" [disabled]="true" gutterSize="0">
  <!-- 工具栏 -->
<as-split-area class="flex-fixed" [size]="40">
    <div class="matech-toolbar">

      <div nz-form>
        <nz-form-item nzFlex="true" nz-row nzGutter="16">
          <nz-form-label nz-col >
             名称:
          </nz-form-label>
          <nz-form-control nz-col nzSpan="4">
            <input nz-input maxlength="200" [(ngModel)]="name" [ngModelOptions]="{standalone: true}"
              type="text">
          </nz-form-control>
          
          <nz-form-control nz-col nzSpan="2">
            <button nz-button (click)="search()">
              <i nz-icon type="search" theme="outline"></i>查找
            </button>
          </nz-form-control>
          <nz-form-control nz-col >
              <button nz-button [nzType]="'primary'" (click)="create()">
                新增系统信息
              </button>
            </nz-form-control>
          <nz-form-control nz-col nzSpan="2">
            <button nz-button [nzType]="'primary'" (click)="reflash()">
              立即刷新
            </button>
          </nz-form-control>
         
        </nz-form-item>
      </div>
    </div> 
</as-split-area>
    <as-split-area [size]="'*'">
   <nz-table nzSize="small" [nzLoading]="loading" [nzData]="mapOfExpandedData" nzFrontPagination="false" nzBordered
      nzShowSizeChanger [nzScroll]="{y:'100%'}" [nzTotal]="count" [(nzPageIndex)]="pageIndex" [(nzPageSize)]="pageSize"
      (nzPageIndexChange)="pageIndexChange($event)" (nzPageSizeChange)="pageSizeChange($event)"
      [nzShowTotal]="totalTemplate" [nzPageSizeOptions]="pageSizeOptions">
      <ng-template #totalTemplate let-total> 共 {{ count }} 条 </ng-template>
      <thead>
        <tr>
          <th nzShowCheckbox [nzChecked]="isAllDisplayDataChecked" (nzCheckedChange)="checkAll($event)"
            [nzWidth]="'60px'"></th>
          <th *ngFor="let t of stColumns" [nzWidth]="t.width" [class]="t.className">{{t.title}}</th>
        </tr>
      </thead>
      <tbody>
        <ng-container *ngFor="let item of mapOfExpandedData;index as i">
          <tr>
            <td nzShowCheckbox [nzChecked]="item.checked" (nzCheckedChange)="checkAll($event, item)"></td>
            <td><div> {{ (pageIndex-1) * pageSize + i + 1 }}</div></td>
            <td>{{item.name}}</td>
            <td>{{item.address}}</td>
            <td>{{item.user }}</td>
            <td>{{item.datasourceType_decode}}</td>
            <td> {{item.datasourceAddress}}</td>
            <td> {{item.datasourcePort}}</td>
            <td>{{item.datasourceUser}}</td>
            <td>{{item.status_decode}}</td>
            <td>
              <a (click)="edit(item)">编辑</a>
              <nz-divider nzType="vertical"></nz-divider>
              <a nz-dropdown nzPlacement="bottomRight" [nzDropdownMenu]="menuTpl">更多<i nz-icon type="down"></i></a>
              <nz-dropdown-menu #menuTpl="nzDropdownMenu">
                <ul nz-menu>
                  <li> 
                    <nz-popconfirm [nzTitle]="confirmtittle" (nzOnConfirm)="delete(item)">
                      <ng-template #confirmtittle>
                        是否确定删除?
                      </ng-template>
                      <a nz-popconfirm nz-menu-item style="width: 100px">删除</a>
                    </nz-popconfirm>
                  </li>
                </ul>
              </nz-dropdown-menu>
            </td>
          </tr>
        </ng-container>
      </tbody>
    </nz-table>
  </as-split-area>
</as-split>

<app-system-editer #systemEdit (onSystemListReload)="reflash()">

</app-system-editer>






