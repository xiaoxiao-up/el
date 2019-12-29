package me.zhengjie.lottery.service.impl;

import me.zhengjie.lottery.domain.ChaseOrder;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.lottery.repository.ChaseOrderRepository;
import me.zhengjie.lottery.service.ChaseOrderService;
import me.zhengjie.lottery.service.dto.ChaseOrderDto;
import me.zhengjie.lottery.service.dto.ChaseOrderQueryCriteria;
import me.zhengjie.lottery.service.mapper.ChaseOrderMapper;
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
* @author lottery
* @date 2019-12-29
*/
@Service
//@CacheConfig(cacheNames = "chaseOrder")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ChaseOrderServiceImpl implements ChaseOrderService {

    private final ChaseOrderRepository chaseOrderRepository;

    private final ChaseOrderMapper chaseOrderMapper;

    public ChaseOrderServiceImpl(ChaseOrderRepository chaseOrderRepository, ChaseOrderMapper chaseOrderMapper) {
        this.chaseOrderRepository = chaseOrderRepository;
        this.chaseOrderMapper = chaseOrderMapper;
    }

    @Override
    //@Cacheable
    public Map<String,Object> queryAll(ChaseOrderQueryCriteria criteria, Pageable pageable){
        Page<ChaseOrder> page = chaseOrderRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(chaseOrderMapper::toDto));
    }

    @Override
    //@Cacheable
    public List<ChaseOrderDto> queryAll(ChaseOrderQueryCriteria criteria){
        return chaseOrderMapper.toDto(chaseOrderRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    //@Cacheable(key = "#p0")
    public ChaseOrderDto findById(Long id) {
        ChaseOrder chaseOrder = chaseOrderRepository.findById(id).orElseGet(ChaseOrder::new);
        ValidationUtil.isNull(chaseOrder.getId(),"ChaseOrder","id",id);
        return chaseOrderMapper.toDto(chaseOrder);
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public ChaseOrderDto create(ChaseOrder resources) {
        return chaseOrderMapper.toDto(chaseOrderRepository.save(resources));
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(ChaseOrder resources) {
        ChaseOrder chaseOrder = chaseOrderRepository.findById(resources.getId()).orElseGet(ChaseOrder::new);
        ValidationUtil.isNull( chaseOrder.getId(),"ChaseOrder","id",resources.getId());
        chaseOrder.copy(resources);
        chaseOrderRepository.save(chaseOrder);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            chaseOrderRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<ChaseOrderDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ChaseOrderDto chaseOrder : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("注单号", chaseOrder.getOrderId());
            map.put("用户名", chaseOrder.getUserName());
            map.put("彩票类型", chaseOrder.getType());
            map.put("玩法", chaseOrder.getPlayName());
            map.put("彩票期号", chaseOrder.getIssue());
            map.put("开奖结果", chaseOrder.getOpen());
            map.put("投注内容", chaseOrder.getContent());
            map.put("倍数", chaseOrder.getMultiple());
            map.put("注数", chaseOrder.getNumber());
            map.put("投注金额", chaseOrder.getAmount());
            map.put("中奖金额", chaseOrder.getWinAmount());
            map.put("状态", chaseOrder.getStat());
            map.put("创建时间", chaseOrder.getCreateTime());
            map.put("更新时间", chaseOrder.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}