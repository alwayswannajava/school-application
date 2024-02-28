package com.spring.schoolApplication.dao.jdbcDao;

import com.spring.schoolApplication.dao.GroupDao;
import com.spring.schoolApplication.entity.Group;
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
        scripts = {"/db/migration/V1__create_tables.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class JdbcGroupDaoTest {
    private static final String COUNT_ALL_GROUPS_QUERY = "select count(*) from groups;";

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
    void testCorrectCreatingGroup() {
        int countGroupBeforeAdd = countAllGroups();
        groupDao.create(new Group(6, "WQ-21"));
        int countGroupAfterAdd = countAllGroups();
        assertEquals(countGroupBeforeAdd + 1, countGroupAfterAdd);
    }

    @DisplayName("Test delete group")
    @Test
    void testCorrectDeletingGroupById() {
        int countGroupBeforeDelete = countAllGroups();
        groupDao.deleteGroupById(5);
        int countGroupAfterDelete = countAllGroups();
        assertEquals(countGroupBeforeDelete - 1, countGroupAfterDelete);
    }

    public int countAllGroups() {
        return jdbcTemplate.queryForObject(COUNT_ALL_GROUPS_QUERY, Integer.class);
    }
}