package me.zhengjie.lottery.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.lottery.domain.Play;
import me.zhengjie.lottery.service.PlayService;
import me.zhengjie.lottery.service.dto.PlayQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author xiaoxiao
* @date 2020-01-01
*/
@Api(tags = "玩法设置管理")
@RestController
@RequestMapping("/api/play")
public class PlayController {

    private final PlayService playService;

    public PlayController(PlayService playService) {
        this.playService = playService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('play:list')")
    public void download(HttpServletResponse response, PlayQueryCriteria criteria) throws IOException {
        playService.download(playService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询玩法设置")
    @ApiOperation("查询玩法设置")
    @PreAuthorize("@el.check('play:list')")
    public ResponseEntity<Object> getPlays(PlayQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(playService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增玩法设置")
    @ApiOperation("新增玩法设置")
    @PreAuthorize("@el.check('play:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Play resources){
        return new ResponseEntity<>(playService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改玩法设置")
    @ApiOperation("修改玩法设置")
    @PreAuthorize("@el.check('play:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Play resources){
        playService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除玩法设置")
    @ApiOperation("删除玩法设置")
    @PreAuthorize("@el.check('play:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        playService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}