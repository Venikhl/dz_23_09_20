package org.step.entity;

import javax.persistence.*;

//@Entity
//@Table(name = "course_user_rating_entity")
public class CourseUserRatingEntity {

    @EmbeddedId
    private CourseUserRatingKey key;

    @MapsId("course_id")
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @MapsId("user_id")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int rating;

    public CourseUserRatingEntity() {
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public CourseUserRatingKey getKey() {
        return key;
    }

    public void setKey(CourseUserRatingKey key) {
        this.key = key;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
