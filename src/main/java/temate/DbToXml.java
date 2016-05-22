package temate;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import org.apache.commons.lang3.StringUtils;
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
    private  String dbName;
    Document doc;


    public DbToXml(String url,String username,String password, String driver) throws SQLException, ClassNotFoundException, ParserConfigurationException {

        Class.forName( driver );
        conn = DriverManager.getConnection(url, username, password);
        dbName = conn.getCatalog();


    }

    /**
     *
     * @return
     * @throws ParserConfigurationException
     * @throws SQLException
     * @throws TransformerException
     */

    public Document parseAllDb() throws ParserConfigurationException, SQLException, TransformerException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        doc = db.newDocument();

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
            //System.out.println(rs.getString("TABLE_NAME"));

            //add table xml element
            Element tableElement = doc.createElement(tableName+"s");
            root.appendChild(tableElement);
            List pKeys = this.getPrimaryKeys(tableName);
            if(pKeys.size()>1)
                tableElement.setAttribute("idType","multiple");
            else
                tableElement.setAttribute("idType","unique");
            //end add



            //get all entries
            r = stat.executeQuery("SELECT * FROM "+tableName);
            System.out.println("\t");

                // get query columns names through metadata
                metaData = r.getMetaData();
                int count = metaData.getColumnCount(); //number of column

                Element line ;
                while(r.next()) {
                    line = getEntryElement(tableName,r,pKeys);
                    tableElement.appendChild(line);
                }
        }

        return doc;

    }


    /**
     *
     * @param query
     * @return
     * @throws ParserConfigurationException
     * @throws SQLException
     * @throws TransformerException
     */
    public Document parseQuery(String query) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        doc = db.newDocument();

        Element root = doc.createElement(dbName);
        doc.appendChild(root);
        Statement stat = conn.createStatement();

        if(!StringUtils.containsIgnoreCase(query, "SELECT")){
           //System.out.println("OKOKOK");
            throw new Exception("This is not a SELECT Query");
        }

        ResultSet r = stat.executeQuery(query);







        return doc;
    }

    public void save(String outputDir) throws TransformerException {
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

    public List getPrimaryKeys(String tableName) throws SQLException {
        List keys = new ArrayList<String>();
        DatabaseMetaData md = conn.getMetaData();
        ResultSet r = md.getPrimaryKeys(dbName, "", tableName);
        ResultSetMetaData metaData = r.getMetaData();
        while(r.next()){
            System.out.println("cléeee = "+ r.getString("COLUMN_NAME"));
            keys.add(r.getString("COLUMN_NAME"));
        }
        return keys;
    }









    /*
    public List getTableNames() throws SQLException {
        List TableNames = new ArrayList<String>();
        DatabaseMetaData md = conn.getMetaData();
        String[] types = {"TABLE"};
        ResultSet rs = md.getTables(dbName, null, "%", types);
        while (rs.next()) {
            System.out.println("table = "+ rs.getString("TABLE_NAME"));
            TableNames.add(rs.getString("TABLE_NAME"));
        }
        return TableNames;
    }*/

    public Element getEntryElement(String tableName, ResultSet r,List pKeys ) throws SQLException {

        Element line = doc.createElement(tableName);
        line.setAttribute("id","");

        ResultSetMetaData metaData = r.getMetaData();
        int ColumnCount = metaData.getColumnCount();

        for (int i = 1; i <= ColumnCount; i++) {
            String columnName = metaData.getColumnLabel(i);
            String columnValue = r.getString(i);
            Element columnElement = doc.createElement(columnName);
            columnElement.appendChild(doc.createTextNode(columnValue));
            line.appendChild(columnElement);
            if(pKeys.contains(columnName)){
                if(pKeys.size() == 1)
                line.setAttribute("id",""+columnValue);
                else
                line.setAttribute("id",line.getAttribute("id")+""+columnValue+"_");
            }
        }

        // remove id 's last char if multiple id (to be improve)
        if(pKeys.size()> 1){

            StringBuilder b = new StringBuilder(line.getAttribute("id"));
            String s = b.substring(0,b.length()-1);
            line.setAttribute("id",s);
        }
        return line;
    }

}
