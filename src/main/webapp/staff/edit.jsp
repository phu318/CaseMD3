<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User Management Application</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

    <style>

        body {
            padding: 20px;
        }

        .form-container {
            width: 100%;
            margin: 20px auto;
        }

        /* CSS cho nút "Save" */
        .save-btn {
            background-color: #28a745;
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
        }

        .save-btn:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="text-center">
        <h1>User Management</h1>
        <h2>
            <!-- Thêm class Bootstrap cho nút -->
            <a href="/staff-page" class="btn btn-primary">List All Staff</a>
        </h2>
    </div>

    <div align="center" class="form-container">
        <form method="post">
            <table class="table table-bordered">
                <caption>
                    <h2>Edit Staff</h2>
                </caption>
                <c:if test="${liststaff != null}">
                    <input type="hidden" name="id" value="<c:out value='${liststaff.id}' />"/>
                </c:if>
                <tr>
                    <th>Staff Name:</th>
                    <td>
                        <input type="text" name="name" size="45" class="form-control"
                               value="<c:out value='${liststaff.name}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <th>Staff Email:</th>
                    <td>
                        <input type="text" name="email" size="45" class="form-control"
                               value="<c:out value='${liststaff.email}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <th>Staff Address:</th>
                    <td>
                        <input type="text" name="address" size="45" class="form-control"
                               value="<c:out value='${liststaff.address}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <th>Staff PhoneNumber:</th>
                    <td>
                        <input type="text" name="phoneNumber" size="45" class="form-control"
                               value="<c:out value='${liststaff.phone}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <th>Staff salary</th>
                    <td>
                        <input type="number" name="salary" size="45" class="form-control"
                               value="<c:out value='${liststaff.salary}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <th>Department:</th>
                    <td>
                        <select name="department" id="department" class="form-control">
                            <c:forEach var="depart" items="${listDepartment}">
                                <option value="${depart.id}" ${ liststaff.department.id == depart.id ? "selected" : ""}>${depart.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <!-- Thêm class CSS tùy chỉnh cho nút -->
                        <input type="submit" value="Save" class="save-btn" />
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>


<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>
