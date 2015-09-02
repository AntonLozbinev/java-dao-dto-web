package main.java.by.itstart.dto;

import java.io.Serializable;
import java.util.List;

public class Subject implements Serializable{

    private Integer id = null;
    private String title;
    private Integer studentId;
    private List<Mark> marks;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return id + ". " + title + " of student with id " + studentId;
    }
}
