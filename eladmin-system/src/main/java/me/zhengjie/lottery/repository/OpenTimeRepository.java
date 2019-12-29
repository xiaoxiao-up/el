package me.zhengjie.lottery.repository;

import me.zhengjie.lottery.domain.OpenTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author xiaoxiao
* @date 2019-12-29
*/
public interface OpenTimeRepository extends JpaRepository<OpenTime, Long>, JpaSpecificationExecutor<OpenTime> {
    /**
    * 根据 Code 查询
    * @param code /
    * @return /
    */
    OpenTime findByCode(String code);
}