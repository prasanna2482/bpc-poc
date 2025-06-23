// Vulnerable JDBC usage
String userInput = request.getParameter("username");
String query = "SELECT * FROM users WHERE username = '" + userInput + "'";
Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery(query);
