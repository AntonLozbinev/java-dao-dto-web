package by.itstart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractJdbcDao<T> implements CrudDao<T> {

    private Connection connection;
    private PreparedStatement readStatement;
    private PreparedStatement insertStatement;
    private PreparedStatement deleteStatement;
    private PreparedStatement updateStatement;
    protected PreparedStatement markStatement;
    protected PreparedStatement subjectStatement;

    public AbstractJdbcDao(Connection connection) {
        this.connection = connection;
    }

    protected Connection getConnection() {
        return connection;
    }

    protected abstract String getSelectQuery();

    protected abstract String getUpdateQuery();

    protected abstract String getInsertQuery();

    protected abstract String getDeleteQuery();

    protected abstract List<T> parseResultSet(ResultSet rs) throws SQLException;

    protected abstract void preparedStatementForInsert(PreparedStatement statement, T object) throws SQLException;

    protected abstract void preparedStatementForUpdate(PreparedStatement statement, T object) throws SQLException;

    @Override
    public T read(int id) throws DaoException {
        List<T> object;
        String sql = getSelectQuery() + " WHERE id = ?;";
        try {
            readStatement = connection.prepareStatement(sql);
            readStatement.setInt(1, id);
            ResultSet resultSet = readStatement.executeQuery();
            object = parseResultSet(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        if (object == null || object.isEmpty()) {
            throw new DaoException("Record not found. PK: " + id);
        }
        if (object.size() > 1) {
            throw new DaoException("Found more than one record");
        }
        return object.iterator().next();
    }

    @Override
    public boolean insert(T object) throws DaoException {
        String sql = getInsertQuery();
        try {
            insertStatement = connection.prepareStatement(sql);
            preparedStatementForInsert(insertStatement, object);
            insertStatement.executeUpdate();
        } catch (Exception e) {
            throw new DaoException("Object wasn't inserted", e);
        }
        return true;
    }

    @Override
    public boolean update(T object) throws DaoException {
        String sql = getUpdateQuery();
        try {
            updateStatement = connection.prepareStatement(sql);
            preparedStatementForUpdate(updateStatement, object);
            int count = updateStatement.executeUpdate();
            if (count != 1) {
                throw new DaoException("Update more than one record");
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return true;
    }

    @Override
    public boolean delete(int id) throws DaoException {
        String sql = getDeleteQuery();
        try {
            deleteStatement = connection.prepareStatement(sql);
            deleteStatement.setInt(1, id);
            deleteStatement.executeUpdate();
        } catch (Exception e) {
            throw new DaoException("object wasn't deleted", e);
        }
        return true;
    }

    @Override
    public List<T> getAll() throws DaoException {
        List<T> objects;
        String sql = getSelectQuery();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)){
            objects = parseResultSet(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Can not select all objects", e);
        }
        return objects;
    }

    public void closeDao() throws DaoException {
        try {
            if (readStatement != null) {
                readStatement.close();
            }
            if (insertStatement != null) {
                insertStatement.close();
            }
            if (deleteStatement != null) {
                deleteStatement.close();
            }
            if (updateStatement != null) {
                updateStatement.close();
            }
            if (markStatement != null) {
                markStatement.close();
            }
            if (subjectStatement != null) {
                subjectStatement.close();
            }
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Can not close Dao", e);
        }
    }
}
