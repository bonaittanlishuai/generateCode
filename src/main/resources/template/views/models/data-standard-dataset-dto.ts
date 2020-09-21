import { MtCategoryTree } from '@mt-datagov-ng/common';
import { AbstractBaseEntity } from '@mt-framework-ng/core';
import {  DataStandardDatasetDataitemDto } from './data-standard-dataset-dataitem-dto';


export interface DataStandardDatasetDto extends AbstractBaseEntity {



    code: string ;


    datasetName: string ;


    category?: MtCategoryTree;



    cateId: string;
    /**
     * 数据源
     */

    datasSource: string ;



    tableName: string;


    comment?: string;
    /**
     * 数据集运行状态
     */
    status: boolean;


    dataitems?: Array<DataStandardDatasetDataitemDto>;


}


