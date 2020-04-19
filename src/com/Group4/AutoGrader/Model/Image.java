package com.Group4.AutoGrader.Model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class Image implements Serializable {
	/**
	 * Location of the file on the computer
	 */
	private final String location;
	/**
	 * The tags and the values of the object
	 */
	private ArrayList<Map.Entry<String, String>> tags;
	/**
	 * The album the image belongs to
	 */
	private Album album;
	/**
	 * The image's caption
	 */
	private String description;

	/**
	 * Initializes an image with the given attributes
	 *
	 * @param location    Location of the file on the computer
	 * @param description The image's caption
	 * @param tags        The image's tags and values
	 */
	public Image(String location, String description, ArrayList<Map.Entry<String, String>> tags) {
		this.location = location;
		this.description = description;
		this.tags = tags;
	}

	/**
	 * Gives the last modified date of the image
	 *
	 * @return The last modified date of the image
	 */
	public Date getDate() {
		return new Date(new File(location).lastModified());
	}

	/**
	 * Gives the location of the image on the computer
	 *
	 * @return The image's location on the computer
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Gives the map of tags of the object
	 *
	 * @return The tags and values of the object
	 */
	public ArrayList<Map.Entry<String, String>> getTags() {
		return tags;
	}

	/**
	 * Gives the caption of the picture
	 *
	 * @return The picture's description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the picture's description
	 *
	 * @param description The new description of the picture
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Sets which album the image belongs to
	 *
	 * @param album The album the image belongs to
	 */
	public void setAlbum(Album album) {
		this.album = album;
	}

	/**
	 * gets which album the image belongs to
	 *
	 * @return album The album the image belongs to
	 */
	public Album getAlbum() {
		return album;
	}
}
