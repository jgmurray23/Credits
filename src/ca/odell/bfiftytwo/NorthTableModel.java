package ca.odell.bfiftytwo;


import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import ca.odell.credits.model.Northbound;



public class NorthTableModel extends AbstractTableModel {
	
	Northbound nb = null;
	String colrow = null;
	
	Hashtable dirtyList = new Hashtable();
	
	int count = 0;
    
    private String[] columnnames = {"OrderId",
    "ParcelID",
    "CCNO","Prcl_Total", "Prcl_CNT",""  };
    
    public Vector<Northbound> data = new Vector<Northbound>();
    
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
    	nb = (Northbound)data.get(row);
    	
    	if( col == 0 ){
    		colrow = nb.getORDER_ID();
    		return colrow;
    	}
    	else if ( col == 1 ){
    		colrow = nb.getINTERNAL_PARCEL_ID();
    		return colrow;
    	}
    	
    	else if (col == 2) {
    		colrow = nb.getTRACKING_NO();
    		return colrow;
    	}
    	else if (col ==3 ){
    		//colrow = String.valueOf( count );
    		colrow = ( nb.getMERCH_PARCEL_TOTAL()!=null)? nb.getMERCH_PARCEL_TOTAL(): "0" ;		
    		return colrow;
    	}
    	else if( col==4 ){
    		colrow = ( nb.getPARCEL_COUNT_NO_EXC() !=null)? nb.getPARCEL_COUNT_NO_EXC(): "0" ;		
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
    
    
    
    public void addNorthBound(Northbound nb){
    	
    	data.add(nb);
    	
    	
    	
    	fireTableDataChanged();
    }
    
    public void removeStockItems(Northbound nb){
    	try{
    		data.remove(nb);
    	}catch( Exception e){
    		e.printStackTrace();
    		System.out.println("tried to remove from vendorcatTableModel " + nb );
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
    
    public Vector<Northbound> getData(){
    	return this.data;
    }

}

