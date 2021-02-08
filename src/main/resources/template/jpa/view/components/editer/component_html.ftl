<nz-modal [(nzVisible)]="visible" [nzMaskClosable]="false" [nzTitle]="system.id?'修改系统信息':'新增系统信息'" (nzOnCancel)="handleCancel(false)"
  [nzFooter]="systemFooter" [nzContent]="systemContent" [nzWidth]="900">

  <ng-template #systemContent>
    <div nz-row style="height: 300px;overflow: auto;">
              <nz-form-item>
                  <nz-form-label [nzSpan]="2">名称</nz-form-label>
                  <nz-form-control [nzSpan]="10">
                      <input nz-input  placeholder="名称"  [(ngModel)]="system.name" />
                    </nz-form-control>
                    <nz-form-label [nzSpan]="2">系统地址</nz-form-label>
                  <nz-form-control [nzSpan]="10">
                      <input nz-input  placeholder="系统地址"  [(ngModel)]="system.address" />
                    </nz-form-control>
              </nz-form-item>
              <nz-form-item>
                <nz-form-label [nzSpan]="2">系统用户</nz-form-label>
                <nz-form-control [nzSpan]="10">
                    <input nz-input  placeholder="系统用户"  [(ngModel)]="system.user" />
                  </nz-form-control>
                  <nz-form-label [nzSpan]="2">系统密码</nz-form-label>
                <nz-form-control [nzSpan]="10">
                    <input nz-input  placeholder="系统密码"  [(ngModel)]="system.password" />
                  </nz-form-control>
              </nz-form-item>
              <nz-form-item>
                <nz-form-label [nzSpan]="2">数据源类型</nz-form-label>
                <nz-form-control [nzSpan]="10">
                    <nz-select  style="width: 100%;"
                    [(ngModel)]="system.datasourceType"
                    nzAllowClear
                    nzPlaceHolder="数据源类型">
                    <nz-option *ngFor="let option of _datasourceType" [nzValue]="option.key" [nzLabel]="option.value"></nz-option>
                   </nz-select>
                  </nz-form-control>
                  <nz-form-label [nzSpan]="2">数据源地址</nz-form-label>
                  <nz-form-control [nzSpan]="10">
                    <input nz-input  placeholder="数据源地址"  [(ngModel)]="system.datasourceAddress" />
                  </nz-form-control>
              </nz-form-item>
              
              <nz-form-item>
                <nz-form-label [nzSpan]="2">数据源端口</nz-form-label>
                <nz-form-control [nzSpan]="10">
                    <input nz-input  placeholder="数据源端口"  [(ngModel)]="system.datasourcePort" />
                  </nz-form-control>
                  <nz-form-label [nzSpan]="2">数据源用户</nz-form-label>
                <nz-form-control [nzSpan]="10">
                    <input nz-input  placeholder="数据源用户"  [(ngModel)]="system.datasourceUser" />
                  </nz-form-control>
              </nz-form-item>

              <nz-form-item>
                <nz-form-label [nzSpan]="2">数据源密码</nz-form-label>
                <nz-form-control [nzSpan]="10">
                    <input nz-input  placeholder="数据源密码"  [(ngModel)]="system.datasourcePassword" />
                </nz-form-control> 
              </nz-form-item>                            
    </div>
  </ng-template>

  <ng-template #systemFooter>
    <button nz-button nzType="default" (click)="handleCancel(false)">取消</button>
    <button nz-button nzType="primary" [nzLoading]="http.loading"
      (click)="save()">确定</button>
  </ng-template>
</nz-modal>