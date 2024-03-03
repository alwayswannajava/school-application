package com.spring.schoolApplication.service.serviceImpl;

import com.spring.schoolApplication.dao.GroupDao;
import com.spring.schoolApplication.entity.Group;
import com.spring.schoolApplication.exception.GroupDoesntExistException;
import com.spring.schoolApplication.exception.GroupExistsException;
import com.spring.schoolApplication.exception.GroupIdIsLessThanZeroException;
import com.spring.schoolApplication.exception.StudentIdIsLessThanZeroException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = {GroupServiceImpl.class})
class GroupServiceImplTest {

    @MockBean
    private GroupDao groupDao;

    @Autowired
    private GroupServiceImpl groupService;

    @DisplayName("Test find group by student id")
    @Test
    void testCorrectFindingGroupByStudentId() {
        List<Group> expectedList = new ArrayList<>();
        List<Group> actualList = groupService.findAllGroupByStudentId(3);
        verify(groupDao).findAllGroupByStudentId(3);
        assertEquals(expectedList, actualList);
    }

    @DisplayName("Test create group")
    @Test
    void testCorrectCreatingGroup() {
        Group group = new Group(1, "TW-62");
        when(groupDao.create(group)).thenReturn(1);
    }

    @DisplayName("Test delete group")
    @Test
    void testCorrectDeletingGroupById() {
        when(groupDao.deleteGroupById(2)).thenReturn(1);
    }

    @DisplayName("Test throw StudentIdLessThanZeroException when invoke findAllGroupByStudentId method")
    @Test
    void testThrowStudentIdLessThanZeroExceptionWhenInvokeFindAllGroupByStudentIdMethod() {
        when(groupDao.findAllGroupByStudentId(-5)).thenThrow(StudentIdIsLessThanZeroException.class);
        assertThrows(StudentIdIsLessThanZeroException.class, () -> groupService.findAllGroupByStudentId(-5));
    }

    @DisplayName("Test throw GroupExistsException when invoke create group")
    @Test
    void testThrowGroupExistsExceptionWhenCreateGroup(){
        Group group = new Group(3, "MK-71");
        when(groupDao.isGroupExist(group.getGroupId())).thenThrow(GroupExistsException.class);
        assertThrows(GroupExistsException.class, () -> groupService.create(group));
    }

    @DisplayName("Test throw GroupIdIsLessThanZeroException when delete group")
    @Test
    void testThrowGroupIdIsLessThanZeroExceptionWhenDeleteGroup(){
        when(groupDao.deleteGroupById(-100)).thenThrow(GroupIdIsLessThanZeroException.class);
        assertThrows(GroupIdIsLessThanZeroException.class, () -> groupService.deleteGroupById(-100));
    }

    @DisplayName("Test throw GroupDoesntExistException when invoke delete group")
    @Test
    void testThrowGroupDoesntExistExceptionWhenInvokeDeleteGroup(){
        when(groupDao.deleteGroupById(200)).thenThrow(GroupDoesntExistException.class);
        assertThrows(GroupDoesntExistException.class, () -> groupService.deleteGroupById(200));
    }

    @DisplayName("Test isGroupExist method returns boolean")
    @Test
    void testIsGroupExistReturnsBoolean() {
        Group group = new Group(4, "WL-61");
        when(groupDao.isGroupExist(group.getGroupId())).thenReturn(true);
        when(!groupDao.isGroupExist(group.getGroupId())).thenReturn(false);
    }


}