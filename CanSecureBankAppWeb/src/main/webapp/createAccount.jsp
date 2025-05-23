<html>
	<head><title>Create Account</title></head>
	<link rel="stylesheet" href="styles.css">
<body>
<div class="container">
    <h2>Create New Account</h2>
    <form action="bank" method="post">
        <input type="hidden" name="action" value="createAccount"/>
        <label for="customerId">Customer ID: </label>
        <input type="text" name="customerId" required>
        <label for="initialBalance">Initial Balance: </label>
        <input type="number" name="initialBalance" step="0.01" required>
        <input type="submit" value="Create Account">
    </form>
    <a href="index.jsp">Back to Home</a>
</div>
</body>
</html>