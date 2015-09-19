package by.itstart.dto;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="mark", catalog="student_db")
public class Mark implements Serializable{

    private Integer id = null;
    private Integer studentId;
    private Integer subjectId;
    private int mark;

    public Mark() {
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

    @Column(name="student_id")
    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    @Column(name="subject_id")
    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    @Column(name="mark")
    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mark that = (Mark) o;

        if (id != that.id) return false;
        if (mark != that.mark) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + mark;
        return result;
    }

    @Override
    public String toString() {
        return id + ". " + mark;
    }

    public JSONObject toJsonObject() {
        return new JSONObject(this);
    }
}
