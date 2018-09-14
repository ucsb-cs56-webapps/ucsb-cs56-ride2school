package edu.ucsb.cs56.ride2school.data;

import org.bson.Document;

public class UserData extends StoreableData {

	public UserData(String name, String tempPassword) {
		super("UserData");
		this.name = name;
		this.tempPassword = tempPassword;
	}

	public UserData(Document d) {
		super("UserData", d);
	}

	private String name;
	private String tempPassword;

	public String getName() {
		return name;
	}

	public String getTempPassword(){
		return tempPassword;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setTempPassword(String tempPassword){
		this.tempPassword = tempPassword;
	}

	@Override
	public Document convertToDocument() {
		return newDoc().append("name", name).append("tempPassword", tempPassword);
	}

	@Override
	public void convertFromDocument(Document d) {
		this.name = d.getString("name");
		this.tempPassword = d.getString("tempPassword");
	}
}
