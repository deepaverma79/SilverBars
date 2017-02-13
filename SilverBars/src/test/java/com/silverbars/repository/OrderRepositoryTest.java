package com.silverbars.repository;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.silverbars.objects.Order;
import com.silverbars.objects.OrderStatus;
import com.silverbars.objects.OrderSummary;
import com.silverbars.objects.OrderType;

import static org.mockito.Mockito.*;

import java.util.Iterator;
import java.util.Set;

public class OrderRepositoryTest {

	@Test
	public void testAddOrder() {
        Order order1 = Mockito.mock(Order.class);
        when(order1.getOrderId()).thenReturn(1);
        when(order1.getOrderStatus()).thenReturn(OrderStatus.ACTIVE);
        when(order1.getOrderType()).thenReturn(OrderType.BUY);
        when(order1.getQuantity()).thenReturn(100.00);
      
        Order order2 = Mockito.mock(Order.class);
        when(order2.getOrderId()).thenReturn(2);
        when(order2.getOrderStatus()).thenReturn(OrderStatus.COMPLETED);
        when(order2.getOrderType()).thenReturn(OrderType.BUY);
        when(order2.getQuantity()).thenReturn(100.00);
        
        OrderRepository orderRepository = OrderRepository.getInstance();
        orderRepository.add(order1);
        orderRepository.add(order2);
        
        Assert.assertEquals(order1, orderRepository.getOrdersByOrderId(1));
        Assert.assertEquals(order2, orderRepository.getOrdersByOrderId(2));
		
	}
	
	@Test
	public void testCancelOrder() {
        Order order1 = Mockito.mock(Order.class);
        when(order1.getOrderId()).thenReturn(1);
        when(order1.getOrderStatus()).thenReturn(OrderStatus.ACTIVE);
        when(order1.getOrderType()).thenReturn(OrderType.BUY);
        when(order1.getQuantity()).thenReturn(100.00);
      
        Order order2 = Mockito.mock(Order.class);
        when(order2.getOrderId()).thenReturn(2);
        when(order2.getOrderStatus()).thenReturn(OrderStatus.COMPLETED);
        when(order2.getOrderType()).thenReturn(OrderType.BUY);
        when(order2.getQuantity()).thenReturn(100.00);
        
        OrderRepository orderRepository = OrderRepository.getInstance();
        orderRepository.add(order1);
        orderRepository.add(order2);
        
        Assert.assertTrue(orderRepository.cancelOrder(1));
        Assert.assertFalse(orderRepository.cancelOrder(2));
        Assert.assertFalse(orderRepository.cancelOrder(10));
	}
	
	@Test
	public void testGenerateOrderSummary() {
		// Should be returned combined with 3
        Order order1 = Mockito.mock(Order.class);
        when(order1.getOrderId()).thenReturn(1);
        when(order1.getOrderStatus()).thenReturn(OrderStatus.ACTIVE);
        when(order1.getOrderType()).thenReturn(OrderType.BUY);
        when(order1.getQuantity()).thenReturn(100.00);
        when(order1.getPrice()).thenReturn(50.00);
        
        // Should not be returned
        Order order2 = Mockito.mock(Order.class);
        when(order2.getOrderId()).thenReturn(2);
        when(order2.getOrderStatus()).thenReturn(OrderStatus.COMPLETED);
        when(order2.getOrderType()).thenReturn(OrderType.SELL);
        when(order2.getQuantity()).thenReturn(100.00);
        when(order2.getPrice()).thenReturn(30.00);
        
    	// Should be returned combined with 1
        Order order3 = Mockito.mock(Order.class);
        when(order3.getOrderId()).thenReturn(3);
        when(order3.getOrderStatus()).thenReturn(OrderStatus.ACTIVE);
        when(order3.getOrderType()).thenReturn(OrderType.BUY);
        when(order3.getQuantity()).thenReturn(100.00);
        when(order3.getPrice()).thenReturn(50.00);

    	// Should be returned 
        Order order4 = Mockito.mock(Order.class);
        when(order4.getOrderId()).thenReturn(4);
        when(order4.getOrderStatus()).thenReturn(OrderStatus.ACTIVE);
        when(order4.getOrderType()).thenReturn(OrderType.BUY);
        when(order4.getQuantity()).thenReturn(100.00);
        when(order4.getPrice()).thenReturn(40.00);

        // Should be returned 
        Order order5 = Mockito.mock(Order.class);
        when(order5.getOrderId()).thenReturn(5);
        when(order5.getOrderStatus()).thenReturn(OrderStatus.ACTIVE);
        when(order5.getOrderType()).thenReturn(OrderType.SELL);
        when(order5.getQuantity()).thenReturn(100.00);
        when(order5.getPrice()).thenReturn(40.00);

        OrderRepository orderRepository = OrderRepository.getInstance();
        orderRepository.add(order1);
        orderRepository.add(order2);
        orderRepository.add(order3);
        orderRepository.add(order4);
        orderRepository.add(order5);
        
		Set<OrderSummary> summaries = orderRepository.generateOrderSummary(OrderStatus.ACTIVE);
		summaries.forEach(a -> System.out.println(a));
		Assert.assertEquals(3, summaries.size());
		Iterator<OrderSummary> itr = summaries.iterator();
		OrderSummary os1 = itr.next();
		Assert.assertEquals(OrderType.BUY , os1.getOrderSummaryKey().getOrderType());
		Assert.assertEquals(50.00 , os1.getOrderSummaryKey().getPrice(), 0.00);
		Assert.assertEquals(200.00 , os1.getQuantity(), 0.00);
	
		OrderSummary os2 = itr.next();
		Assert.assertEquals(OrderType.BUY , os2.getOrderSummaryKey().getOrderType());
		Assert.assertEquals(40.00 , os2.getOrderSummaryKey().getPrice(), 0.00);
		Assert.assertEquals(100.00 , os2.getQuantity(), 0.00);
		
		OrderSummary os3 = itr.next();
		Assert.assertEquals(OrderType.SELL , os3.getOrderSummaryKey().getOrderType());
		Assert.assertEquals(40.00 , os3.getOrderSummaryKey().getPrice(), 0.00);
		Assert.assertEquals(100.00 , os3.getQuantity(), 0.00);
	}
}
