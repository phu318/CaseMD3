package com.example.casemd3.controller;

import com.example.casemd3.dao.DepartmentDAO;
import com.example.casemd3.dao.StaffDAO;
import com.example.casemd3.model.Department;
import com.example.casemd3.model.Staff;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "staffServlets", value = "/staff-page")
public class StaffServlet extends HttpServlet {

    private StaffDAO staffDAO;
    private DepartmentDAO departmentDAO;
    public void init() {
        staffDAO=new StaffDAO();
        departmentDAO=new DepartmentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "create":
                showAddForm(req, resp);
                break;
            case "edit":
                showEditForm(req, resp);
                break;
            case "delete":
                deleteStaff(req, resp);
                break;
            case "search":
                search(req,resp);
                break;
            default:
                showStaffPage(req, resp);
                break;
        }

    }

    private void search(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchName = request.getParameter("name");
            List<Staff> searchResults = performSearch(searchName);
            request.setAttribute("searchName", searchName);
            request.setAttribute("searchResults", searchResults);
            request.getRequestDispatcher("staff/search.jsp").forward(request, response);

    }
    private List<Staff> performSearch(String searchName) {
        List<Staff> staff =new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/staff", "root", "123456")) {
            String sql = "select stafftable.id, stafftable.`name`, stafftable.email,stafftable.address,stafftable.phoneNumber,stafftable.salary,stafftable.departmentId,department.`name` as departName  from stafftable join department\n" +
                    "             on `stafftable`.departmentId = department.id WHERE stafftable.`name` LIKE ?;";


            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, "%" + searchName + "%");
                ResultSet rs = statement.executeQuery();

                // Xử lý và thu thập kết quả tìm kiếm
                while (rs.next()) {


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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return staff;
    }


    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("listDepartment", departmentDAO.selectAllDepartment());
        request.getRequestDispatcher("staff/create.jsp").forward(request,response);

    }
    private void showStaffPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Staff> listStaff = staffDAO.selectAllStaff();
        req.setAttribute("liststaff", listStaff);

        req.getRequestDispatcher("staff/list.jsp").forward(req, resp);
    }
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws  ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Staff existingUser = staffDAO.selectStaffById(id);
        request.setAttribute("liststaff", existingUser);

        request.setAttribute("listDepartment", departmentDAO.selectAllDepartment());
        request.getRequestDispatcher("staff/edit.jsp").forward(request,response);

    }

    private void deleteStaff(HttpServletRequest request, HttpServletResponse response)
            throws  IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        staffDAO.deleteStaff(id);

        List<Staff> listStaff = staffDAO.selectAllStaff();
        request.setAttribute("liststaff", listStaff);
        RequestDispatcher dispatcher = request.getRequestDispatcher("staff/list.jsp");
        dispatcher.forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    insertStaff(request, response);
                    break;
                case "edit":
                    updateStaff(request, response);
                    break;

                case "search":
                    search(request,response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void insertStaff(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        Department department=new Department(Integer.parseInt(request.getParameter("department")));

        Staff newStaff = new Staff(
                request.getParameter("name"),
                request.getParameter("email"),
                request.getParameter("address"),
                request.getParameter("phoneNumber"),
                request.getParameter("salary"),
                department
        );
        staffDAO.insertStaff(newStaff);
//        RequestDispatcher dispatcher = request.getRequestDispatcher("staff/create.jsp");
//        dispatcher.forward(request, response);
        response.sendRedirect("http://localhost:8080/staff-page");

    }

    private void updateStaff(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        Department department=new Department(Integer.parseInt(request.getParameter("department")));

        Staff newStaff = new Staff(
                Integer.parseInt(request.getParameter("id")),
                request.getParameter("name"),
                request.getParameter("email"),
                request.getParameter("address"),
                request.getParameter("phoneNumber"),
                request.getParameter("salary"),
                department
        );
        staffDAO.updateStaff(newStaff);
//        RequestDispatcher dispatcher = request.getRequestDispatcher("staff/edit.jsp");
//        dispatcher.forward(request, response);
        response.sendRedirect("http://localhost:8080/staff-page");
    }
}
