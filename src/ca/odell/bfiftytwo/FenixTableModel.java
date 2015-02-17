package ca.odell.bfiftytwo;


import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import ca.odell.credits.model.FenixData;
import ca.odell.credits.model.FenixData;



public class FenixTableModel extends AbstractTableModel {
	
	FenixData fd = null;
	String colrow = null;
	
	Hashtable dirtyList = new Hashtable();
	
	int count = 0;
    
    private String[] columnnames = {"CCNO",
    "Importer",
    "CCNO","Duty", "ExciseDuty","ExciseTax","GST","PST"  };
    
    public Vector<FenixData> data = new Vector<FenixData>();
    
    public Hashtable getDirtyList(){
    	return this.dirtyList;
    }
    
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
    	fd = (FenixData)data.get(row);
    	
    	if( col == 0 ){
    		colrow = fd.getCCNO();
    		return colrow;
    	}
    	else if ( col == 1 ){
    		colrow = fd.getImporter();
    		return colrow;
    	}
    	
    	else if (col == 2) {
    		colrow = fd.getCCNO();
    		return colrow;
    	}
    	else if (col ==3 ){
    		//colrow = String.valueOf( count );
    		colrow =  fd.getDuty_Amt();
    		return colrow;
    	}
    	else if( col==4 ){
    		colrow = fd.getExcise_Amt();		
    		return colrow;
    	}
    	
    	else if( col==5 ){
    		colrow = fd.getGST_Amt();	
    		return colrow;
    	}
    	
    	else if( col==6 ){
    		colrow = fd.getHSQty();
    		return colrow;
    	}
    	
    	else{
    		colrow="";
    		return colrow;
    	}
    	
    	 
    }
    
    
    public void setValueAt(Object value, int row, int col) {
    	
    }
    
    
    
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
    
    
    
    public void addFenixData(FenixData fd){
    	
    	data.add(fd);
    	
    	
    	
    	fireTableDataChanged();
    }
    
    public void removeStockItems(FenixData fd){
    	try{
    		data.remove(fd);
    	}catch( Exception e){
    		e.printStackTrace();
    		System.out.println("tried to remove from vendorcatTableModel " + fd );
    	}
    	fireTableDataChanged();
    }
    
    public void clearStockItems(){
    	data.removeAllElements();
    	dirtyList.clear();
    }
    
    /*
    public void setCount(String spincount){
    	
    	count = Integer.parseInt(spincount);
    	fireTableDataChanged();
    	
    }
    */
    
    public Vector<FenixData> getData(){
    	return this.data;
    }

}

