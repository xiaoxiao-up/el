package me.zhengjie.lottery.service.impl;

import me.zhengjie.lottery.domain.Play;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.lottery.repository.PlayRepository;
import me.zhengjie.lottery.service.PlayService;
import me.zhengjie.lottery.service.dto.PlayDto;
import me.zhengjie.lottery.service.dto.PlayQueryCriteria;
import me.zhengjie.lottery.service.mapper.PlayMapper;
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
//@CacheConfig(cacheNames = "play")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PlayServiceImpl implements PlayService {

    private final PlayRepository playRepository;

    private final PlayMapper playMapper;

    public PlayServiceImpl(PlayRepository playRepository, PlayMapper playMapper) {
        this.playRepository = playRepository;
        this.playMapper = playMapper;
    }

    @Override
    //@Cacheable
    public Map<String,Object> queryAll(PlayQueryCriteria criteria, Pageable pageable){
        Page<Play> page = playRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(playMapper::toDto));
    }

    @Override
    //@Cacheable
    public List<PlayDto> queryAll(PlayQueryCriteria criteria){
        return playMapper.toDto(playRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    //@Cacheable(key = "#p0")
    public PlayDto findById(Long id) {
        Play play = playRepository.findById(id).orElseGet(Play::new);
        ValidationUtil.isNull(play.getId(),"Play","id",id);
        return playMapper.toDto(play);
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public PlayDto create(Play resources) {
        return playMapper.toDto(playRepository.save(resources));
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Play resources) {
        Play play = playRepository.findById(resources.getId()).orElseGet(Play::new);
        ValidationUtil.isNull( play.getId(),"Play","id",resources.getId());
        play.copy(resources);
        playRepository.save(play);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            playRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<PlayDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (PlayDto play : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("玩法名称", play.getName());
            map.put("玩法简介", play.getTitle());
            map.put("赔率", play.getOdds());
            map.put("投注限制", play.getOrderMax());
            map.put("状态", play.getStat());
            map.put("创建时间", play.getCreateTime());
            map.put("更新时间", play.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}