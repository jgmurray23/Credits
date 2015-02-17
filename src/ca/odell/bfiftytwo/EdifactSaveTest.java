package ca.odell.bfiftytwo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import ca.odell.credits.model.EdifactData;

public class EdifactSaveTest {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
		//EdifactSaveTest et = new EdifactSaveTest();
		
		Vector<EdifactData> v = new Vector<EdifactData>();
		
		EdifactData ed = new EdifactData();
		ed.setAdjustmentNumber("adj1");
		ed.setCity("city");
		ed.setCountry("CA");
		ed.setDocumentNumber("dn");
		ed.setDuty("0.00");
		ed.setDutyPaidDate(new java.util.Date( System.currentTimeMillis() ));
		ed.setExciseDuty("0.00");
		ed.setExciseTax("0.00");
		ed.setExportCompanyIdentification("BORDFR");
		ed.setExportDate(new java.util.Date( System.currentTimeMillis() ));
		ed.setGST("0.05");
		ed.setImportAddress("23 23RD ST");
		ed.setImporterFirstName("firstname");
		ed.setImporterLastName("lastname");
		ed.setPostalcode("N1T2A2");
		ed.setPowerOfAttorneyIndicator("1");
		ed.setProvince("ON");
		ed.setPST("0.09");
		ed.setShipmentNumber("shipmentNumber");
		
		v.add(ed);

		EdifactData ed1 = new EdifactData();
		ed1.setAdjustmentNumber("1adj1");
		ed1.setCity("city1");
		ed1.setCountry("CA");
		ed1.setDocumentNumber("dn1");
		ed1.setDuty("1.00");
		ed1.setDutyPaidDate(new java.util.Date( System.currentTimeMillis() ));
		ed1.setExciseDuty("1.00");
		ed1.setExciseTax("1.00");
		ed1.setExportCompanyIdentification("BORDFR1");
		ed1.setExportDate(new java.util.Date( System.currentTimeMillis() ));
		ed1.setGST("0.05");
		ed1.setImportAddress("23 23RD ST-1");
		ed1.setImporterFirstName("firstname1");
		ed1.setImporterLastName("lastname1");
		ed1.setPostalcode("N1T2A2");
		ed1.setPowerOfAttorneyIndicator("1");
		ed1.setProvince("ON");
		ed1.setPST("0.09");
		ed1.setShipmentNumber("shipmentNumber1");

		v.add(ed1);
		
		CreditsMain cm = new CreditsMain();
		cm.saveReturnCandidates(v);
		
		

	}
	
	public void saveEdifacts(Vector<EdifactData> v ){
		
		
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
