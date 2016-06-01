package com.stcs.spa.vo;

import com.google.gson.annotations.SerializedName;

public enum EventType {
	@SerializedName("subscription.created")
	SUBSCRIPTION_CREATED,
	@SerializedName("subscription.canceled")
	SUBSCRIPTION_CANCELED,
	@SerializedName("subscription.upgraded")
	SUBSCRIPTION_UPGRADED,
	@SerializedName("subscription.downgraded")
	SUBSCRIPTION_DOWNGRADED,
	@SerializedName("subscription.addon.attached")
	SUBSCRIPTION_ADDON_ATTACHED,
	@SerializedName("subscription.addon.canceled")
	SUBSCRIPTION_ADDON_CANCELED,
	@SerializedName("subscription.user.added")
	SUBSCRIPTION_USER_ADDED,
	@SerializedName("subscription.user.removed")
	SUBSCRIPTION_USER_REMOVED,
	@SerializedName("webhook.test")
	WEBHOOK_TEST,
	@SerializedName("account.suspended")
	ACCOUNT_SUSPENDED,
	@SerializedName("account.resumed")
	ACCOUNT_RESUMED,
	@SerializedName("account.terminated")
	ACCOUNT_TERMINATED,
	@SerializedName("event.expired")
	EVENT_EXPIRED
}
