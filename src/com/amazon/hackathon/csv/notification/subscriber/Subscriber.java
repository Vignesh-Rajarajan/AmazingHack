package com.amazon.hackathon.csv.notification.subscriber;

import java.util.List;

import com.amazon.hackathon.csv.model.ProductItem;

public interface Subscriber {
	public void execute(List<ProductItem> itemsList);
}
