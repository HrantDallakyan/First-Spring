<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register</title>
</head>
<body>

<h3><%= request.getAttribute("message") != null ? request.getAttribute("message") : "" %>


<form action="http://localhost:8080/register" method="post">
    Name: <input name="name"/><br/>
    Last Name: <input name="lastname"/><br/>
    Age: <input name="age"/><br/>
    Email: <input name="email"/><br/>
    Password: <input name="password"/><br/>

    <button type="submit"> Register</button>
</form>


</body>
</html>