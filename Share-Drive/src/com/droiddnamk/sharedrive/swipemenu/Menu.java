package com.droiddnamk.sharedrive.swipemenu;

/**
 * Object menu: Contains information of item in a list menu
 * 
 * @author Leonardo Salles
 */
public class Menu {

	private int id;
	private int iconReference;
	private String title;
	private String description;
	private String group;

	public Menu() {

	}

	public Menu(int id, int iconReference, String title, String description,
			String group) {
		this.id = id;
		this.iconReference = iconReference;
		this.title = title;
		this.description = description;
		this.group = group;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIconReference() {
		return iconReference;
	}

	public void setIconReference(int iconReference) {
		this.iconReference = iconReference;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}