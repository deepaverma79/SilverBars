package com.silverbars.controller;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;
import com.silverbars.objects.Order;
import com.silverbars.objects.OrderSummary;
import com.silverbars.objects.OrderType;

public class ObjectControllerTest {
	
	@Test
	public void testRegisterAndCancelOrder() {
		OrderController orderController = new OrderController();
		Optional<Order> ord = orderController.registerOrder("USER1", 3.5, 306, OrderType.SELL);
		Assert.assertTrue(ord.isPresent());
		Assert.assertTrue("Order Id is not null", ord.get().getOrderId()!=0);
		Assert.assertTrue(orderController.cancelOrder(ord.get().getOrderId()));
	}
	
	@Test
	public void testActiveOrderSummary() {
		OrderController orderController = new OrderController();
		Order ord1 = orderController.registerOrder("USER1", 3.5, 306, OrderType.SELL).get();
		Order ord2 =orderController.registerOrder("USER2", 1.2, 310, OrderType.SELL).get();
		Order ord3 =orderController.registerOrder("USER3", 1.5, 307, OrderType.SELL).get();
		Order ord4 =orderController.registerOrder("USER4", 2.0, 306, OrderType.SELL).get();
		
		Set<OrderSummary> orderSummaries = orderController.getActiveOrderSummary();
		orderSummaries.forEach(a -> System.out.println(a));
		
		Assert.assertEquals(3, orderSummaries.size());
		
		Iterator<OrderSummary> itr = orderSummaries.iterator();
		Assert.assertEquals(5.5, itr.next().getQuantity(), 0.0);
		Assert.assertEquals(1.5, itr.next().getQuantity(), 0.0);
		Assert.assertEquals(1.2, itr.next().getQuantity(), 0.0);
		Assert.assertTrue(orderController.cancelOrder(ord1.getOrderId()));
		Assert.assertTrue(orderController.cancelOrder(ord2.getOrderId()));
		Assert.assertTrue(orderController.cancelOrder(ord3.getOrderId()));
		Assert.assertTrue(orderController.cancelOrder(ord4.getOrderId()));
	}
	
	@Test
	public void testActiveOrderSummary2() {
		OrderController orderController = new OrderController();
		Order ord1 =orderController.registerOrder("USER1", 3.5, 306, OrderType.SELL).get();
		Order ord2 =orderController.registerOrder("USER2", 1.2, 310, OrderType.SELL).get();
		Order ord3 =orderController.registerOrder("USER3", 1.5, 307, OrderType.SELL).get();
		Order ord4 =orderController.registerOrder("USER4", 2.0, 306, OrderType.SELL).get();
		Order ord5 =orderController.registerOrder("USER3", 1.5, 307, OrderType.BUY).get();
		Order ord6 =orderController.registerOrder("USER4", 2.0, 306, OrderType.BUY).get();
		Order ord7 =orderController.registerOrder("USER4", 2.0, 306, OrderType.BUY).get();
		
		Set<OrderSummary> orderSummaries = orderController.getActiveOrderSummary();
		orderSummaries.forEach(a -> System.out.println(a));
		
		Assert.assertEquals(5, orderSummaries.size());
		
		Iterator<OrderSummary> itr = orderSummaries.iterator();
		OrderSummary os1 = itr.next();
		Assert.assertEquals(307, os1.getOrderSummaryKey().getPrice(), 0.0);
		Assert.assertEquals(OrderType.BUY, os1.getOrderSummaryKey().getOrderType());
		Assert.assertEquals(1.5, os1.getQuantity(), 0.0);
		
		OrderSummary os2 = itr.next();
		Assert.assertEquals(306, os2.getOrderSummaryKey().getPrice(), 0.0);
		Assert.assertEquals(OrderType.BUY, os2.getOrderSummaryKey().getOrderType());
		Assert.assertEquals(4.0, os2.getQuantity(), 0.0);
		
		OrderSummary os3 = itr.next();
		Assert.assertEquals(306, os3.getOrderSummaryKey().getPrice(), 0.0);
		Assert.assertEquals(OrderType.SELL, os3.getOrderSummaryKey().getOrderType());
		Assert.assertEquals(5.5, os3.getQuantity(), 0.0);
		
		OrderSummary os4 = itr.next();
		Assert.assertEquals(307, os4.getOrderSummaryKey().getPrice(), 0.0);
		Assert.assertEquals(OrderType.SELL, os4.getOrderSummaryKey().getOrderType());
		Assert.assertEquals(1.5, os4.getQuantity(), 0.0);
		
		OrderSummary os5 = itr.next();
		Assert.assertEquals(310, os5.getOrderSummaryKey().getPrice(), 0.0);
		Assert.assertEquals(OrderType.SELL, os5.getOrderSummaryKey().getOrderType());
		Assert.assertEquals(1.2, os5.getQuantity(), 0.0);
		
		Assert.assertTrue(orderController.cancelOrder(ord1.getOrderId()));
		Assert.assertTrue(orderController.cancelOrder(ord2.getOrderId()));
		Assert.assertTrue(orderController.cancelOrder(ord3.getOrderId()));
		Assert.assertTrue(orderController.cancelOrder(ord4.getOrderId()));
		Assert.assertTrue(orderController.cancelOrder(ord5.getOrderId()));
		Assert.assertTrue(orderController.cancelOrder(ord6.getOrderId()));
		Assert.assertTrue(orderController.cancelOrder(ord7.getOrderId()));
	}
}
