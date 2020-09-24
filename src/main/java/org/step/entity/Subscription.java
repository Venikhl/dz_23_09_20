package org.step.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "subscriptions")
public class Subscription {
    @Id
    private String id;

    @NotEmpty(message = "Field 'name' should not be empty!")
    @NotNull(message = "Field 'name' should not be null!")
    @Size(min = 4, max = 128, message = "Field 'name' should be at least 4, and less than 128 symbols!")
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "profile_subscription",
            joinColumns = @JoinColumn(name = "subscription_id"),
            // inverseJoinColumns - для сущности, на другой стороне
            inverseJoinColumns = @JoinColumn(name = "profile_id")
    )
    private Set<Profile> profileSet = new HashSet<>();

    public Subscription() {
    }

    public Subscription(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }


    public static Subscription.SubscriptionBuilder builder() {
        return new Subscription.SubscriptionBuilder();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public static class SubscriptionBuilder {
        private String id, name, description;

        private SubscriptionBuilder() {
        }

        public Subscription.SubscriptionBuilder id() {
            this.id = UUID.randomUUID().toString();
            return this;
        }

        public Subscription.SubscriptionBuilder name(String name) {
            this.name = name;
            return this;
        }

        public Subscription.SubscriptionBuilder description(String description) {
            this.description = description;
            return this;
        }

        public Subscription build() {
            return new Subscription(id, name, description);
        }
    }
}
