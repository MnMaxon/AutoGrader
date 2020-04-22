package com.Group4.AutoGrader.Controllers;

import com.Group4.AutoGrader.AutoGrader;

import java.io.FileNotFoundException;

public class MainController extends Controller {
	/**
	 * Gives the xml file name, eg: MainView.fxml
	 *
	 * @return "MainView.fxml"
	 */
	@Override
	public String getFileName() {
		return "MainView.fxml";
	}

	public void manage() throws FileNotFoundException {
		new SelectController().show();
		AutoGrader.loadRecents();
	}

	public void grade() {
		new RunController().show();
	}
}