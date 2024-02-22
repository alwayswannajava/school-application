package com.spring.schoolApplication.service;

import com.spring.schoolApplication.entity.Group;

import java.util.List;

public interface GroupService {
    List<Group> findAllGroupByStudentId(long studentId);

    int create(Group group);

    int deleteGroupById(long groupId);

    int countAllGroups();

}
