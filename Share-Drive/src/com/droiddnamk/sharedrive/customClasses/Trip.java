package com.droiddnamk.sharedrive.customClasses;

public class Trip {

	String id, user_id, city, from_country, from, to, vehicle_type, from_time,
			to_time, no_passangers, cur_passangers, payment_type, status,
			to_country;
	
	public Trip(String id, String user_id, String city, String from_country,
			String from, String to, String vehicle_type, String from_time,
			String to_time, String no_passangers, String cur_passangers,
			String payment_type, String status, String to_country) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.city = city;
		this.from_country = from_country;
		this.from = from;
		this.to = to;
		this.vehicle_type = vehicle_type;
		this.from_time = from_time;
		this.to_time = to_time;
		this.no_passangers = no_passangers;
		this.cur_passangers = cur_passangers;
		this.payment_type = payment_type;
		this.status = status;
		this.to_country = to_country;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getFrom_country() {
		return from_country;
	}

	public void setFrom_country(String from_country) {
		this.from_country = from_country;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getVehicle_type() {
		return vehicle_type;
	}

	public void setVehicle_type(String vehicle_type) {
		this.vehicle_type = vehicle_type;
	}

	public String getFrom_time() {
		return from_time;
	}

	public void setFrom_time(String from_time) {
		this.from_time = from_time;
	}

	public String getTo_time() {
		return to_time;
	}

	public void setTo_time(String to_time) {
		this.to_time = to_time;
	}

	public String getNo_passangers() {
		return no_passangers;
	}

	public void setNo_passangers(String no_passangers) {
		this.no_passangers = no_passangers;
	}

	public String getCur_passangers() {
		return cur_passangers;
	}

	public void setCur_passangers(String cur_passangers) {
		this.cur_passangers = cur_passangers;
	}

	public String getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTo_country() {
		return to_country;
	}

	public void setTo_country(String to_country) {
		this.to_country = to_country;
	}
	
	@Override
	public boolean equals(Object o) {
		Trip temp = (Trip)o;
		if(id.equals(temp.id)){
			return true;
		}
		else return false;
	}
	

}
