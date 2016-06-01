package com.stcs.spa.vo;

import java.util.Date;
import java.util.Map;

public class Plan extends Base {

	private int id;
	private String name,description;
	private Date created,modified;
	private String initial_price;
	private Feature[] features;
	private Price[] prices;
	private PlanItem[] plan_item;
	private Map<String,String> metadata;
	private Type type;
	/**
	 * @return the id
	 */
	@org.codehaus.jackson.annotate.JsonIgnore
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
	 * @return the created
	 */
	@org.codehaus.jackson.annotate.JsonIgnore
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
	@org.codehaus.jackson.annotate.JsonIgnore
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
	 * @return the initial_price
	 */
	public String getInitial_price() {
		return initial_price;
	}
	/**
	 * @param initial_price the initial_price to set
	 */
	public void setInitial_price(String initial_price) {
		this.initial_price = initial_price;
	}
	/**
	 * @return the features
	 */
    @org.codehaus.jackson.annotate.JsonIgnore
	public Feature[] getFeatures() {
		return features;
	}
	/**
	 * @param features the features to set
	 */
	public void setFeatures(Feature[] features) {
		this.features = features;
	}
	/**
	 * @return the prices
	 */
	
	public Price[] getPrices() {
		return prices;
	}
	/**
	 * @param prices the prices to set
	 */
	public void setPrices(Price[] prices) {
		this.prices = prices;
	}
	/**
	 * @return the plan_item
	 */
    @org.codehaus.jackson.annotate.JsonIgnore
	public PlanItem[] getPlan_item() {
		return plan_item;
	}
	/**
	 * @param plan_item the plan_item to set
	 */
	public void setPlan_item(PlanItem[] plan_item) {
		this.plan_item = plan_item;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	
	public Map<String, String> getMetadata() {
		return metadata;
	}
	
	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public enum Type {
		base,addon
	}
}
