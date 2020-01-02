package me.zhengjie.lottery.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.lottery.domain.Withdraw;
import me.zhengjie.lottery.service.WithdrawService;
import me.zhengjie.lottery.service.dto.WithdrawQueryCriteria;
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
* @date 2020-01-01
*/
@Api(tags = "me.zhengjie.lottery管理")
@RestController
@RequestMapping("/api/withdraw")
public class WithdrawController {

    private final WithdrawService withdrawService;

    public WithdrawController(WithdrawService withdrawService) {
        this.withdrawService = withdrawService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('withdraw:list')")
    public void download(HttpServletResponse response, WithdrawQueryCriteria criteria) throws IOException {
        withdrawService.download(withdrawService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询me.zhengjie.lottery")
    @ApiOperation("查询me.zhengjie.lottery")
    @PreAuthorize("@el.check('withdraw:list')")
    public ResponseEntity<Object> getWithdraws(WithdrawQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(withdrawService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增me.zhengjie.lottery")
    @ApiOperation("新增me.zhengjie.lottery")
    @PreAuthorize("@el.check('withdraw:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Withdraw resources){
        return new ResponseEntity<>(withdrawService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改me.zhengjie.lottery")
    @ApiOperation("修改me.zhengjie.lottery")
    @PreAuthorize("@el.check('withdraw:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Withdraw resources){
        withdrawService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除me.zhengjie.lottery")
    @ApiOperation("删除me.zhengjie.lottery")
    @PreAuthorize("@el.check('withdraw:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        withdrawService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}