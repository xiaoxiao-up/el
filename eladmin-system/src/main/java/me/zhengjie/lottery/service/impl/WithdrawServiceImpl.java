package me.zhengjie.lottery.service.impl;

import me.zhengjie.lottery.domain.Withdraw;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.lottery.repository.WithdrawRepository;
import me.zhengjie.lottery.service.WithdrawService;
import me.zhengjie.lottery.service.dto.WithdrawDto;
import me.zhengjie.lottery.service.dto.WithdrawQueryCriteria;
import me.zhengjie.lottery.service.mapper.WithdrawMapper;
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
* @date 2020-01-01
*/
@Service
//@CacheConfig(cacheNames = "withdraw")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WithdrawServiceImpl implements WithdrawService {

    private final WithdrawRepository withdrawRepository;

    private final WithdrawMapper withdrawMapper;

    public WithdrawServiceImpl(WithdrawRepository withdrawRepository, WithdrawMapper withdrawMapper) {
        this.withdrawRepository = withdrawRepository;
        this.withdrawMapper = withdrawMapper;
    }

    @Override
    //@Cacheable
    public Map<String,Object> queryAll(WithdrawQueryCriteria criteria, Pageable pageable){
        Page<Withdraw> page = withdrawRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(withdrawMapper::toDto));
    }

    @Override
    //@Cacheable
    public List<WithdrawDto> queryAll(WithdrawQueryCriteria criteria){
        return withdrawMapper.toDto(withdrawRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    //@Cacheable(key = "#p0")
    public WithdrawDto findById(Long id) {
        Withdraw withdraw = withdrawRepository.findById(id).orElseGet(Withdraw::new);
        ValidationUtil.isNull(withdraw.getId(),"Withdraw","id",id);
        return withdrawMapper.toDto(withdraw);
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public WithdrawDto create(Withdraw resources) {
        return withdrawMapper.toDto(withdrawRepository.save(resources));
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Withdraw resources) {
        Withdraw withdraw = withdrawRepository.findById(resources.getId()).orElseGet(Withdraw::new);
        ValidationUtil.isNull( withdraw.getId(),"Withdraw","id",resources.getId());
        withdraw.copy(resources);
        withdrawRepository.save(withdraw);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            withdrawRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<WithdrawDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WithdrawDto withdraw : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("单号", withdraw.getOrderId());
            map.put("用户id", withdraw.getUserId());
            map.put("手续费", withdraw.getFee());
            map.put("提现金额", withdraw.getWithdrawPrice());
            map.put("开户银行", withdraw.getBank());
            map.put("银行类型", withdraw.getBankType());
            map.put("开户姓名", withdraw.getBankName());
            map.put("银行账号", withdraw.getBankAccount());
            map.put("状态", withdraw.getStat());
            map.put("创建时间", withdraw.getCreateTime());
            map.put("更新时间", withdraw.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}