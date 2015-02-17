package ca.odell.bfiftytwo;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Color;
import java.awt.Insets;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.JButton;

import ca.odell.credits.model.EdifactData;
import ca.odell.credits.model.FenixData;
import ca.odell.credits.model.Northbound;
import ca.odell.credits.model.Southbound;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CreditsMain {

	private JFrame frame;
	private JLabel parcel_IDLabel;
	private JLabel order_IDLabel;
	private JTable northTable;
	private NorthTableModel northTableModel;
	private EdifactTableModel edifactTableModel;
	private JTable table;
	private JTable edifactTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreditsMain window = new CreditsMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CreditsMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(600, 600, 750, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel.setBackground(Color.WHITE);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel label = new JLabel(" ");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		panel.add(label, gbc_label);
		
		JLabel lblCredits = new JLabel("Credits");
		lblCredits.setBackground(Color.WHITE);
		lblCredits.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 16));
		GridBagConstraints gbc_lblCredits = new GridBagConstraints();
		gbc_lblCredits.gridwidth = 2;
		gbc_lblCredits.insets = new Insets(0, 0, 5, 5);
		gbc_lblCredits.gridx = 1;
		gbc_lblCredits.gridy = 0;
		panel.add(lblCredits, gbc_lblCredits);
		
		JLabel label_1 = new JLabel(" ");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 0);
		gbc_label_1.gridx = 3;
		gbc_label_1.gridy = 0;
		panel.add(label_1, gbc_label_1);
		
		JLabel lblNorth = new JLabel(" North");
		lblNorth.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 16));
		GridBagConstraints gbc_lblNorth = new GridBagConstraints();
		gbc_lblNorth.insets = new Insets(0, 0, 5, 5);
		gbc_lblNorth.gridx = 1;
		gbc_lblNorth.gridy = 1;
		panel.add(lblNorth, gbc_lblNorth);
		
		JLabel lblParcelid = new JLabel(" Parcel_ID");
		lblParcelid.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		GridBagConstraints gbc_lblParcelid = new GridBagConstraints();
		gbc_lblParcelid.anchor = GridBagConstraints.EAST;
		gbc_lblParcelid.insets = new Insets(0, 0, 5, 5);
		gbc_lblParcelid.gridx = 1;
		gbc_lblParcelid.gridy = 2;
		panel.add(lblParcelid, gbc_lblParcelid);
		
		parcel_IDLabel = new JLabel("                    ");
		GridBagConstraints gbc_parcel_IDLabel = new GridBagConstraints();
		gbc_parcel_IDLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_parcel_IDLabel.insets = new Insets(0, 0, 5, 5);
		gbc_parcel_IDLabel.gridx = 2;
		gbc_parcel_IDLabel.gridy = 2;
		panel.add(parcel_IDLabel, gbc_parcel_IDLabel);
		
		JLabel lblOrderid = new JLabel(" Order_ID");
		lblOrderid.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		GridBagConstraints gbc_lblOrderid = new GridBagConstraints();
		gbc_lblOrderid.anchor = GridBagConstraints.EAST;
		gbc_lblOrderid.insets = new Insets(0, 0, 5, 5);
		gbc_lblOrderid.gridx = 1;
		gbc_lblOrderid.gridy = 3;
		panel.add(lblOrderid, gbc_lblOrderid);
		
		order_IDLabel = new JLabel("");
		GridBagConstraints gbc_order_IDLabel = new GridBagConstraints();
		gbc_order_IDLabel.insets = new Insets(0, 0, 5, 5);
		gbc_order_IDLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_order_IDLabel.gridx = 2;
		gbc_order_IDLabel.gridy = 3;
		panel.add(order_IDLabel, gbc_order_IDLabel);
		
		JLabel lblAwb = new JLabel("AWB/CCNO");
		lblAwb.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		GridBagConstraints gbc_lblAwb = new GridBagConstraints();
		gbc_lblAwb.anchor = GridBagConstraints.EAST;
		gbc_lblAwb.insets = new Insets(0, 0, 5, 5);
		gbc_lblAwb.gridx = 1;
		gbc_lblAwb.gridy = 4;
		panel.add(lblAwb, gbc_lblAwb);
		
		JLabel lblSouth = new JLabel("North");
		lblSouth.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 16));
		GridBagConstraints gbc_lblSouth = new GridBagConstraints();
		gbc_lblSouth.insets = new Insets(0, 0, 5, 5);
		gbc_lblSouth.gridx = 1;
		gbc_lblSouth.gridy = 5;
		panel.add(lblSouth, gbc_lblSouth);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 6;
		panel.add(scrollPane, gbc_scrollPane);
		
		
		northTableModel = new NorthTableModel();
		northTable = new JTable(northTableModel);
		scrollPane.setViewportView(northTable);
		
		JLabel lblFenix = new JLabel("Fenix");
		lblFenix.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 16));
		GridBagConstraints gbc_lblFenix = new GridBagConstraints();
		gbc_lblFenix.insets = new Insets(0, 0, 5, 5);
		gbc_lblFenix.gridx = 1;
		gbc_lblFenix.gridy = 7;
		panel.add(lblFenix, gbc_lblFenix);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridwidth = 2;
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 8;
		panel.add(scrollPane_1, gbc_scrollPane_1);
		
		table = new JTable(northTableModel);
		scrollPane_1.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("Transmission");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 16));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 9;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.gridwidth = 2;
		gbc_scrollPane_2.gridx = 1;
		gbc_scrollPane_2.gridy = 10;
		panel.add(scrollPane_2, gbc_scrollPane_2);
		
		edifactTableModel = new EdifactTableModel();
		edifactTable = new JTable(edifactTableModel);
		scrollPane_2.setViewportView(edifactTable);
		
		JLabel label_2 = new JLabel("  ");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.gridwidth = 2;
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 1;
		gbc_label_2.gridy = 11;
		panel.add(label_2, gbc_label_2);
		
		JButton btnSave = new JButton("Save");
		
		btnSave.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				try{
					
					
					Vector<Northbound> v = getReturnCandidates();
					//iterate through candidates and set params one by one
					//orderReturnCandidatesByOrderID(v);
					
					//Hashtable<String,Vector<Northbound>> h = orderReturnCandidatesByOrderID(v);
					
					Vector<EdifactData> ediV =  createEdifactDatas( v );
					
					
					//saveReturnCandidates(ediV);
					
					
					
				}catch(Exception e1 ){
					e1.printStackTrace();
				}
				
			}
		});
		btnSave.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 16));
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.insets = new Insets(0, 0, 0, 5);
		gbc_btnSave.gridx = 1;
		gbc_btnSave.gridy = 12;
		panel.add(btnSave, gbc_btnSave);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 16));
		GridBagConstraints gbc_btnRefresh = new GridBagConstraints();
		gbc_btnRefresh.insets = new Insets(0, 0, 0, 5);
		gbc_btnRefresh.gridx = 2;
		gbc_btnRefresh.gridy = 12;
		panel.add(btnRefresh, gbc_btnRefresh);
	}
	
	//createEdifactDatas
	
	/*
	 * 
	 */
	protected Vector<EdifactData> createEdifactDatas( Vector<Northbound> v )  throws Exception{
		
		Iterator<Northbound> i = v.iterator();
		Hashtable<String,EdifactData> h = new Hashtable<String,EdifactData>();
		
		EdifactData ed = null;
		Northbound nb  = null;
		Vector<EdifactData> edifactVector = new Vector<EdifactData>();
		String fenixccno = null;
		String nborderId = null;
		
		while( i.hasNext()  ){
			
			ed = new EdifactData();
			nb = i.next();
			
			//all the north bound parcels in the order
			
			
			ed.setDocumentNumber( generateDocumentNumber( nb ) );
			ed.setAdjustmentNumber( generateAdjustmentNumber() );
			ed.setShipmentNumber( nb.getORDER_ID() );
			ed.setDutyPaidDate( getDutyPaidDate( nb )  );
			ed.setExportDate(getFenixExportFromDatabase(nb));
			ed.setExportCompanyIdentification("BORDFR");
			ed.setPowerOfAttorneyIndicator("1");
			ed.setImporterFirstName( getImporterFirstName( nb ) );
			ed.setImporterLastName( getImporterLastName( nb ) );
			ed.setImportAddress(getImporterAddress( nb ));
			ed.setCity( getImporterCity( nb ) );
			ed.setPostalcode( getImporterPostalCode(nb) );
			ed.setProvince( getImporterProvince( ed.getPostalcode() )  );
			ed.setCountry("CA");
			ed.setDuty( getDuty(nb) );
			ed.setExciseDuty("0");
			ed.setExciseTax("0");
			ed.setGST( getGST( nb ) );
			ed.setPST( getPST( nb, ed.getProvince() ) );
			ed.setTRACKING_NO( nb.getTRACKING_NO() );
			
			System.out.println("ed: " + ed.generateLines() );
			
			updateNorthBound(nb.getORDER_ID() );
			saveEdifact(ed );
			
			
			
		}
		
		return edifactVector;
	}

	/*
	 * 
	 
	protected Vector<EdifactData> createEdifact(
			Hashtable<String, Vector<Northbound>> h)  throws Exception{
		// TODO Auto-generated method stub
		
		Enumeration<String> e1 = h.keys();
		
		EdifactData ed = null;
		Northbound nb  = null;
		Vector<Northbound> northboundVector = null;
		String fenixccno = null;
		String nborderId = null;
		
		while(e1.hasMoreElements()){
			
			ed = new EdifactData();
			nborderId = e1.nextElement();
			
			//all the north bound parcels in the order
			northboundVector = h.get( nborderId  );
			
			ed.setDocumentNumber( generateDocumentNumber( northboundVector ) );
			ed.setAdjustmentNumber( generateAdjustmentNumber() );
			ed.setShipmentNumber( northboundVector.get(0).getORDER_ID() );
			ed.setDutyPaidDate( getDutyPaidDate( northboundVector )  );
			ed.setExportDate(getFenixExportFromDatabase(northboundVector));
			ed.setExportCompanyIdentification("BORDFR");
			ed.setPowerOfAttorneyIndicator("1");
			ed.setImporterFirstName( getImporterFirstName( northboundVector ) );
			ed.setImporterLastName( getImporterLastName( northboundVector ) );
			ed.setImportAddress(getImporterAddress(northboundVector));
			ed.setCity( getImporterCity( northboundVector ) );
			ed.setPostalcode( getImporterPostalCode(northboundVector) );
			ed.setProvince( getImporterProvince( ed.getPostalcode() )  );
			ed.setCountry("CA");
			ed.setDuty( getDuty(northboundVector) );
			ed.setExciseDuty("0");
			ed.setExciseTax("0");
			ed.setGST( getGST(northboundVector ) );
			ed.setPST( getPST( northboundVector, ed.getProvince() ) );
			
			System.out.println("ed: " + ed.generateLines() );
			
			
			
		}
		
		return null;
	}
	
	*/
	
	private String getPST( Northbound nb, String province ) throws Exception {
		
		
		
		String fenixccno = nb.getFENIX_CCNO();
		
		//String pstRate = getPstRateFromClassficationCode(  province );
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String dutyString = "";
		BigDecimal vfd;
		BigDecimal dutyAmt = new BigDecimal(0);
		String classificationcode ="";
		String vfdstring = "";
		String dutyamtstring = "";
		String pstrate = "";
		BigDecimal pstratmultiplier ;
		BigDecimal pst ;
		BigDecimal pst1;
		BigDecimal totalPST = new BigDecimal(  Double.toString(0.00) );
		
		Connection conn = getConnection();
		

			pstmt = conn.prepareStatement("select classification, vfd, dutyamt from fenix where ccno = ?");
			pstmt.setString(1, fenixccno );
			rs= pstmt.executeQuery();
			
			while(rs.next()){
				
				classificationcode = rs.getString("classification");
				vfdstring = rs.getString("vfd");
				dutyamtstring = rs.getString("dutyamt");
				
				System.out.println("classificationcode: " + classificationcode + "substring: " + classificationcode.substring( classificationcode.length()-2, classificationcode.length() ) );
				
				//pstrate = getPstRateFromClassificationCode( classificationcode.substring( classificationcode.length()-2, classificationcode.length() ) )  ;
				
				pstrate = getPstRateFromProvince( province );
				vfd = new BigDecimal(vfdstring.trim() );
				dutyAmt = new BigDecimal(dutyamtstring.trim() );
				
				System.out.println("dutyAmt: " + dutyAmt );
				System.out.println("pstrate: " + pstrate );
				
				pstratmultiplier = new BigDecimal(pstrate.trim()).divide(  new BigDecimal( Integer.toString( 100 ) )    );
				System.out.println("pstratemultiplier: " + pstratmultiplier );
				
				pst1 = (vfd.add(dutyAmt));
				pst = pst1.multiply(pstratmultiplier);
				
				System.out.println("pst: " + pst );
				totalPST = totalPST.add(pst);
				System.out.println("totalPST: " + totalPST );
				/*
				duty = new BigDecimal(dutyString);
				dutySum.add( duty );
				*/
				
			}
		
			
	
		pstmt.close();
		rs.close();
		
		
		totalPST = totalPST.setScale(2, RoundingMode.HALF_EVEN );
		System.out.println("PST is:" + totalPST );
		return String.valueOf( totalPST ) ;
	}
	
private String getPstRateFromProvince( String province ) throws SQLException {

		
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String pstrate = "0";
		
		Connection conn = getConnection();
		

			pstmt = conn.prepareStatement("select pst from pstrates where province = ?");
			pstmt.setString(1, province );
			rs= pstmt.executeQuery();
			
			if(rs.next()){
				
				pstrate = rs.getString("pst");
				
				
			}
		
			
	
		pstmt.close();
		rs.close();
		
		return pstrate ;
	}

	private String getPstRateFromClassificationCode( String classificationcode ) throws SQLException {

		
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String pstrate = "";
		
		Connection conn = getConnection();
		

			pstmt = conn.prepareStatement("select pst from pstrates where code = ?");
			pstmt.setString(1, classificationcode );
			rs= pstmt.executeQuery();
			
			if(rs.next()){
				
				pstrate = rs.getString("pst");
				
				
			}
		
			
	
		pstmt.close();
		rs.close();
		
		return pstrate ;
	}

	/*
	 * (VFD + dutyamt) * 0.05 
	 * 
	 * 
	 */
	
	private String getGST( Northbound nb ) throws Exception {
		
		
		
		String fenixccno = nb.getFENIX_CCNO();
		
		//String pstRate = getPstRateFromClassficationCode(  province );
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String dutyString = "";
		BigDecimal vfd;
		BigDecimal dutyAmt = new BigDecimal(0);
		String classificationcode ="";
		String vfdstring = "";
		String dutyamtstring = "";
		String gstrate = "";
		BigDecimal gstratmultiplier ;
		BigDecimal gst ;
		BigDecimal gst1;
		BigDecimal totalGST = new BigDecimal(  Double.toString(0.00) );
		
		Connection conn = getConnection();
		

			pstmt = conn.prepareStatement("select classification, vfd, dutyamt from fenix where ccno = ?");
			pstmt.setString(1, fenixccno );
			rs= pstmt.executeQuery();
			
			while(rs.next()){
				
				classificationcode = rs.getString("classification");
				vfdstring = rs.getString("vfd");
				dutyamtstring = rs.getString("dutyamt");
				
				System.out.println("classificationcode: " + classificationcode + "substring: " + classificationcode.substring( classificationcode.length()-2, classificationcode.length() ) );
				
				//pstrate = getPstRateFromClassificationCode( classificationcode.substring( classificationcode.length()-2, classificationcode.length() ) )  ;
				
				//pstrate = getPstRateFromProvince( province );
				gstrate = "5";
				vfd = new BigDecimal(vfdstring.trim() );
				dutyAmt = new BigDecimal(dutyamtstring.trim() );
				
				System.out.println("dutyAmt: " + dutyAmt );
				System.out.println("gstrate: " + gstrate );
				
				gstratmultiplier = new BigDecimal(gstrate.trim()).divide(  new BigDecimal( Integer.toString( 100 ) )    );
				System.out.println("gstratemultiplier: " + gstratmultiplier );
				
				gst1 = (vfd.add(dutyAmt));
				gst = gst1.multiply(gstratmultiplier);
				
				System.out.println("gst: " + gst );
				totalGST = totalGST.add(gst);
				System.out.println("totalGST: " + totalGST );
				/*
				duty = new BigDecimal(dutyString);
				dutySum.add( duty );
				*/
				
			}
		
			
	
		pstmt.close();
		rs.close();
		
		
		totalGST = totalGST.setScale(2, RoundingMode.HALF_EVEN );
		System.out.println("GST is:" + totalGST );
		return String.valueOf( totalGST ) ;
	}
	
	private String getGSTOLD(Northbound  nb) throws SQLException {
		
		String fenixccno = nb.getFENIX_CCNO();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String dutyString = "";
		BigDecimal duty;
		BigDecimal dutySum = new BigDecimal(0);
		
		
		Connection conn = getConnection();
		

			pstmt = conn.prepareStatement("select gstamt from fenix where ccno = ?");
			pstmt.setString(1, fenixccno );
			rs= pstmt.executeQuery();
			
			
			while(rs.next()){
				dutyString = rs.getString("gstamt");
				
				System.out.println("dutyString: " + dutyString.trim() );
				duty = new BigDecimal(dutyString.trim());
				dutySum = dutySum.add( duty );
				
			}
		
			
	
		pstmt.close();
		rs.close();
		dutySum.setScale(2, RoundingMode.HALF_EVEN );
		return String.valueOf( dutySum ) ;
		
	}

	private String getDuty( Northbound nb) throws Exception {

		
		 String fenixccno = nb.getFENIX_CCNO();
		//String fenixccno = northboundVector.get(0).getTRACKING_NO();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String dutyString = "";
		BigDecimal duty;
		BigDecimal dutySum = new BigDecimal(0);
		
		
		Connection conn = getConnection();
		

			pstmt = conn.prepareStatement("select dutyamt from fenix where ccno = ?");
			pstmt.setString(1, fenixccno.trim() );
			rs= pstmt.executeQuery();
			
			System.out.println(" fenixccno: " + fenixccno );
			
			while(rs.next()){
				dutyString = rs.getString("dutyamt");
				duty = new BigDecimal(dutyString.trim() );
				dutySum = dutySum.add( duty );
				
			}
		
			
	
		pstmt.close();
		rs.close();
		dutySum.setScale(2, RoundingMode.HALF_EVEN );
		
		return String.valueOf( dutySum ) ;
		
		
		
	}

	private String getImporterPostalCode( Northbound nb ) throws SQLException {
		
		//String fenixccno = northboundVector.get(0).getFENIX_CCNO();
		
		String fenixccno = nb.getTRACKING_NO();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String postalcode = "";
		
		
		Connection conn = getConnection();
		

			pstmt = conn.prepareStatement("select * from address where ccno = ?");
			pstmt.setString(1, fenixccno.trim() );
			rs= pstmt.executeQuery();
			
			if(rs.next()){
				postalcode = rs.getString("POSTALCODE");
				
			}
		
	
		pstmt.close();
		rs.close();
		System.out.println("postalcodes: " + postalcode );
		return postalcode.replaceAll("\\s", "");
	}

	private String getImporterProvince( String postalcode) throws SQLException {
		
		
		System.out.println("postalcode: " + postalcode  );
		System.out.println("postalcode.substring: " + postalcode.substring(0, 1)  );
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String province = null;
		
		
		Connection conn = getConnection();
		

			pstmt = conn.prepareStatement("select * from provincetable where firstchar = ?");
			pstmt.setString(1, postalcode.substring(0, 1));
			rs= pstmt.executeQuery();
			
			if(rs.next()){
				province = rs.getString("PROVINCE");
				
			}
		
	
		pstmt.close();
		rs.close();
		
		return province;
		
	}

	private String getImporterAddress ( Northbound  nb) throws SQLException {
		String fenixccno = nb.getTRACKING_NO() ;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String streetcity = "";
		
		
		Connection conn = getConnection();
		

			pstmt = conn.prepareStatement("select * from address where ccno = ?");
			pstmt.setString(1, fenixccno.trim() );
			rs= pstmt.executeQuery();
			
			if(rs.next()){
				streetcity = rs.getString("STREETCITY");
				
			}
		
	
		pstmt.close();
		rs.close();
		
		
		String []street = streetcity.split("\\.") ;
		
		System.out.println("street length: " + street.length );
		
		
		if(   street == null || street.length <2 ){
			return "" ;
			
			
		}
		
		else {
			
			System.out.println("city is: "  + street[0] );
			
			return street[0].replaceAll("\\s", "");
		}
		
		
		
	}

	private String getImporterCity( Northbound  nb) throws SQLException{
		
		String fenixccno = nb.getTRACKING_NO();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String streetcity = "";
		
		
		Connection conn = getConnection();
		

			pstmt = conn.prepareStatement("select * from address where ccno = ?");
			pstmt.setString(1, fenixccno.trim() );
			rs= pstmt.executeQuery();
			
			if(rs.next()){
				streetcity = rs.getString("STREETCITY");
				System.out.println("git a city: " + streetcity );
			}
		
	
		pstmt.close();
		rs.close();
		
	
		String []city = streetcity.split("\\.") ;
		
		System.out.println("city length: " + city.length );
		
		
		if(   city == null || city.length <2 ){
			return "" ;
			
			
		}
		
		else {
			
			System.out.println("city is: "  + city[1] );
			
			return city[1].replaceAll("\\s", "");
		}
	}

	private String getImporterLastName(Northbound  nb) throws SQLException{

		
		String trackingno = nb.getTRACKING_NO();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String lastname = "";
		
		
		Connection conn = getConnection();
		

			pstmt = conn.prepareStatement("select * from consigneenames where ccno = ?");
			pstmt.setString(1, trackingno );
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

	private String getImporterFirstName( Northbound  nb) throws SQLException{

		System.out.println("@@@getImporterFirstName nb_tracking_no: " + nb.getTRACKING_NO() );
		
		
		//String fenixccno = northboundVector.get(0).getFENIX_CCNO();
		String trackingno = nb.getTRACKING_NO();
		System.out.println("tracking_no: " + trackingno  );
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String firstname = "";
		
		
		Connection conn = getConnection();
		

			pstmt = conn.prepareStatement("select * from consigneenames where ccno = ?");
			//pstmt.setString(1, fenixccno.substring( fenixccno.length()-10,fenixccno.length() ) );
			pstmt.setString(1, trackingno.trim() );
			rs= pstmt.executeQuery();
			
			if(rs.next()){
				firstname = rs.getString("names");
			}
	System.out.println("firstname: " + firstname );
		pstmt.close();
		rs.close();
		
		
		String[] st = firstname.split("\\s* \\s*");
		
		/*
		return street[0].trim();
		*/
		
		if(   st == null || st.length <2 ){
			return "" ;
			
			
		}
		else return st[0] ;
		
		
		
	}

	private Date getDutyPaidDate(Northbound nb) throws Exception{
		
		
		String fenixccno = nb.getFENIX_CCNO();
		
		return getFenixDutyPaidDateFromDatabase(fenixccno);
	}
	
	
	/* get the fenix dsdate and then forward the calendar to the next month, 24 day
	 * 
	 * 
	 */
	private java.util.Date getFenixExportFromDatabase( Northbound nb ) throws Exception{
		
		String fenixccno = nb.getFENIX_CCNO();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String seq = null;
		
		
		Connection conn = getConnection();
		

			pstmt = conn.prepareStatement("select dsdate from fenix where ccno = ?");
			pstmt.setString(1, fenixccno.trim() );
			rs= pstmt.executeQuery();
			
			if(rs.next()){
				seq = rs.getString(1);
			}
			
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date inputDate = dateFormat.parse(seq.trim() ) ;
		
		pstmt.close();
		rs.close();
		
		
		return inputDate;
		
	}
	
	

	/* get the fenix dsdate and then forward the calendar to the next month, 24 day
	 * 
	 * 
	 */
	private java.util.Date getFenixDutyPaidDateFromDatabase(String fenixccno) throws Exception{
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String seq = null;
		
		
		Connection conn = getConnection();
		

			pstmt = conn.prepareStatement("select dsdate from fenix where ccno = ?");
			pstmt.setString(1, fenixccno.trim() );
			rs= pstmt.executeQuery();
			
			if(rs.next()){
				seq = rs.getString(1);
			}
			
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date inputDate = dateFormat.parse(seq) ;
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(inputDate);
			calendar.add(Calendar.MONTH, 1);
			calendar.set( Calendar.DAY_OF_MONTH, 24);
			
		
		
		
		pstmt.close();
		rs.close();
		
		
		return calendar.getTime();
		
	}

	private String generateAdjustmentNumber() throws Exception{
		
		String dhlSecurityNumber = "16881";
		
		String seq = getSequence();
		
		return dhlSecurityNumber + seq;
	}

	private String getSequence() throws SQLException {
		
		
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String seq = null;
		
		
		Connection conn = getConnection();
		

			pstmt = conn.prepareStatement("select next value for dbo.adjnumseq");
			rs= pstmt.executeQuery();
			
			if(rs.next()){
				seq = rs.getString(1);
			}
			
		
		
		
		pstmt.close();
		rs.close();
		
		
		return seq;
		
	}

	private String generateDocumentNumber( Northbound  nb)  throws SQLException {
		
		
		//String ccno = nb.getFENIX_CCNO();
		String ccno = getBarcodeFromFenix( nb.getFENIX_CCNO() );
		
		StringBuffer documentNumber = new StringBuffer();
		
		documentNumber.append( ccno.substring(0, 5) ).append("-").append(ccno.substring(5,ccno.length() ));
		
		return  documentNumber.toString();
	}

	protected Hashtable<String,Vector<Northbound>>  orderReturnCandidatesByOrderID(Vector<Northbound> v) {
		
		
		Hashtable<String,Vector<Northbound>> byOrderId = new Hashtable<String,Vector<Northbound>>();
		Vector<Northbound> toAdd = null;
		
		Vector byOrderIdVector = null;
		
		Iterator<Northbound> i1 = v.iterator();
		Northbound nb = null;
		String nb_order_id = null;
		
		while( i1.hasNext() ){
			
			nb = i1.next(); 
			nb_order_id = nb.getORDER_ID();
			
			Object obj = byOrderId.get( nb_order_id );
			
			if( obj == null  ){
				
				toAdd = new Vector<Northbound>();
				toAdd.add(nb);
				byOrderId.put(  nb_order_id, toAdd  );
				
			}
			else{
				
				toAdd = byOrderId.get( nb_order_id );
				toAdd.addElement(nb);
				
				
			}
			
				
		}
		
		return byOrderId;
		
	}
	
protected void saveEdifact( EdifactData ed ) throws Exception {
		
		Connection conn = getConnection();
		
		PreparedStatement pstmt = null;
		
		
		
		/*
		 * documentNumber   	adjustmentNumber  	dutyPaidDate   	exportDate   	exportCompanyIdentification  	powerOfAttorneyIndicator   	importerFirstName   	importerLastName   	importAddress   	city  	province   	country   	postalcode  	 
		 * duty   	exciseDuty   	exciseTax   	GST  	PST
		 */
		
		
		
		
		pstmt = conn.prepareStatement("insert into edifact ( documentNumber, adjustmentNumber, dutyPaidDate,exportDate, exportCompanyIdentification"
				+ ", powerOfAttorneyIndicator,  importerFirstName, importerLastName, importAddress, city, province, country, postalcode, duty, exciseDuty, exciseTax, GST, PST, shipmentnumber, TRACKING_NO ) "
				+ " values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )" );
		
		
			pstmt.setString(1, ed.getDocumentNumber()  );
			pstmt.setString(2, ed.getAdjustmentNumber() );
			pstmt.setDate (3, new java.sql.Date( ed.getDutyPaidDate().getTime() ) );
			pstmt.setDate(4, new java.sql.Date(  ed.getExportDate().getTime() ) );
			pstmt.setString(5, ed.getExportCompanyIdentification()  );
			pstmt.setString(6,	ed.getPowerOfAttorneyIndicator()		);
			pstmt.setString(7, ed.getImporterFirstName() );
			pstmt.setString(8,  ed.getImporterLastName() );
			pstmt.setString(9, ed.getImportAddress() );
			pstmt.setString(10,  ed.getCity() );
			pstmt.setString(11,	 ed.getProvince()  );
			pstmt.setString(12, ed.getCountry() );
			pstmt.setString(13, ed.getPostalcode() );
			pstmt.setString(14, ed.getDuty() );
			pstmt.setString(15, ed.getExciseDuty() );
			pstmt.setString(16, ed.getExciseTax()  );
			pstmt.setString(17, ed.getGST());
			pstmt.setString(18, ed.getPST());
			pstmt.setString(19, ed.getShipmentNumber() );
			pstmt.setString(20, ed.getTRACKING_NO() );
		
		
		pstmt.execute();
		pstmt.close();
		
		conn.close();
	
	}

	protected void saveReturnCandidates(Vector<EdifactData> v) throws Exception {
		
		Connection conn = getConnection();
		
		PreparedStatement pstmt = null;
		
		EdifactData ed = null;
		
		/*
		 * documentNumber   	adjustmentNumber  	dutyPaidDate   	exportDate   	exportCompanyIdentification  	powerOfAttorneyIndicator   	importerFirstName   	importerLastName   	importAddress   	city  	province   	country   	postalcode  	 
		 * duty   	exciseDuty   	exciseTax   	GST  	PST
		 */
		
		
		Iterator<EdifactData> i = v.iterator();
		
		pstmt = conn.prepareStatement("insert into edifact ( documentNumber, adjustmentNumber, dutyPaidDate,exportDate, exportCompanyIdentification"
				+ ", powerOfAttorneyIndicator,  importerFirstName, importerLastName, importAddress, city, province, country, postalcode, duty, exciseDuty, exciseTax, GST, PST, shipmentnumber ) "
				+ " values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )" );
		
		while ( i.hasNext() ){
		
		ed = i.next();
		
			
		
			pstmt.setString(1, ed.getDocumentNumber()  );
			pstmt.setString(2, ed.getAdjustmentNumber() );
			pstmt.setDate (3, new java.sql.Date( ed.getDutyPaidDate().getTime() ) );
			pstmt.setDate(4, new java.sql.Date(  ed.getExportDate().getTime() ) );
			pstmt.setString(5, ed.getExportCompanyIdentification()  );
			pstmt.setString(6,	ed.getPowerOfAttorneyIndicator()		);
			pstmt.setString(7, ed.getImporterFirstName() );
			pstmt.setString(8,  ed.getImporterLastName() );
			pstmt.setString(9, ed.getImportAddress() );
			pstmt.setString(10,  ed.getCity() );
			pstmt.setString(11,	 ed.getProvince()  );
			pstmt.setString(12, ed.getCountry() );
			pstmt.setString(13, ed.getPostalcode() );
			pstmt.setString(14, ed.getDuty() );
			pstmt.setString(15, ed.getExciseDuty() );
			pstmt.setString(16, ed.getExciseTax()  );
			pstmt.setString(17, ed.getGST());
			pstmt.setString(18, ed.getPST());
			pstmt.setString(19, ed.getShipmentNumber() );
		
			pstmt.addBatch();
			
		}
		
		
		pstmt.executeBatch();
		conn.close();
	
	}
		
		

	public JLabel getParcel_IDLabel() {
		return parcel_IDLabel;
	}
	
	public JLabel getOrder_IDLabel() {
		return order_IDLabel;
	}
	
	private Vector<Northbound> getReturnCandidates() throws Exception {
		
		
		Vector<Northbound> v = new Vector<Northbound>();
		
		//go to southbound file and get all full return order_ids
		
		Hashtable<String,String> parcel_ids = new Hashtable<String,String>(); 
		
		Vector<String> v1 = getSouthboundFull();
		
		System.out.println("southbound size: " + v1.size() );
		
		//go to northbound table and get the data objects by order_id
		//each northbound entry is unique by internal_parcel_id
		Vector<Northbound> v2 = getNorthbound( v1 );
		System.out.println("northbound size: " + v2.size() );
		
		//go to the fenix table into RAM
		
		Hashtable<String,String> h1 = getFenixData();
		System.out.println("Fenix size: " + h1.size() );
		//go through north bound list and match 
		//the NORTHBOUND tracking_no to the FENIX CCNO
		
		Iterator<Northbound> inb = v2.iterator();
		
		int count = 0;
		EdifactData ed = null;
		Northbound nb = null;
		String fenixccno = null;
		
		
		
		while(inb.hasNext()){
			
			nb = inb.next();
			fenixccno = h1.get ( nb.getTRACKING_NO()  ) ;
			
			//check the fenix hashtable
			if(  fenixccno !=null ){
				
				//System.out.println("match!!!$$$$$");
				//each nb is unique against INTERNAL_PARCEL_ID
				nb.setFENIX_CCNO( fenixccno );	
				v.add(nb);
				count++;
				
			}
			
			
			
		}
		
		System.out.println("match count: " + count );
		//save return candiates
		
		
		
		//display
		
		
		return v; 
		
	}
	
	private String getBarcodeFromFenix( String ccno ) throws SQLException {

		
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String barcode = "";
		
		Connection conn = getConnection();
		
	
			pstmt = conn.prepareStatement("select barcode From fenix where ccno = ?");
			pstmt.setString(1,  ccno );
			rs = pstmt.executeQuery();
			
			//System.out.println("fenix..ing..");
			
			if( rs.next() ){
				
				barcode = rs.getString("barcode") ;
				
			}
			
		
		return barcode;
	}
	
	
	private Hashtable<String,String> getFenixData() throws SQLException {

		//Vector<String> v = new Vector<String>();
		Hashtable<String,String>h = new Hashtable<String,String>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Northbound nb = null;
		FenixData fd = null;
		String keyvalue = null;
		String putter = null;
		
		Connection conn = getConnection();
		
		//Iterator<Northbound> i = v2.iterator();
		
		//while(i.hasNext() ){
			
			pstmt = conn.prepareStatement("select ccno From fenix");
			//pstmt.setString(1,   "%"+i.next().getTRACKING_NO() );
			rs = pstmt.executeQuery();
			
			//System.out.println("fenix..ing..");
			
			while( rs.next() ){
				
				keyvalue = rs.getString("CCNO") ;
				
				if(keyvalue != null && keyvalue.length() > 11 ){
					putter = keyvalue.substring( keyvalue.length()-10 , keyvalue.length() );
					//System.out.println("keyvalue: " + keyvalue );
					//System.out.println("putting with: " + putter );
				
					//v.add( rs.getString("CCNO") );
					if( putter!=null ) {
						h.put( putter, keyvalue );
					}
				}
			}
			
		//}
		
		
		return h;
	}

	private Vector<Northbound> getNorthbound(Vector strings ) throws SQLException  {
		
		Vector<Northbound>v = new Vector<Northbound>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Northbound nb = null;
		
		int recordcounter = 0;
		
		Connection conn = getConnection();
		
		Iterator<String> i = strings.iterator();
		
		int counter = 0;
		
		while(i.hasNext() && recordcounter < 50000 ){
		
			recordcounter++;
			counter++;
			//pstmt = conn.prepareStatement("select  * From northbound where ORDER_ID = ? ");
			pstmt = conn.prepareStatement("select distinct(INTERNAL_PARCEL_ID), ORDER_ID, MERCH_NAME, SHIP_DATE,TRACKING_NO From northbound where ORDER_ID = ? and processed = ? ");
			pstmt.setString(1, i.next() );
			pstmt.setString(2,"N");
			rs= pstmt.executeQuery();
			
			while( rs.next() ){
				nb = new Northbound();
				nb.setORDER_ID(rs.getString("ORDER_ID"));
				nb.setMERCH_NAME(rs.getString("MERCH_NAME"));
				nb.setINTERNAL_PARCEL_ID(rs.getString("INTERNAL_PARCEL_ID"));
				nb.setTRACKING_NO(rs.getString("TRACKING_NO"));
				nb.setSHIP_DATE(rs.getString("SHIP_DATE"));
				
				//System.out.println("get nb adding...");
				v.add(nb);
			}
		
		}
		
		pstmt.close();
		rs.close();
		
		
		return v;
	}
	
	
private void updateNorthBound( String order_id ) throws SQLException {
		
		PreparedStatement pstmt;
		
		
		Connection conn = getConnection();
		
		pstmt = conn.prepareStatement("update northbound set processed = ? where ORDER_ID = ?" );
		pstmt.setString(1, "Y");
		pstmt.setString(2,order_id);
		pstmt.execute();
		
		pstmt.close();
		conn.close();
		
	
	}
	
	private Vector<String> getSouthboundFull() throws SQLException {
		
		Vector<String> v = new Vector<String>();
		PreparedStatement pstmt;
		ResultSet rs;
		String ORDER_ID  = null;
		
		
		Connection conn = getConnection();
		
		pstmt = conn.prepareStatement("select * From southbound where FWD_RTN_SAME_YN = ? " );
		pstmt.setString(1, "Y");
		rs = pstmt.executeQuery();
		
		while(rs.next() ){
			
			ORDER_ID = new String( rs.getString("ORDER_ID") );
			
			v.add(ORDER_ID);
		}
		
		pstmt.close();
		rs.close();
		conn.close();
		
		return v;
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
