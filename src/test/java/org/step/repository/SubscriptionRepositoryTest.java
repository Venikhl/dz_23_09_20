package org.step.repository;

import org.junit.Test;
import org.step.entity.Course;
import org.step.entity.Subscription;
import org.step.repository.impl.CourseRepositoryImpl;
import org.step.repository.impl.SubscriptionRepositoryImpl;

public class SubscriptionRepositoryTest {

    private final CrudRepository<Subscription> subscriptionCrudRepository = new SubscriptionRepositoryImpl();

    @Test
    public void shouldSaveCourse() {
        Subscription builtSubscription = Subscription.builder().name("name").description("description").build();

        Subscription save = subscriptionCrudRepository.save(builtSubscription);

        System.out.println(save.toString());
    }
}
