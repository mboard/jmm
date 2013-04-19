package com.droiddnamk.sharedrive.StaticData;

public class Singelton {

	private static Singelton instance;
	private ListTrips trips;
	private Singelton(){
		trips= new ListTrips();
	}
	public static synchronized Singelton getInstance(){
		if (instance==null){
			instance = new Singelton();
		}
		return instance;
	}
	public ListTrips getTrips() {
		return trips;
	}
	public void setTrips(ListTrips taskLista) {
		trips = taskLista;
	}

}
