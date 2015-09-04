package by.itstart.mysql;

import by.itstart.dao.AbstractJdbcDao;
import by.itstart.dao.DaoException;
import by.itstart.dao.DaoFactory;
import by.itstart.dto.Mark;
import by.itstart.dto.Subject;


import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class MySqlSubjectDao extends AbstractJdbcDao<Subject> {

    public MySqlSubjectDao(Connection connection) {
        super(connection);
    }

    @Override
    protected String getSelectQuery() {
        return "SELECT ID, student_id, title FROM subject";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE subject SET student_id, title WHERE id = ?;";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO subject (student_id, title) values(?, ?);";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM subject WHERE id = ?";
    }

    @Override
    protected List<Subject> parseResultSet(ResultSet rs) throws SQLException {
        List<Subject> result = new LinkedList<>();
        while (rs.next()) {
            Subject subject = new Subject();
            subject.setId(rs.getInt("ID"));
            subject.setStudentId(rs.getInt("student_id"));
            subject.setTitle(rs.getString("title"));
            result.add(subject);
        }
        return result;
    }

    @Override
    protected void preparedStatementForInsert(PreparedStatement statement, Subject object) throws SQLException {
        statement.setInt(1, object.getStudentId());
        statement.setString(2, object.getTitle());
    }

    @Override
    protected void preparedStatementForUpdate(PreparedStatement statement, Subject object) throws SQLException {
        statement.setInt(1, object.getStudentId());
        statement.setString(2, object.getTitle());
        statement.setInt(3, object.getId());
    }

    public List<Subject> getAllWithMarksByStudentId(int id) throws DaoException {
        List<Subject> subjects;
        String sql = getSelectQuery() + " WHERE student_id = ?;";
        try {
            subjectStatement = getConnection().prepareStatement(sql);
            subjectStatement.setInt(1, id);
            ResultSet resultSet = subjectStatement.executeQuery();
            subjects = parseResultSet(resultSet);
            resultSet.close();
            DaoFactory factory = new MySqlDaoFactory();
            MySqlMarkDao dao = (MySqlMarkDao) factory.getDao(getConnection(), Mark.class);
            for (Subject subject : subjects) {
                subject.setMarks(dao.getAllBySubjectId(subject.getId()));
            }
        } catch (Exception e) {
            throw new DaoException("Can not select all subjects with marks", e);
        }
        return subjects;
    }

    public List<Subject> getAllByStudentId(int id) throws DaoException {
        List<Subject> subjects;
        String sql = getSelectQuery() + " WHERE student_id = ?;";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            subjects = parseResultSet(set);
            set.close();
        } catch (SQLException e) {
            throw new DaoException("Can not select all subjects by id " + id, e);
        }
        return subjects;
    }
}
