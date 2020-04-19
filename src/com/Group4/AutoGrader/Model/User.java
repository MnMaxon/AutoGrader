package com.Group4.AutoGrader.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
	/**
	 * Name of the user
	 */
	private final String name;
	/**
	 * The albums owned by the user
	 */
	private final ArrayList<Album> albums;
	/**
	 * The user's tags that can have multiple values
	 */
	public final ArrayList<String> multiTags = new ArrayList<>();
	/**
	 * The user's tags with single values
	 */
	public final ArrayList<String> singleTags = new ArrayList<>();

	/**
	 * Initializes a user with given attributes
	 * Makes sure all albums know who they belong to
	 *
	 * @param name The user's name
	 * @param albums List of the user's albums
	 */
	public User(String name, ArrayList<Album> albums) {
		multiTags.add("People");
		singleTags.add("Location");
		singleTags.add("Type");
		this.name = name;
		this.albums = albums;
		for (Album album: albums)album.setUser(this);
	}

	/**
	 * Gives the albums owned by the user
	 *
	 * @return The user's albums
	 */
	public ArrayList<Album> getAlbums() {
		return albums;
	}

	/**
	 * Gives the name of the user
	 *
	 * @return The user's name
	 */
	public String getName() {
		return name;
	}
}
