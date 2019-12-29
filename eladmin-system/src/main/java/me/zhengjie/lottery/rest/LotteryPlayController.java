package me.zhengjie.lottery.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.lottery.domain.LotteryPlay;
import me.zhengjie.lottery.service.LotteryPlayService;
import me.zhengjie.lottery.service.dto.LotteryPlayQueryCriteria;
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
* @author 小小
* @date 2019-12-29
*/
@Api(tags = "彩票玩法管理")
@RestController
@RequestMapping("/api/lotteryPlay")
public class LotteryPlayController {

    private final LotteryPlayService lotteryPlayService;

    public LotteryPlayController(LotteryPlayService lotteryPlayService) {
        this.lotteryPlayService = lotteryPlayService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('lotteryPlay:list')")
    public void download(HttpServletResponse response, LotteryPlayQueryCriteria criteria) throws IOException {
        lotteryPlayService.download(lotteryPlayService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询彩票玩法")
    @ApiOperation("查询彩票玩法")
    @PreAuthorize("@el.check('lotteryPlay:list')")
    public ResponseEntity<Object> getLotteryPlays(LotteryPlayQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(lotteryPlayService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增彩票玩法")
    @ApiOperation("新增彩票玩法")
    @PreAuthorize("@el.check('lotteryPlay:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody LotteryPlay resources){
        return new ResponseEntity<>(lotteryPlayService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改彩票玩法")
    @ApiOperation("修改彩票玩法")
    @PreAuthorize("@el.check('lotteryPlay:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody LotteryPlay resources){
        lotteryPlayService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除彩票玩法")
    @ApiOperation("删除彩票玩法")
    @PreAuthorize("@el.check('lotteryPlay:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        lotteryPlayService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}