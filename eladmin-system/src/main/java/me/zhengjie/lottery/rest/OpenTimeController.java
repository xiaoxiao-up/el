package me.zhengjie.lottery.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.lottery.domain.OpenTime;
import me.zhengjie.lottery.service.OpenTimeService;
import me.zhengjie.lottery.service.dto.OpenTimeQueryCriteria;
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
@Api(tags = "开奖时间管理管理")
@RestController
@RequestMapping("/api/openTime")
public class OpenTimeController {

    private final OpenTimeService openTimeService;

    public OpenTimeController(OpenTimeService openTimeService) {
        this.openTimeService = openTimeService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('openTime:list')")
    public void download(HttpServletResponse response, OpenTimeQueryCriteria criteria) throws IOException {
        openTimeService.download(openTimeService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询开奖时间管理")
    @ApiOperation("查询开奖时间管理")
    @PreAuthorize("@el.check('openTime:list')")
    public ResponseEntity<Object> getOpenTimes(OpenTimeQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(openTimeService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增开奖时间管理")
    @ApiOperation("新增开奖时间管理")
    @PreAuthorize("@el.check('openTime:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody OpenTime resources){
        return new ResponseEntity<>(openTimeService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改开奖时间管理")
    @ApiOperation("修改开奖时间管理")
    @PreAuthorize("@el.check('openTime:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody OpenTime resources){
        openTimeService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除开奖时间管理")
    @ApiOperation("删除开奖时间管理")
    @PreAuthorize("@el.check('openTime:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        openTimeService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}