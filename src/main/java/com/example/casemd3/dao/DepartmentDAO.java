package com.example.casemd3.dao;

import com.example.casemd3.context.DBConnect;
import com.example.casemd3.model.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO extends DBConnect implements IDepartmentDAO {

    private static final String SELECT_ALL_DEPARTMENT = "SELECT * FROM staff.department;";

    @Override
    public List<Department> selectAllDepartment() {
        List<Department> departments=new ArrayList<>();
        try {
            Connection connection= getConnection();
            PreparedStatement preparedStatement= connection.prepareStatement(SELECT_ALL_DEPARTMENT);
            System.out.println(preparedStatement);
            ResultSet rs=preparedStatement.executeQuery();

            while (rs.next()){
                departments.add(new Department(
                        rs.getInt("id"),
                        rs.getString("name"))
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return departments;
    }
}
