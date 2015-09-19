package by.itstart.dto;

import org.hibernate.annotations.*;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="subject", catalog="student_db")
public class Subject implements Serializable{

    private Integer id = null;
    private String title;
    private Integer studentId;
    private List<Mark> marks = new ArrayList<>(0);

    public Subject() {
    }

    public Subject(Integer id) {
        this.id = id;
    }

    @Column(name="student_id")
    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    @Column(name="ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name="title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subject that = (Subject) o;

        if (id != that.id) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    public JSONObject toJsonObject() {
        return new JSONObject(this);
    }
}
