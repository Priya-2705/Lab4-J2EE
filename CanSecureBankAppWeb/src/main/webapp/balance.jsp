<!DOCTYPE html>
<html>
	<head><title>Balance Inquiry</title></head>
	<link rel="stylesheet" href="styles.css">
<body>
	<div class="container">
	    <h2>Check Balance</h2>
	    <form action="bank" method="post">
	        <input type="hidden" name="action" value="balance"/>
	    	<label for="accountId">Account No:</label>
	        <input type="text" name="accountId"/>

	        
	        <button type="submit">Check Balance</button>
	    </form>
	</div>
</body>
</html>