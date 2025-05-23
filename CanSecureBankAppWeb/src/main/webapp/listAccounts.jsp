<html>
	<head><title>List Accounts</title></head>
	<link rel="stylesheet" href="styles.css">
<body>
<div class="container">
    <h2>List Customer Accounts</h2>
    <form action="bank" method="post">
        <input type="hidden" name="action" value="listAccounts"/>
        Customer ID: <input type="text" name="customerId" required>
        <input type="submit" value="List Accounts">
    </form>
    <a href="index.jsp">Back to Home</a>
</div>
</body>
</html>