package me.zhengjie.lottery.service.impl;

import me.zhengjie.lottery.domain.Recharge;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.lottery.repository.RechargeRepository;
import me.zhengjie.lottery.service.RechargeService;
import me.zhengjie.lottery.service.dto.RechargeDto;
import me.zhengjie.lottery.service.dto.RechargeQueryCriteria;
import me.zhengjie.lottery.service.mapper.RechargeMapper;
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
//@CacheConfig(cacheNames = "recharge")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RechargeServiceImpl implements RechargeService {

    private final RechargeRepository rechargeRepository;

    private final RechargeMapper rechargeMapper;

    public RechargeServiceImpl(RechargeRepository rechargeRepository, RechargeMapper rechargeMapper) {
        this.rechargeRepository = rechargeRepository;
        this.rechargeMapper = rechargeMapper;
    }

    @Override
    //@Cacheable
    public Map<String,Object> queryAll(RechargeQueryCriteria criteria, Pageable pageable){
        Page<Recharge> page = rechargeRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(rechargeMapper::toDto));
    }

    @Override
    //@Cacheable
    public List<RechargeDto> queryAll(RechargeQueryCriteria criteria){
        return rechargeMapper.toDto(rechargeRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    //@Cacheable(key = "#p0")
    public RechargeDto findById(Long id) {
        Recharge recharge = rechargeRepository.findById(id).orElseGet(Recharge::new);
        ValidationUtil.isNull(recharge.getId(),"Recharge","id",id);
        return rechargeMapper.toDto(recharge);
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public RechargeDto create(Recharge resources) {
        return rechargeMapper.toDto(rechargeRepository.save(resources));
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Recharge resources) {
        Recharge recharge = rechargeRepository.findById(resources.getId()).orElseGet(Recharge::new);
        ValidationUtil.isNull( recharge.getId(),"Recharge","id",resources.getId());
        recharge.copy(resources);
        rechargeRepository.save(recharge);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            rechargeRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<RechargeDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (RechargeDto recharge : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("单号", recharge.getOrderId());
            map.put("用户id", recharge.getUserId());
            map.put("充值金额", recharge.getRechargePrice());
            map.put("实际到账", recharge.getActualPrice());
            map.put("充值前金额", recharge.getPreRecharge());
            map.put("充值方式", recharge.getType());
            map.put("备注", recharge.getBak());
            map.put("状态", recharge.getStat());
            map.put("创建时间", recharge.getCreateTime());
            map.put("更新时间", recharge.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}