package ca.odell.bfiftytwo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

public class SpotCheckUtils {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	public void countParcels(Vector<String>orders ) throws Exception {
		
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String PARCEL_ID = "";
		
		Connection conn = getConnection();
		
		Iterator<String>i = orders.iterator();
		
		//iterate through orders and check how many ORDER_IDS have 
		//> 1 parcel
		while(i.hasNext()){
			
			
			
			
			
		}
		
	}
	
	
	public Vector<String> getMultipleInternalParcelID() throws Exception {
	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String ORDER_ID = "";
		Vector<String>order_ids = new Vector<String>();
		
		Connection conn = getConnection();
		

			pstmt = conn.prepareStatement("select  ORDER_ID from SOUTHBOUND where FWD_RTN_SAME_YN = 'Y' ");
			
			rs= pstmt.executeQuery();
			
			if(rs.next()){
				ORDER_ID = rs.getString("ORDER_ID");
			}
			order_ids.add(ORDER_ID);

		pstmt.close();
		rs.close();
		
		return order_ids;
		
		
				
		
	}
	
	
	
	
	private Connection getConnection() throws SQLException {

  		 ResourceBundle msdatabundle = ResourceBundle.getBundle("msdata", Locale.ENGLISH );
  		 
  		 String user = msdatabundle.getString("msdata.user");
  		 String password = msdatabundle.getString("msdata.password");
  		 String database = msdatabundle.getString("msdata.database");
  		 String servername = msdatabundle.getString("msdata.servername");
  		 String ipaddress = msdatabundle.getString("msdata.ipaddress");
  		 String portnumber = msdatabundle.getString("msdata.portnumber");
  		
  			
  			try {

  				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

  			}  catch( java.lang.ClassNotFoundException nfe) {
  					nfe.printStackTrace();
  			}

  			
  			String connectionUrl = "jdbc:sqlserver://" + ipaddress  + ":" + portnumber + ";" + "databaseName=" + database + ";" + "user="  + user + ";" + "password=" + password + ";" ;
  			
  			
  			Connection con = DriverManager.getConnection(connectionUrl);
  			
  			


  			 

  			return con; 

  	        	
  	    } // getConnectionMS

}
