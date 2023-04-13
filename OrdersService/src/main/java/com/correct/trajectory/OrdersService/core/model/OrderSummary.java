package com.correct.trajectory.OrdersService.core.model;

import lombok.Value;

@Value
public class OrderSummary {
    private final String orderId;
    private final OrderStatus orderStatus;
    private final String message;
}
