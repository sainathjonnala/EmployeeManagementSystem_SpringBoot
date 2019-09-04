<%@page import="com.comakeit.spring.entities.*"
	import="java.time.LocalDate" import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Employee Home Page</title>
<style>
.link {
	margin: 0px;
	padding: 10px 10px;
	font-size: 19px;
}

table {
	border-collapse: collapse;
	width: 100%;
}

th, td {
	text-align: left;
	padding: 9px;
}

tr:nth-child(even) {
	background-color: #f2f2f2;
}
</style>
</head>
<body>

	<%
		EmployeeEntity employee = (EmployeeEntity) session.getAttribute("employee");
		List<LeaveEntity> leavesList = (List<LeaveEntity>) request.getAttribute("leavesList");
		String role = employee.getLogin().getRole().getRole_name();
		if (role.equals("employee")) {
	%>
	<div class="link" style="color: blue;">
		<%="<h2>Welcome Employee: " + employee.getEmployee_id() + " " + employee.getLast_name() + " "
						+ employee.getFirst_name() + " </h2>"%>
		<%="Reporting to : " + employee.getManager_id()%>
	</div>
	<div class="link" style="color: black;">
		<h2>Choose from below operations:</h2>

		<%
			String message = request.getParameter("message");
				if (message != null) {
					if (message.equals("success"))
						out.println("<h2>Leave Applied Successfully!!</h2>");
					else if (message.equals("failed"))
						out.println("<h2>Leave Application Failed!! Try Again!!</h2>");
				}
		%>
	</div>


	<div class="link" style="color: red;">
		<a href="EmployeeHomePage.jsp?action=apply">Apply For Leave</a>
	</div>
	<div class="link" style="color: red;">
		<a href="/viewLeaveBalance">View My Leave Balance </a>
	</div>
	<div class="link" style="color: red;">
		<a href="/viewLeaves">View My Leaves </a>
	</div>
	<div class="link" style="color: red;">
		<a href="/viewAppliedLeaves">Cancel Leave </a>
	</div>

	<div class="link" style="color: black;">
		<a href="index.jsp">Logout </a>
	</div>
	<%
		} else if (role.equals("manager") && !employee.getEmployee_id().equals(employee.getManager_id())) {
	%>
	<div class="link" style="color: blue;">
		<%
			String message = request.getParameter("message");
				out.println("<h2>Welcome Manager: " + employee.getEmployee_id() + " " + employee.getLast_name() + " "
						+ employee.getFirst_name() + " </h2>");
				out.println("<h3>Reporting To: " + employee.getManager_id() + "</h3>");
				if (message != null) {
					if (message.equals("success"))
						out.println("<h2>Leave Applied Successfully!!</h2>");
					else if (message.equals("failed"))
						out.println("<h2>Leave Application Failed!! Try Again!!</h2>");
				}
		%>
	</div>
	<div class="link" style="color: red;">
		<a href="EmployeeHomePage.jsp?action=apply">Apply Leave</a>
	</div>
	<div class="link" style="color: red;">
		<a href="/viewLeaveRequests">View Leave Requests</a>
	</div>
	<div class="link" style="color: red;">
		<a href="viewLeaveBalance">View My Leave Balance </a>
	</div>
	<div class="link" style="color: red;">
		<a href="/viewLeaves">View My Leaves </a>
	</div>
	<div class="link" style="color: red;">
		<a href="/viewAppliedLeaves">Cancel Leave </a>
	</div>
	<div class="link" style="color: black;">
		<a href="index.jsp">Logout </a>
	</div>

	<%
		} else if (role.equals("manager")) {
	%>
	<div class="link" style="color: blue;">
		<%="<h2>Welcome Manager: " + employee.getEmployee_id() + " " + employee.getLast_name() + " "
						+ employee.getFirst_name() + " </h2>"%>
	</div>
	<div class="link" style="color: black;">
		<h2>Choose from below operations:</h2>
	</div>
	<div class="link" style="color: red;">
		<a href="/viewLeaveRequests">View Leave Requests</a>
	</div>
	<div class="link" style="color: black;">
		<a href="index.jsp">Logout </a>
	</div>

	<%
		}
		String action = (String) request.getParameter("action");
		if (action != null) {
			if (action.equals("apply")) {
	%>
	<form class="link" action="applyLeave" method="post">
		LeaveType::<select name="leave_type">
			<option value="casual" selected>Casual</option>
			<option value="loss_of_pay">Loss Of Pay</option>
		</select><br /> From date::<input type="date" name="start_date"
			value="<%=LocalDate.now()%>" placeholder="yyyy-MM-dd"><br />
		To date::<input type="date" name="end_date" placeholder="yyyy-MM-dd" /><br />
		Apply To <input type="text" value="<%=employee.getManager_id()%>"
			name="apply_to" disabled="disabled"> Reason::<input
			type="text" name="reason" /><br /> <input type="submit"
			name="Apply" />
	</form>

	<%
		} else if (action.equals("view_leaves")) {
	%>
	<table>
		<caption>Leave Directory</caption>
		<tr>
			<th>Leave-Id</th>
			<th>Leave Type</th>
			<th>Status</th>
			<th>From Date</th>
			<th>To Date</th>
		</tr>

		<%
			for (LeaveEntity iterator : leavesList) {
						out.println("<tr><td>" + iterator.getLeave_id() + "</td>");
						out.println("<td>" + iterator.getLeave_type() + "</td>");
						out.println("<td>" + iterator.getStatus() + "</td>");
						out.println("<td>" + iterator.getFrom_date() + "</td>");
						out.println("<td>" + iterator.getTo_date() + "</td></tr>");
					}
				}

				else if (action.equals("view_leave_requests")) {
		%>
	</table>
	<table>
		<caption>Leave Requests</caption>
		<tr>
			<th>employee-Id</th>
			<th>name</th>
			<th>leave-id</th>
			<th>leave type</th>
			<th>from_date</th>
			<th>to_date</th>
			<th>reason</th>
			<th>accept</th>
			<th>reject</th>
		</tr>
		<%
			for (LeaveEntity iterator : leavesList) {
						out.println("<tr><td>" + iterator.getEmployee().getEmployee_id() + "</td>");
						out.println("<td>" + iterator.getEmployee().getLast_name()
								+ iterator.getEmployee().getFirst_name() + "</td>");
						out.println("<td>" + iterator.getLeave_id() + "</td>");
						out.println("<td>" + iterator.getLeave_type() + "</td>");
						out.println("<td>" + iterator.getFrom_date() + "</td>");
						out.println("<td>" + iterator.getTo_date() + "</td>");
						out.println("<td>" + iterator.getReason() + "</td>");
						out.println(
								"<td><a href='acceptLeave?leave_id=" + iterator.getLeave_id() + "'>accept</a></td>");
						out.println("<td><a href='rejectLeave?leave_id=" + iterator.getLeave_id()
								+ "'>reject</a></td></tr>");
					}
				} else if (action.equals("cancel_leave")) {
		%>
	</table>
	<table>
		<caption>Cancel Leave</caption>
		<tr>
			<th>leave-id</th>
			<th>leave type</th>
			<th>from_date</th>
			<th>to_date</th>
			<th>status</th>
			<th>reason</th>
			<th>cancel</th>
		</tr>
		<%
			for (LeaveEntity iterator : leavesList) {
						if (iterator.getTo_date().isAfter(LocalDate.now())) {
							out.println("<tr><td>" + iterator.getLeave_id() + "</td>");
							out.println("<td>" + iterator.getLeave_type() + "</td>");
							out.println("<td>" + iterator.getFrom_date() + "</td>");
							out.println("<td>" + iterator.getTo_date() + "</td>");
							out.println("<td>" + iterator.getStatus() + "</td>");
							out.println("<td>" + iterator.getReason() + "</td>");

							out.println("<td><a href='cancelLeave?leave_id=" + iterator.getLeave_id()
									+ "'>cancel</a></td></tr>");

						}
					}
				} else if (action.equals("view_leave_balance")) {
		%>
	</table>
	<table>
		<caption>Leave Balance</caption>
		<tr>
			<th>Leave Balance-Id</th>
			<th>No of Casual Leaves</th>
			<th>Loss of Pay Leaves</th>
		</tr>
		<tr>
			<td>${leaveBalance.id}</td>
			<td>${leaveBalance.casual_leaves}</td>
			<td>${leaveBalance.loss_of_pay}</td>
		</tr>

		<%
			}
			}
		%>
	</table>
</body>
</html>