package com.silverbars.objects;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import com.silverbars.objects.OrderSummary.OrderSummaryKey;

import org.junit.Assert;

public class OrderSummaryTest {

	@Test
	public void testOrderSummaryKeyComparator() {
		OrderSummaryKey o1 = new OrderSummaryKey(OrderType.BUY, 345.00);
		OrderSummaryKey o2 = new OrderSummaryKey(OrderType.BUY, 345.00);
		OrderSummaryKey o3 = new OrderSummaryKey(OrderType.BUY, 123.00);
		OrderSummaryKey o4 = new OrderSummaryKey(OrderType.BUY, 678.00);
		OrderSummaryKey o5 = new OrderSummaryKey(OrderType.SELL, 345.00);
		OrderSummaryKey o6 = new OrderSummaryKey(OrderType.SELL, 678.00);
		OrderSummaryKey o7 = new OrderSummaryKey(OrderType.SELL, 989.00);

		Set<OrderSummaryKey> orderSummaryKeys = new TreeSet<>();
		orderSummaryKeys.addAll(Arrays.asList(o1, o2, o3, o4, o5, o6, o7));

		orderSummaryKeys.forEach(a -> System.out.println(a));
		Iterator<OrderSummaryKey> iterator = orderSummaryKeys.iterator();
		Assert.assertEquals(o4.getPrice(), iterator.next().getPrice());
		Assert.assertEquals(o1.getPrice(), iterator.next().getPrice());
		Assert.assertEquals(o3.getPrice(), iterator.next().getPrice());
		Assert.assertEquals(o5.getPrice(), iterator.next().getPrice());
		Assert.assertEquals(o6.getPrice(), iterator.next().getPrice());
		Assert.assertEquals(o7.getPrice(), iterator.next().getPrice());
	}

	@Test
	public void testOrderSummaryComparator() {
		OrderSummaryKey o1 = new OrderSummaryKey(OrderType.BUY, 345.00);
		OrderSummary os1 = new OrderSummary(o1, 1500.00);//3
		OrderSummaryKey o2 = new OrderSummaryKey(OrderType.BUY, 345.00);
		OrderSummary os2 = new OrderSummary(o2, 500.00); //2
		OrderSummaryKey o3 = new OrderSummaryKey(OrderType.BUY, 123.00);
		OrderSummary os3 = new OrderSummary(o3, 2500.00);//4
		OrderSummaryKey o4 = new OrderSummaryKey(OrderType.BUY, 678.00);
		OrderSummary os4 = new OrderSummary(o4, 5200.00);//1
		OrderSummaryKey o5 = new OrderSummaryKey(OrderType.SELL, 345.00);
		OrderSummary os5 = new OrderSummary(o5, 5100.00);//5
		OrderSummaryKey o6 = new OrderSummaryKey(OrderType.SELL, 678.00);
		OrderSummary os6 = new OrderSummary(o6, 3000.00);//6
		OrderSummaryKey o7 = new OrderSummaryKey(OrderType.SELL, 989.00);
		OrderSummary os7 = new OrderSummary(o7, 8000.00);//7

		Set<OrderSummary> orderSummary = new TreeSet<>();
		orderSummary.addAll(Arrays.asList(os1, os2, os3, os4, os5, os6, os7));

		orderSummary.forEach(a -> System.out.println(a));
		Iterator<OrderSummary> iterator = orderSummary.iterator();
		Assert.assertEquals(os4.getQuantity(), iterator.next().getQuantity());
		Assert.assertEquals(os2.getQuantity(), iterator.next().getQuantity());
		Assert.assertEquals(os1.getQuantity(), iterator.next().getQuantity());
		Assert.assertEquals(os3.getQuantity(), iterator.next().getQuantity());
		Assert.assertEquals(os5.getQuantity(), iterator.next().getQuantity());
		Assert.assertEquals(os6.getQuantity(), iterator.next().getQuantity());
		Assert.assertEquals(os7.getQuantity(), iterator.next().getQuantity());
	}

}
