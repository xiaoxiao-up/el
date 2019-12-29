package me.zhengjie.lottery.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.lottery.domain.Order;
import me.zhengjie.lottery.service.OrderService;
import me.zhengjie.lottery.service.dto.OrderQueryCriteria;
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
@Api(tags = "order管理")
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('order:list')")
    public void download(HttpServletResponse response, OrderQueryCriteria criteria) throws IOException {
        orderService.download(orderService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询order")
    @ApiOperation("查询order")
    @PreAuthorize("@el.check('order:list')")
    public ResponseEntity<Object> getOrders(OrderQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(orderService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增order")
    @ApiOperation("新增order")
    @PreAuthorize("@el.check('order:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Order resources){
        return new ResponseEntity<>(orderService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改order")
    @ApiOperation("修改order")
    @PreAuthorize("@el.check('order:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Order resources){
        orderService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除order")
    @ApiOperation("删除order")
    @PreAuthorize("@el.check('order:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        orderService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}