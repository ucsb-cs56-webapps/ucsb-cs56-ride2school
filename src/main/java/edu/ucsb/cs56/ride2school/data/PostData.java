package edu.ucsb.cs56.ride2school.data;

import java.util.Date;

public class PostData {

	public PostData(Long id, String title, Location departingLocation, Location arrivingLocation, Date date,
			UserData poster, Date lastUpdate, double price, int rideSeats, int seatsTaken) {
		this.title = title;
		this.id = id;
		this.departingLocation = departingLocation;
		this.arrivingLocation = arrivingLocation;
		this.date = date;
		this.poster = poster;
		this.lastUpdate = lastUpdate;
		this.price = price;
		this.rideSeats = rideSeats;
		this.seatsTaken = seatsTaken;
	}

	private String title;
	private Long id;

	private Location departingLocation;
	private Location arrivingLocation;

	private Date date;

	private UserData poster;

	private Date lastUpdate;

	private double price;
	private int rideSeats;
	private int seatsTaken;

	public String getTitle() {
		return title;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
}
