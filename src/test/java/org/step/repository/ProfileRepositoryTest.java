package org.step.repository;

import org.hibernate.Session;
import org.hibernate.graph.RootGraph;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.step.entity.Course;
import org.step.entity.Profile;
import org.step.entity.Subscription;
import org.step.entity.User;
import org.step.repository.impl.ProfileRepositoryImpl;
import org.step.repository.impl.UserRepositoryImpl;

import javax.persistence.EntityManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfileRepositoryTest {

    private final ProfileRepository profileRepository = new ProfileRepositoryImpl();
    private final EntityManager entityManager = SessionFactoryCreator.getEntityManager();
    private final List<Profile> profiles = new ArrayList<>();
    private final ProfileRepository profileRepositoryImpl = new ProfileRepositoryImpl();

    @Before
    public void setup() {
        Profile profile = Profile.builder()
                .abilities("abilities")
                .graduation("graduation")
                .workExperience("experience")
                .build();

        entityManager.getTransaction().begin();

        entityManager.persist(profile);

        entityManager.getTransaction().commit();

        profiles.add(profile);
    }

    @After
    public void clean() {
        entityManager.getTransaction().begin();

        entityManager.createQuery("delete from Profile p").executeUpdate();

        entityManager.getTransaction().commit();
    }

    @Test
    public void getAllSubscriptionByProfile() {

        List<Profile> all = profileRepositoryImpl.findAll();

        Session session = SessionFactoryCreator.getSession();

        session.beginTransaction();

        RootGraph<Profile> entityGraph = session.createEntityGraph(Profile.class);

        entityGraph.addAttributeNode("subscriptionSet");

        Profile singleResult1 = session.createQuery("select p from Profile p where p.id=:id", Profile.class)
                .setParameter("id", all.get(0).getId())
                .applyFetchGraph(entityGraph)
                .getSingleResult();

        List<Object> objectList = session.createNativeQuery("SELECT SUBSCRIPTION_ID FROM PROFILE_SUBSCRIPTION WHERE PROFILE_ID=?")
                .setParameter(1, all.get(0).getId())
                .getResultList();

        System.out.println(objectList.toString());

        session.getTransaction().commit();

        session.close();
    }
}
