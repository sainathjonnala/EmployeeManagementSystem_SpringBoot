<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.comakeit.spring.entities.*"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Administrator Operations</title>
<style type="text/css">
body {
	padding-left: 20px;
}

* {
	box-sizing: border-box;
}

.links {
	float: left;
	width: 40%;
}

.forms {
	float: left;
	width: 60%;
	padding: 20px;
	font-family: monospace;
	font-size: 16px;
}

.hrefs {
	margin: 0px;
	padding: 10px 10px;
	font-size: 20px;
	color: red;
}

table {
	display: table;
	border-collapse: separate;
	border-spacing: 2px;
	border-color: grey;
}

th {
	color: #E4F1F6;
	text-align: left;
	padding: 8px;
	background-color: #034F85;
}

td {
	text-align: left;
	padding: 8px;
	background-color: #1A3E4C;
	color: white;
}

tr {
	color: black;
}
</style>
</head>
<body>
	<div class="body">
		<div style="padding: 10px 10px;">
			<h2>Welcome Administrator</h2>
			<%
				LoginEntity loginCredentials = (LoginEntity) session.getAttribute("loginCredentials");
				out.println("<h2>" + loginCredentials.getUsername() + "</h2>");
			%>
		</div>
		<div class="links">
			<div
				style="margin: 0px; padding: 10px 10px; font-size: 20px; color: black;">
				<h3>Choose from below operations:</h3>
			</div>
			<div class="hrefs">
				<a href="AdminOperationsHomePage.jsp?action=create">Add Employee
				</a>
			</div>

			<div class="hrefs">
				<a href="AdminOperationsHomePage.jsp?action=delete">Delete
					Employee</a>
			</div>
			<div class="hrefs">

				<a href="viewEmployees">View Employees</a>
			</div>
			<div class="hrefs">
				<a href="viewDepartments">View Departments</a>
			</div>
			<div class="hrefs">
				<a
					href="AdminOperationsHomePage.jsp?action=viewEmployeesofSpecificManager">View
					Employees of specific Manager</a>
			</div>
			<div class="hrefs">
				<a href="AdminOperationsHomePage.jsp?action=viewEmployeeDetails">View
					Employee Details and PF</a>
			</div>
			<div class="hrefs">
				<a href="AdminOperationsHomePage.jsp?action=listEmployeesBySalary">View
					Employees By Salary Range</a>
			</div>
			<div class="hrefs">
				<a href="index.jsp">Logout</a>
			</div>
		</div>
		<div class="forms">
			<%
				String action = request.getParameter("action");
				if (action != null) {
					if (action.equals("create")) {
			%>

			<div style="">
				<p>Employee Details:</p>
				<br />
				<p id="demo"></p>
				<h2>Create User</h2>
				<form action="addEmployee" method="POST"
					onsubmit="return myfunction(email)">
					<div style="text-align: left;">
						<div>
							Employee-ID: <input name="employee_id" />
						</div>

						<div>
							LastName: <input name="last_name" />
						</div>
						<div>
							FirstName: <input name="first_name" />
						</div>
						<div>
							Email-ID: <input name="email" />
						</div>
						<div>
							Address: <input name="address" />
						</div>
						<div>
							Department-ID: <input name="department_id" />
						</div>
						<div>
							Salary: <input name="salary" />
						</div>
						<div>
							Reporting-Manager-id: <input name="manager_id" />
						</div>
						<div>
							Role-Id: <input name="role_id" />
						</div>
						<div>
							<input type="submit" name="operation" value="add" />
						</div>
					</div>
				</form>
			</div>


			<%
				} else if (action.equals("delete")) {
			%>
			<form action="deleteEmployee" method="post">
				<p>Enter the Employee-ID to delete from records:</p>
				<div style="width: 100px; text-align: left;">
					<div>
						Employee-ID: <input name="employee_id" /> <input type="submit"
							value="delete" />
					</div>
				</div>
			</form>


			<%
				} else if (action.equals("viewEmployeesofSpecificManager")) {
			%>
			<form action="viewEmployeesOfManager" method="post">
				<p>Enter the Reporting Manager:</p>
				<div style="width: 100px; text-align: left;">
					<div>
						Reporting Manager: <input name="manager_id" /> <input
							type="submit" value="view" />
					</div>
				</div>
			</form>


			<%
				} else if (action.equals("viewEmployeeDetails")) {
			%>
			<form action="viewEmployeeDetails" method="post">
				<p>Enter the EmployeeID :</p>
				<div style="width: 100px; text-align: left;">
					<div>
						Employee-ID: <input name="employee_id" /> <input type="submit"
							value="view" />
					</div>
				</div>
			</form>


			<%
				} else if (action.equals("listEmployeesBySalary")) {
			%>
			<form action="viewEmployeesBySalary" method="post">
				<p>Enter the Range :</p>
				<div style="width: 100px; text-align: left;">
					<div>
						Salary From: <input name="salaryFrom" /> To:<input
							name="salaryTo" /> <input type="submit" value="view" />
					</div>
				</div>
			</form>


			<%
				}
				}
				String error = request.getParameter("error");
				if (error != null) {
					if (error.equals("create")) {
						out.println("<h3>Error in adding employee!!!</h3>");
						out.println("<a href=\"AdminOperationsHomePage.jsp\">Go Back</a> ");
					} else if (error.equals("delete")) {
						out.println("<h4>Error in Deletion</h4>");
					} else if (error.equals("viewEmployeesOfManager")) {
						out.println("<h4>Enter valid Manager name</h4>");
					} else if (error.equals("viewEmployeeDetails")) {
						out.println("<h4>No Employee Found!!</h4>");
					} else if (error.equals("listEmployeesBySalary")) {
						out.println("<h4>No Employees Found!!</h4>");
					}
				}
			%>
			<%
				String result = request.getParameter("result");

				EmployeeEntity employee = (EmployeeEntity) request.getAttribute("employee");

				List<EmployeeEntity> employeesList = (ArrayList<EmployeeEntity>) request.getAttribute("employeesList");

				List<DepartmentEntity> departmentsList = (ArrayList<DepartmentEntity>) request
						.getAttribute("departmentsList");

				String manager_id = (String) request.getAttribute("manager_id");
			%>

			<%
				if (result != null) {
					if (result.equals("created")) {
			%>

			<div class="hrefs">
				<p><%="Employee Created " + employee.getEmployee_id()%></p>

				<%
					} else if (result.equals("deleted")) {
				%>
				<p><%="Employee Deleted " + employee.getEmployee_id()%></p>

			</div>
			<%
				} else if (result.equals("viewEmployees")) {
						out.println("<table>");
						out.println("<caption>Employees Directory</caption>");
						out.println("<th>Employee ID </th><th>Employee Name</th><th>Department ID</th>");
						for (EmployeeEntity iterator : employeesList) {
							out.println("<tr><td>" + iterator.getEmployee_id() + "</td><td>" + iterator.getLast_name() + " "
									+ iterator.getFirst_name() + "</td><td>" + iterator.getDepartment().getDepartment_id()
									+ "</td></tr>");
						}
						out.println("</table>");
					} else if (result.equals("viewDepartments")) {
						out.println("<table>");
						out.println("<caption>Departments List</caption>");
						out.println("<th>Department ID</th><th>Department Name</th>");
						for (DepartmentEntity iterator : departmentsList) {
							out.println("<tr><td>" + iterator.getDepartment_id() + "</td><td>"
									+ iterator.getDepartment_name() + "</td></tr>");
						}

						out.println("</table>");

					} else if (result.equals("viewEmployeesOfManager")) {
						out.println("<table>");
						out.println("<h4>Reporting Manager: " + manager_id + "</h4>");
						out.println("<th>Employee ID </th><th>Employee Name</th><th>Department ID</th>");
						for (EmployeeEntity iterator : employeesList) {
							out.println("<tr><td>" + iterator.getEmployee_id() + "</td><td> " + iterator.getLast_name()
									+ " " + iterator.getFirst_name() + "</td><td>"
									+ iterator.getDepartment().getDepartment_id() + "</td></tr>");
						}
						out.println("</table>");
					} else if (result.equals("employeeDetails")) {
						out.println("<table>");
						out.println("<caption>Employee Details</caption>");

						out.println(
								"<th>Employee ID</th><th>Employee Name</th><th>Department ID</th><th>Employee Salary</th><th>PF</th><th>Reporting TO</th>");
						out.println("<tr><td>" + employee.getEmployee_id() + "</td><td>" + employee.getLast_name() + " "
								+ employee.getFirst_name() + "</td><td>" + employee.getDepartment().getDepartment_id()
								+ "</td><td>" + employee.getSalary() + "</td><td>" + employee.getPF() + "</td><td>"
								+ employee.getManager_id() + "</td></tr>");
						out.println("</table>");
					} else if (result.equals("listEmployeesBySalary")) {
						out.println("<table>");
						out.println("<caption>Employees List</caption>");
						out.println("<th>Employee ID </th><th>Employee Name</th><th>Employee Salary</th>");
						for (EmployeeEntity iterator : employeesList) {
							out.println("<tr><td>" + iterator.getEmployee_id() + "</td><td> " + iterator.getLast_name()
									+ " " + iterator.getFirst_name() + "</td><td>" + iterator.getSalary() + "</td></tr>");
						}
						out.println("</table>");
					}
				}
			%>
		</div>
	</div>
</body>
</html>
