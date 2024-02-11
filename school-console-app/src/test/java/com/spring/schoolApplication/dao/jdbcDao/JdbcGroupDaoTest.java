package com.spring.schoolApplication.dao.jdbcDao;

import com.spring.schoolApplication.dao.GroupDao;
import com.spring.schoolApplication.entity.Group;
import com.spring.schoolApplication.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        scripts = {"/sql/V1__create_test_tables.sql", "/sql/V2__insert_data_to_test_tables.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class JdbcGroupDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private GroupDao groupDao;

    @BeforeEach
    void setUp() {
        groupDao = new JdbcGroupDao(jdbcTemplate);
    }

    @DisplayName("Test find group by student id")
    @Test
    void testCorrectFindingdGroupByStudentId(){
        List<Group> expectedFoundGroups = new ArrayList<>();
        expectedFoundGroups.add(new Group(1, "TF-12"));
        expectedFoundGroups.add(new Group(2, "HQ-61"));
        expectedFoundGroups.add(new Group(3, "XG-88"));
        List<Group> actualFoundGroups = groupDao.findAllGroupByStudentId(3);
        assertEquals(expectedFoundGroups, actualFoundGroups);
    }

    @DisplayName("Test create group")
    @Test
    void testCorrectCreatingGroup(){
        int expectedReturnAfterAdd = groupDao.create(new Group(6, "AW-81"));
        assertEquals(1, expectedReturnAfterAdd);
    }

    @DisplayName("Test delete group")
    @Test
    void testCorrectDeletingGroupById() {
        groupDao.create(new Group(11, "GS-18"));
        int expectedReturnAfterDelete = groupDao.deleteGroupById(11);
        assertEquals(1, expectedReturnAfterDelete);
    }

}