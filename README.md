<h2>DbToXml </h2>
<h4> A small library for parsing database query to an xml output </h4>

<ul>
	<li>	
		<h5> parsing the whole database </h5>
		DbToXml db = new DbToXml("jdbc:mysql://localhost:3306/...", "root", "", "com.mysql.cj.jdbc.Driver"); </br>
        Document doc = db.parseAllDb();</br>
        db.save("file.xml");</br>

        file.xml:
        ```
        <?xml version="1.0" encoding="utf-8" standalone="no"?>
            <databaseName>
                <table1s>
                    <table1 id="[id value for line in database]">
                        <field1>
                        [field1 value]
                        </field1>

                        <field2>
                        [field2 value]
                        </field2>
                        ...

                    </table1>
                    ...

                </table1s>

                ...
            </databaseName>
            ```

	</li>
		
</ul>