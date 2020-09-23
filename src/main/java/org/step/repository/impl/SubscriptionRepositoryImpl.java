package org.step.repository.impl;

import org.step.entity.Course;
import org.step.entity.Subscription;
import org.step.repository.CrudRepository;
import org.step.repository.SessionFactoryCreator;

import javax.persistence.EntityManager;
import java.util.List;

public class SubscriptionRepositoryImpl implements CrudRepository<Subscription> {
    private final EntityManager entityManager = SessionFactoryCreator.getEntityManager();

    @Override
    public Subscription save(Subscription subscription) {
        entityManager.getTransaction().begin();

        Subscription builtSubscription = Subscription.builder()
                .id()
                .name(subscription.getName())
                .description(subscription.getDescription())
                .build();

        entityManager.persist(builtSubscription);

        entityManager.getTransaction().commit();

        return builtSubscription;
    }

    @Override
    public List<Subscription> findAll() {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
