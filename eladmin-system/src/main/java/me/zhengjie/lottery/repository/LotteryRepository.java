package me.zhengjie.lottery.repository;

import me.zhengjie.lottery.domain.Lottery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author xiaoxiao
* @date 2019-12-29
*/
public interface LotteryRepository extends JpaRepository<Lottery, Long>, JpaSpecificationExecutor<Lottery> {
    /**
    * 根据 Code 查询
    * @param code /
    * @return /
    */
    Lottery findByCode(String code);
}