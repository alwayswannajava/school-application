package com.spring.schoolApplication.service.serviceImpl;

import com.spring.schoolApplication.dao.GroupDao;
import com.spring.schoolApplication.dao.jdbcDao.JdbcGroupDao;
import com.spring.schoolApplication.exception.*;
import com.spring.schoolApplication.service.GroupService;
import com.spring.schoolApplication.entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupDao groupRepository;

    @Override
    public List<Group> findAllGroupByStudentId(long studentId) {
        if (studentId < 0) {
            throw new StudentIdIsLessThanZeroException("Student id cannot be less than zero");
        }
        return groupRepository.findAllGroupByStudentId(studentId);
    }

    @Override
    public int create(Group group) {
        if (groupRepository.isGroupExist(group.getGroupId())) {
            throw new GroupExistsException("Group is already exist");
        }
        return groupRepository.create(group);
    }

    @Override
    public int deleteGroupById(long groupId) {
        if (groupId < 0) {
            throw new GroupIdIsLessThanZeroException("Group id cannot be less than zero");
        } else if (!groupRepository.isGroupExist(groupId)) {
            throw new GroupDoesntExistException("There is no group with that id, maybe it has deleted before");
        }
        return groupRepository.deleteGroupById(groupId);
    }

}
