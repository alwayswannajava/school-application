package dao.postgres;

import dao.GroupDao;
import entity.Group;
import db.DatabaseConnector;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class PostgresSqlGroupDao implements GroupDao {
    DatabaseConnector connector = new DatabaseConnector();
    private static final String FIND_ALLGROUP_BY_STUDENT_ID_QUERY = "select groups.group_id, group_name from groups\n" +
            "inner join students student on groups.group_id = student.group_id\n" +
            "where student_id <= ?;";
    private static Logger log = LogManager.getLogger(PostgresSqlGroupDao.class);

    @Override
    public List<Group> findAllGroupByStudentId(long studentId) {
        log.info("Find all group by student id: " + studentId);
        List<Group> groupsByStudentIdList = new LinkedList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            log.trace("Open connection");
            connection = connector.connectToDatabase();
            log.trace("Create prepared statement");
            preparedStatement = connection.prepareStatement(FIND_ALLGROUP_BY_STUDENT_ID_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, (int) studentId);
            log.trace("Create result set");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String groupName = resultSet.getString("group_name");
                int groupId = resultSet.getInt("group_id");
                groupsByStudentIdList.add(new Group(groupId, groupName));
            }
        } catch (SQLException throwables) {
            log.error("Cannot open connection", throwables);
        } finally {
            log.trace("Closing prepared statement");
            try {
                preparedStatement.close();
                log.trace("Prepared statement closed");
            } catch (SQLException throwables) {
                log.trace("Cannot close prepared statement", throwables);
            }
            log.trace("Closing result set");
            try {
                resultSet.close();
                log.trace("Result set closed");
            } catch (SQLException throwables) {
                log.error("Cannot close result set", throwables);
            }
            log.trace("Closing connection");
            try {
                connection.close();
                log.trace("Connection closed");
            } catch (SQLException throwables) {
                log.error("Cannot close connection", throwables);
            }
        }
        return groupsByStudentIdList;
    }
}
