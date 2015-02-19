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

import ca.odell.credits.model.EdifactData;

public class UpdateAddressAndNames {

	public static void main(String[] args)  throws Exception {
		// TODO Auto-generated method stub
		
		UpdateAddressAndNames up = new UpdateAddressAndNames();
		up.lastNames();
		up.firstNames();
		up.cities();
		up.addresses();
		
		

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
	
	//address
private void addresses( ) throws Exception{
		
		PreparedStatement pstmt = null; 
		
		Vector<String>addresses = getAddresses();
		
		Connection conn = getConnection() ;
		
		pstmt = conn.prepareStatement("update edifact set importAddress = ? where tracking_no = ?");
		
		Iterator<String> i = addresses.iterator() ;
		
		String tracking_no = null;
		
		while( i.hasNext() ) {
			
			tracking_no = i.next() ;
			
			String address = getImporterAddressFromDatabase(tracking_no);
			
			
			pstmt.setString(1, address );
			pstmt.setString(2, tracking_no.trim() );
			pstmt.addBatch();
			
			System.out.println("ccno: " + tracking_no + " address: " + address  );
			
		}
		System.out.println("excuting....");
		pstmt.executeBatch();
		System.out.println(".....done");
		
	}
	
	
	//city
	private void cities( ) throws Exception{
		
		PreparedStatement pstmt = null; 
		
		Vector<String>cities = getCities();
		
		Connection conn = getConnection() ;
		
		pstmt = conn.prepareStatement("update edifact set city = ? where tracking_no = ?");
		
		Iterator<String> i = cities.iterator() ;
		
		String tracking_no = null;
		
		while( i.hasNext() ) {
			
			tracking_no = i.next() ;
			
			String city = getImporterCityFromDatabase(tracking_no);
			
			
			pstmt.setString(1, city );
			pstmt.setString(2, tracking_no.trim() );
			pstmt.addBatch();
			
			System.out.println("ccno: " + tracking_no + " city: " + city  );
			
		}
		System.out.println("excuting....");
		pstmt.executeBatch();
		System.out.println(".....done");
		
	}
	
	//firstnames
	private void firstNames( ) throws Exception{
		
		PreparedStatement pstmt = null; 
		
		Vector<String>firstNames = getFirstNames();
		
		Connection conn = getConnection() ;
		
		pstmt = conn.prepareStatement("update edifact set importerFirstName = ? where tracking_no = ?");
		
		Iterator<String> i = firstNames.iterator() ;
		
		String tracking_no = null;
		
		while( i.hasNext() ) {
			
			tracking_no = i.next() ;
			
			String importerFirstName = getFirstNameFromDatabase(tracking_no);
			
			
			pstmt.setString(1, importerFirstName );
			pstmt.setString(2, tracking_no );
			pstmt.addBatch();
			
			System.out.println("ccno: " + tracking_no + " firstname: " + importerFirstName  );
			
		}
		System.out.println("excuting....");
		pstmt.executeBatch();
		System.out.println(".....done");
		
	}
	
private void lastNames( ) throws Exception{
	
	PreparedStatement pstmt = null; 
	
	Vector<String>lastNames = getLastNames();
	
	Connection conn = getConnection() ;
	
	pstmt = conn.prepareStatement("update edifact set importerLastName = ? where tracking_no = ?");
	
	Iterator<String> i = lastNames.iterator() ;
	
	String tracking_no = null;
	
	while( i.hasNext() ) {
		
		tracking_no = i.next() ;
		
		String importerLastName = getLastNameFromDatabase(tracking_no);
		
		
		pstmt.setString(1, importerLastName );
		pstmt.setString(2, tracking_no );
		pstmt.addBatch();
		
		System.out.println("ccno: " + tracking_no + " lastname: " + importerLastName  );
		
	}
	System.out.println("excuting....");
	pstmt.executeBatch();
	System.out.println(".....done");
	
}


private String getFirstNameFromDatabase(String tracking_no ) throws Exception {
	

	
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String lastname = "";
	
	
	Connection conn = getConnection();
	

		pstmt = conn.prepareStatement("select * from consigneenames where ccno = ?");
		pstmt.setString(1, tracking_no );
		rs= pstmt.executeQuery();
		
		if(rs.next()){
			lastname = rs.getString("names");
		}

	pstmt.close();
	rs.close();
	
	String[] st = lastname.split("\\s* \\s*");
	
	/*
	return street[0].trim();
	*/
	
	if(   st == null || st.length <2 ){
		return "" ;
		
		
	}
	else return st[0] ;
}
	
	private String getLastNameFromDatabase(String tracking_no ) throws Exception {
		

		
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String lastname = "";
		
		
		Connection conn = getConnection();
		

			pstmt = conn.prepareStatement("select * from consigneenames where ccno = ?");
			pstmt.setString(1, tracking_no );
			rs= pstmt.executeQuery();
			
			if(rs.next()){
				lastname = rs.getString("names");
			}
	
		pstmt.close();
		rs.close();
		
		String[] st = lastname.split("\\s* \\s*");
		
		/*
		return street[0].trim();
		*/
		
		if(   st == null || st.length <2 ){
			return "" ;
			
			
		}
		else return st[1] ;
}
	
private Vector<String> getAddresses()  throws Exception  {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
			
		Connection conn = getConnection() ;
		Vector<String> v = new Vector<String>() ;
	
		
		
			pstmt = conn.prepareStatement("select tracking_no from edifact where importAddress = '' ");
			
			rs= pstmt.executeQuery();
			
			while( rs.next() ){
				
				v.add( rs.getString(1) );
			}
		
		
		
		pstmt.close();
		rs.close();
		
		
		return v;

}
	
	
private Vector<String> getCities()  throws Exception  {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
			
		Connection conn = getConnection() ;
		Vector<String> v = new Vector<String>() ;
	
		
		
			pstmt = conn.prepareStatement("select tracking_no from edifact where city = '' ");
			
			rs= pstmt.executeQuery();
			
			while( rs.next() ){
				
				v.add( rs.getString(1) );
			}
		
		
		
		pstmt.close();
		rs.close();
		
		
		return v;

}

private Vector<String> getFirstNames()  throws Exception  {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
			
		Connection conn = getConnection() ;
		Vector<String> v = new Vector<String>() ;
	
		
		
			pstmt = conn.prepareStatement("select tracking_no from edifact where importerfirstname = '' ");
			
			rs= pstmt.executeQuery();
			
			while( rs.next() ){
				
				v.add( rs.getString(1) );
			}
		
		
		
		pstmt.close();
		rs.close();
		
		
		return v;

}

	private Vector<String> getLastNames()  throws Exception  {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
			
		Connection conn = getConnection() ;
		Vector<String> v = new Vector<String>() ;
	
		
		
			pstmt = conn.prepareStatement("select tracking_no from edifact where importerlastname = '' ");
			
			rs= pstmt.executeQuery();
			
			while( rs.next() ){
				
				v.add( rs.getString(1) );
			}
		
		
		
		pstmt.close();
		rs.close();
		
		
		return v;

}
	
private String getImporterCityFromDatabaseOld( String fenixccno ) throws SQLException{
		
		
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String streetcity = "";
		
		
		Connection conn = getConnection();
		

			pstmt = conn.prepareStatement("select * from address where ccno = ?");
			pstmt.setString(1, fenixccno.trim() );
			rs= pstmt.executeQuery();
			
			if(rs.next()){
				streetcity = rs.getString("STREETCITY");
				System.out.println("got a city: " + streetcity );
			}
		
	
		pstmt.close();
		rs.close();
		
	
		String []city = streetcity.split("\\.") ;
		
		System.out.println("city length: " + city.length );
		
		
		if(   city == null || city.length <2 ){
			
			if( streetcity.length() >8  ){
				System.out.println("city substring: "  + streetcity.substring(streetcity.length()-8, streetcity.length()) );
				return streetcity.substring(streetcity.length()-8, streetcity.length() );
			}
			
			else{
				return "" ;
			}
			
			
		}
		
		else {
			
			System.out.println("city is: "  + city[1] );
			
			return city[1].replaceAll("\\s", "");
		}
	}

	
private String getImporterCityFromDatabase( String fenixccno ) throws SQLException{
		
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String streetcity = "";
		
		
		Connection conn = getConnection();
		

			pstmt = conn.prepareStatement("select * from address where ccno = ?");
			pstmt.setString(1, fenixccno.trim() );
			rs= pstmt.executeQuery();
			
			if(rs.next()){
				streetcity = rs.getString("STREETCITY");
				System.out.println("got a city: " + streetcity );
			}
		
	
		pstmt.close();
		rs.close();
		
		if( streetcity !=null && streetcity.length() > 10 ){
			
			return streetcity.substring( streetcity.length()-10, streetcity.length() );
			
		}
		else{
			return "NOADDRESS" ;
		}
	
		
	}


private String getImporterAddressFromDatabase ( String ccno  ) throws SQLException {
	
	
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String streetcity = "";
	
	
	Connection conn = getConnection();
	

		pstmt = conn.prepareStatement("select * from address where ccno = ?");
		pstmt.setString(1, ccno.trim() );
		rs= pstmt.executeQuery();
		
		if(rs.next()){
			streetcity = rs.getString("STREETCITY");
			
		}
	

	pstmt.close();
	rs.close();
	
	
	//String []street = streetcity.split("\\.") ;
	
	//System.out.println("street length: " + street.length );
	/*
	
	if(   street == null || street.length <2 ){
		return "" ;
		
		
	}
	
	else {
		
		System.out.println("street is: "  + street[0] );
		
		return street[0].replaceAll("\\s", "");
	}
	*/
	
	if( streetcity !=null && streetcity.length()> 20){
		return streetcity.substring(0, 20);
	}
	else{
		return "NOSTREET";
	}
	
	
	
}
	
}
