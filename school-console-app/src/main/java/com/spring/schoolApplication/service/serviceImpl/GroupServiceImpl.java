package com.spring.schoolApplication.service.serviceImpl;

import com.spring.schoolApplication.DataGeneratorUtil;
import com.spring.schoolApplication.dao.jdbcDao.JdbcGroupDao;
import com.spring.schoolApplication.service.GroupService;
import com.spring.schoolApplication.entity.Group;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {
    private JdbcGroupDao groupRepository;

    public GroupServiceImpl(JdbcGroupDao groupRepository) {
        this.groupRepository = groupRepository;
    }


    @Override
    public List<Group> findAllGroupByStudentId(long studentId) {
        return groupRepository.findAllGroupByStudentId(studentId);
    }

    @Override
    public int create(Group group) {
        return groupRepository.create(group);
    }

    @Override
    public int deleteGroupById(long groupId) {
        return groupRepository.deleteGroupById(groupId);
    }

    @Override
    public int countAllGroups() {
        return groupRepository.countAllGroups();
    }
}
