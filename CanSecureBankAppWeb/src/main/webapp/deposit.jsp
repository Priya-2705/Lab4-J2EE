<!DOCTYPE html>
<html>
	<head><title>Deposit</title></head>
	<link rel="stylesheet" href="styles.css">
<body>
    <div class="container">
    	<h2>Deposit Funds</h2>
	    <form action="bank" method="post">
	        <input type="hidden" name="action" value="deposit"/>
			<label for="accountId">Account No:</label>
	        <input type="text" name="accountId"/>

	        <label for="amount">Amount: </label>
	        <input type="number" name="amount" step="0.01" required/>
	        <button type="submit">Deposit</button>
	    </form>
	    <a href="index.jsp">Back to Home</a>
	</div>
</body>
</html>