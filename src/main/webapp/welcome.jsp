<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>My first project</title>
</head>
<body>

<h3><%= request.getAttribute("message") != null ? request.getAttribute("message") : "" %>
</h3>

<h2>Welcome to our site</h2>


<form action="http://localhost:8080/login" method="post">


    Email: <input name="email"/><br/>
    Password: <input name="password"/><br/>

    Remember me <input type="checkbox" name="rememberMe"><br/>
    <button type="submit"> Login</button>
</form>

<button><a href="register.jsp">Register</a></button>


</body>
</html>