package temate;

import java.io.File;
import java.sql.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by Gaetan on 06/05/2016.
 */
public class DbToXml {


    private Connection conn = null;


    public DbToXml(String url,String username,String password, String driver) throws SQLException, ClassNotFoundException {

        Class.forName( driver );
        conn = DriverManager.getConnection(url, username, password);
    }

    public void parseAllDb(String outputDir) throws ParserConfigurationException, SQLException, TransformerException {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.newDocument();

        System.out.println("DB = "+conn.getCatalog());
        String dbName = conn.getCatalog();

        Element root = doc.createElement(dbName);
        doc.appendChild(root);

        DatabaseMetaData md = conn.getMetaData();
        String[] types = {"TABLE"};
        ResultSet rs = md.getTables(dbName, null, "%", types);
        Statement stat = conn.createStatement();
        ResultSet r = null;
        String tableName;
        String columnName;
        String columnValue;
        ResultSetMetaData metaData = null;
        //iterate over tables
        while (rs.next()) {
            tableName = rs.getString("TABLE_NAME");
            System.out.println(rs.getString("TABLE_NAME"));

            //add table xml element
            Element tableElement = doc.createElement(tableName);
            root.appendChild(tableElement);
            //end add

            r = stat.executeQuery("SELECT * FROM "+tableName);
            System.out.println("\t");

                metaData = r.getMetaData();
                int count = metaData.getColumnCount(); //number of column
                String[] tabcolumn = new String[count];

                for (int i = 1; i <= count; i++)
                {
                    /*System.out.println(metaData.getColumnLabel(i));
                    columnName = metaData.getColumnLabel(i);
                    Element columnElement = doc.createElement(columnName);
                    tableElement.appendChild(columnElement);*/
                    tabcolumn[i-1] = metaData.getColumnLabel(i);

                }


                while (r.next()) {
                    for (int i = 1; i <= count; i++){
                    columnName = metaData.getColumnLabel(i);
                    columnValue = r.getString(i);
                    Element columnElement = doc.createElement(columnName);
                    columnElement.appendChild(doc.createTextNode(columnValue));
                    tableElement.appendChild(columnElement);
                    System.out.println(columnValue);
                    }
                }


        }


        //saving file
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        transformer.transform(new DOMSource(doc), new StreamResult(new File(outputDir)));
        //end saving file

    }
}
