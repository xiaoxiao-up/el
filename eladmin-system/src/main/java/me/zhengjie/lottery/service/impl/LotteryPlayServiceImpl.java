package me.zhengjie.lottery.service.impl;

import me.zhengjie.lottery.domain.LotteryPlay;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.lottery.repository.LotteryPlayRepository;
import me.zhengjie.lottery.service.LotteryPlayService;
import me.zhengjie.lottery.service.dto.LotteryPlayDto;
import me.zhengjie.lottery.service.dto.LotteryPlayQueryCriteria;
import me.zhengjie.lottery.service.mapper.LotteryPlayMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @author 小小
* @date 2019-12-29
*/
@Service
//@CacheConfig(cacheNames = "lotteryPlay")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LotteryPlayServiceImpl implements LotteryPlayService {

    private final LotteryPlayRepository lotteryPlayRepository;

    private final LotteryPlayMapper lotteryPlayMapper;

    public LotteryPlayServiceImpl(LotteryPlayRepository lotteryPlayRepository, LotteryPlayMapper lotteryPlayMapper) {
        this.lotteryPlayRepository = lotteryPlayRepository;
        this.lotteryPlayMapper = lotteryPlayMapper;
    }

    @Override
    //@Cacheable
    public Map<String,Object> queryAll(LotteryPlayQueryCriteria criteria, Pageable pageable){
        Page<LotteryPlay> page = lotteryPlayRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(lotteryPlayMapper::toDto));
    }

    @Override
    //@Cacheable
    public List<LotteryPlayDto> queryAll(LotteryPlayQueryCriteria criteria){
        return lotteryPlayMapper.toDto(lotteryPlayRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    //@Cacheable(key = "#p0")
    public LotteryPlayDto findById(Long id) {
        LotteryPlay lotteryPlay = lotteryPlayRepository.findById(id).orElseGet(LotteryPlay::new);
        ValidationUtil.isNull(lotteryPlay.getId(),"LotteryPlay","id",id);
        return lotteryPlayMapper.toDto(lotteryPlay);
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public LotteryPlayDto create(LotteryPlay resources) {
        return lotteryPlayMapper.toDto(lotteryPlayRepository.save(resources));
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(LotteryPlay resources) {
        LotteryPlay lotteryPlay = lotteryPlayRepository.findById(resources.getId()).orElseGet(LotteryPlay::new);
        ValidationUtil.isNull( lotteryPlay.getId(),"LotteryPlay","id",resources.getId());
        lotteryPlay.copy(resources);
        lotteryPlayRepository.save(lotteryPlay);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            lotteryPlayRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<LotteryPlayDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (LotteryPlayDto lotteryPlay : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("玩法名称", lotteryPlay.getLotteryId());
            map.put("玩法简介", lotteryPlay.getLotteryName());
            map.put("赔率", lotteryPlay.getPlayId());
            map.put("状态", lotteryPlay.getStat());
            map.put("创建时间", lotteryPlay.getCreateTime());
            map.put("更新时间", lotteryPlay.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}