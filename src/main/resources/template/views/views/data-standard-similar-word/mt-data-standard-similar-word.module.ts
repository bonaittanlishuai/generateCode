import { NgModule } from '@angular/core';
import { SharedModule } from '@mt-framework-ng/view';
import { CategoryTreeModule } from '../../../../common/src/views/index';
import { DataStandardSimilarWordComponent } from './data-standard-similar-word.component';
import { DataStandardComponentModule} from '../../components/data-standard.module';
import { DataStandardSimilarWordEditComponentModule } from './edit/data-standard-similar-word-edit.module';




const COMPONENTS = [
    DataStandardSimilarWordComponent,
];

const MODULE = [
    SharedModule,
    CategoryTreeModule,
    DataStandardComponentModule,
    DataStandardSimilarWordEditComponentModule
];

@NgModule({
    // 从其他地方导入的模块
    imports: [...MODULE],
    // 本模块包含的组件
    declarations: [...COMPONENTS],
    // 本模块导出的组件
    exports: [...COMPONENTS]
})
export class MtDataStandardSimilarWordModule { }
