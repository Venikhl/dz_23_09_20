package org.step.repository.impl;

import org.hibernate.Session;
import org.step.entity.Profile;
import org.step.entity.User;
import org.step.repository.ProfileRepository;
import org.step.repository.SessionFactoryCreator;

import javax.persistence.EntityManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProfileRepositoryImpl implements ProfileRepository {

    private final EntityManager entityManager = SessionFactoryCreator.getEntityManager();

    @Override
    public Profile save(Profile profile) {
        return null;
    }

    @Override
    public List<Profile> findAll() {
        Session session = SessionFactoryCreator.getSession();

//        EntityGraph<User> graph = session.createEntityGraph(User.class);
//
//        graph.addAttributeNodes("profile");

        List<Profile> profiles = new ArrayList<>();

        session.doWork(connection -> {

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM PROFILES");

            while (resultSet.next()) {
                profiles.add(
                        Profile.builder()
                                .id(resultSet.getLong("id"))
                                .fullName(resultSet.getString("full_name"))
                                .abilities(resultSet.getString("abilities"))
                                .graduation(resultSet.getString("graduation"))
                                .workExperience(resultSet.getString("work_experience"))
                                .build()
                );
            }
        });

        session.close();

        return profiles;
    }

    @Override
    public void delete(Long id) {

    }
}
