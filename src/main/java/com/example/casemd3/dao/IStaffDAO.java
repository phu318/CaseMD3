package com.example.casemd3.dao;

import com.example.casemd3.model.Staff;

import java.sql.SQLException;
import java.util.List;

public interface IStaffDAO {
    Staff selectStaffById(int id);
    List<Staff> selectAllStaff();
    void insertStaff(Staff staff);

    public boolean deleteStaff(int id) throws SQLException;

    boolean updateStaff(Staff staff);
}
