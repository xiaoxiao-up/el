package me.zhengjie.lottery.repository;

import me.zhengjie.lottery.domain.Recharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author xiaoxiao
* @date 2020-01-01
*/
public interface RechargeRepository extends JpaRepository<Recharge, Long>, JpaSpecificationExecutor<Recharge> {
}