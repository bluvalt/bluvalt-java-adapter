package com.stcs.spa.vo;

/**
 * Created by mroshan on 6/1/2016.
 */
public class QuantifiableItemPrice {

   private QuantifiableItem quantifiable_item;
    private  String currency;
    private Long price,min,max;

    public QuantifiableItem getQuantifiable_item() {
        return quantifiable_item;
    }

    public void setQuantifiable_item(QuantifiableItem quantifiable_item) {
        this.quantifiable_item = quantifiable_item;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getMin() {
        return min;
    }

    public void setMin(Long min) {
        this.min = min;
    }

    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }
}
