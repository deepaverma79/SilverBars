package com.silverbars.controller;

import java.util.Optional;
import java.util.Set;

import com.silverbars.objects.Order;
import com.silverbars.objects.OrderStatus;
import com.silverbars.objects.OrderSummary;
import com.silverbars.objects.OrderType;
import com.silverbars.repository.OrderRepository;

/**
 * This is a controller class all order related information. Responsibility of
 * this class is to orchestrate and maintain all order information in the
 * system.
 * 
 * @author Deepa
 *
 */
public class OrderController {

	/**
	 * This method is to generate and register a new order in the system. If the
	 * order is successfully generated and registered , then the order object is
	 * returned. If there was a problem generating the order, an Empty Optional
	 * would be returned.
	 */
	public Optional<Order> registerOrder(String userId, double quantity, double price, OrderType orderType) {
		if(userId==null || quantity <=0 || price <=0 || orderType==null) {
			return Optional.empty();
		}
		Order order = new Order(userId, quantity, price, orderType);
		return OrderRepository.getInstance().add(order) ? Optional.of(order) : Optional.empty();
	}

	/**
	 * This method is responsible for cancelling the order if the order exists
	 * in the system and it is not completed.
	 */
	public boolean cancelOrder(int orderId) {
		return OrderRepository.getInstance().cancelOrder(orderId);
	}

	/**
	 * This method generates the summary of the active orders that exist in the system.
	 */
	public Set<OrderSummary> getActiveOrderSummary() {
		return OrderRepository.getInstance().generateOrderSummary(OrderStatus.ACTIVE);
	}
}
