package me.zhengjie.lottery.service.impl;

import me.zhengjie.lottery.domain.TbRecharge;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.lottery.repository.TbRechargeRepository;
import me.zhengjie.lottery.service.TbRechargeService;
import me.zhengjie.lottery.service.dto.TbRechargeDto;
import me.zhengjie.lottery.service.dto.TbRechargeQueryCriteria;
import me.zhengjie.lottery.service.mapper.TbRechargeMapper;
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
* @date 2020-01-01
*/
@Service
//@CacheConfig(cacheNames = "tbRecharge")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TbRechargeServiceImpl implements TbRechargeService {

    private final TbRechargeRepository tbRechargeRepository;

    private final TbRechargeMapper tbRechargeMapper;

    public TbRechargeServiceImpl(TbRechargeRepository tbRechargeRepository, TbRechargeMapper tbRechargeMapper) {
        this.tbRechargeRepository = tbRechargeRepository;
        this.tbRechargeMapper = tbRechargeMapper;
    }

    @Override
    //@Cacheable
    public Map<String,Object> queryAll(TbRechargeQueryCriteria criteria, Pageable pageable){
        Page<TbRecharge> page = tbRechargeRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(tbRechargeMapper::toDto));
    }

    @Override
    //@Cacheable
    public List<TbRechargeDto> queryAll(TbRechargeQueryCriteria criteria){
        return tbRechargeMapper.toDto(tbRechargeRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    //@Cacheable(key = "#p0")
    public TbRechargeDto findById(Long id) {
        TbRecharge tbRecharge = tbRechargeRepository.findById(id).orElseGet(TbRecharge::new);
        ValidationUtil.isNull(tbRecharge.getId(),"TbRecharge","id",id);
        return tbRechargeMapper.toDto(tbRecharge);
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public TbRechargeDto create(TbRecharge resources) {
        return tbRechargeMapper.toDto(tbRechargeRepository.save(resources));
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(TbRecharge resources) {
        TbRecharge tbRecharge = tbRechargeRepository.findById(resources.getId()).orElseGet(TbRecharge::new);
        ValidationUtil.isNull( tbRecharge.getId(),"TbRecharge","id",resources.getId());
        tbRecharge.copy(resources);
        tbRechargeRepository.save(tbRecharge);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            tbRechargeRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<TbRechargeDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TbRechargeDto tbRecharge : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("单号", tbRecharge.getOrderId());
            map.put("用户id", tbRecharge.getUserId());
            map.put("充值金额", tbRecharge.getRechargePrice());
            map.put("实际到账", tbRecharge.getActualPrice());
            map.put("充值前金额", tbRecharge.getPreRecharge());
            map.put("充值方式", tbRecharge.getType());
            map.put("备注", tbRecharge.getBak());
            map.put("状态", tbRecharge.getStat());
            map.put("创建时间", tbRecharge.getCreateTime());
            map.put("更新时间", tbRecharge.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}