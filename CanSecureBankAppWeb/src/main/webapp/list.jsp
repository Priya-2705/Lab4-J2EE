<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Account List</title></head>
	<link rel="stylesheet" href="styles.css">
<body>
<div class="container">
    <h2>Customer's Account IDs</h2>
    <ul>
        <c:forEach var="id" items="${accounts}">
            <li>${id}</li>
        </c:forEach>
    </ul>
    <a href="index.jsp">Back to Home</a>
</div>
</body>
</html>