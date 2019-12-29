package me.zhengjie.lottery.service.impl;

import me.zhengjie.lottery.domain.LotteryOpen;
import me.zhengjie.exception.EntityExistException;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.lottery.repository.LotteryOpenRepository;
import me.zhengjie.lottery.service.LotteryOpenService;
import me.zhengjie.lottery.service.dto.LotteryOpenDto;
import me.zhengjie.lottery.service.dto.LotteryOpenQueryCriteria;
import me.zhengjie.lottery.service.mapper.LotteryOpenMapper;
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
* @author xiaoxiao
* @date 2019-12-29
*/
@Service
//@CacheConfig(cacheNames = "lotteryOpen")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LotteryOpenServiceImpl implements LotteryOpenService {

    private final LotteryOpenRepository lotteryOpenRepository;

    private final LotteryOpenMapper lotteryOpenMapper;

    public LotteryOpenServiceImpl(LotteryOpenRepository lotteryOpenRepository, LotteryOpenMapper lotteryOpenMapper) {
        this.lotteryOpenRepository = lotteryOpenRepository;
        this.lotteryOpenMapper = lotteryOpenMapper;
    }

    @Override
    //@Cacheable
    public Map<String,Object> queryAll(LotteryOpenQueryCriteria criteria, Pageable pageable){
        Page<LotteryOpen> page = lotteryOpenRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(lotteryOpenMapper::toDto));
    }

    @Override
    //@Cacheable
    public List<LotteryOpenDto> queryAll(LotteryOpenQueryCriteria criteria){
        return lotteryOpenMapper.toDto(lotteryOpenRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    //@Cacheable(key = "#p0")
    public LotteryOpenDto findById(Long id) {
        LotteryOpen lotteryOpen = lotteryOpenRepository.findById(id).orElseGet(LotteryOpen::new);
        ValidationUtil.isNull(lotteryOpen.getId(),"LotteryOpen","id",id);
        return lotteryOpenMapper.toDto(lotteryOpen);
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public LotteryOpenDto create(LotteryOpen resources) {
        if(lotteryOpenRepository.findByCode(resources.getCode()) != null){
            throw new EntityExistException(LotteryOpen.class,"code",resources.getCode());
        }
        return lotteryOpenMapper.toDto(lotteryOpenRepository.save(resources));
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(LotteryOpen resources) {
        LotteryOpen lotteryOpen = lotteryOpenRepository.findById(resources.getId()).orElseGet(LotteryOpen::new);
        ValidationUtil.isNull( lotteryOpen.getId(),"LotteryOpen","id",resources.getId());
        LotteryOpen lotteryOpen1 = null;
        lotteryOpen1 = lotteryOpenRepository.findByCode(resources.getCode());
        if(lotteryOpen1 != null && !lotteryOpen1.getId().equals(lotteryOpen.getId())){
            throw new EntityExistException(LotteryOpen.class,"code",resources.getCode());
        }
        lotteryOpen.copy(resources);
        lotteryOpenRepository.save(lotteryOpen);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            lotteryOpenRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<LotteryOpenDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (LotteryOpenDto lotteryOpen : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("彩票编码", lotteryOpen.getCode());
            map.put("彩票名称", lotteryOpen.getName());
            map.put("期数", lotteryOpen.getIssue());
            map.put("开奖时间", lotteryOpen.getOpenTime());
            map.put("开奖号码", lotteryOpen.getOpenNumber());
            map.put("注数", lotteryOpen.getNumber());
            map.put("投注金额", lotteryOpen.getAmount());
            map.put("中奖金额", lotteryOpen.getWinAmount());
            map.put("状态0-不开启，1-开启", lotteryOpen.getStat());
            map.put("创建时间", lotteryOpen.getCreateTime());
            map.put("更新时间", lotteryOpen.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}