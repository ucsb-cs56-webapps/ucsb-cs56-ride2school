package edu.ucsb.cs56.ride2school.data;

import org.bson.Document;

public class UserData extends StoreableData {

	public UserData(String name) {
		super("UserData");
		this.name = name;
	}

	public UserData(Document d) {
		super("UserData", d);
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Document convertToDocument() {
		return newDoc().append("name", name);
	}

	@Override
	public void convertFromDocument(Document d) {
		this.name = d.getString("name");
	}

}
