package main.java.by.itstart.mysql;

import main.java.by.itstart.dao.AbstractJdbcDao;
import main.java.by.itstart.dao.DaoException;
import main.java.by.itstart.dto.Mark;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class MySqlMarkDao extends AbstractJdbcDao<Mark> {

    public MySqlMarkDao(Connection connection) {
        super(connection);
    }

    @Override
    protected String getSelectQuery() {
        return "SELECT ID, student_id, subject_id, mark FROM mark";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE mark SET student_id = ?, subject_id = ?, mark = ? WHERE id = ?;";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO mark (student_id, subject_id, mark) values(?, ?, ?);";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM mark WHERE id = ?;";
    }

    @Override
    protected List<Mark> parseResultSet(ResultSet rs) throws SQLException {
        List<Mark> result = new LinkedList<>();
        while (rs.next()) {
            Mark mark = new Mark();
            mark.setId(rs.getInt("ID"));
            mark.setStudentId(rs.getInt("student_id"));
            mark.setSubjectId(rs.getInt("subject_id"));
            mark.setMark(rs.getInt("mark"));
            result.add(mark);
        }
        return result;
    }

    @Override
    protected void preparedStatementForInsert(PreparedStatement statement, Mark object) throws SQLException {
        statement.setInt(1, object.getStudentId());
        statement.setInt(2, object.getSubjectId());
        statement.setInt(3, object.getMark());
    }

    @Override
    protected void preparedStatementForUpdate(PreparedStatement statement, Mark object) throws SQLException {
        statement.setInt(1, object.getStudentId());
        statement.setInt(2, object.getSubjectId());
        statement.setInt(3, object.getMark());
        statement.setInt(4, object.getId());
    }

    public List<Mark> getAllBySubjectId(int id) throws DaoException {
        List<Mark> marks;
        String sql = getSelectQuery() + " WHERE subject_id = ?;";
        try {
            markStatement = getConnection().prepareStatement(sql);
            markStatement.setInt(1, id);
            ResultSet resultSet = markStatement.executeQuery();
            marks = parseResultSet(resultSet);
            resultSet.close();
        } catch (Exception e) {
            throw new DaoException("Can not select all Marks", e);
        }
        return marks;
    }
}
