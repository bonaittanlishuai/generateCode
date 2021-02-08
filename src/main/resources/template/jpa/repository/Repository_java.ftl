package com.matech.dispatch.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author Ming.Su.Li
 * @date 2021/1/13
 */
@Repository
public interface SystemInfoRepository extends JpaRepository<com.matech.dispatch.common.entity.SystemInfo, Long>, JpaSpecificationExecutor<com.matech.dispatch.common.entity.SystemInfo> {
}
