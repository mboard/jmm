package com.droiddnamk.sharedrive.customClasses;

public class Trip {

	String id, user_id, from_city, from_country, from_address, to_address, vehicle_type,
			from_time, to_time, no_passangers, cur_passangers, payment_type,
			status, to_country,user, to_city,from_city_id,from_country_id,to_country_id,to_city_id,availible_passangers,no_likes,no_dislikes,phone_number,status2;
	public final String getUser() {
		return user;
	}

	public final void setUser(String user) {
		this.user = user;
	}

	public Trip(String id, String user_id, String from_city, String from_country,
			String from, String to, String vehicle_type, String from_time,
			String to_time, String no_passangers, String cur_passangers,
			String payment_type, String status, String to_country,String to_city,String from_city_id,String from_country_id,
			String to_country_id,String to_city_id,String user,String availible_passangers,String no_likes,String no_dislikes,String phone_number,String status2) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.from_city = from_city;
		this.from_country = from_country;
		this.from_city_id = from_city_id;
		this.from_country_id = from_country_id;		
		this.from_address = from;
		this.to_address = to;
		this.vehicle_type = vehicle_type;
		this.from_time = from_time;
		this.to_time = to_time;
		this.no_passangers = no_passangers;
		this.cur_passangers = cur_passangers;
		this.payment_type = payment_type;
		this.status = status;
		this.to_country = to_country;
		this.to_city = to_city;
		this.to_country_id = to_country_id;
		this.to_city_id = to_city_id;
		this.user = user;
		this.availible_passangers = availible_passangers;
		this.no_likes = no_likes;
		this.no_dislikes = no_dislikes;		
		this.phone_number = phone_number;
		this.status2 = status2;
	}

	public final String getFrom_city_id() {
		return from_city_id;
	}

	public final void setFrom_city_id(String from_city_id) {
		this.from_city_id = from_city_id;
	}

	public final String getFrom_country_id() {
		return from_country_id;
	}

	public final void setFrom_country_id(String from_country_id) {
		this.from_country_id = from_country_id;
	}

	public final String getTo_country_id() {
		return to_country_id;
	}

	public final void setTo_country_id(String to_country_id) {
		this.to_country_id = to_country_id;
	}

	public final String getTo_city_id() {
		return to_city_id;
	}

	public final void setTo_city_id(String to_city_id) {
		this.to_city_id = to_city_id;
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

	public final String getFrom_city() {
		return from_city;
	}

	public final void setFrom_city(String from_city) {
		this.from_city = from_city;
	}

	public final String getTo_city() {
		return to_city;
	}

	public final void setTo_city(String to_city) {
		this.to_city = to_city;
	}

	public String getFrom_country() {
		return from_country;
	}

	public void setFrom_country(String from_country) {
		this.from_country = from_country;
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
		Trip temp = (Trip) o;
		if (id.equals(temp.id)) {
			return true;
		} else
			return false;
	}

	public final String getFrom_address() {
		return from_address;
	}

	public final void setFrom_address(String from_address) {
		this.from_address = from_address;
	}

	public final String getTo_address() {
		return to_address;
	}

	public final void setTo_address(String to_address) {
		this.to_address = to_address;
	}

	public final String getAvailible_passangers() {
		return availible_passangers;
	}

	public final void setAvailible_passangers(String availible_passangers) {
		this.availible_passangers = availible_passangers;
	}

	public final String getNo_likes() {
		return no_likes;
	}

	public final void setNo_likes(String no_likes) {
		this.no_likes = no_likes;
	}

	public final String getNo_dislikes() {
		return no_dislikes;
	}

	public final void setNo_dislikes(String no_dislikes) {
		this.no_dislikes = no_dislikes;
	}

	public final String getPhone_number() {
		return phone_number;
	}

	public final void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public final String getStatus2() {
		return status2;
	}

	public final void setStatus2(String status2) {
		this.status2 = status2;
	}

}
