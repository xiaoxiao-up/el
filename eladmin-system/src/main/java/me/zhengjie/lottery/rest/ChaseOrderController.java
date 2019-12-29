package me.zhengjie.lottery.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.lottery.domain.ChaseOrder;
import me.zhengjie.lottery.service.ChaseOrderService;
import me.zhengjie.lottery.service.dto.ChaseOrderQueryCriteria;
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
* @author lottery
* @date 2019-12-29
*/
@Api(tags = "追号管理")
@RestController
@RequestMapping("/api/chaseOrder")
public class ChaseOrderController {

    private final ChaseOrderService chaseOrderService;

    public ChaseOrderController(ChaseOrderService chaseOrderService) {
        this.chaseOrderService = chaseOrderService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('chaseOrder:list')")
    public void download(HttpServletResponse response, ChaseOrderQueryCriteria criteria) throws IOException {
        chaseOrderService.download(chaseOrderService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询追号")
    @ApiOperation("查询追号")
    @PreAuthorize("@el.check('chaseOrder:list')")
    public ResponseEntity<Object> getChaseOrders(ChaseOrderQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(chaseOrderService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增追号")
    @ApiOperation("新增追号")
    @PreAuthorize("@el.check('chaseOrder:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody ChaseOrder resources){
        return new ResponseEntity<>(chaseOrderService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改追号")
    @ApiOperation("修改追号")
    @PreAuthorize("@el.check('chaseOrder:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody ChaseOrder resources){
        chaseOrderService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除追号")
    @ApiOperation("删除追号")
    @PreAuthorize("@el.check('chaseOrder:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        chaseOrderService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}