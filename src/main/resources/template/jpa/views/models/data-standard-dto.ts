import { MtCategoryTree } from './../../../common/src/models/tree/mt-category-tree';

import { AbstractBaseEntity, DegreeTypeEnum } from '@mt-framework-ng/core';
import { DataStandardSimilarWordDto } from './data-standard-similar-word-dto';
import { FieldType } from './enums/field-type';
import { RefStandard } from './enums/ref-standrd';


export interface DataStandardDto extends AbstractBaseEntity {

    code: string;

    nameIdentifter: string;

    name: string;

    fieldType: FieldType;

    typeFormat?: string;

    refStandard: RefStandard;

    category?: MtCategoryTree;

    cateId: string;

    comment?: string;

    similarWord?: DataStandardSimilarWordDto;

    validateRules: {};

}
