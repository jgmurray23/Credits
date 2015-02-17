package ca.odell.bfiftytwo;

import java.util.Vector;

public class MatchingData {
	
	String ccno;
	
	Vector<String> NBLines ;
	Vector<String> B3Lines ;
	
	
	public String getCcno() {
		return ccno;
	}
	public void setCcno(String ccno) {
		this.ccno = ccno;
	}
	public Vector<String> getNBLines() {
		return NBLines;
	}
	public void setNBLines(Vector<String> nBLines) {
		NBLines = nBLines;
	}
	public Vector<String> getB3Lines() {
		return B3Lines;
	}
	public void setB3Lines(Vector<String> b3Lines) {
		B3Lines = b3Lines;
	}
	
	
	

}
