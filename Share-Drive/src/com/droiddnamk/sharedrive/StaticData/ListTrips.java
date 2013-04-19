package com.droiddnamk.sharedrive.StaticData;

import java.util.ArrayList;

import com.droiddnamk.sharedrive.MainActivity;
import com.droiddnamk.sharedrive.customClasses.Trip;

public class ListTrips  {

	ArrayList<Trip> trips = new ArrayList<Trip>();

	public ArrayList<Trip> getTrips() {
		return trips;
	}

	public void setTrips(ArrayList<Trip> taskovi) {
		trips = taskovi;
	}
	public void removeTrip(Trip t){
		trips.remove(t);
		MainActivity.adapter.notifyDataSetChanged();
		}
	public void addTrip(Trip t) {
		trips.add(t);
		MainActivity.adapter.notifyDataSetChanged();
	}
	public boolean isDuplicate(Trip p){
		if(trips.contains(p))return false;
		else return true;
	}
	

}
