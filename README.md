<h2>DbToXml </h2>
<h4> A small library for parsing database query to an xml output </h4>

<ul>
	<li>	
		<h5> parsing the whole database </h5>
		DbToXml db = new DbToXml("jdbc:mysql://localhost:3306/...", "root", "", "com.mysql.cj.jdbc.Driver"); </br>
        Document doc = db.parseAllDb();</br>
        db.save("file.xml");</br>

        file.xml:
        <i>
        <?xml version="1.0" encoding="utf-8" standalone="no"?></br>
            <databaseName></br>
                <table1s></br>
                    <table1 id="[id value for line in database]"></br>
                        <field1></br>
                        [field1 value]</br>
                        </field1></br>

                        <field2></br>
                        [field2 value]</br>
                        </field2></br>
                        ...</br>

                    </table1></br>
                    ...</br>

                </table1s></br>

                ...</br>
            </databaseName> </br>
        </i>
	</li>
		
</ul>