package com.Group4.AutoGrader.Model;

import java.io.*;
import java.util.ArrayList;

public class Assignment implements Serializable {
	private ArrayList<Question> questions = new ArrayList<>();
	private VirtualDocker docker = new VirtualDocker();
	transient private String lastLoc = "ERROR!";

	private Assignment() {
	}

	public VirtualDocker getDocker() {
		return docker;
	}

	public ArrayList<Question> getQuestions() {
		return questions;
	}

	public void save() {
		File f = new File(lastLoc);
		try {
			FileOutputStream file = new FileOutputStream(lastLoc);
			ObjectOutputStream out = new ObjectOutputStream(file);
			out.writeObject(this);
		} catch (Exception ex) {
			System.out.println("ERROR: The file " + lastLoc + " could not be saved!");
			ex.printStackTrace();
		}
	}

	public void saveAs(String location) {
		lastLoc = location;
		save();
	}

	public static Assignment load(String fileLocation) {
		Assignment asmt = null;
		try {
			if (new File(fileLocation).exists()) {
				FileInputStream fin = new FileInputStream(fileLocation);
				ObjectInputStream in = new ObjectInputStream(fin);
				System.out.println("giunksbrljntb");
				asmt = (Assignment) in.readObject();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (asmt == null) asmt = new Assignment();
		asmt.lastLoc = fileLocation;
		return asmt;
	}
}
