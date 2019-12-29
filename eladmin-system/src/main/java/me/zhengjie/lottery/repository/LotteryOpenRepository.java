package me.zhengjie.lottery.repository;

import me.zhengjie.lottery.domain.LotteryOpen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author xiaoxiao
* @date 2019-12-29
*/
public interface LotteryOpenRepository extends JpaRepository<LotteryOpen, Long>, JpaSpecificationExecutor<LotteryOpen> {
    /**
    * 根据 Code 查询
    * @param code /
    * @return /
    */
    LotteryOpen findByCode(String code);
}