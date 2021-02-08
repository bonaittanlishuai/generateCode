import { Injectable } from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { ApiPagedData, ApiSimpleData, QueryOptions } from '@mt-framework-ng/core';
import { Observable } from 'rxjs';
import { SystemInfoDTO, DataSourceTableDTO } from '../models/index';

@Injectable({
  providedIn: 'root'
})
export class SystemInfoService {

  private URL = '/systemInfo/';

  constructor(private http: _HttpClient) {
  }

  /**
   * 删除系统信息
   */
  delete(id: string): Observable<any> {
    return this.http.delete(this.URL + `${id}`);
  }

  /**
   * 创建系统信息
   */
  create(entity: SystemInfoDTO): Observable<SystemInfoDTO> {
    return this.http.post<SystemInfoDTO>(this.URL, entity);
  }

    /**
   * 测试系统信息
   */
  testSave(entity: SystemInfoDTO): Observable<ApiSimpleData<string>> {
    return this.http.post<ApiSimpleData<string>>(this.URL + `check`, entity);
  }


  /**
   * 更新系统信息
   */
  update(id: string, entity: SystemInfoDTO): Observable<SystemInfoDTO> {
    return this.http.put<SystemInfoDTO>(this.URL + id, entity);
  }

  /**
   * 根据ID查找系统信息
   */
  findById(id: string): Observable<any> {
    return this.http.get<any>(this.URL + id);
  }

  /**
   * 分页查询系统信息
   */
  findOnePage(queryOptions: QueryOptions, name?: string): Observable<ApiPagedData<SystemInfoDTO>> {
    const params = {};
    Object.assign(params, queryOptions);
    Object.assign(params, name ? { name: name } : {});
    return this.http.get<ApiPagedData<SystemInfoDTO>>(this.URL, params);
}

    /**
   * 查询所有系统信息
   */
  findAll(): Observable<Array<SystemInfoDTO>> {
    return this.http.get(this.URL + `all`);
  }

  /**
   * 根据系统信息id查找权限内的所有数据库
   */
  getDBList(id: string): Observable<Array<string>> {
    return this.http.get(this.URL + `${id}/scheme`);
  }

  /**
   * 根据系统信息id和数据库名称查找权限内的所有数据表
   */
  getTableList(id: string, name: string): Observable<DataSourceTableDTO[]> {
    return this.http.get(this.URL + `${id}/${encodeURIComponent(name)}/table`);
  }

  /**
   * 数据预览
   */
  previewData(id: string, name: string): Observable<any> {
    return this.http.get(this.URL + `${id}/${encodeURIComponent(name)}/data`);
  }

  /**
   * 根据系统信息id和数据库查找权限内的所有字段
   */
  getColumns(id: string, name: string): Observable<any> {
    return this.http.get(this.URL + `${id}/${encodeURIComponent(name)}/column`);
  }
}
