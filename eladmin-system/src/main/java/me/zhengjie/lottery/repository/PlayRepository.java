package me.zhengjie.lottery.repository;

import me.zhengjie.lottery.domain.Play;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author xiaoxiao
* @date 2020-01-01
*/
public interface PlayRepository extends JpaRepository<Play, Long>, JpaSpecificationExecutor<Play> {
}