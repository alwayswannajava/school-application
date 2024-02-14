package com.spring.schoolApplication.dao;

import com.spring.schoolApplication.entity.Group;

import java.util.List;

public interface GroupDao {
    List<Group> findAllGroupByStudentId(long studentId);

    int create(Group group);

    int deleteGroupById(long groupId);

    List<Group> findAllGroups();

}
