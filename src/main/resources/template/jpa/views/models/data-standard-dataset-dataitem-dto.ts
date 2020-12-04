import { AbstractBaseEntity } from '@mt-framework-ng/core';
import { DataStandardDatasetDto  } from './data-standard-dataset-dto';
import { FieldType } from './enums/field-type';

export interface DataStandardDatasetDataitemDto extends AbstractBaseEntity {



    dataset?: DataStandardDatasetDto  ;

    orginFieldName: string  ;

    chinessName?: string  ;

    fieldName?: string ;

    isMajorKey: boolean ;

    fieldType: string ;


    typeFormat?: string  ;

    comment?: string ;

     // 是否选中
     checked: boolean;
}

