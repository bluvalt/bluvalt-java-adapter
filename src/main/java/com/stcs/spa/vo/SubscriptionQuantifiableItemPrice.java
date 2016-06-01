package com.stcs.spa.vo;

/**
 * Created by mroshan on 6/1/2016.
 */
public class SubscriptionQuantifiableItemPrice {

    private QuantifiableItemPrice quantifiable_item_price;
    private Long  quantity;

    public QuantifiableItemPrice getQuantifiable_item_price() {
        return quantifiable_item_price;
    }

    public void setQuantifiable_item_price(QuantifiableItemPrice quantifiable_item_price) {
        this.quantifiable_item_price = quantifiable_item_price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
