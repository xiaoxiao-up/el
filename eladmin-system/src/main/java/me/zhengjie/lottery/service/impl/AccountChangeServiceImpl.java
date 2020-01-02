package me.zhengjie.lottery.service.impl;

import me.zhengjie.lottery.domain.AccountChange;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.lottery.repository.AccountChangeRepository;
import me.zhengjie.lottery.service.AccountChangeService;
import me.zhengjie.lottery.service.dto.AccountChangeDto;
import me.zhengjie.lottery.service.dto.AccountChangeQueryCriteria;
import me.zhengjie.lottery.service.mapper.AccountChangeMapper;
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
//@CacheConfig(cacheNames = "accountChange")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AccountChangeServiceImpl implements AccountChangeService {

    private final AccountChangeRepository accountChangeRepository;

    private final AccountChangeMapper accountChangeMapper;

    public AccountChangeServiceImpl(AccountChangeRepository accountChangeRepository, AccountChangeMapper accountChangeMapper) {
        this.accountChangeRepository = accountChangeRepository;
        this.accountChangeMapper = accountChangeMapper;
    }

    @Override
    //@Cacheable
    public Map<String,Object> queryAll(AccountChangeQueryCriteria criteria, Pageable pageable){
        Page<AccountChange> page = accountChangeRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(accountChangeMapper::toDto));
    }

    @Override
    //@Cacheable
    public List<AccountChangeDto> queryAll(AccountChangeQueryCriteria criteria){
        return accountChangeMapper.toDto(accountChangeRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    //@Cacheable(key = "#p0")
    public AccountChangeDto findById(Long id) {
        AccountChange accountChange = accountChangeRepository.findById(id).orElseGet(AccountChange::new);
        ValidationUtil.isNull(accountChange.getId(),"AccountChange","id",id);
        return accountChangeMapper.toDto(accountChange);
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public AccountChangeDto create(AccountChange resources) {
        return accountChangeMapper.toDto(accountChangeRepository.save(resources));
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(AccountChange resources) {
        AccountChange accountChange = accountChangeRepository.findById(resources.getId()).orElseGet(AccountChange::new);
        ValidationUtil.isNull( accountChange.getId(),"AccountChange","id",resources.getId());
        accountChange.copy(resources);
        accountChangeRepository.save(accountChange);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            accountChangeRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<AccountChangeDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (AccountChangeDto accountChange : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("类型", accountChange.getType());
            map.put("用户id", accountChange.getUserId());
            map.put("账变金额", accountChange.getChange());
            map.put("余额", accountChange.getBalance());
            map.put("备注", accountChange.getRemark());
            map.put("状态", accountChange.getStat());
            map.put("创建时间", accountChange.getCreateTime());
            map.put("更新时间", accountChange.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}