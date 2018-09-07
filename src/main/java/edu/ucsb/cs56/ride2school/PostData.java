package edu.ucsb.cs56.ride2school;

import java.time.LocalDate;
import java.time.LocalTime;

public class PostData {

	public PostData(Long id, String title, Location departingLocation, Location arrivingLocation, LocalDate date,
			LocalTime time, UserData poster, LocalTime lastUpdate, float price, int rideSeats, int seatsTaken) {
		this.title = title;
		this.id = id;
		this.departingLocation = departingLocation;
		this.arrivingLocation = arrivingLocation;
		this.date = date;
		this.time = time;
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

	private LocalDate date;
	private LocalTime time;

	private UserData poster;

	private LocalTime lastUpdate;

	private float price;
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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public UserData getPoster() {
		return poster;
	}

	public void setPoster(UserData poster) {
		this.poster = poster;
	}

	public LocalTime getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(LocalTime lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
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
