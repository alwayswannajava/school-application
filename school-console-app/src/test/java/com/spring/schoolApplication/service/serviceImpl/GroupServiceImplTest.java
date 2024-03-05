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
import static org.mockito.Mockito.*;


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
        groupService.create(group);
        verify(groupDao, times(1)).create(group);
    }

    @DisplayName("Test groupIsExist return false")
    @Test
    void testGroupIsExistReturnFalse(){
        Group group = new Group(2, "KW-28");
        when(groupDao.isGroupExist(group.getGroupId())).thenReturn(false);
        assertEquals(false, groupDao.isGroupExist(group.getGroupId()));
    }


    @DisplayName("Test throw StudentIdLessThanZeroException when invoke findAllGroupByStudentId method")
    @Test
    void testThrowStudentIdLessThanZeroExceptionWhenInvokeFindAllGroupByStudentIdMethod() {
        assertThrows(StudentIdIsLessThanZeroException.class, () -> groupService.findAllGroupByStudentId(-5));
    }

    @DisplayName("Test throw GroupIdIsLessThanZeroException when delete group")
    @Test
    void testThrowGroupIdIsLessThanZeroExceptionWhenDeleteGroup(){
        assertThrows(GroupIdIsLessThanZeroException.class, () -> groupService.deleteGroupById(-100));
    }

    @DisplayName("Test throw GroupDoesntExistException when invoke delete group")
    @Test
    void testThrowGroupDoesntExistExceptionWhenInvokeDeleteGroup(){
        assertThrows(GroupDoesntExistException.class, () -> groupService.deleteGroupById(200));
    }


}