package com.spring.schoolApplication.dao.jdbcDao;

import com.spring.schoolApplication.dao.GroupDao;
import com.spring.schoolApplication.entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final String CREATE_GROUP_QUERY = "insert into groups (group_id, group_name) values (?, ?);";

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
        return jdbcTemplate.update(CREATE_GROUP_QUERY, group.getGroupId(), group.getGroupName());
    }

    @Override
    public int deleteGroupById(long groupId) {
        return jdbcTemplate.update(DROP_GROUP_BY_ID_QUERY, groupId);
    }

}
