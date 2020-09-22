package org.step.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

//@Embeddable
public class CourseUserRatingKey implements Serializable {

    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "user_id")
    private Long userId;

    public CourseUserRatingKey() {
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
