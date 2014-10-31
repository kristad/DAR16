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
import javax.servlet.http.HttpSession;

import metier.User;
import domain.DAOUser;


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
        out.println("jdbc:mysql://"+host+":"+port+"/dar "+ appname);
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");
            HttpSession session; // il faut ajouter l'utilisation de cookies et url et autres ?
    		//int  visitCount=0;
    		 
    		String email=request.getParameter("email");
    		String password=request.getParameter("password");
    		
    		String password2=request.getParameter("password2");
    		String name=request.getParameter("name");
    		String firstname=request.getParameter("firstname");
    		String datenaissance=request.getParameter("datenaissance");
    		String sexe=request.getParameter("sexe");
    		
    		String insc=request.getParameter("inscription");
    		String con=request.getParameter("connect");
    		
    		if((insc == null && con != null) || (insc != null && con == null)){ // un seul bouton clické

    			 DAOUser du = new DAOUser();
    			 User u;
    		if(insc == null && con != null){ // connection
    			if(!(email.equals("")) && !(password.equals(""))){
    				
    				// verifier patern mail
    				// et password, minimum 4 caracteres -----------
    				try{
    				 u=du.getUserByEmail(email); // connexion bd
    				 if(u != null){
    					 if(password.equals(u.getPass())){ // verifier password et hasher !
    						 
    						// ********** a ajouter, inactiver l'ancienne et créer une nouvelle session, ?
    						 // RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html"); // revoyer la requete
    						 
    						 // il faut toujours donner à l'utilisateur la possibilité de se déconnecter ou savoir qu'il est connecté
    						try{
    						 session = request.getSession(false); // détruire les anciennes sessions
    						 session.invalidate();
    						}
    						catch(Exception e){
    						}
    						 session = request.getSession(true);
    						 response.getWriter().write(u.toStrig()); 
    				         session.setAttribute("userID", u.getId());
    						 
    				         /*
    						 if (session.isNew()){
    					         session.setAttribute("userID", u.getId());
    					      } else {
    					      }
    							// visitCount = (Integer)session.getAttribute("visitCountKey")+1;
    					        //  userID = (String)session.getAttribute(userID);
    					     	// session.setAttribute("visitCountKey",  visitCount);	 
    					      */
    					      
    						 
    					 }else{
    						 response.getWriter().write("mot de pass éronné");
    					 }
    				 }else{
    					 response.getWriter().write("utilisateur introuvable");
    				 }
    				}catch(Exception e){
    					response.getWriter().write("utilisateur introuvable"); // wait et redirect
    				}

    			}else{
    				response.sendRedirect("index.html");
    			}
    		}else{ // inscription
    			
    			if(sexe == null || !(password.equals(password2)) || email.equals("") || password.equals("") || password2.equals("") || name.equals("") || firstname.equals("") || datenaissance.equals("")){
    				response.sendRedirect("index.html");
    			}else{
    				//a faire, verifier le reste des paterns
    				// exécuter--------
    				
    				u=du.getUserByEmail(email);
    				int j=0;
    				int s=10;
    				if(sexe.equals("homme")){ // completer ------------------
    					s=1;
    				}else{
    					s=0;
    				}
    				try{
    					j=Integer.parseInt(datenaissance);
    				}catch (Exception e){
    					response.sendRedirect("index.html");
    				}
    				if(u == null && j != 0 && j<100000000 && j>= 10000000){ // inscrire
    					u=du.addUser(firstname, name, email, password, s, j, null);
    					
    				
    					
    					 session = request.getSession(true);
    	
    					 if (session.isNew()){
    				         session.setAttribute("userID", u.getId());
    				      } else { // seul moyen utilisé pour se déconncecter automatiquement sinon, session reste ouverte?
    				     // visitCount = (Integer)session.getAttribute("visitCountKey")+1;
    				       //  userID = (String)session.getAttribute(userID);
    				    	  // je ne fais pas inactivate ici
    					      session.setAttribute("userID", u.getId()); // ce n'est pas important de donner à un navigateur la possibilité de créer plussieures comptes, mais juste pour le teste
    				      }
    				      //session.setAttribute("visitCountKey",  visitCount);
    					 try{
    						wait(5000);
    					 }catch(Exception e){ 
    					 }
    						response.sendRedirect("Choix_contrat");
    					
    					
    				}else{ // utilisateur éxistant
    					response.getWriter().write("email dejas utilisé");
    				}
    					
    			}	
    		}}	
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
