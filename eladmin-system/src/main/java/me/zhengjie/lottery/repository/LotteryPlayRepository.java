package me.zhengjie.lottery.repository;

import me.zhengjie.lottery.domain.LotteryPlay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author 小小
* @date 2019-12-29
*/
public interface LotteryPlayRepository extends JpaRepository<LotteryPlay, Long>, JpaSpecificationExecutor<LotteryPlay> {
}