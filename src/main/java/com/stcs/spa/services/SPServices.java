package com.stcs.spa.services;


import com.google.gson.*;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;
import com.stcs.spa.services.exception.EventProcessingException;
import com.stcs.spa.services.exception.UserValidationException;
import com.stcs.spa.util.Config;
import com.stcs.spa.vo.*;
import org.apache.log4j.Logger;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public interface SPServices  {
	
	Logger logger = Logger.getLogger(SPServices.class);
	Config config = Config.getInstance();
	/**
	 * This method is used to handle the Event coming from Bluvalt. It takes the Event String (JSON) as Input.
	 * The method construct VO from JSON and make it available for Processing.	
	 * The service providers have to call their custom code from here.
	 * Once the event is parsed the SP will take the necessary actions based on the event type. 
	 * @param EventStr
	 * @return void
	 * @throws EventProcessingException
	 */
	void handleEvent(String EventStr) throws EventProcessingException ;
	/**
	 * This method is called by the OpenIDCAuthenticationFilter to validate the incoming user requesting authentication.
	 * Here the Service provider have to call thier internal user registry (DB,LDAP etc.) to verify if the user is valid.
	 * The method return Boolean indicating if the user is valid or not.
	 * @param userId
	 * @return
	 * @throws UserValidationException
	 */
	Boolean validateUser(Long userId) throws UserValidationException;
	/**
	 * The actual processing of the Event Occurs here. The input for this method could be anything. 
	 * It is the implementor choice to pass whatever he wants. 
	 * For Automated processing of Event this Method can be called from HandleEvent method to propagate the Event status to Bluvalt.
	 * The method have to call sendEventResponse to update the Event status on Bluvalt.
	 * @param data
	 * @return
	 * @throws EventProcessingException
	 */
	String processEvent(Object... data
	) throws EventProcessingException ;
	
	
	default String getAccessToken() {
		try {
			OIDCProviderMetadata providerMetadata = config.providerMetadata;
			MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
			params.add("client_id", config.properties.getProperty("client_id"));
			params.add("client_secret", config.properties.getProperty("client_secret"));
			params.add("grant_type", "client_credentials");
			RestTemplate restTemplate = new RestTemplate();
			AccessToken accessToken = restTemplate.postForObject(
					providerMetadata.getTokenEndpointURI(), params,
					AccessToken.class);
			logger.debug("Got Token:" + accessToken.getAccess_token());
			return accessToken.getAccess_token();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	default boolean sendEventResponse(Event event, EventResponse eventResponse, String accessToken) {
		try {

			Map<String, String> urlParams = new HashMap<String, String>();
			logger.debug("event_id:" + eventResponse.getEventID());
			urlParams.put("event_id", eventResponse.getEventID());
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", "Bearer " + accessToken);
			headers.setContentType(MediaType.APPLICATION_JSON);
			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<String> entity = new HttpEntity<String>(
					eventResponse.toString(), headers);
			HttpStatus status = restTemplate.exchange(
					config.properties.getProperty("event_reponse_url"), HttpMethod.PUT, entity,
					String.class, urlParams).getStatusCode();
			logger.debug("BluvaltEvent EventResponse sent. status:" + status);
			switch (status) {
			case ACCEPTED: // 202
				return true;
			case OK:       // 200
				return true;
			case CREATED:  // 201
				return true;
			default:
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	default Event constructEventType(String eventStr) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Event.class, new JsonDeserializer<Event>() {
		    public Event deserialize(JsonElement elem, Type type,
		            JsonDeserializationContext jdc)
		            throws JsonParseException  {
		        JsonObject jsonObj = elem.getAsJsonObject();
		        GsonBuilder gsonBuilder = new GsonBuilder();
		        Gson g = gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
		        Event event = g.fromJson(elem, Event.class);
		    	switch (event.getType()) {
				case SUBSCRIPTION_CREATED:
					  SubscriptionData subscription = g.fromJson(jsonObj.get("data"), SubscriptionData.class);
					logger.debug("DATA SUBSCRIPTION_CREATED:"+jsonObj.get("data"));
				      event.setData(subscription);
					break;
				case SUBSCRIPTION_CANCELED:
					 SubscriptionData subscription1 = g.fromJson(jsonObj.get("data"), SubscriptionData.class);
				      event.setData(subscription1);
					 logger.debug("DATA SUBSCRIPTION_CANCELED:"+jsonObj.get("data"));
					break;
				case SUBSCRIPTION_UPGRADED:
					break;
				case SUBSCRIPTION_DOWNGRADED:
					break;
				case SUBSCRIPTION_USER_ADDED:
					  SubscriptionUserData SubscriptionUserDataAddedEvent = g.fromJson(jsonObj.get("data"), SubscriptionUserData.class);
				      event.setData(SubscriptionUserDataAddedEvent);
					logger.debug("DATA SUBSCRIPTION_USER_ADDED:"+jsonObj.get("data"));
					break;
				case SUBSCRIPTION_USER_REMOVED:
					  SubscriptionUserData userRemoved = g.fromJson(jsonObj.get("data"), SubscriptionUserData.class);
				      event.setData(userRemoved);
					logger.debug("DATA SUBSCRIPTION_USER_REMOVED:"+jsonObj.get("data"));
					break;
				case SUBSCRIPTION_ADDON_ATTACHED:
					SubscriptionData subscriptionAddon = g.fromJson(jsonObj.get("data"), SubscriptionData.class);
				      event.setData(subscriptionAddon);
					logger.debug("DATA SUBSCRIPTION_ADDON_ATTACHED:"+jsonObj.get("data"));
				      break;
				case SUBSCRIPTION_ADDON_CANCELED:
					SubscriptionData subscriptionAddonCanceled = g.fromJson(jsonObj.get("data"), SubscriptionData.class);
				      event.setData(subscriptionAddonCanceled);
					logger.debug("DATA SUBSCRIPTION_ADDON_CANCELED:"+jsonObj.get("data"));
					break;
                case ACCOUNT_RESUMED:
                    Customer customerar= g.fromJson(jsonObj.get("data"), Customer.class);
                    event.setData(customerar);
                    logger.debug("DATA ACCOUNT_RESUMED:"+jsonObj.get("data"));
                    break;
                case ACCOUNT_SUSPENDED:
                    Customer customeras= g.fromJson(jsonObj.get("data"), Customer.class);
                    event.setData(customeras);
                    logger.debug("DATA ACCOUNT_SUSPENDED:"+jsonObj.get("data"));
                    break;
                case ACCOUNT_TERMINATED:
                    Customer customerat= g.fromJson(jsonObj.get("data"), Customer.class);
                    event.setData(customerat);
                    logger.debug("DATA ACCOUNT_TERMINATED:"+jsonObj.get("data"));
                    break;
                case WEBHOOK_TEST:
                break;
				default:
					break;
				}
		        return event;
		    }
		});
	    Gson gson =gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
		return gson.fromJson(eventStr, Event.class);
	}
	
	
}
