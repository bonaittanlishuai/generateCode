import { NgModule } from '@angular/core';
import { SharedModule } from '@mt-framework-ng/view';
import { CategoryTreeModule } from '../../../../common/src/views/index';
import { DataStandardComponent } from './data-standard.component';
import { DataStandardComponentModule} from '../../components/data-standard.module';
import { DataStandardEditComponentModule } from './edit/data-standard-edit.module';






const COMPONENTS = [
    DataStandardComponent
];

const MODULE = [
    SharedModule,
    CategoryTreeModule,
    DataStandardComponentModule,
    DataStandardEditComponentModule,

];

@NgModule({
    // 从其他地方导入的模块
    imports: [...MODULE],
    // 本模块包含的组件
    declarations: [...COMPONENTS],
    // 本模块导出的组件
    exports: [...COMPONENTS]
})
export class MtDataStandardModule { }
