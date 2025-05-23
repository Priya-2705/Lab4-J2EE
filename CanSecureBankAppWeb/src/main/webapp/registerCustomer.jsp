<html>
	<head><title>Create Customer</title></head>
	<link rel="stylesheet" href="styles.css">
<body>
<div class="container">
    <h2>Create New Customer</h2>
    <form action="bank" method="post">
        <input type="hidden" name="action" value="createCustomer"/>
        <label for="name">Name: </label>
        <input type="text" name="name" required>
        <label for="email">Email: </label>
        <input type="text" name="email" required>
        <input type="submit" value="Create Customer">
    </form>
    <a href="index.jsp">Back to Home</a>
</div>
</body>
</html>