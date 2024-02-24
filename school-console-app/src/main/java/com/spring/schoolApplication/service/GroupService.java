package com.spring.schoolApplication.service;

import com.spring.schoolApplication.entity.Group;
import com.spring.schoolApplication.exception.*;

import java.util.List;

public interface GroupService {
    List<Group> findAllGroupByStudentId(long studentId) throws StudentIdIsLessThanZeroException;

    int create(Group group) throws GroupExistsException;

    int deleteGroupById(long groupId) throws GroupDoesntExistException, GroupIdIsLessThanZeroException;

    int countAllGroups();

}
