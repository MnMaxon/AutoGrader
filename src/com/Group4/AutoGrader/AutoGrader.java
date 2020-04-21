package com.Group4.AutoGrader;

import com.Group4.AutoGrader.Controllers.MainController;
import com.Group4.AutoGrader.Model.Assignment;
import com.Group4.AutoGrader.Model.DockerUtils;
import com.Group4.AutoGrader.Model.VirtualDocker;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
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
		DockerUtils.test();
//		dockerTest();
	}

	public void dockerTest() {
		runCommand("docker rm AutoGrader");
		System.out.println(runCommandString("docker run --name AutoGrader hello-world"));
		runCommand("docker rm AutoGrader");
	}

	public static ArrayList<String> runCommand(String command) {
		ArrayList<String> ar = new ArrayList<>();
		try {
			Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec(command);
			BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			BufferedReader error = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
			ProcessBuilder pv = new ProcessBuilder();

			ArrayList<String> list = new ArrayList<>();
			String line = null;


//			int exitVal = pr.waitFor();
//			System.out.println("Exited with error code " + exitVal);
			while ((line = input.readLine()) != null) {
				if (line.equals(VirtualDocker.DELIM)){
					String eLine;
					while ((eLine = error.readLine()) != null && !eLine.contains(VirtualDocker.DELIM))
						list.add(eLine);
				}
				list.add(line);
			}
			while ((line = error.readLine()) != null) list.add(line);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			ArrayList<String> ret = new ArrayList<>();
			ret.add(e.getMessage());
			return ret;
		}
	}

	public static String runCommandString(String command) {
		StringBuilder ret = new StringBuilder();
		for (String s : runCommand(command)) {
			if (!ret.toString().equals("")) ret.append("\n");
			ret.append(s);
		}
		return ret.toString();
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
