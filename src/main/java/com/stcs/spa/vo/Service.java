package com.stcs.spa.vo;

import java.util.Date;

public class Service extends BaseVO {

	private int id;
	private String name,description,meta_data,status,url;
	private Date created,modified;
	private Item[] items;
	private Feature[] featurs;
	private Plan[] plans;
	private Bundle[] bundles;
	private Addon[] add_ons;
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
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
	 * @return the items
	 */
	public Item[] getItems() {
		return items;
	}
	/**
	 * @param items the items to set
	 */
	public void setItems(Item[] items) {
		this.items = items;
	}
	/**
	 * @return the featurs
	 */
	public Feature[] getFeaturs() {
		return featurs;
	}
	/**
	 * @param featurs the featurs to set
	 */
	public void setFeaturs(Feature[] featurs) {
		this.featurs = featurs;
	}
	/**
	 * @return the plans
	 */
	public Plan[] getPlans() {
		return plans;
	}
	/**
	 * @param plans the plans to set
	 */
	public void setPlans(Plan[] plans) {
		this.plans = plans;
	}
	/**
	 * @return the bundles
	 */
	public Bundle[] getBundles() {
		return bundles;
	}
	/**
	 * @param bundles the bundles to set
	 */
	public void setBundles(Bundle[] bundles) {
		this.bundles = bundles;
	}
	/**
	 * @return the add_ons
	 */
	public Addon[] getAdd_ons() {
		return add_ons;
	}
	/**
	 * @param add_ons the add_ons to set
	 */
	public void setAdd_ons(Addon[] add_ons) {
		this.add_ons = add_ons;
	}
	
	
}
