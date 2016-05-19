package com.stcs.spa.vo;



public class SubscriptionUserAddedRemovedData extends EventData {

	private Long id,subscription,user;	
	private boolean admin;
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
	 * @return the subscription
	 */
	public Long getSubscription() {
		return subscription;
	}
	/**
	 * @param subscription the subscription to set
	 */
	public void setSubscription(Long subscription) {
		this.subscription = subscription;
	}
	/**
	 * @return the user
	 */
	public Long getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(Long user) {
		this.user = user;
	}
	/**
	 * @return the admin
	 */
	public boolean isAdmin() {
		return admin;
	}
	/**
	 * @param admin the admin to set
	 */
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	
	
}
	
	
