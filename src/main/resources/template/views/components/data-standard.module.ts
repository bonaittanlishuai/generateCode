import { NgModule } from '@angular/core';
import { SharedModule } from '@mt-framework-ng/view';
import { DataStandardListComponent } from './data-standard/data-standard-list.component';
import { DataStandardSimilarWordListComponent } from './data-stnadard-similar-word/data-standard-similar-word-list.component';
import { DataStandardDatasetListComponent } from './data-stnadard-dataset/data-standard-dataset-list.component';




const COMPONENTS = [
    DataStandardListComponent,
    DataStandardSimilarWordListComponent,
    DataStandardDatasetListComponent,
];

const MODULE = [
    SharedModule,
];

@NgModule({
    // 从其他地方导入的模块
    imports: [...MODULE],
    // 本模块包含的组件
    declarations: [...COMPONENTS],
    // 本模块导出的组件
    exports: [...COMPONENTS]
})
export class DataStandardComponentModule { }
