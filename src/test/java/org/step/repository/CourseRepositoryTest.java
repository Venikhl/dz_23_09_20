package org.step.repository;

import org.junit.Test;
import org.step.entity.Course;
import org.step.repository.impl.CourseRepositoryImpl;

public class CourseRepositoryTest {

    private final CrudRepository<Course> courseCrudRepository = new CourseRepositoryImpl();

    @Test
    public void shouldSaveCourse() {
        Course builtCourse = Course.builder().topic("topic").courseDescription("course description").build();

        Course save = courseCrudRepository.save(builtCourse);

        System.out.println(save.toString());
    }
}
