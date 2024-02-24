package com.spring.schoolApplication.dao.jdbcDao;

import com.spring.schoolApplication.DataGeneratorUtil;
import com.spring.schoolApplication.dao.GroupDao;
import com.spring.schoolApplication.entity.Group;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcGroupDao implements GroupDao {
    private static final String FIND_ALLGROUP_BY_STUDENT_ID_QUERY = "select groups.group_id, group_name from groups\n" +
            "inner join students student on groups.group_id = student.group_id\n" +
            "where student.student_id <= ?;";
    private static final String DROP_GROUP_BY_ID_QUERY = "delete from groups where group_id = ?;";
    private static final String COUNT_ALL_GROUPS_QUERY = "select count(*) from groups;";
    private static final String COUNT_GROUPS_EXISTS_BY_ID_QUERY = "select count(*) from groups where group_id = ?";

    private JdbcTemplate jdbcTemplate;

    public JdbcGroupDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Group> findAllGroupByStudentId(long studentId) {
        return jdbcTemplate.query(FIND_ALLGROUP_BY_STUDENT_ID_QUERY, BeanPropertyRowMapper.newInstance(Group.class), studentId);
    }

    @Override
    public int create(Group group) {
        return jdbcTemplate.update(DataGeneratorUtil.INSERT_TO_GROUP_TABLE_QUERY, group.getGroupId(), group.getGroupName());
    }

    @Override
    public int deleteGroupById(long groupId) {
        return jdbcTemplate.update(DROP_GROUP_BY_ID_QUERY, groupId);
    }

    @Override
    public int countAllGroups() {
        return jdbcTemplate.queryForObject(COUNT_ALL_GROUPS_QUERY, Integer.class);
    }

    @Override
    public boolean isGroupExist(long groupId) {
        Integer countExistGroup = jdbcTemplate.queryForObject(COUNT_GROUPS_EXISTS_BY_ID_QUERY, Integer.class, groupId);
        return countExistGroup != null && countExistGroup > 0;
    }

}
