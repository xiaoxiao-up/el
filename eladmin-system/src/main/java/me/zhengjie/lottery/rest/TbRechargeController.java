package me.zhengjie.lottery.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.lottery.domain.TbRecharge;
import me.zhengjie.lottery.service.TbRechargeService;
import me.zhengjie.lottery.service.dto.TbRechargeQueryCriteria;
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
@RequestMapping("/api/tbRecharge")
public class TbRechargeController {

    private final TbRechargeService tbRechargeService;

    public TbRechargeController(TbRechargeService tbRechargeService) {
        this.tbRechargeService = tbRechargeService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('tbRecharge:list')")
    public void download(HttpServletResponse response, TbRechargeQueryCriteria criteria) throws IOException {
        tbRechargeService.download(tbRechargeService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询充值")
    @ApiOperation("查询充值")
    @PreAuthorize("@el.check('tbRecharge:list')")
    public ResponseEntity<Object> getTbRecharges(TbRechargeQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(tbRechargeService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增充值")
    @ApiOperation("新增充值")
    @PreAuthorize("@el.check('tbRecharge:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody TbRecharge resources){
        return new ResponseEntity<>(tbRechargeService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改充值")
    @ApiOperation("修改充值")
    @PreAuthorize("@el.check('tbRecharge:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody TbRecharge resources){
        tbRechargeService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除充值")
    @ApiOperation("删除充值")
    @PreAuthorize("@el.check('tbRecharge:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        tbRechargeService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}