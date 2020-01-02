package me.zhengjie.lottery.repository;

import me.zhengjie.lottery.domain.Withdraw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author 小小
* @date 2020-01-01
*/
public interface WithdrawRepository extends JpaRepository<Withdraw, Long>, JpaSpecificationExecutor<Withdraw> {
}