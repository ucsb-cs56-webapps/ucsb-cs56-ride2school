package edu.ucsb.cs56.ride2school.data;

import java.util.Date;

import org.bson.Document;

public class PostData extends StoreableData {

	public PostData(String title, Location departingLocation, Location arrivingLocation, Date date, UserData poster,
			Date lastUpdate, double price, int rideSeats, int seatsTaken) {
		super("PostData");
		this.title = title;
		this.departingLocation = departingLocation;
		this.arrivingLocation = arrivingLocation;
		this.date = date;
		this.poster = poster;
		this.lastUpdate = lastUpdate;
		this.price = price;
		this.rideSeats = rideSeats;
		this.seatsTaken = seatsTaken;
	}

	public PostData(Document d) {
		super("PostData", d);
	}

	private String title;

	private Location departingLocation;
	private Location arrivingLocation;

	private Date date;

	private UserData poster;

	private Date lastUpdate;

	private double price;
	private int rideSeats;
	private int seatsTaken;

	@Override
	public Document convertToDocument() {
		return newDoc().append("Title", this.title).append("ArrivingLocationName", this.arrivingLocation.getName())
				.append("DepartingLocationName", this.departingLocation.getName()).append("Date", this.date)
				.append("PosterID", this.poster.getID()).append("lastUpdate", this.lastUpdate)
				.append("price", this.price).append("rideSeats", this.rideSeats).append("seatsTaken", this.seatsTaken);
	}

	@Override
	public void convertFromDocument(Document doc) {
		this.title = doc.getString("Title");
		this.arrivingLocation = new Location(doc.getString("ArrivingLocationName"));
		this.departingLocation = new Location(doc.getString("DepartingLocationName"));
		this.date = doc.getDate("Date");
		this.lastUpdate = doc.getDate("lastUpdate");
		this.price = doc.getDouble("price");
		this.rideSeats = doc.getInteger("rideSeats");
		this.seatsTaken = doc.getInteger("seatsTaken");

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Location getDepartingLocation() {
		return departingLocation;
	}

	public void setDepartingLocation(Location departingLocation) {
		this.departingLocation = departingLocation;
	}

	public Location getArrivingLocation() {
		return arrivingLocation;
	}

	public void setArrivingLocation(Location arrivingLocation) {
		this.arrivingLocation = arrivingLocation;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public UserData getPoster() {
		return poster;
	}

	public void setPoster(UserData poster) {
		this.poster = poster;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getRideSeats() {
		return rideSeats;
	}

	public void setRideSeats(int rideSeats) {
		this.rideSeats = rideSeats;
	}

	public int getSeatsTaken() {
		return seatsTaken;
	}

	public void setSeatsTaken(int seatsTaken) {
		this.seatsTaken = seatsTaken;
	}

	public int getSeatsLeft() {
		return rideSeats - seatsTaken;
	}
}
