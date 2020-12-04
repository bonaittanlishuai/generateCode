import { NgModule } from '@angular/core';
import { SharedModule } from '@mt-framework-ng/view';
import { CategoryTreeModule } from '../../../../common/src/views/index';
import { DataStandardDatasetComponent } from './data-standard-dataset.component';
import { DataStandardComponentModule} from '../../components/data-standard.module';
import { DataStandardDatasetEditComponentModule } from './edit/data-standard-dataset-edit.module';




const COMPONENTS = [
    DataStandardDatasetComponent
];

const MODULE = [
    SharedModule,
    CategoryTreeModule,
    DataStandardComponentModule,
    DataStandardDatasetEditComponentModule
];

@NgModule({
    // 从其他地方导入的模块
    imports: [...MODULE],
    // 本模块包含的组件
    declarations: [...COMPONENTS],
    // 本模块导出的组件
    exports: [...COMPONENTS]
})
export class MtDataStandardDatasetModule { }
