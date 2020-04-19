package com.Group4.AutoGrader;

import com.Group4.AutoGrader.Controllers.MainController;
import com.Group4.AutoGrader.Model.User;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

/**
 * The main class, handles initially setting up the project
 *
 * @author Mason Grosset
 * @author Jared Neal
 */
public class AutoGrader extends Application {
	/**
	 * The main jfx stage
	 */
	private static Stage mainStage;

	/**
	 * All of the users on the application
	 */
	public static ArrayList<User> users = new ArrayList<>();

	/**
	 * Gives the main jfx stage, used to switch between views
	 *
	 * @return The main javafx stage
	 */
	public static Stage getMainStage() {
		return mainStage;
	}

	/**
	 * Sets up initial view
	 *
	 * @param primaryStage The main stage used by this application
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			if (new File("data.dat").exists()) {
				//TODO Load Data
//				//noinspection unchecked
//				users = (ArrayList<User>) new ObjectInputStream(new FileInputStream("data.dat")).readObject();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		primaryStage.setTitle("AutoGrader");
		mainStage = primaryStage;
		new MainController().show();
	}

	/**
	 * Saves all of the application's data
	 */
	public static void save() {
		try {
			//noinspection ResultOfMethodCallIgnored
			new File("data.dat").createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//TODO Save Data
//		try {
//			new ObjectOutputStream(new FileOutputStream("data.dat")).writeObject(users);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	/**
	 * Launches the application
	 *
	 * @param args The arguments that will be passed to jfx
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
