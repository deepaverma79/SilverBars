package com.silverbars.objects;

/**
 * This is a representation of Order Summary class.
 * 
 * Please note : BUY and SELL orders will NOT be netted against each other , as that would cause a loss of information.
 * The question doesnt give any specific instructions about netting hence I have made the assumption of not netting the fields.
 * @author Deepa
 *
 */
public class OrderSummary implements Comparable<OrderSummary> {
	public static class OrderSummaryKey implements Comparable<OrderSummaryKey> {
		private final OrderType orderType;
		private final Double price;

		public OrderSummaryKey(OrderType orderType, Double price) {
			super();
			this.orderType = orderType;
			this.price = price;
		}

		public OrderType getOrderType() {
			return orderType;
		}

		public Double getPrice() {
			return price;
		}

		@Override
		public int compareTo(OrderSummaryKey o) {
			int i = this.getOrderType().compareTo(o.getOrderType());
			if (i == 0) {
				if (this.getOrderType() == OrderType.SELL)
					i = (this.getPrice().compareTo(o.getPrice()));
				else 
					i = (o.getPrice().compareTo(this.getPrice()));
			}
			return i;
		}

		@Override
		public String toString() {
			return "OrderSummaryKey [orderType=" + orderType + ", price=" + price + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((orderType == null) ? 0 : orderType.hashCode());
			result = prime * result + ((price == null) ? 0 : price.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			OrderSummaryKey other = (OrderSummaryKey) obj;
			if (orderType != other.orderType)
				return false;
			if (price == null) {
				if (other.price != null)
					return false;
			} else if (!price.equals(other.price))
				return false;
			return true;
		}
	}

	public OrderSummary(OrderSummaryKey orderSummaryKey, Double quantity) {
		super();
		this.orderSummaryKey = orderSummaryKey;
		this.quantity = quantity;
	}

	private OrderSummaryKey orderSummaryKey;
	private Double quantity;

	public OrderSummaryKey getOrderSummaryKey() {
		return orderSummaryKey;
	}

	public Double getQuantity() {
		return quantity;
	}

	public OrderSummary addQuantity(Double quantity) {
		this.quantity = this.quantity + quantity;
		return this;
	}

	@Override
	public int compareTo(OrderSummary o) {
		int i = this.getOrderSummaryKey().compareTo(o.getOrderSummaryKey());
		if (i == 0) {
			i = this.quantity.compareTo(o.getQuantity());
		}
		return i;
	}

	@Override
	public String toString() {
		return  this.orderSummaryKey.getOrderType() +" : " + this.getQuantity() + " for £" + this.orderSummaryKey.getPrice() ;
	}
}
