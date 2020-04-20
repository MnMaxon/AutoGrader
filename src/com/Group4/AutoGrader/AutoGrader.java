package com.Group4.AutoGrader;

import com.Group4.AutoGrader.Controllers.MainController;
import com.Group4.AutoGrader.Model.User;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.DockerCmdExecFactory;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.jaxrs.JerseyDockerCmdExecFactory;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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
		dockerTest();
	}

	public void dockerTest() {
		try {
			Runtime rt = Runtime.getRuntime();
			System.out.println("AAAAAAA");
			rt.exec("docker rm AutoGrader");
			Process pr = rt.exec("docker run --name AutoGrader hello-world");
			BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));

			String line=null;
			while((line=input.readLine()) != null) {
				System.out.println(line);
			}

			int exitVal = pr.waitFor();
			System.out.println("Exited with error code "+exitVal);

			rt.exec("docker rm AutoGrader");
			System.out.println("Done");
		} catch (Exception e) {
			System.out.println("Docker ERROR");
			e.printStackTrace();
		}
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
