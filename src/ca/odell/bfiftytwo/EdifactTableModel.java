package ca.odell.bfiftytwo;


import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import ca.odell.credits.model.EdifactData;
import ca.odell.credits.model.EdifactData;



public class EdifactTableModel extends AbstractTableModel {
	
	EdifactData ed = null;
	String colrow = null;
	
	Hashtable dirtyList = new Hashtable();
	
	
    
    private String[] columnnames = {"Document#",
    "Adjustment#",
    "Shipment#","DutyPaidDate", "exportDate","exportCompanyIdentification",
    "powerOfAttorneyIndicator","importerFirstName","importerLastName",
    "importAddress","city", "province","country","postalcode",
    "duty","exciseDuty","exciseTax","gst","pst", "Processed" };
    
    public Vector<EdifactData> data = new Vector<EdifactData>();
    
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
    	
    	ed = (EdifactData)data.get(row);
    	
    	if( col == 0 ){
    		colrow = ed.getDocumentNumber();
    		return colrow;
    	}
    	else if ( col == 1 ){
    		colrow = ed.getAdjustmentNumber();
    		return colrow;
    	}
    	
    	else if (col == 2) {
    		colrow = ed.getShipmentNumber();
    		return colrow;
    	}
    	else if (col ==3 ){
    		//colrow = String.valueOf( count );
    		Date colrowDate = ed.getDutyPaidDate();		
    		return colrow;
    	}
    	else if( col==4 ){
    		Date colrowDate = ed.getExportDate();	
    		return colrowDate;
    	}
    	
    	else if(col == 5 ){
    		colrow = ed.getExportCompanyIdentification() ;
    		return colrow;
    	}
    	
    	else if(col == 6 ){
    		colrow = ed.getPowerOfAttorneyIndicator();
    		return colrow;
    	}
    	else if(col == 7 ){
    		colrow = ed.getImporterFirstName();
    		return colrow;
    	}
    	else if(col == 8  ){
    		colrow = ed.getImporterLastName();
    		return colrow;
    	}
    	else if(col == 9 ){
    		colrow = ed.getImportAddress();
    		return colrow;
    	}
    	else if(col == 10 ){
    		colrow = ed.getCity();
    		return colrow;
    	}
    	else if(col == 11 ){
    		colrow = ed.getProvince();
    		return colrow;
    	}
    	else if(col == 12 ){
    		colrow = ed.getDuty();
    		return colrow;
    	}
    	else if(col == 13 ){
    		colrow = ed.getExciseDuty();
    		return colrow;
    	}
    	else if(col == 14 ){
    		colrow = ed.getExciseTax();
    		return colrow;
    	}
    	else if(col == 15 ){
    		colrow = ed.getGST();
    		return colrow;
    	}
    	else if(col == 16 ){
    		colrow = ed.getPST();
    		return colrow;
    	}
    	else if(col == 17 ){
    		return new Boolean(false);
    	}
    	else {
    		return "";
    	}
    	
    }
    
    
    public void setValueAt(Object value, int row, int col) {
    	
    }
    
    
    
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
    
    
    
    public void addEdifactData(EdifactData ed){
    	
    	data.add(ed);
    	
    	
    	
    	fireTableDataChanged();
    }
    
    public void removeStockItems(EdifactData ed){
    	try{
    		data.remove(ed);
    	}catch( Exception e){
    		e.printStackTrace();
    		System.out.println("tried to remove from vendorcatTableModel " + ed );
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
    
    public Vector<EdifactData> getData(){
    	return this.data;
    }

}

