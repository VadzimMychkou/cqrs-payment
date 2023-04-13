package com.correct.trajectory.OrdersService.query;

import com.correct.trajectory.OrdersService.core.data.OrderEntity;
import com.correct.trajectory.OrdersService.core.data.OrderRepository;
import com.correct.trajectory.OrdersService.core.model.OrderSummary;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class OrderQueriesHandler {

    OrderRepository orderRepository;

    @QueryHandler
    public OrderSummary findOrder(FindOrderQuery findOrderQuery) {
        OrderEntity orderEntity = orderRepository.findByOrderId(findOrderQuery.getOrderId());

        return new OrderSummary(orderEntity.getOrderId(), orderEntity.getOrderStatus(), "");
    }
}