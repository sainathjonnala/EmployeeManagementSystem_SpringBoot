<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home Page</title>
</head>
<body>
	<div style="padding-left: 50px; font-family: monospace;">
		<h2>Login Page</h2>
		<form action="/loginValidation" method="POST">
			<div style="width: 100px; text-align: left;">
				
				<div style="padding: 10px;">
					User Name: <input name=username />
				</div>
				<div style="padding: 10px;">
					Password: <input type="password" name="password" />
				</div>
				<div style="padding: 20px; text-align: center">
					<input type="submit" value="Submit" />
				</div>
			</div>
		</form>
		<%
			String validation = request.getParameter("validation");
			if (validation != null) {
				if (validation.equals("invalid")) {
					out.println("Invalid Credentials!! Login Again!!");
				}
			}
		%>
	</div>
</body>
</html>