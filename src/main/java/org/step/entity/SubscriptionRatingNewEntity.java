package org.step.entity;

import javax.persistence.*;

//@Entity
//@Table(name = "subscription_rating_new_entity")
public class SubscriptionRatingNewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    public SubscriptionRatingNewEntity() {

    }

    public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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

        public void setProfile(User user) {
            this.profile = profile;
        }
}
