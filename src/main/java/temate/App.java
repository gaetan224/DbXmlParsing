package temate;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
{
    try {
        DbToXml db = new DbToXml("jdbc:mysql://localhost:3306/filme?useLegacyDatetimeCode=false&serverTimezone=America/New_York", "root", "", "com.mysql.cj.jdbc.Driver");
        db.parseAllDb("file.xml");

    }catch (Exception ex){
        System.out.println("someting went wrong!");
        ex.printStackTrace();
    }
}
}
