import { MtCategoryTree } from '@mt-datagov-ng/common';
import { AbstractBaseEntity } from '@mt-framework-ng/core';

/**
 * 同义词
 */
export interface DataStandardSimilarWordDto extends AbstractBaseEntity {


    code: string;

    category?: MtCategoryTree;

    cateId: string;

    word: string;

    comment: string ;

    /**
     * '词语_描述
     */
    similarWords: {} ;

}
