package com.example.casemd3.dao;

import com.example.casemd3.context.DBConnect;
import com.example.casemd3.model.Department;
import com.example.casemd3.model.Staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO extends DBConnect implements IStaffDAO {

    private static final String SELECT_ALL_STAFF = "SELECT * FROM staff.stafftable;";

    private  static final String SEARCH = "SELECT * FROM stafftable WHERE name LIKE ?";

    private static final String INSERT_STAFF_SQL = "INSERT INTO stafftable (name, email, address, phoneNumber, salary, departmentId) VALUES (?, ?, ?,?,?,?);";

    public static final String SELECT_STAFF_BY_ID = "select stafftable.id, stafftable.`name`, stafftable.email,stafftable.address,stafftable.phoneNumber,stafftable.salary,stafftable.departmentId,department.`name` as departName from stafftable join department\n" +
            " on `stafftable`.departmentId = department.id where stafftable.id=? ;";
    private static final String DELETE_STAFF_SQL = "delete from stafftable where id = ?;";

    private static final String UPDATE_STAFF_SQL = "update stafftable set name = ?, email= ?,address=?, phoneNumber=?,salary=?,departmentId=? where id = ?;";
    private static final String SELECT_ALL_STAFF_TYPE = "select stafftable.id, stafftable.`name`, stafftable.email,stafftable.address,stafftable.phoneNumber,stafftable.salary,stafftable.departmentId,department.`name` as departName  from stafftable join department\n" +
            " on `stafftable`.departmentId = department.id ;";



    @Override
    public Staff selectStaffById(int id) {
        Staff staff = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STAFF_BY_ID)) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Department department=new Department(
                        rs.getInt("departmentId"),
                        rs.getString("departName"));

                staff = new Staff(
                rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("phoneNumber"),
                        rs.getString("salary"),
                        department
                );
            }
        } catch (SQLException e) {
            printSQLException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return staff;
    }

    @Override
    public List<Staff> selectAllStaff() {
        List<Staff> staff=new ArrayList<>();
        try {
            Connection connection= getConnection();
            PreparedStatement preparedStatement= connection.prepareStatement(SELECT_ALL_STAFF_TYPE);
            System.out.println(preparedStatement);
            ResultSet rs=preparedStatement.executeQuery();

            while (rs.next()){
                Department department=new Department(rs.getInt("departmentId"),rs.getString("departName"));

                staff.add(new Staff(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("phoneNumber"),
                        rs.getString("salary"),
                        department
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return staff;
    }

    @Override
    public void insertStaff(Staff staff) {
        System.out.println(INSERT_STAFF_SQL);
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STAFF_SQL)
        ) {
            preparedStatement.setString(1, staff.getName());
            preparedStatement.setString(2, staff.getEmail());
            preparedStatement.setString(3, staff.getAddress());
            preparedStatement.setString(4, staff.getPhone());
            preparedStatement.setString(5, staff.getSalary());
            preparedStatement.setInt(6, staff.getDepartment().getId());


            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteStaff(int id) {
        boolean rowDeleted;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_STAFF_SQL)) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return rowDeleted;
    }

    @Override
    public boolean updateStaff(Staff staff) {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STAFF_SQL);) {

            preparedStatement.setString(1, staff.getName());
            preparedStatement.setString(2, staff.getEmail());
            preparedStatement.setString(3, staff.getAddress());
            preparedStatement.setString(4, staff.getPhone());
            preparedStatement.setString(5, staff.getSalary());
            preparedStatement.setInt(6, staff.getDepartment().getId());
            preparedStatement.setInt(7, staff.getId());


            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();

            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return rowUpdated;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
