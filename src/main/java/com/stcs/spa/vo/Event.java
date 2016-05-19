package com.stcs.spa.vo;

public class Event {
	
	private String id,api_version;
	private EventType type;
	private EventService service;
	private EventData data;
	

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * @return the api_version
	 */
	public String getApi_version() {
		return api_version;
	}
	/**
	 * @param api_version the api_version to set
	 */
	public void setApi_version(String api_version) {
		this.api_version = api_version;
	}
	/**
	 * @return the serviceId
	 */
	public EventService getService() {
		return service;
	}

	public void setService(EventService service) {
		this.service = service;
	}

	/**
	 * @return the data
	 */
	public EventData getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(EventData data) {
		this.data = data;
	}
	/**
	 * @return the type
	 */
	public EventType getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(EventType type) {
		this.type = type;
	}
	
	
	
	
}
