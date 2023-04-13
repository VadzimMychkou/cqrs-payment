package com.correct.trajectory.OrdersService.query;

import lombok.Value;

@Value
public class FindOrderQuery {
    private final String orderId;
}
