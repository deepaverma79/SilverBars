package com.silverbars.repository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;
import com.silverbars.objects.Order;
import com.silverbars.objects.OrderStatus;
import com.silverbars.objects.OrderSummary;
import com.silverbars.objects.OrderSummary.OrderSummaryKey;

/**
 * This is a Singleton main Order Repository class which maintains all the orders and
 * their state as it exists in the system.
 * 
 * Ideally all the order information should be stored in database and I would usually not need to have a singleton class.
 * However for this exercise, I need to maintain a central repository for all Orders in system and hence I have created this singleton.
 * 
 * @author Deepa
 *
 */
public class OrderRepository {

	private static final OrderRepository INSTANCE = new OrderRepository();
	private static volatile int nextOrderId;
	
	private Map<Integer, Order> ordersByOrderId = new HashMap<>();
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	private OrderRepository() {
		// There cannot be two Order Repositories in the system.
	}

	public static OrderRepository getInstance() {
		return INSTANCE;
	}
	
	public static int getNextOrderId() {
		return ++nextOrderId;
	}
	
	public Order getOrdersByOrderId(int orderId) {
		return ordersByOrderId.get(orderId);
	}

	public boolean add(Order order) {
		lock.writeLock().lock();
		try {
			 ordersByOrderId.put(order.getOrderId(), order);
		}  finally {
			lock.writeLock().unlock();
		}
		return true;
	}

	public boolean cancelOrder(int orderId) {
		boolean cancelled = false;
		Order order = ordersByOrderId.get(orderId);
		if (order != null && order.getOrderStatus() != OrderStatus.COMPLETED) {
			lock.writeLock().lock();
			try {
				order.setOrderStatus(OrderStatus.CANCELLED);
				cancelled = true;
			} finally {
				lock.writeLock().unlock();
			}
		}
		return cancelled;
	}

	public Set<OrderSummary> generateOrderSummary(OrderStatus orderStatus) {
		lock.readLock().lock();
		try {
			List<Order> orders = ordersByOrderId.values().stream().collect(Collectors.groupingBy(Order::getOrderStatus)).get(orderStatus);
			Map<OrderSummaryKey, OrderSummary> orderSummary = new HashMap<>();
			orders.forEach(
					order -> orderSummary.merge(new OrderSummaryKey(order.getOrderType(), order.getPrice()), 
												new OrderSummary(new OrderSummaryKey(order.getOrderType(), order.getPrice()), order.getQuantity()),
												(a,b) -> a.addQuantity(b.getQuantity())));
			return orderSummary.values().stream()
					.sorted(Comparator.comparing(OrderSummary::getOrderSummaryKey).thenComparing(OrderSummary::getQuantity))
					.collect(TreeSet::new, TreeSet::add, TreeSet::addAll); 
		} finally {
			lock.readLock().unlock();
		}
	}
}
