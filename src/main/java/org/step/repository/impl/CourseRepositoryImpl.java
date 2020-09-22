package org.step.repository.impl;

import org.step.entity.Course;
import org.step.repository.CrudRepository;
import org.step.repository.SessionFactoryCreator;

import javax.persistence.EntityManager;
import java.util.List;

public class CourseRepositoryImpl implements CrudRepository<Course> {

    private final EntityManager entityManager = SessionFactoryCreator.getEntityManager();

    @Override
    public Course save(Course course) {
        entityManager.getTransaction().begin();

        Course builtCourse = Course.builder()
                .id()
                .topic(course.getTopic())
                .courseDescription(course.getCourseDescription())
                .build();

        entityManager.persist(builtCourse);

        entityManager.getTransaction().commit();

        return builtCourse;
    }

    @Override
    public List<Course> findAll() {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
