package main.java.by.itstart.mysql;

import main.java.by.itstart.dao.AbstractJdbcDao;
import main.java.by.itstart.dto.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class MySqlStudentDao extends AbstractJdbcDao<Student> {

    public MySqlStudentDao(Connection connection) {
        super(connection);
    }

    @Override
    protected String getSelectQuery() {
        return "SELECT ID, first_name, second_name, enter_year FROM student";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE student SET first_name = ?, second_name = ?, enter_year = ? WHERE id = ?;";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO student (first_name, second_name, enter_year) values (?, ?, ?);";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM student WHERE id = ?;";
    }

    @Override
    protected List<Student> parseResultSet(ResultSet rs) throws SQLException {
        List<Student> result = new LinkedList<>();
        while (rs.next()) {
            Student student = new Student();
            student.setId(rs.getInt("ID"));
            student.setFirstName(rs.getString("first_name"));
            student.setSecondName(rs.getString("second_name"));
            student.setEnterYear(rs.getInt("enter_year"));
            result.add(student);
        }
        return result;
    }

    @Override
    protected void preparedStatementForInsert(PreparedStatement statement, Student object) throws SQLException {
        statement.setString(1, object.getFirstName());
        statement.setString(2, object.getSecondName());
        statement.setInt(3, object.getEnterYear());
    }

    @Override
    protected void preparedStatementForUpdate(PreparedStatement statement, Student object) throws SQLException {
        statement.setString(1, object.getFirstName());
        statement.setString(2, object.getSecondName());
        statement.setInt(3, object.getEnterYear());
        statement.setInt(4, object.getId());
    }
}
