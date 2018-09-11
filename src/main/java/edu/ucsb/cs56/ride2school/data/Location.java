package edu.ucsb.cs56.ride2school.data;

public class Location {

	@Override
	public String toString() {
		return "Location [name=" + name + "]";
	}

	public Location(String name) {
		this.name = name;
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
