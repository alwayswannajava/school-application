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

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;
class PostgresSqlGroupDaoTest {
    public static final DatabaseConnector connector = new DatabaseConnector();
    private static final String COUNT_GROUP_QUERY = "select count(*) from groups;";


    @BeforeAll
    public static void setUp() throws SQLException {
        DatabaseTableDeleter tableDeleter = new DatabaseTableDeleter();
        DatabaseTableCreator tableCreator = new DatabaseTableCreator();
        tableDeleter.dropDatabaseTables();
        tableCreator.createDatabaseTables();
    }

    @DisplayName("Test find all group by student id")
    @Test
    public void testCorrectFindingGroupsByStudentId() {
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
        groupDao.deleteGroupById(3);
    }

    @DisplayName("Test creating group")
    @Test
    public void testCorrectCreatingGroup(){
        int countGroupAfterCreate = 0;
        GroupDao groupDao = new PostgresSqlGroupDao();
        groupDao.create(new Group(1, "GW-82"));
        try(Connection connection = connector.connectToDatabase();
        PreparedStatement preparedCountStatement = connection.prepareStatement(COUNT_GROUP_QUERY)) {
            ResultSet resultSet = preparedCountStatement.executeQuery();
            resultSet.next();
            countGroupAfterCreate = resultSet.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assertEquals(1, countGroupAfterCreate);
        groupDao.deleteGroupById(1);
    }

    @DisplayName("Test deleting group by id")
    @Test
    public void testCorrectDeletingGroupById(){
        int countGroupAfterDelete = 0;
        GroupDao groupDao = new PostgresSqlGroupDao();
        groupDao.create(new Group(1, "ER-12"));
        try(Connection connection = connector.connectToDatabase();
            PreparedStatement preparedCountStatement = connection.prepareStatement(COUNT_GROUP_QUERY)) {
            groupDao.deleteGroupById(1);
            ResultSet resultSet = preparedCountStatement.executeQuery();
            resultSet.next();
            countGroupAfterDelete = resultSet.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assertEquals(0, countGroupAfterDelete);
    }

}
