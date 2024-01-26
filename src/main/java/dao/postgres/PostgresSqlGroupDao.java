package dao.postgres;

import dao.GroupDao;
import entity.Group;
import db.DatabaseConnector;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresSqlGroupDao implements GroupDao {
    DatabaseConnector connector = new DatabaseConnector();
    private static final String FIND_ALLGROUP_BY_STUDENT_ID_QUERY = "select groups.group_id, group_name from groups\n" +
            "inner join students student on groups.group_id = student.group_id\n" +
            "where student.student_id <= ?;";
    private static Logger log = LogManager.getLogger(PostgresSqlGroupDao.class);

    @Override
    public List<Group> findAllGroupByStudentId(long studentId) {
        log.info("Find all group by student id: " + studentId);
        List<Group> groupsByStudentIdList = new ArrayList<>();
        ResultSet resultSet;
        try (Connection connection = connector.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALLGROUP_BY_STUDENT_ID_QUERY)) {
            log.trace("Opening connection, creating prepared statement");
            preparedStatement.setInt(1, (int) studentId);
            resultSet = preparedStatement.executeQuery();
            log.trace("Create result set");
            while (resultSet.next()) {
                String groupName = resultSet.getString("group_name");
                int groupId = resultSet.getInt("group_id");
                groupsByStudentIdList.add(new Group(groupId, groupName));
            }

        } catch (SQLException throwables) {
            log.error("Something went wrong", throwables);
        }
        log.trace("Closing connection, prepared statement, result set");
        return groupsByStudentIdList;
    }
}
