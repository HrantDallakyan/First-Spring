<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register</title>
</head>
<body>

<h3><%= request.getAttribute("message") != null ? request.getAttribute("message") : "" %>


<form action="http://localhost:8080/verify" method="post">
    Email: <input name="email"/><br/>
    Code: <input name="code"/><br/>

    <button type="submit"> Register</button>
</form>


</body>
</html>