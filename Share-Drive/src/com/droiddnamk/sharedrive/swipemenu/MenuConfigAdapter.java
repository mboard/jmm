package com.droiddnamk.sharedrive.swipemenu;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;

import com.droiddnamk.sharedrive.MainActivity;
import com.droiddnamk.sharedrive.R;

/**
 * Class to control of menu, add menu to array for add a new menu
 * @author Leonardo Salles
 */
public class MenuConfigAdapter {
	public static List<Menu> getMenuDefault(Activity ac){
		//Menu menu = new Menu(int id, int drawableIcon, String title, String description)
		
		
		//	NE MENUVAJ 'ID' NA VEKE POSTOECKI I FUNKCIONALNI MENU ITEMS BIDEJKI TOA E KLUC !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		
		
		Menu menu8 = new Menu(8, R.drawable.user, "Your Profile (" + MainActivity.logged_username + ")", "Manage your profile informations","Your informations");
		Menu menu9 = new Menu(9, R.drawable.logout, "Log Out", "Log Out from application","");
		/*Menu menu3 = new Menu(3, R.drawable.cupons, "Ð’Ð¸Ñ€ÑƒÑ�Ð¸", "Coupons of my app");
		Menu menu4 = new Menu(4, R.drawable.home, "ÐŸÐ¾Ñ‡ÐµÑ‚Ð½Ð°", "Initial page of my app");
		Menu menu5 = new Menu(5, R.drawable.coupon, "Ð“Ð»ÑƒÐ¿Ð¾Ñ�Ñ‚Ð¸", "Principals offers of my app");
		Menu menu6 = new Menu(6, R.drawable.fork, "ÐžÑ€Ñ‚Ð¾Ð¿ÐµÐ´Ð¸Ñ˜Ð°", "Offers of gastronomy");
		Menu menu7 = new Menu(7, R.drawable.beauty, "Ð£Ð±Ð°Ð²Ð¸Ð½Ð°", "Offers of beauty");
		Menu menu8 = new Menu(8, R.drawable.several, "ÐšÐ¾Ð½Ñ‚Ð°ÐºÑ‚", "Several offers");
		Menu menu9 = new Menu(9, R.drawable.products, "Ð—Ð° Ð�Ð°Ñ�", "Products");*/
		Menu menu1 = new Menu(1, R.drawable.filter, "Filter Drives","FIlter displayed drives","Filters");
		Menu menu2 = new Menu(2, R.drawable.create, "Create", "Create new drive","Manage drives");
		Menu menu3 = new Menu(3, R.drawable.edit, "Manage drives", "Edit drive","");
		Menu menu5 = new Menu(5, R.drawable.attend, "Attended drives", "Attending drive","");
		//Menu menu4 = new Menu(4, R.drawable.attend, "Attend", "Attend to drive","Manage attendance");
		//Menu menu5 = new Menu(5, R.drawable.cancel, "Cancel", "Cancel drive","");
		
		SharedPreferences shared = ac.getSharedPreferences("shared_speed",0);
		String speed = shared.getString("shared_speed_key","On");
		Menu menu4 = new Menu(4, R.drawable.splash, "Splash Screen ( " + speed + " )", "Splash Screen","Settings");
		List<Menu> listMenu = new ArrayList<Menu>();
		
		listMenu.add(menu1);
		listMenu.add(menu2);
		listMenu.add(menu3);
		listMenu.add(menu5);
		listMenu.add(menu4);
		listMenu.add(menu8);
		listMenu.add(menu9);
		/*listMenu.add(menu3);
		listMenu.add(menu4);
		listMenu.add(menu5);
		listMenu.add(menu6);
		listMenu.add(menu7);
		listMenu.add(menu8);
		listMenu.add(menu9);*/

		return listMenu;
	}
}
