package edu.ucsb.cs56.ride2school.data;

import org.bson.Document;
import org.bson.types.ObjectId;

public abstract class StoreableData {

	private ObjectId ID;
	private String collectionName;

	public StoreableData(String collectionName) {
		this.collectionName = collectionName;
		ID = new ObjectId();
	}

	public StoreableData(String collectionName, Document d) {
		this.collectionName = collectionName;
		this.ID = d.getObjectId("_id");
		this.convertFromDocument(d);
	}

	public abstract Document convertToDocument();

	public abstract void convertFromDocument(Document d);

	public String getCollectionName() {
		return this.collectionName;
	}

	protected Document newDoc() {
		return new Document().append("_id", this.getID());
	}

	public ObjectId getID() {
		return this.ID;
	}

	public void setID(ObjectId ID) {
		this.ID = ID;
	}

}
