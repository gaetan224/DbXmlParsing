<h2>DbToXml </h2>
<h4> A small library for parsing database query to an xml output </h4>

<ul>
	<li>	
		<h5> parsing the whole database </h5>
		DbToXml db = new DbToXml("jdbc:mysql://localhost:3306/...", "root", "", "com.mysql.cj.jdbc.Driver"); </br>
        Document doc = db.parseAllDb();</br>
        db.save("file.xml");</br>

        file.xml:
        <pre>
        &lt;?xml version="1.0" encoding="utf-8" standalone="no"?&gt;
            &lt;databaseName&gt;
                &lt;table1s&gt;
                    &lt;table1 id="[id value for line in database]"&gt;
                        &lt;field1&gt;
                        [field1 value]
                        &lt;/field1&gt;

                        &lt;field2&gt;
                        [field2 value]
                        &lt;/field2&gt;
                        ...

                    &lt;/table1&gt;
                    ...

                &lt;/table1s&gt;

                ...
            &lt;/databaseName&gt;
            </pre>

	</li>
		
</ul>