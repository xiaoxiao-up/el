package me.zhengjie.lottery.service.impl;

import me.zhengjie.lottery.domain.Order;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.lottery.repository.OrderRepository;
import me.zhengjie.lottery.service.OrderService;
import me.zhengjie.lottery.service.dto.OrderDto;
import me.zhengjie.lottery.service.dto.OrderQueryCriteria;
import me.zhengjie.lottery.service.mapper.OrderMapper;
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
//@CacheConfig(cacheNames = "order")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    //@Cacheable
    public Map<String,Object> queryAll(OrderQueryCriteria criteria, Pageable pageable){
        Page<Order> page = orderRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(orderMapper::toDto));
    }

    @Override
    //@Cacheable
    public List<OrderDto> queryAll(OrderQueryCriteria criteria){
        return orderMapper.toDto(orderRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    //@Cacheable(key = "#p0")
    public OrderDto findById(Long id) {
        Order order = orderRepository.findById(id).orElseGet(Order::new);
        ValidationUtil.isNull(order.getId(),"Order","id",id);
        return orderMapper.toDto(order);
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public OrderDto create(Order resources) {
        return orderMapper.toDto(orderRepository.save(resources));
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Order resources) {
        Order order = orderRepository.findById(resources.getId()).orElseGet(Order::new);
        ValidationUtil.isNull( order.getId(),"Order","id",resources.getId());
        order.copy(resources);
        orderRepository.save(order);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            orderRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<OrderDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (OrderDto order : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("注单号", order.getOrderId());
            map.put("用户名", order.getUserName());
            map.put("彩票类型", order.getType());
            map.put("玩法", order.getPlayName());
            map.put("彩票期号", order.getIssue());
            map.put("开奖结果", order.getOpen());
            map.put("投注内容", order.getContent());
            map.put("倍数", order.getMultiple());
            map.put("注数", order.getNumber());
            map.put("投注金额", order.getAmount());
            map.put("中奖金额", order.getWinAmount());
            map.put("状态0-不开启，1-开启", order.getStat());
            map.put("创建时间", order.getCreateTime());
            map.put("更新时间", order.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}