<h2>DbToXml </h2>
<h4> A small library for parsing database query to an xml output </h4>

<ul>
	<li>	
		DbToXml db = new DbToXml("jdbc:mysql://localhost:3306/...", "root", "", "com.mysql.cj.jdbc.Driver");
        Document doc = db.parseAllDb();
        db.save("file.xml");
	</li>
		
</ul>