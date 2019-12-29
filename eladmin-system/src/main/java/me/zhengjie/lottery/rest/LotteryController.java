package me.zhengjie.lottery.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.lottery.domain.Lottery;
import me.zhengjie.lottery.service.LotteryService;
import me.zhengjie.lottery.service.dto.LotteryQueryCriteria;
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
* @date 2019-12-29
*/
@Api(tags = "彩票信息管理")
@RestController
@RequestMapping("/api/lottery")
public class LotteryController {

    private final LotteryService lotteryService;

    public LotteryController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('lottery:list')")
    public void download(HttpServletResponse response, LotteryQueryCriteria criteria) throws IOException {
        lotteryService.download(lotteryService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询彩票信息")
    @ApiOperation("查询彩票信息")
    @PreAuthorize("@el.check('lottery:list')")
    public ResponseEntity<Object> getLotterys(LotteryQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(lotteryService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增彩票信息")
    @ApiOperation("新增彩票信息")
    @PreAuthorize("@el.check('lottery:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Lottery resources){
        return new ResponseEntity<>(lotteryService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改彩票信息")
    @ApiOperation("修改彩票信息")
    @PreAuthorize("@el.check('lottery:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Lottery resources){
        lotteryService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除彩票信息")
    @ApiOperation("删除彩票信息")
    @PreAuthorize("@el.check('lottery:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        lotteryService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}