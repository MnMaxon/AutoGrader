package com.Group4.AutoGrader.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Album implements Serializable {
	/**
	 * The user the album belongs to
	 */
	private User user;
	/**
	 * The images that are in the album
	 */
	public final ArrayList<Image> images;
	/**
	 * The name of the album
	 */
	private String name;

	/**
	 * Constructor that assigns a name and a set of images to the album
	 *
	 * @param name		name of the album
	 * @param images	list of images to put in album
	 */
	public Album(String name, ArrayList<Image> images) {
		this.name = name;
		this.images = images;
		for (Image image: images) image.setAlbum(this);
	}

	/**
	 * Sets the owner of the album
	 *
	 * @param user user to be set to owner
	 */
	public void setUser(User user){
		this.user = user;
	}

	/**
	 * Get the owner of the album
	 *
	 * @return user who owns the album
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Name of the album
	 *
	 * @return name of the album
	 */
	public String getName() {
		return name;
	}

	/**
	 * Change the name of the album
	 *
	 * @param name new name for the album
	 */
	public void setName(String name) {
		this.name = name;
	}
}
