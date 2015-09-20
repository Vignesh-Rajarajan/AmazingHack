package com.amazon.hackathon.csv.core;

import java.util.List;

import com.amazon.hackathon.csv.model.ProductItem;

public interface NotificationEngine {
	public void ProcessUpdates(List<ProductItem> itemsList);
}
