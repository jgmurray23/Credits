package ca.odell.credits.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EdifactData {
	
	public EdifactData(){
		super();
	}
	
	public EdifactData(String documentNumber, String adjustmentNumber,
			String shipmentNumber, Date dutyPaidDate, Date exportDate,
			String exportCompanyIdentification,
			String powerOfAttorneyIndicator, String importerFirstName,
			String importerLastName, String importAddress, String city,
			String province, String country, String postalcode, String duty,
			String exciseDuty, String exciseTax, String gST, String pST) {
		super();
		this.documentNumber = documentNumber;
		this.adjustmentNumber = adjustmentNumber;
		this.shipmentNumber = shipmentNumber;
		this.dutyPaidDate = dutyPaidDate;
		this.exportDate = exportDate;
		this.exportCompanyIdentification = exportCompanyIdentification;
		this.powerOfAttorneyIndicator = powerOfAttorneyIndicator;
		this.importerFirstName = importerFirstName;
		this.importerLastName = importerLastName;
		this.importAddress = importAddress;
		this.city = city;
		this.province = province;
		this.country = country;
		this.postalcode = postalcode;
		this.duty = duty;
		this.exciseDuty = exciseDuty;
		this.exciseTax = exciseTax;
		GST = gST;
		PST = pST;
		
		
		
	}

	private String TRANSMISSION_NO;
	
	private String TRACKING_NO;
	
	private String documentNumber;
	
	private String adjustmentNumber;
	
	private String shipmentNumber ;
	
	//all dates YYYYMMDD
	private Date dutyPaidDate;
	
	private Date exportDate;
	
	private String exportCompanyIdentification;
	
	private String powerOfAttorneyIndicator;
	
	private String importerFirstName;
	private String importerLastName ;
	
	private String importAddress;
	
	private String city;
	private String province; // i.e. ON,BC,etc.
	private String country ; //i.e. CA
	private String postalcode;
	
	//store currencies as a string - manipulate with BigDecimal
	private String duty;
	private String exciseDuty;
	private String exciseTax;
	private String GST;
	private String PST;
	
	
	
	public String getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	public String getAdjustmentNumber() {
		return adjustmentNumber;
	}
	public void setAdjustmentNumber(String adjustmentNumber) {
		this.adjustmentNumber = adjustmentNumber;
	}
	public String getShipmentNumber() {
		return shipmentNumber;
	}
	public void setShipmentNumber(String shipmentNumber) {
		this.shipmentNumber = shipmentNumber;
	}
	public Date getDutyPaidDate() {
		return dutyPaidDate;
	}
	public void setDutyPaidDate(Date dutyPaidDate) {
		this.dutyPaidDate = dutyPaidDate;
	}
	public Date getExportDate() {
		return exportDate;
	}
	public void setExportDate(Date exportDate) {
		this.exportDate = exportDate;
	}
	public String getExportCompanyIdentification() {
		return exportCompanyIdentification;
	}
	public void setExportCompanyIdentification(String exportCompanyIdentification) {
		this.exportCompanyIdentification = exportCompanyIdentification;
	}
	public String getPowerOfAttorneyIndicator() {
		return powerOfAttorneyIndicator;
	}
	public void setPowerOfAttorneyIndicator(String powerOfAttorneyIndicator) {
		this.powerOfAttorneyIndicator = powerOfAttorneyIndicator;
	}
	public String getImporterFirstName() {
		return importerFirstName;
	}
	public void setImporterFirstName(String importerFirstName) {
		this.importerFirstName = importerFirstName;
	}
	public String getImporterLastName() {
		return importerLastName;
	}
	public void setImporterLastName(String importerLastName) {
		this.importerLastName = importerLastName;
	}
	public String getImportAddress() {
		return importAddress;
	}
	public void setImportAddress(String importAddress) {
		this.importAddress = importAddress;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public String getExciseDuty() {
		return exciseDuty;
	}
	public void setExciseDuty(String exciseDuty) {
		this.exciseDuty = exciseDuty;
	}
	public String getGST() {
		return GST;
	}
	public void setGST(String gST) {
		GST = gST;
	}
	public String getPST() {
		return PST;
	}
	public void setPST(String pST) {
		PST = pST;
	}
	public String getExciseTax() {
		return exciseTax;
	}
	public void setExciseTax(String exciseTax) {
		this.exciseTax = exciseTax;
	}
	
	/*
	 * DHLCAA19.001
16881237352689|12345677890|E4X000007433310|20110511|20110501|BRDRFR|1|Jesse|Smith|7 Grand Avenue|Cambridge|ON|CA|N1T2A7|95.03|90.90|10.90|11.23|5.63

	 * 
	 * 	DOCUMENT NUMBER
	 * 	ADJUSTMENT NUMBER
	 *	SHIPMENT NUMBER
	 *	DUTY PAID DATE
	 *	EXPORT DATE
	 *	EXPORT COMPANY IDENTIFICATION - or Company Name (Not sure if this is constant or not, if it is then no need to send it.)
	 *	POWER OF ATTORNEY - INDICATOR
	 *	IMPORTER First NAME 
	 *	IMPORTER Last NAME
	 *	IMPORTER Address
	 *	IMPORTER City
	 *	IMPORTER Prov
	 *	IMPORT Country 
	 *	IMPORTER Postal Code
	 *	Duty
	 *	Excise Duty
	 *	Excise Tax
	 *	GST
	 *	PST
	 *
	 *
	 */
	public String generateLines(){
		

		SimpleDateFormat formatter = new SimpleDateFormat("YYYYMMdd" );
		
		StringBuffer sb = new StringBuffer();
		
		String delimiter = "|";
		
		sb.append( getDocumentNumber() ).append( delimiter ).append( getAdjustmentNumber() ).append(delimiter).append( getShipmentNumber() ).append(delimiter)
		.append(  formatter.format(  getDutyPaidDate() ) ).append(delimiter).append(  formatter.format( getExportDate()  )   ).append(delimiter)
		.append( this.getExportCompanyIdentification() ).append(delimiter).append( getPowerOfAttorneyIndicator()   ).append(delimiter ).append( getImporterFirstName() )
		.append(delimiter).append(this.getImporterLastName()  ).append(delimiter).append( this.getImportAddress()  ).append(delimiter).append( this.getCity() )
		.append(delimiter).append( this.getProvince() ).append(delimiter).append( this.getCountry() ).append(delimiter).append( this.getPostalcode() ).append( delimiter )
		.append( this.getDuty() ).append(delimiter).append( this.getExciseDuty() ).append(delimiter).append( this.getExciseTax() ).append(delimiter)
		.append( this.getGST() ).append(delimiter).append( this.getPST() );
		
		
		
		return sb.toString();
	}

	public String getTRACKING_NO() {
		return TRACKING_NO;
	}

	public void setTRACKING_NO(String tRACKING_NO) {
		TRACKING_NO = tRACKING_NO;
	}

	public String getTRANSMISSION_NO() {
		return TRANSMISSION_NO;
	}

	public void setTRANSMISSION_NO(String tRANSMISSION_NO) {
		TRANSMISSION_NO = tRANSMISSION_NO;
	}
	
	
	
	
	

}
