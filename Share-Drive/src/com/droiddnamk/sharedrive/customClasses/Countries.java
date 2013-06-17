package com.droiddnamk.sharedrive.customClasses;

import java.util.ArrayList;

import android.util.Log;

public class Countries {
	public static ArrayList<Country> countries = new ArrayList<Country>();
	public static String[] countries_list;
	public static void LoadCountries(String data) { 
		String[] tmp = data.split("###");
		countries_list = new String[tmp.length];
		int i = 0;
		Country c;
		while (i < tmp.length) {
			if (!tmp[i].equals("")){
			String[] tmp2 = tmp[i].split("~~~");
			c = new Country(tmp2[0], tmp2[1],tmp2[2]);
			countries_list[i] = tmp2[1];
			countries.add(c);
			}
			i++;
		}
	}
}
