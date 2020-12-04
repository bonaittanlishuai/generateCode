import { Injectable } from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { Observable } from 'rxjs';
import { ApiPagedData, QueryOptions } from '@mt-framework-ng/core';
import {  DataStandardDatasetDto } from '../models/data-standard-dataset-dto';

@Injectable({
    providedIn: 'root'
  })
export class DataStandardDatasetService {

    private URL = '/data/standard/dataset/';

    constructor(public http: _HttpClient) {

    }

    // 分页查询同义词
    findPage(queryOptions: QueryOptions, categoryCode?: string, code?: string, name?: string
        ): Observable<ApiPagedData<DataStandardDatasetDto>> {
        const params = {};
        Object.assign(
            params,
            queryOptions,
            { categoryCode: categoryCode },
            code ? { code: code } : {},
            name ? { name: name } : {}
        );
        return this.http.get<ApiPagedData<DataStandardDatasetDto>>(this.URL, params);
    }

    /**
     *
     * @param id 逻辑删除
     */
    logicDeleteById(id: string): Observable<boolean> {

        return this.http.delete(this.URL + id);
    }

    /**
     *
     * @param ids 批量删除s
     */
    batchDeleteByIds(ids: Array<string>): Observable<boolean> {
        return this.http.post(this.URL + 'batchDelete' , {ids: ids});
    }

    /**
     *
     * @param dataStandardSimilarWordDto 添加同义词
     */
    add(dataStandardDatasetDto: DataStandardDatasetDto ): Observable<DataStandardDatasetDto> {
        return this.http.post(this.URL, dataStandardDatasetDto);
    }

    /**
     *
     * @param dataStandardSimilarWordDto 修改同义词
     */
    update(id: string, dataStandardDatasetDto: DataStandardDatasetDto ): Observable<DataStandardDatasetDto> {
        return this.http.post(this.URL + id, dataStandardDatasetDto);
    }


    /**
     *@param id 根据id 获取单个实体
     *
     */
    findById(id: string): Observable<DataStandardDatasetDto> {
        return this.http.get(this.URL + id);
    }

     /**
     *@param code 对code 进行累加 code 为空则获取最大+1
     *
     */
    getCode(code?: string): Observable<string> {
        const params = {code: code};
        Object.assign(params, code ? { code: code } : {});
        return this.http.get(this.URL + 'getCode' , params);
    }

}
