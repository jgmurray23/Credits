package ca.odell.bfiftytwo;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.bind.DatatypeConverter;

import ca.odell.credits.model.EdifactData;

public class Upload
{
		
		//dhltransfer:kw98hjka2b
        public static String USER="dhltransfer";
        public static String PASS="kw98hjka2b";
        //public static String UPLOAD_URL="https://dhl.candata.com/transfer/upload?name=CREDITS"; 
        //public static String UPLOAD_URL="https://dhl.candata.com/transfer/upload?name=CREDITS_TEST"; 
        public static String hostname = "dhl.candata.com";

        public static void main(String args[])
                throws Exception
        {
        	
        	
        		String t1 = "abc";
        	
        		Upload up = new Upload();
        		Vector<EdifactData>v = up.getAvailableEdifacts();
        		
        		up.setTranmissionNumbers(v);
        		
        		StringBuffer sb = new StringBuffer();
        		
        		Iterator<EdifactData> i = v.iterator();
        		EdifactData ed = null;
        		
        		while(i.hasNext() ){ 
        
        			ed = i.next() ;
        			if( !t1.equals(  ed.getTRANSMISSION_NO() ) ){
        			
        				sb.append(ed.getTRANSMISSION_NO()  ).append("\n");
        				t1 = ed.getTRANSMISSION_NO();
        				
        			}
        			sb.append( ed.generateLines()  ).append("\n");
        		
        		
        		}
        		
        	
        		String _hostname_ = InetAddress.getByName(hostname).getCanonicalHostName();
        		
        		//String UPLOAD_URL = "https://"+_hostname_+"/transfer/upload?name=CREDITS_TEST";
        		
        		String UPLOAD_URL = "https://"+_hostname_+"/transfer/upload?name=CREDITS_TEST";
        	
                URL url = new URL(UPLOAD_URL);
                HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
                /*
                 * This is required or else we do not know what type of message it is and will not process it.
                 */
                con.setRequestProperty("Content-type", "text/plain");
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestProperty("Authorization", "Basic " + DatatypeConverter.printBase64Binary((USER + ":" + PASS).getBytes()));

                OutputStream out = con.getOutputStream();
                //out.write("message goes here".getBytes());
                out.write(sb.toString().getBytes() );
                out.close();

                System.out.println(sb.toString() );
                
                int res = con.getResponseCode();
                
                if(res != HttpURLConnection.HTTP_OK)
               {
                        throw new Exception("Bad response "+res);
                }

                ByteArrayOutputStream outb = new ByteArrayOutputStream();
                byte buf[] = new byte[10000];
                InputStream in = con.getInputStream();
                int siz=0;
                while((siz=in.read(buf))>0)
                {
                        outb.write(buf, 0, siz);
                }
                out.close();
                in.close();
                con.disconnect();

                System.out.println("Response->\n"+new String(outb.toByteArray()));
        }
        

        
        private void setTranmissionNumbers(Vector<EdifactData> v) throws Exception {
			
        	
        	Calendar calendar = Calendar.getInstance();
            Date date =  calendar.getTime();
			
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH); 
            int monthint = calendar.get(Calendar.MONTH  );
            
            System.out.println("DAY OF MONTH: " + dayOfMonth );
            
            String month = getMonthAsLetter(monthint);
            
            System.out.println("DAY OF MONTH: " + month );
            
            String dayOfMonthString = String.valueOf(dayOfMonth);
            
            if(dayOfMonthString.length() ==1  ){
            	dayOfMonthString = "0" + dayOfMonthString;
            }
            
            String trans1 = "DHLCA" + month + dayOfMonthString ;
            
            int tranmissioncounter = 1;
            int recordcounter = 0;
            
            EdifactData ed = null;
            
            Iterator<EdifactData> i = v.iterator();
            
            while(i.hasNext()){
            	
            	ed = i.next();
            	recordcounter++;
            	
            	if( recordcounter == 1000 ) {
            		recordcounter = 1;
            		tranmissioncounter++;
            	}
            	
            	
            	String transString = String.valueOf(tranmissioncounter);
                
                if(transString.length() ==1  ){
                	transString = "00" + transString;
                }
                else if (transString.length() ==2  ){
                	transString = "0" + transString;
                }
                else if( transString.length() ==3  ){
                	//
                }
                
                
                ed.setTRANSMISSION_NO( trans1 + "." + transString  );
            	saveTranmissionNumber(ed);
            }
            
            
            
		}

        private void saveTranmissionNumber(EdifactData ed) throws SQLException {
        	
        	Connection conn = getConnection();
        	
        	PreparedStatement pstmt = conn.prepareStatement("update EDIFACT set transmissionNumbers = ? where adjustmentnumber = ?");
		
        	pstmt.setString(1, ed.getTRANSMISSION_NO() );
			pstmt.setString(2, ed.getAdjustmentNumber() );
			
			pstmt.execute();
			
			pstmt.close();
			conn.close();
			
		}



		private String	getMonthAsLetter( int monthOfYear ){
        	
        	
        	String monthString = "";
        	
        	if( monthOfYear == 0 ){
        		monthString = "A" ;
        	}
        	if( monthOfYear == 1 ){
        		monthString = "B" ;
        	}
        	
        	if( monthOfYear == 2 ){
        		monthString = "C";
        	}
        	if( monthOfYear == 3 ){
        		monthString = "D";
        	}
        	if( monthOfYear == 4 ){
        		monthString = "E";
        	}
        	if( monthOfYear == 5 ){
        		monthString = "F" ;
        	}
        	if( monthOfYear == 6 ){
        		monthString = "G" ;
        	}
        	if( monthOfYear == 7 ){
        		monthString = "H" ;
        	}
        	if( monthOfYear == 8 ){
        		monthString = "I" ;
        	}
        	if( monthOfYear == 9 ){
        		monthString = "J" ;
        	}
        	if( monthOfYear == 10 ){
        		monthString = "K" ;
        	}
        	if( monthOfYear == 11 ){
        		monthString = "L" ;
        	}
        	
        	
        	return monthString;
        	
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
        
        
        
        
        
        private Vector<EdifactData> getAvailableEdifacts()  throws Exception  {
    		
    		PreparedStatement pstmt = null;
    		ResultSet rs = null;
    			
    		Connection conn = getConnection() ;
    		Vector<EdifactData> v = new Vector<EdifactData>() ;
    	
    		EdifactData ed =null;
    		
    			pstmt = conn.prepareStatement("select * from EDIFACT where transmitted is null");
    			
    			
    			rs= pstmt.executeQuery();
    			
    			while( rs.next() ){
    				
    				ed = new EdifactData();
    				
    				ed.setDocumentNumber( rs.getString("documentNumber") ) ;
    				ed.setAdjustmentNumber( rs.getString("adjustmentNumber" ) );
    				ed.setShipmentNumber(rs.getString("shipmentNumber"));
    				ed.setDutyPaidDate(rs.getDate("dutyPaidDate"));
    				ed.setExportDate(rs.getDate("exportdate"));
    				ed.setExportCompanyIdentification(rs.getString("exportcompanyidentification"));
    				ed.setPowerOfAttorneyIndicator(rs.getString("powerofAttorneyIndicator"));
    				ed.setImporterFirstName(rs.getString("importerFirstName"));
    				ed.setImporterLastName(rs.getString("importerLastName"));
    				ed.setImportAddress(rs.getString("importAddress"));
    				ed.setCity(rs.getString("city"));
    				ed.setProvince(rs.getString("province"));
    				ed.setCountry(rs.getString("Country"));
    				ed.setPostalcode(rs.getString("postalcode"));
    				ed.setDuty(rs.getString("duty"));
    				ed.setExciseDuty(rs.getString("exciseDuty"));
    				ed.setExciseTax(rs.getString("exciseTax"));
    				ed.setGST(rs.getString("GST"));
    				ed.setPST(rs.getString("PST"));
    				
    			
    				//System.out.println("get nb adding...");
    				v.add(ed);
    			}
    		
    		
    		
    		pstmt.close();
    		rs.close();
    		
    		return v;
    
}

    
        
}
