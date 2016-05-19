package com.stcs.spa.vo;

import java.util.Date;


public class Addon extends BaseVO {

	private int id,maximum_allowed_subscriptions,service_id;
	private String name,description,meta_data,quantity;
	private Date created,modified;
	private Item Item;
	private Unit unit;
	private Price price;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the maximum_allowed_subscriptions
	 */
	public int getMaximum_allowed_subscriptions() {
		return maximum_allowed_subscriptions;
	}
	/**
	 * @param maximum_allowed_subscriptions the maximum_allowed_subscriptions to set
	 */
	public void setMaximum_allowed_subscriptions(int maximum_allowed_subscriptions) {
		this.maximum_allowed_subscriptions = maximum_allowed_subscriptions;
	}
	/**
	 * @return the quantity
	 */
	public String getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the service_id
	 */
	public int getService_id() {
		return service_id;
	}
	/**
	 * @param service_id the service_id to set
	 */
	public void setService_id(int service_id) {
		this.service_id = service_id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the meta_data
	 */
	public String getMeta_data() {
		return meta_data;
	}
	/**
	 * @param meta_data the meta_data to set
	 */
	public void setMeta_data(String meta_data) {
		this.meta_data = meta_data;
	}
	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}
	/**
	 * @param created the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}
	/**
	 * @return the modified
	 */
	public Date getModified() {
		return modified;
	}
	/**
	 * @param modified the modified to set
	 */
	public void setModified(Date modified) {
		this.modified = modified;
	}
	/**
	 * @return the item
	 */
	public Item getItem() {
		return Item;
	}
	/**
	 * @param item the item to set
	 */
	public void setItem(Item item) {
		Item = item;
	}
	/**
	 * @return the unit
	 */
	public Unit getUnit() {
		return unit;
	}
	/**
	 * @param unit the unit to set
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	/**
	 * @return the price
	 */
	public Price getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(Price price) {
		this.price = price;
	}
	
	
}
	
	
