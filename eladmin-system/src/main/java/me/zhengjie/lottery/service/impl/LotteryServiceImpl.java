package me.zhengjie.lottery.service.impl;

import me.zhengjie.lottery.domain.Lottery;
import me.zhengjie.exception.EntityExistException;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.lottery.repository.LotteryRepository;
import me.zhengjie.lottery.service.LotteryService;
import me.zhengjie.lottery.service.dto.LotteryDto;
import me.zhengjie.lottery.service.dto.LotteryQueryCriteria;
import me.zhengjie.lottery.service.mapper.LotteryMapper;
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
//@CacheConfig(cacheNames = "lottery")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LotteryServiceImpl implements LotteryService {

    private final LotteryRepository lotteryRepository;

    private final LotteryMapper lotteryMapper;

    public LotteryServiceImpl(LotteryRepository lotteryRepository, LotteryMapper lotteryMapper) {
        this.lotteryRepository = lotteryRepository;
        this.lotteryMapper = lotteryMapper;
    }

    @Override
    //@Cacheable
    public Map<String,Object> queryAll(LotteryQueryCriteria criteria, Pageable pageable){
        Page<Lottery> page = lotteryRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(lotteryMapper::toDto));
    }

    @Override
    //@Cacheable
    public List<LotteryDto> queryAll(LotteryQueryCriteria criteria){
        return lotteryMapper.toDto(lotteryRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    //@Cacheable(key = "#p0")
    public LotteryDto findById(Long id) {
        Lottery lottery = lotteryRepository.findById(id).orElseGet(Lottery::new);
        ValidationUtil.isNull(lottery.getId(),"Lottery","id",id);
        return lotteryMapper.toDto(lottery);
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public LotteryDto create(Lottery resources) {
        if(lotteryRepository.findByCode(resources.getCode()) != null){
            throw new EntityExistException(Lottery.class,"code",resources.getCode());
        }
        return lotteryMapper.toDto(lotteryRepository.save(resources));
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Lottery resources) {
        Lottery lottery = lotteryRepository.findById(resources.getId()).orElseGet(Lottery::new);
        ValidationUtil.isNull( lottery.getId(),"Lottery","id",resources.getId());
        Lottery lottery1 = null;
        lottery1 = lotteryRepository.findByCode(resources.getCode());
        if(lottery1 != null && !lottery1.getId().equals(lottery.getId())){
            throw new EntityExistException(Lottery.class,"code",resources.getCode());
        }
        lottery.copy(resources);
        lotteryRepository.save(lottery);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            lotteryRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<LotteryDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (LotteryDto lottery : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("彩票编码", lottery.getCode());
            map.put("彩票名称", lottery.getName());
            map.put("图标", lottery.getIcon());
            map.put("彩票类型", lottery.getLevel());
            map.put("状态", lottery.getStat());
            map.put("创建时间", lottery.getCreateTime());
            map.put("更新时间", lottery.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}