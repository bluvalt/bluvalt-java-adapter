package com.stcs.spa.vo;

public class EventResponse {


	private String eventID,status,ref_number,message;
	private EventType type;
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRef_number() {
		return ref_number;
	}

	public void setRef_number(String ref_number) {
		this.ref_number = ref_number;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "{ \"status\":\"" + status + "\", \"ref_number\":\"" + ref_number
				+ "\", \"message\":\"" + message + "\"}";
	}

	/**
	 * @return the eventID
	 */
	public String getEventID() {
		return eventID;
	}

	/**
	 * @param eventID the eventID to set
	 */
	public void setEventID(String eventID) {
		this.eventID = eventID;
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
