<h2>DbToXml </h2>
<h4> A small library for parsing database query to an xml output </h4>

<ul>
	<li>	
		<h5> parsing the whole database </h5>
		DbToXml db = new DbToXml("jdbc:mysql://localhost:3306/...", "root", "", "com.mysql.cj.jdbc.Driver"); </br>
        Document doc = db.parseAllDb();</br>
        db.save("file.xml");</br>
	</li>
		
</ul>