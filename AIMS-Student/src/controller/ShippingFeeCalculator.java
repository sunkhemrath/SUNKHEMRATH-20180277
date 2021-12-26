package controller;

import entity.order.Order;

/**
 * Shipping interface
 */
public interface ShippingFeeCalculator{
   int calculateShippingFee(Order order);
}