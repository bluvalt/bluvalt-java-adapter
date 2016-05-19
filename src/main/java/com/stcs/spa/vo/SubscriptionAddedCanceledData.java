package com.stcs.spa.vo;

import java.util.Date;
import java.util.Map;




public class SubscriptionAddedCanceledData extends EventData {

	private Long id;	
	private String status,cancel_reason,name;
	private Date start,canceled_at,created;
	private Customer customer;
	private Price price;
	private Plan plan;
	private Long[] admin_users;
	private String[] admin_users_usernames;
	private Map<String,Map<String,String>> extra_fields;
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	
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
	 * @return the cancel_reason
	 */
	public String getCancel_reason() {
		return cancel_reason;
	}
	/**
	 * @param cancel_reason the cancel_reason to set
	 */
	public void setCancel_reason(String cancel_reason) {
		this.cancel_reason = cancel_reason;
	}
	/**
	 * @return the start
	 */
	public Date getStart() {
		return start;
	}
	/**
	 * @param start the start to set
	 */
	public void setStart(Date start) {
		this.start = start;
	}
	/**
	 * @return the canceled_at
	 */
	public Date getCanceled_at() {
		return canceled_at;
	}
	/**
	 * @param canceled_at the canceled_at to set
	 */
	public void setCanceled_at(Date canceled_at) {
		this.canceled_at = canceled_at;
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
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}
	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
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
	/**
	 * @return the plan
	 */
	public Plan getPlan() {
		return plan;
	}
	/**
	 * @param plan the plan to set
	 */
	public void setPlan(Plan plan) {
		this.plan = plan;
	}
	/**
	 * @return the admin_users
	 */
	public Long[] getAdmin_users() {
		return admin_users;
	}
	/**
	 * @param admin_users the admin_users to set
	 */
	public void setAdmin_users(Long[] admin_users) {
		this.admin_users = admin_users;
	}
	/**
	 * @return the admin_users_usernames
	 */
	public String[] getAdmin_users_usernames() {
		return admin_users_usernames;
	}
	/**
	 * @param admin_users_usernames the admin_users_usernames to set
	 */
	public void setAdmin_users_usernames(String[] admin_users_usernames) {
		this.admin_users_usernames = admin_users_usernames;
	}
	/**
	 * @return the extra_fields
	 */
	public Map<String,Map<String,String>> getExtra_fields() {
		return extra_fields;
	}
	/**
	 * @param extra_fields the extra_fields to set
	 */
	public void setExtra_fields(Map<String,Map<String,String>> extra_fields) {
		this.extra_fields = extra_fields;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
	
	
