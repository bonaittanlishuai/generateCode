import { NgModule } from '@angular/core';


import { SharedModule } from '@mt-framework-ng/view';

import { DataStandardSimilarWordEditComponent } from './data-standard-similar-word-edit.component';



const COMPONENTS = [
    DataStandardSimilarWordEditComponent
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
export class DataStandardSimilarWordEditComponentModule { }
