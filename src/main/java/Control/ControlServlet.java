package Control;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import javax.activation.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class ControlServlet
 */
@WebServlet("/ControlServlet")
@MultipartConfig(location = "/src/main/java/Control/ControlServlet")
public class ControlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
    public ControlServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	/*	define('DB_HOST', getenv('OPENSHIFT_MYSQL_DB_HOST'));
		define('DB_PORT', getenv('OPENSHIFT_MYSQL_DB_PORT'));
		define('DB_USER', getenv('OPENSHIFT_MYSQL_DB_USERNAME'));
		define('DB_PASS', getenv('OPENSHIFT_MYSQL_DB_PASSWORD'));
		define('DB_NAME', getenv('OPENSHIFT_GEAR_NAME'));
			*/  
		String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
        String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
        String appname = System.getenv("OPENSHIFT_APP_NAME");
        response.setContentType("text/html"); 
        PrintWriter out = response.getWriter();
        out.println("jdbc:mysql://"+host+":"+port+"/test "+ appname);
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://"+host+":"+port+"/test", "adminin72uv9", "iM2MZJYmwhLf");
            Statement stmt = conn.createStatement();
            String query = "select * from contrat ;";
            ResultSet resultset = stmt.executeQuery(query);

            
            response.setContentType("text/html"); 
            out.println("jdbc:mysql://"+host+":"+port+"/test");
            int i = 0;
            while (resultset.next()) {

                String id = resultset.getString(1);
                out.println(id);
                String id1 = resultset.getString(2);
                out.println(id1);
                String id2 = resultset.getString(3);
                out.println(id2);
                i = i + 1;

            }
            out.println("Vous avez " + i + " Enregistrement");

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		
		
//		System.out.println("//////////////////////////");
//		String connectionparams = "jdbc:mysql://127.12.161.2:3306/test";
	//	String connectionparams = "jdbc:mysql://test-rhvelo.rhcloud.com:3306/";
		//Map<String, String> env = System.getenv();
		//String s = env.get("OPENSHIFT_MYSQLDB_DB_HOST");
		//System.out.println("variable env : " + s);
	   
//		String uname = "adminin72uv9";                   
//		String psword = "iM2MZJYmwhLf";
//		PrintWriter out=response.getWriter();
//		out.println("OK");
//		
//		
//		try {
//		    Class.forName("com.mysql.jdbc.Driver");
//		} catch (ClassNotFoundException ex) {
//			out.println("Exeption class formname");  // error out
//		}
//		Connection connection=null;
//		
//		try {
//		//	String connectionparams="jdbc:mysql://$OPENSHIFT_DB_HOST:$OPENSHIFT_DB_PORT/";
//			connection = DriverManager.getConnection(connectionparams, uname, psword); 
//			//connection = DriverManager.getConnection(    "jdbc:mysql://127.12.161.2:3306/test", uname, psword);
//	 
//			Statement statement = connection.createStatement();
//			
//			String sql = "INSERT INTO user(name, firstname, email, pass, sexe , datenaissance, idc) VALUES('k','d','k@d','k','1','23654789','3') ;"; 
//					//+ name + ")";
//			int rs=statement.executeUpdate(sql);
//			out.println("user ajoute"); 
//		   
//			connection.close();
//			//PreparedStatement prep = connection.prepareStatement(sql);
//		}
//	catch(Exception E){ //Any Exceptions will be caught here System.out.println(“The error is==”+E.getMessage()); }
//
//		out.println("The error is=="+E.getMessage()); 
//		}
		//this.getServletContext().getRequestDispatcher( "/index.html" ).forward( request, response );
	}
		 //this.getServletContext().getRequestDispatcher( "/test.jsp" ).forward( request, response );
		

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
