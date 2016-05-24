<h2>DbToXml </h2>
<h4> A small library for parsing database query to an xml output </h4>

<ul>
	<li>	
		<h5> parsing the whole database </h5>
		DbToXml db = new DbToXml("jdbc:mysql://localhost:3306/...", "root", "", "com.mysql.cj.jdbc.Driver"); </br>
        db.parseAllDb();</br>
        db.save("file.xml");</br>

        file.xml:
        <pre>
        &lt;?xml version="1.0" encoding="utf-8" standalone="no"?&gt;
            &lt;databaseName&gt;
                &lt;table1s&gt;
                    &lt;table1 id="[id value for line in database]"&gt;
                        &lt;field1&gt;
                        [field1's value]
                        &lt;/field1&gt;

                        &lt;field2&gt;
                        [field2's value]
                        &lt;/field2&gt;
                        ...

                    &lt;/table1&gt;
                    ...

                &lt;/table1s&gt;

                ...
            &lt;/databaseName&gt;
            </pre>

	</li>


		<li>
    		<h5> parsing an SQL query </h5>
    		DbToXml db = new DbToXml("jdbc:mysql://localhost:3306/...", "root", "", "com.mysql.cj.jdbc.Driver"); </br>
            db.parseQuery("SelEct * FROM role,people where role.pid = people.pid ");
            db.save("file.xml");

            file.xml:
            <pre>
            &lt;?xml version="1.0" encoding="utf-8" standalone="no"?&gt;
                &lt;Query&gt;
                    &lt;line&gt;
                        &lt;table1 id="[id value for line in database]"&gt;
                            &lt;field1&gt;
                            [field1's value]
                            &lt;/field1&gt;

                            &lt;field2&gt;
                            [field2's value]
                            &lt;/field2&gt;
                            ...

                    &lt;/line&gt;

                    ...
                &lt;/Query&gt;
                </pre>

    	</li>

    		<li>
            		<h5> parsing using preparedStatement and indicating output folder </h5>
            		DbToXml db = new DbToXml("jdbc:mysql://localhost:3306/...", "root", "", "com.mysql.cj.jdbc.Driver"); </br>
                    db.parseQuery("SelEct * FROM role,people where role.pid = people.pid ");
                    db.save("file.xml");

                    file.xml:
                    <pre>
                    &lt;?xml version="1.0" encoding="utf-8" standalone="no"?&gt;
                        &lt;Query&gt;
                            &lt;line&gt;
                                &lt;table1 id="[id value for line in database]"&gt;
                                    &lt;field1&gt;
                                    [field1's value]
                                    &lt;/field1&gt;

                                    &lt;field2&gt;
                                    [field2's value]
                                    &lt;/field2&gt;
                                    ...

                            &lt;/line&gt;

                            ...
                        &lt;/Query&gt;
                        </pre>

            	</li>
		
</ul>