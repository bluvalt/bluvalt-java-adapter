package com.stcs.spa.vo;

import java.util.Date;


public class SubscriptionAddonAttachedCanceled extends EventData {

	private Long id;	
	private String status,cancel_reason;
	private Date start,canceled_at,created;
	private SubscriptionAddedCanceledData  subscription ;
	private Addon  addon ;
	private Price price;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * @return the subscription
	 */
	public SubscriptionAddedCanceledData getSubscription() {
		return subscription;
	}
	/**
	 * @param subscription the subscription to set
	 */
	public void setSubscription(SubscriptionAddedCanceledData subscription) {
		this.subscription = subscription;
	}
	/**
	 * @return the addon
	 */
	public Addon getAddon() {
		return addon;
	}
	/**
	 * @param addon the addon to set
	 */
	public void setAddon(Addon addon) {
		this.addon = addon;
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
