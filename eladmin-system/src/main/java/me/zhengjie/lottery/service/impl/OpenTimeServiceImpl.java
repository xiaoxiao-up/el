package me.zhengjie.lottery.service.impl;

import me.zhengjie.lottery.domain.OpenTime;
import me.zhengjie.exception.EntityExistException;
import me.zhengjie.lottery.service.mapper.OpenTimeMapper;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.lottery.repository.OpenTimeRepository;
import me.zhengjie.lottery.service.OpenTimeService;
import me.zhengjie.lottery.service.dto.OpenTimeDto;
import me.zhengjie.lottery.service.dto.OpenTimeQueryCriteria;
import me.zhengjie.lottery.service.mapper.OpenTimeMapper;
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
//@CacheConfig(cacheNames = "openTime")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class OpenTimeServiceImpl implements OpenTimeService {

    private final OpenTimeRepository openTimeRepository;

    private final OpenTimeMapper openTimeMapper;

    public OpenTimeServiceImpl(OpenTimeRepository openTimeRepository, OpenTimeMapper openTimeMapper) {
        this.openTimeRepository = openTimeRepository;
        this.openTimeMapper = openTimeMapper;
    }

    @Override
    //@Cacheable
    public Map<String,Object> queryAll(OpenTimeQueryCriteria criteria, Pageable pageable){
        Page<OpenTime> page = openTimeRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(openTimeMapper::toDto));
    }

    @Override
    //@Cacheable
    public List<OpenTimeDto> queryAll(OpenTimeQueryCriteria criteria){
        return openTimeMapper.toDto(openTimeRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    //@Cacheable(key = "#p0")
    public OpenTimeDto findById(Long id) {
        OpenTime openTime = openTimeRepository.findById(id).orElseGet(OpenTime::new);
        ValidationUtil.isNull(openTime.getId(),"OpenTime","id",id);
        return openTimeMapper.toDto(openTime);
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public OpenTimeDto create(OpenTime resources) {
        if(openTimeRepository.findByCode(resources.getCode()) != null){
            throw new EntityExistException(OpenTime.class,"code",resources.getCode());
        }
        return openTimeMapper.toDto(openTimeRepository.save(resources));
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(OpenTime resources) {
        OpenTime openTime = openTimeRepository.findById(resources.getId()).orElseGet(OpenTime::new);
        ValidationUtil.isNull( openTime.getId(),"OpenTime","id",resources.getId());
        OpenTime openTime1 = null;
        openTime1 = openTimeRepository.findByCode(resources.getCode());
        if(openTime1 != null && !openTime1.getId().equals(openTime.getId())){
            throw new EntityExistException(OpenTime.class,"code",resources.getCode());
        }
        openTime.copy(resources);
        openTimeRepository.save(openTime);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            openTimeRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<OpenTimeDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (OpenTimeDto openTime : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("彩票编码", openTime.getCode());
            map.put("彩票名称", openTime.getName());
            map.put("期数", openTime.getIssue());
            map.put("开奖时间", openTime.getOpenTime());
            map.put("开始时间", openTime.getStartTime());
            map.put("截止时间", openTime.getEndTime());
            map.put("创建时间", openTime.getCreateTime());
            map.put("更新时间", openTime.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}