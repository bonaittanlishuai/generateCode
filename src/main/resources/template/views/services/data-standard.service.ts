import { Injectable } from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { Observable } from 'rxjs';
import { ApiPagedData , QueryOptions } from '@mt-framework-ng/core';
import {  DataStandardDto } from '../models/data-standard-dto';

@Injectable({
    providedIn: 'root'
  })
export class DataStandardService {

    private URL = '/data/standard/';

    constructor(public http: _HttpClient) {

    }

    // 分页查询同义词
    findPage(queryOptions: QueryOptions , categoryCode?: string, code?: string, name?: string
        ): Observable<ApiPagedData<DataStandardDto>> {
            const params = {};
            Object.assign(
                params,
                queryOptions,
                { categoryCode: categoryCode },
                code ? { code: code } : {},
                name ? { name: name } : {}
            );
        return this.http.get<ApiPagedData<DataStandardDto>>(this.URL, params);
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
    add(dataStandardDto: any ): Observable<DataStandardDto> {
        return this.http.post(this.URL, dataStandardDto);
    }

    /**
     *
     * @param dataStandardSimilarWordDto 修改同义词
     */
    update(id: string, dataStandardDto: any ): Observable<DataStandardDto> {
        return this.http.post(this.URL + id, dataStandardDto);
    }


    /**
     *@param id 根据id 获取单个实体
     *
     */
    findById(id: string): Observable<DataStandardDto> {
        return this.http.get(this.URL + id);
    }

     /**
     *@param code 对code 进行累加 code 为空则获取最大+1
     *
     */
    getCode(code?: string): Observable<string> {
        const params = {code: code};
        Object.assign(params, code ? { code: code } : {});
        const returnResult = this.http.get<string>(this.URL + 'getCode' , params);
        return returnResult;
    }

}
