package dao.postgres;

import dao.GroupDao;
import db.DatabaseConnector;
import db.DatabaseTableCreator;
import db.DatabaseTableDeleter;
import entity.Group;
import mainClasses.DataGeneratorUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;
class PostgresSqlGroupDaoTest {
    public static final DatabaseConnector connector = new DatabaseConnector();

    @BeforeAll
    public static void setUp() throws SQLException {
        connector.readDatabaseFileProperties();
        DatabaseTableDeleter tableDeleter = new DatabaseTableDeleter();
        DatabaseTableCreator tableCreator = new DatabaseTableCreator();
        tableDeleter.dropDatabaseTables(connector.connectToDatabase());
        tableCreator.createDatabaseTables(connector.connectToDatabase());
    }

    @DisplayName("Test find all group by student id query")
    @Test
    public void testCorrectFindingStudentsByCourseName() {
        try (Connection connection = connector.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(DataGeneratorUtil.INSERT_TO_GROUP_TABLE_QUERY)) {
            preparedStatement.setInt(1, 3);
            preparedStatement.setString(2, "AA-22");
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try (Connection connection = connector.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(DataGeneratorUtil.INSERT_TO_STUDENTS_TABLE_QUERY)) {
             preparedStatement.setInt(1, 3);
             preparedStatement.setString(2, "Ivan");
             preparedStatement.setString(3, "Ivanov");
             preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        List<Group> expectedGroupsByStudentIdList = new ArrayList<>();
        expectedGroupsByStudentIdList.add(new Group(3, "AA-22"));
        GroupDao groupDao = new PostgresSqlGroupDao();
        List<Group> actualGroupsByStudentIdList = groupDao.findAllGroupByStudentId(1);
        assertEquals(expectedGroupsByStudentIdList, actualGroupsByStudentIdList);
    }
}