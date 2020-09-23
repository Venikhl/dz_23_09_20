package org.step.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

//@Entity
//@Table(name = "subscription_profile_rating_entity")
public class SubscriptionProfileRatingEntity {
    @EmbeddedId
    private SubscriptionProfileRatingKey key;

    @MapsId("subscription_id")
    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    @MapsId("profile_id")
    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    private int rating;

    public SubscriptionProfileRatingEntity() {
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public SubscriptionProfileRatingKey getKey() {
        return key;
    }

    public void setKey(SubscriptionProfileRatingKey key) {
        this.key = key;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
