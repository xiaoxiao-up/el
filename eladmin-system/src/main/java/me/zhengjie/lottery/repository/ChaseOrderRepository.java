package me.zhengjie.lottery.repository;

import me.zhengjie.lottery.domain.ChaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author lottery
* @date 2019-12-29
*/
public interface ChaseOrderRepository extends JpaRepository<ChaseOrder, Long>, JpaSpecificationExecutor<ChaseOrder> {
}