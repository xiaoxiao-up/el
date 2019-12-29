package me.zhengjie.lottery.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.lottery.domain.LotteryOpen;
import me.zhengjie.lottery.service.LotteryOpenService;
import me.zhengjie.lottery.service.dto.LotteryOpenQueryCriteria;
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
@Api(tags = "开奖管理管理")
@RestController
@RequestMapping("/api/lotteryOpen")
public class LotteryOpenController {

    private final LotteryOpenService lotteryOpenService;

    public LotteryOpenController(LotteryOpenService lotteryOpenService) {
        this.lotteryOpenService = lotteryOpenService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('lotteryOpen:list')")
    public void download(HttpServletResponse response, LotteryOpenQueryCriteria criteria) throws IOException {
        lotteryOpenService.download(lotteryOpenService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询开奖管理")
    @ApiOperation("查询开奖管理")
    @PreAuthorize("@el.check('lotteryOpen:list')")
    public ResponseEntity<Object> getLotteryOpens(LotteryOpenQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(lotteryOpenService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增开奖管理")
    @ApiOperation("新增开奖管理")
    @PreAuthorize("@el.check('lotteryOpen:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody LotteryOpen resources){
        return new ResponseEntity<>(lotteryOpenService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改开奖管理")
    @ApiOperation("修改开奖管理")
    @PreAuthorize("@el.check('lotteryOpen:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody LotteryOpen resources){
        lotteryOpenService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除开奖管理")
    @ApiOperation("删除开奖管理")
    @PreAuthorize("@el.check('lotteryOpen:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        lotteryOpenService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}