package org.step.repository.impl;

import org.step.entity.Profile;
import org.step.repository.ProfileRepository;
import org.step.repository.SessionFactoryCreator;

import javax.persistence.EntityManager;
import java.util.List;

public class ProfileRepositoryImpl implements ProfileRepository {

    private final EntityManager entityManager = SessionFactoryCreator.getEntityManager();

    @Override
    public Profile save(Profile profile) {
        return null;
    }

    @Override
    public List<Profile> findAll() {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
