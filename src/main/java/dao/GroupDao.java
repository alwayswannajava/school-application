package dao;

import entity.Group;

import java.util.List;

public interface GroupDao {
    List<Group> findAllGroupByStudentId(long studentId);

    Group create(Group group);

    void deleteGroupById(long groupId);
}
