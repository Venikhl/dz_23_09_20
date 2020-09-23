package org.step.entity;

import javax.persistence.Column;
import java.io.Serializable;

public class SubscriptionProfileRatingKey implements Serializable {

    @Column(name = "subscription_id")
    private Long subscriptionId;

    @Column(name = "profile_id")
    private Long profileId;

    public SubscriptionProfileRatingKey() {
    }

    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }
}
