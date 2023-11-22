package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.Key.StudentCourseKey;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "studentCourse")
public class StudentCourse {
    @JsonIgnore
    @EmbeddedId
    private StudentCourseKey studentCourseID;
    @JsonIgnore
    @ManyToOne
    @MapsId("studentID")
    @JoinColumn(name="studentFK")
    private Students student;
    @ManyToOne
    @MapsId("courseID")
    @JoinColumn(name="courseFK")
    Course course;
    @Column(name="note")
    private double note;

}
