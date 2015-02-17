package ca.odell.bfiftytwo;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;

import javax.swing.JButton;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;





public class B52MatchApp {

	
	private int matchcount;
	
	private JFrame frame;
	
	private File file1;
	private File file2;
	
	private Vector<String> vfiftytwo;
	
	//private Vector<String> nb;
	private HashMap<String,String> nb;
	private HashMap<String,String> bHashMap;
	
	
	private JTable table;
	MatchTableModel matchTableModel;
	private JLabel matchesLabel;
	private JLabel fiftytwoSizeLabel;
	private JLabel northboundSizeLabel;
	private JLabel bfLabel;

	public int recordcount;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					B52MatchApp window = new B52MatchApp();
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
	public B52MatchApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		vfiftytwo = new Vector<String>();
		//nb = new Vector<String>();
		nb = new HashMap<String,String>();
		bHashMap = new HashMap<String,String>();
		
		matchTableModel = new MatchTableModel();
		
		frame = new JFrame();
		frame.setBounds(100, 100, 850, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblBMatcher = new JLabel("B52 Matcher");
		lblBMatcher.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 16));
		GridBagConstraints gbc_lblBMatcher = new GridBagConstraints();
		gbc_lblBMatcher.gridwidth = 4;
		gbc_lblBMatcher.insets = new Insets(0, 0, 5, 0);
		gbc_lblBMatcher.gridx = 0;
		gbc_lblBMatcher.gridy = 0;
		panel.add(lblBMatcher, gbc_lblBMatcher);
		
		JButton bfiftytwoButton = new JButton("Load B52");
		bfiftytwoButton.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 18));
		bfiftytwoButton.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent e) {
				
				final JFileChooser fc = new JFileChooser();
				
				int returnVal = fc.showOpenDialog( frame );

		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		             file1 = fc.getSelectedFile();
		            //This is where a real application would open the file.
		            System.out.println("Opening: " + file1.getName()  );
		        } else {
		        	System.out.println("Open command cancelled by user." );
		        }
				
		        try{ 
		        	vfiftytwo = loadB52(file1);
		        	getFiftytwoSizeLabel().setText( String.valueOf( vfiftytwo.size() ) );
		        	
		        }catch(Exception e1){
		        	e1.printStackTrace();
		        	JOptionPane.showMessageDialog(frame, "Problem Loading B52");
		        }
				
				
				
				
				
			}
		});
		
		bfLabel = new JLabel(" ");
		GridBagConstraints gbc_bfLabel = new GridBagConstraints();
		gbc_bfLabel.insets = new Insets(0, 0, 5, 5);
		gbc_bfLabel.gridx = 1;
		gbc_bfLabel.gridy = 12;
		panel.add(bfLabel, gbc_bfLabel);
		
		fiftytwoSizeLabel = new JLabel(" ");
		GridBagConstraints gbc_fiftytwoSizeLabel = new GridBagConstraints();
		gbc_fiftytwoSizeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_fiftytwoSizeLabel.gridx = 0;
		gbc_fiftytwoSizeLabel.gridy = 13;
		panel.add(fiftytwoSizeLabel, gbc_fiftytwoSizeLabel);
		
		northboundSizeLabel = new JLabel(" ");
		GridBagConstraints gbc_northboundSizeLabel = new GridBagConstraints();
		gbc_northboundSizeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_northboundSizeLabel.gridx = 1;
		gbc_northboundSizeLabel.gridy = 13;
		panel.add(northboundSizeLabel, gbc_northboundSizeLabel);
		
		GridBagConstraints gbc_bfiftytwoButton = new GridBagConstraints();
		gbc_bfiftytwoButton.insets = new Insets(0, 0, 5, 5);
		gbc_bfiftytwoButton.gridx = 0;
		gbc_bfiftytwoButton.gridy = 14;
		panel.add(bfiftytwoButton, gbc_bfiftytwoButton);
		
		JButton northboundButton = new JButton("Load Northbound");
		northboundButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				final JFileChooser fc = new JFileChooser();
				
				int returnVal = fc.showOpenDialog( frame );

		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		             file2 = fc.getSelectedFile();
		            //This is where a real application would open the file.
		            System.out.println("Opening: " + file1.getName()  );
		        } else {
		        	System.out.println("Open command cancelled by user." );
		        }
				
		        try{
				
		        	nb = loadNorthBound(file2);
		        	
		        	
				
		        }catch(Exception e2){
		        	e2.printStackTrace();
		        	JOptionPane.showMessageDialog(frame, "Problem Loading Northbound File" );
		        }
				
			}
		});
		northboundButton.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 18));
		GridBagConstraints gbc_northboundButton = new GridBagConstraints();
		gbc_northboundButton.anchor = GridBagConstraints.WEST;
		gbc_northboundButton.insets = new Insets(0, 0, 5, 5);
		gbc_northboundButton.gridx = 1;
		gbc_northboundButton.gridy = 14;
		panel.add(northboundButton, gbc_northboundButton);
		
		JButton compareButton = new JButton("Compare");
		compareButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				//generateMatches();
				
				
			}
		});
		compareButton.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 18));
		GridBagConstraints gbc_compareButton = new GridBagConstraints();
		gbc_compareButton.anchor = GridBagConstraints.WEST;
		gbc_compareButton.insets = new Insets(0, 0, 5, 5);
		gbc_compareButton.gridx = 0;
		gbc_compareButton.gridy = 15;
		panel.add(compareButton, gbc_compareButton);
		
		JButton btnGenerateList = new JButton("Generate List");
		btnGenerateList.addActionListener(  new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				( new MatchesTask()).execute();
				
			}

			
		}  );
		btnGenerateList.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 18));
		GridBagConstraints gbc_btnGenerateList = new GridBagConstraints();
		gbc_btnGenerateList.anchor = GridBagConstraints.WEST;
		gbc_btnGenerateList.insets = new Insets(0, 0, 5, 5);
		gbc_btnGenerateList.gridx = 1;
		gbc_btnGenerateList.gridy = 15;
		panel.add(btnGenerateList, gbc_btnGenerateList);
		
		JLabel lblNewLabel = new JLabel("Total Matches:");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 18));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 16;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		
		matchesLabel = new JLabel(" ");
		matchesLabel.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 18));
		GridBagConstraints gbc_matchesLabel = new GridBagConstraints();
		gbc_matchesLabel.gridwidth = 2;
		gbc_matchesLabel.insets = new Insets(0, 0, 5, 0);
		gbc_matchesLabel.gridx = 2;
		gbc_matchesLabel.gridy = 16;
		panel.add(matchesLabel, gbc_matchesLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 17;
		panel.add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		table.setModel(matchTableModel);
		scrollPane.setViewportView(table);
	}

	
	

	private Vector<String> getFenixElements(String ccnoFromNorth ) {
		
		String cvsSplitBy = ",";
		String[] extractelements;
		String line = null;
		Vector<String> v = new Vector<String>();
		String ccno = null;
		
		Iterator<String> i = vfiftytwo.iterator();
		//iterate through the B52 lines - extract the ccno and compare
		//if there is a match, just add it to the vector
		while( i.hasNext() ){
			
			line = i.next();
			line = line.replace("\"", "");
			//System.out.println("LINE: " + line );
			
			extractelements = line.trim().split(cvsSplitBy);
			
			//System.out.println("b5 [3]: " + extractelements[3] );
			
			if( extractelements[3].length() >9 ){
				
				
				ccno = extractelements[3].substring( extractelements[3].length()-10 , extractelements[3].length() );
				//System.out.println("CCNO from FENIX: " + ccno );
				//System.out.println("b5 [3]: " + ccno );
				
				//ccno from northbound file matches ccno from the fenix file
				if( ccnoFromNorth.equalsIgnoreCase(ccno) ){
					System.out.println("MATCH");
					matchcount++;
					v.add(line);
				}
				
				
			}
			
			
			
		}
		
		return v;
	}

	
	//
	
	
 
    private class MatchesTask extends SwingWorker<Void, Integer > {
    	
        @Override
        protected Void doInBackground() {
        	
        	matchcount = 0;
    		
    		Iterator<String> i = vfiftytwo.iterator();
    		
    		String line;
    		String ccno;
    		
    		//Vector<String> fenixElements = new Vector<String>();
    		
    		MatchingData md =null;
    		
    		String[] extractelements;
    		
    		String cvsSplitBy = ",";
    		
    		//iterate over b52/fenix elements - go to nb hash map for a match
    		while(i.hasNext() ){
    			
    			//System.out.println("checking...");
    			
    			//md = new MatchingData();
    			//this is a parcel from northbound
    			line = i.next();
    		
    			extractelements = line.trim().split(cvsSplitBy);
    			
    			if( extractelements[12].length() >9 ){
    				
    				//ccno = extractelements[12].substring( extractelements[12].length()-11 , extractelements[12].length()-1 );
    				
    				ccno = extractelements[12].substring( extractelements[12].length()-11 , extractelements[12].length()-1 );
    				//System.out.println("CCNO from FENIX: " + ccno );
    				//System.out.println("b5 [3]: " + ccno );
    				
    				//check NB hash map for match
    				
    				if( ( nb.get(ccno))!=null  ){
    					
    					matchcount++;
    					System.out.println("hit: " + matchcount );
    					System.out.println("############################################");
    					System.out.println("b52 ccno: " + line );
    					System.out.println("nb.get(ccno) " +  nb.get(ccno) );
    					
    				}
    				
    				//ccno from northbound file matches ccno from the fenix file
    				
    				
    				
    			}
    			
    			
    			
    			//ccno = extractelements[12];
    			//check hash map
    			
    			
    			//md.setCcno(ccno);
    			//System.out.println("nb ccno: " + ccno );
    			
    			//md.setB3Lines(  getFenixElementsFromHashMap(ccno) );
    			//System.out.println("publish!");
    			Vector v = new Vector();
    			v.add( new Integer( matchcount ) );
    			
    			
    			
    			
    			
    			
    			publish( matchcount );
    			
    		}
    		
    		//getMatchesLabel().setText( String.valueOf(matchcount)  );
        	//publish( new Integer( matchcount ));
        	
    		HashMap result = new HashMap(nb);
    		result.keySet().retainAll(bHashMap.keySet());
    		
    		
    		
    		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$");
    		
    		System.out.println("intersection size: " + result.size() );
    		
            //publish(  new  Integer(0)  );
            //publish();
            return null;
        }
 
       

		@Override
        protected void process(   List<Integer> matches  ) {
        	
        	
        	getBfLabel().setText( String.valueOf(  matches.get(0) )  );
        	//System.out.println("matchcount: " + matchcount );
        	getMatchesLabel().setText( String.valueOf( matches.get(0)   )  );
           // FlipPair pair = pairs.get(pairs.size() - 1);
            //headsText.setText(String.format("%d", pair.heads));
            //totalText.setText(String.format("%d", pair.total));
            //devText.setText(String.format("%.10g", 
            //        ((double) pair.heads)/((double) pair.total) - 0.5));
        }
    }
	
	
	
	
	//
	/*
	protected Vector<String> generateMatches(  ) {
		
		
		matchTableModel.clearStockItems();
		
		Iterator<String> i = vfiftytwo.iterator();
		String line = null;
		
		//iterator through b52 vector - and attempt to match - the lines from b52 with the lines from
		//the northbound vector. 
		
		while( i.hasNext()){
			
			line = i.next();
			line = line.replace("\"", "");
			
			
			//System.out.println("line: " + line );
			
			if ( nb.contains( line )){
				System.out.println("match: " + line );
				
				//if( !matchTableModel.getData().contains(line) ){
					matchTableModel.addStockItems(line);
				//}
			}
			
			
		}
		
		getMatchesLabel() .setText( String.valueOf( matchTableModel.getData().size() ) );
		
		Collections.sort( matchTableModel.getData() );
		
		matchTableModel.fireTableDataChanged();
		
		return null;
	}
	*/

	protected HashMap<String,String> loadNorthBound(File file22) throws Exception {
		
		
		//Vector<String> v = new Vector<String>();
		HashMap<String,String>h = new HashMap<String,String>();
	
		
		String[] extractelements;
		String cvsSplitBy = ",";
		String line;
		String ccno;
		BufferedReader br = new BufferedReader(new FileReader(file22));
		
		String putter = null;
		
		while (( line = br.readLine()) != null) {
 
		       // use comma as separator
			extractelements = line.trim().split(cvsSplitBy);
			//System.out.println("nb: " + extractelements[3] );
			//v.add(	extractelements[3] );
			//v.add( line );
			//System.out.println("[3]: " + extractelements[3] + " " + extractelements[3].length() );
			if( extractelements[3].length() > 9 ){	
				putter = extractelements[3].substring( extractelements[3].length()-10 , extractelements[3].length() );
				System.out.println("putter: " + putter );
				//h.put(extractelements[3].trim(), line  );
				h.put( putter, line);
			
			getNorthboundSizeLabel().setText( String.valueOf( h.size() )   );
			}
		
		}
		
		System.out.println("vnb size: " + h.size() );
		return h ;
		
		
		
	}

	protected Vector<String> loadB52(File file12) throws Exception{
		
		Vector<String> v = new Vector<String>();
		
		String[] extractelements;
		String cvsSplitBy = ",";
		String line;
		String ccno;
		BufferedReader br = new BufferedReader(new FileReader(file12));
		
		while (( line = br.readLine()) != null) {
 
		     // use comma as separator
			extractelements = line.trim().split(cvsSplitBy);
			//System.out.println("b52 []: " + extractelements[12] );
			if( extractelements[12].length() >11 ){
				ccno = extractelements[12].substring( extractelements[12].length()-11 , extractelements[12].length()-1 );
				//System.out.println("b5 [12]: " + ccno + ": " + ccno.length() );
				//v.add(	ccno );
				v.add( line );
				bHashMap.put(ccno, line);
			}
			
			
			
		
		}
		
		System.out.println("v size: " + v.size() );
		return v ;
	}

	public JLabel getMatchesLabel() {
		return matchesLabel;
	}
	public JLabel getFiftytwoSizeLabel() {
		return fiftytwoSizeLabel;
	}
	public JLabel getNorthboundSizeLabel() {
		return northboundSizeLabel;
	}
	public JLabel getBfLabel() {
		return bfLabel;
	}
}

class MatchTableModel extends AbstractTableModel {
	
	String ed = null;
	String colrow = null;
	
	
	
	
	int count = 0;
    
    private String[] columnnames = {"Match" };
    
    private Vector<String> data = new Vector<String>();
    
    
    
    public int getColumnCount() {
        return columnnames.length;
    }
    
    public int getRowCount() {
        return data.size();
    }
    
    public String getColumnName(int col) {
        return columnnames[col];
    }
    
    public Object getValueAt(int row, int col) {
        //return data[row][col];
    	ed = (String)data.get(row);
    	
    	if( col == 0 ){
    		colrow = ed;
    		return colrow;
    	}
    	
   
    	return colrow;
    	
    	
    }
    
    
    public void setValueAt(Object value, int row, int col) {
    	
    	String ed = null;
        
        ed =data.get(row);
       

    	
        fireTableCellUpdated(row, col);
    	
    	
    }
    
    
    
    public Class getColumnClass(int c) {
    	
    	System.out.println("c is:" + c);
    	
        return getValueAt(0, c).getClass();
    }
   
    
    public boolean isCellEditable(int row, int col) {
       
        return false;
        
    }
    
    public void addStockItems(String ed){
    	
    	data.add(ed);
    	
    	
    }
    
    public void clearStockItems(){
    	data.removeAllElements();
    	
    }
    
    public java.util.Vector<String> getData(){
    	return this.data;
    }
    
    
    
    
    
    
    
    
    
    
    

}








