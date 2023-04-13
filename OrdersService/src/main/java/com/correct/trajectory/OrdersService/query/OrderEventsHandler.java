package com.correct.trajectory.OrdersService.query;

import com.correct.trajectory.OrdersService.core.data.OrderEntity;
import com.correct.trajectory.OrdersService.core.data.OrderRepository;
import com.correct.trajectory.OrdersService.core.events.OrderApprovedEvent;
import com.correct.trajectory.OrdersService.core.events.OrderCreatedEvent;
import com.correct.trajectory.OrdersService.core.events.OrderRejectedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("order-group")
public class OrderEventsHandler {

    private final OrderRepository ordersRepository;

    public OrderEventsHandler(OrderRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @EventHandler
    public void on(OrderCreatedEvent orderCreatedEvent) {
        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(orderCreatedEvent, orderEntity);

        try {
            this.ordersRepository.save(orderEntity);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
    }

    @EventHandler
    public void on(OrderApprovedEvent orderApprovedEvent) {
        OrderEntity orderEntity = this.ordersRepository.findByOrderId(orderApprovedEvent.getOrderId());

        if (orderEntity == null) {
            return;
        }
        orderEntity.setOrderStatus(orderApprovedEvent.getOrderStatus());
        ordersRepository.save(orderEntity);
    }

    @EventHandler
    public void on(OrderRejectedEvent orderRejectedEvent) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderStatus(orderRejectedEvent.getOrderStatus());
        ordersRepository.save(orderEntity);
    }
}