package me.zhengjie.lottery.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.lottery.domain.Recharge;
import me.zhengjie.lottery.service.RechargeService;
import me.zhengjie.lottery.service.dto.RechargeQueryCriteria;
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
@Api(tags = "充值管理")
@RestController
@RequestMapping("/api/recharge")
public class RechargeController {

    private final RechargeService rechargeService;

    public RechargeController(RechargeService rechargeService) {
        this.rechargeService = rechargeService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('recharge:list')")
    public void download(HttpServletResponse response, RechargeQueryCriteria criteria) throws IOException {
        rechargeService.download(rechargeService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询充值")
    @ApiOperation("查询充值")
    @PreAuthorize("@el.check('recharge:list')")
    public ResponseEntity<Object> getRecharges(RechargeQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(rechargeService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增充值")
    @ApiOperation("新增充值")
    @PreAuthorize("@el.check('recharge:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Recharge resources){
        return new ResponseEntity<>(rechargeService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改充值")
    @ApiOperation("修改充值")
    @PreAuthorize("@el.check('recharge:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Recharge resources){
        rechargeService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除充值")
    @ApiOperation("删除充值")
    @PreAuthorize("@el.check('recharge:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        rechargeService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}