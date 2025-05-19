<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Withdraw Funds</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <div class="container">
        <h2>Withdraw from Account</h2>

        <form action="bank" method="post">
            <label for="amount">Amount to Withdraw:</label>
            <input type="number" name="amount" step="0.01" required>

            <input type="hidden" name="action" value="withdraw">
            <input type="submit" value="Withdraw">
        </form>

        <br>
        <a href="index.jsp">Back to Home</a>
    </div>
</body>
</html>