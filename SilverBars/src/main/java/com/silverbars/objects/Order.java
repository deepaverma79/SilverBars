package com.silverbars.objects;

import com.silverbars.repository.OrderRepository;

/**
 * This class is a representation of order information.
 * @author Deepa
 *
 */
public class Order {

	private final int orderId;
	private final String userId;
	private final double quantity;
	private final double price;
	private final OrderType orderType;
	private volatile OrderStatus orderStatus;

	public Order(String userId, double quantity, double price, OrderType orderType) {
		super();
		this.userId = userId;
		this.quantity = quantity;
		this.price = price;
		this.orderType = orderType;
		this.orderStatus = OrderStatus.ACTIVE;
		this.orderId = OrderRepository.getNextOrderId();
	}

	public int getOrderId() {
		return orderId;
	}

	public String getUserId() {
		return userId;
	}

	public double getQuantity() {
		return quantity;
	}

	public double getPrice() {
		return price;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
}
