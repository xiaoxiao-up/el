package me.zhengjie.lottery.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.lottery.domain.AccountChange;
import me.zhengjie.lottery.service.AccountChangeService;
import me.zhengjie.lottery.service.dto.AccountChangeQueryCriteria;
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
@Api(tags = "账户变动管理")
@RestController
@RequestMapping("/api/accountChange")
public class AccountChangeController {

    private final AccountChangeService accountChangeService;

    public AccountChangeController(AccountChangeService accountChangeService) {
        this.accountChangeService = accountChangeService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('accountChange:list')")
    public void download(HttpServletResponse response, AccountChangeQueryCriteria criteria) throws IOException {
        accountChangeService.download(accountChangeService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询账户变动")
    @ApiOperation("查询账户变动")
    @PreAuthorize("@el.check('accountChange:list')")
    public ResponseEntity<Object> getAccountChanges(AccountChangeQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(accountChangeService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增账户变动")
    @ApiOperation("新增账户变动")
    @PreAuthorize("@el.check('accountChange:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody AccountChange resources){
        return new ResponseEntity<>(accountChangeService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改账户变动")
    @ApiOperation("修改账户变动")
    @PreAuthorize("@el.check('accountChange:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody AccountChange resources){
        accountChangeService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除账户变动")
    @ApiOperation("删除账户变动")
    @PreAuthorize("@el.check('accountChange:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        accountChangeService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}