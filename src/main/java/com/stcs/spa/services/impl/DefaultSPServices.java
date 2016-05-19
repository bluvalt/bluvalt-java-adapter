package com.stcs.spa.services.impl;

import com.stcs.spa.services.SPServices;
import com.stcs.spa.services.exception.EventProcessingException;
import com.stcs.spa.services.exception.UserValidationException;
import com.stcs.spa.util.Config;
import com.stcs.spa.vo.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@Qualifier("defaultSPServices")
public class DefaultSPServices implements SPServices {


    private static final Logger logger = Logger
            .getLogger(DefaultSPServices.class);
    Config config = Config.getInstance();

    @Override
    public void handleEvent(String EventStr)
            throws EventProcessingException {
        Event event = constructEventType(EventStr);
        EventData eventData = null;
        Long subscriptionId = null;
        Long bluevaltUserId = null;
        String customerName = "";
        switch (event.getType()) {
            case SUBSCRIPTION_CREATED:
                eventData = (SubscriptionAddedCanceledData) event.getData();
                subscriptionId = ((SubscriptionAddedCanceledData) event.getData())
                        .getId();
                bluevaltUserId = ((SubscriptionAddedCanceledData) event.getData())
                        .getAdmin_users()[0];
                customerName = ((SubscriptionAddedCanceledData) event.getData())
                        .getCustomer().getName();
                break;
            case SUBSCRIPTION_CANCELED:
                eventData = (SubscriptionAddedCanceledData) event.getData();
                subscriptionId = ((SubscriptionAddedCanceledData) event.getData())
                        .getId();
                customerName = ((SubscriptionAddedCanceledData) event.getData())
                        .getCustomer().getName();
                break;
            case SUBSCRIPTION_UPGRADED:
                break;
            case SUBSCRIPTION_DOWNGRADED:
                break;
            case SUBSCRIPTION_USER_ADDED:
                eventData = (SubscriptionUserAddedRemovedData) event.getData();
                subscriptionId = ((SubscriptionUserAddedRemovedData) event
                        .getData()).getSubscription();
                break;
            case SUBSCRIPTION_ADDON_ATTACHED:
                eventData = (SubscriptionAddonAttachedCanceled) event.getData();
                subscriptionId = ((SubscriptionAddonAttachedCanceled) event
                        .getData()).getSubscription().getId();
                customerName = ((SubscriptionAddonAttachedCanceled) event
                        .getData()).getSubscription().getCustomer().getName();
                break;
            case SUBSCRIPTION_ADDON_CANCELED:
                eventData = (SubscriptionAddonAttachedCanceled) event.getData();
                subscriptionId = ((SubscriptionAddonAttachedCanceled) event
                        .getData()).getSubscription().getId();
                customerName = ((SubscriptionAddonAttachedCanceled) event
                        .getData()).getSubscription().getCustomer().getName();
                break;
            case SUBSCRIPTION_USER_REMOVED:
                eventData = (SubscriptionUserAddedRemovedData) event.getData();
                subscriptionId = ((SubscriptionUserAddedRemovedData) event
                        .getData()).getSubscription();
                break;
            case WEBHOOK_TEST:
                processEvent(event.getId(), EventType.WEBHOOK_TEST);
                break;
            default:
                break;

        }
        // Do the Custom Event Handling here.
        logger.debug("Event data:" + eventData);
        if (eventData != null) {

        }
        //  Process the event if automated Bluevalt Response is required.

    }

    @Override
    public Boolean validateUser(Long userId) throws UserValidationException {
        logger.debug("Validing user:" + userId);
        // call custom code from here to validate user.
        return true;
    }

    @Override
    public String processEvent(Object... data) throws EventProcessingException {
        Long eventId = 0L;
        EventType type = null;
        MultiValueMap<String, String> parameters = null;
        Map<String, MultipartFile> files = null;
        for (Object obj : data) {
            if (obj instanceof Long) {
                eventId = (Long) obj;
            }
            if (obj instanceof EventType) {
                type = (EventType) obj;
            }
        }
        Event event = null; // constructEventType("Event STR Saved from handleEvent");
        EventResponse eventResponse = new EventResponse();
        if (type != null && type.equals(EventType.WEBHOOK_TEST)) {
            // EventType.WEBHOOK_TEST
            eventResponse.setStatus("success");
            eventResponse.setEventID(eventId.toString());
            eventResponse.setType(EventType.WEBHOOK_TEST);
            String accessToken = getAccessToken();
            if (accessToken != null
                    && sendEventResponse(null,eventResponse, accessToken)) {
                return "success";
            } else {
                throw new EventProcessingException("Error Reporting to Bluevalt. ");
            }
        }
        // BluevaltEvent execution outcome, success or error
        eventResponse.setStatus("error");
        //eventResponse.setEventID(event.getId());
        eventResponse.setStatus("success");
        eventResponse.setMessage("event processed");
        eventResponse.setRef_number("REF");
        String accessToken = getAccessToken();
        if (accessToken != null
                && sendEventResponse(null,eventResponse, accessToken)) {
            return "success";
        } else {
            throw new EventProcessingException("Error Reporting to Bluevalt. ");
        }

    }


}
