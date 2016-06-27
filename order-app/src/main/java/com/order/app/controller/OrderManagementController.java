package com.order.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.order.app.service.OrderManagementService;
import com.order.common.entity.Order;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value= "Order Management Services")
@RestController("OrderManageController")
public class OrderManagementController {

    private OrderManagementService orderService;
    private static final Logger    logger = LoggerFactory.getLogger(OrderManagementController.class);

    @Autowired
    public OrderManagementController(OrderManagementService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(path = "/createOrder", method = RequestMethod.PUT, consumes = "application/json")
    @ApiOperation(value = "Creates a new order", notes = "Creates a new order and returns orderId.", response = Long.class)
    @ApiResponses(value = { 
            @ApiResponse(code = 201, message = "Successful created the order", response = Long.class), 
            @ApiResponse(code = 400, message = "Invalid request") ,
            @ApiResponse(code = 500, message = "Internal server error") 
            }
    )
    public ResponseEntity<Long> createOrder(@RequestBody Order order) {
        try {
            logger.debug("In createOrder service");

            Long orderId = orderService.createOrder(order);
            if (null != orderId)
                return new ResponseEntity<Long>(orderId, HttpStatus.CREATED);

        } catch (Exception e) {
            logger.error("Exception while creating order", e);
            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @RequestMapping(path = "/getOrder", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Get the order by order_id ", notes = "Get the order details for the orderId.", response = Order.class)
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Successfully retried the order", response = Order.class),
            @ApiResponse(code = 400, message = "Invalid request") ,
            @ApiResponse(code = 500, message = "Internal server error") })
    public ResponseEntity<Order> getOrder(@RequestParam(value = "orderId") Long orderId) {
        try {
            logger.debug("In getOrder service");
            Order order = orderService.getOrder(orderId);
            if (null != order)
                return new ResponseEntity<Order>(order, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Exception while getting order for orderId : {}", orderId, e);
            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
