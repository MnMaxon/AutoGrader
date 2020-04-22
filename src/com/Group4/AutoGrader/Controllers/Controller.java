package com.Group4.AutoGrader.Controllers;

import com.Group4.AutoGrader.AutoGrader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

/**
 * The base controller class, used to interact with JFX
 *
 * @author Mason Grosset
 * @author Jared Neal
 */
public abstract class Controller {
	/**
	 * The fxmlLoader root after it is loaded
	 */
	private final Parent root;

	public boolean cancelShow = false;
	private Scene scene = null;

	/**
	 * Sets up the FxmlLoader at the controller's file
	 */
	public Controller() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/" + getFileName()));
		fxmlLoader.setController(this);
		try {
			root = fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	/**
	 * Gives the xml file name, eg: album.fxml
	 *
	 * @return the name of the xml file
	 */
	public abstract String getFileName();

	/**
	 * Shows the fxml file on the main stage
	 */
	public void show() {
		if (cancelShow) return;
		if (scene == null) {
			scene = new Scene(root, 600, 400);
		}
		AutoGrader.getMainStage().setScene(scene);
		AutoGrader.getMainStage().show();
	}

}