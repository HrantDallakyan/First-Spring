<%@ page import="am.smartcode.first_spring.util.constants.Parameter" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Home</title>
</head>
<body>

<h1>Welcome to home page dear <%=request.getSession().getAttribute(Parameter.NAME)%>
</h1>

<button><a href="/secure/note.jsp">Note</a></button><br/>
<button><a href="/secure/changePassword.jsp">Change Password</a></button><br/>

<button><a href="/logout">Logout</a></button>


</body>
</html>