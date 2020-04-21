package com.Group4.AutoGrader.Controllers;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;

public class SelectController extends Controller {
	/**
	 * Gives the xml file name, eg: MainView.fxml
	 *
	 * @return "ResultView.fxml"
	 */

	public TextField fileName;
	public Button confirmBtn;
	public Button cancelBtn;

	@Override
	public String getFileName() {
		return "SelectAssignmentView.fxml";
	}

	public void home() {
		new MainController().show();
	}

	public void edit() {
		//TODO open file that they select
		//new EditController().show();
	}

	public void newAssignment() {
		fileName.setVisible(true);
		confirmBtn.setVisible(true);
		cancelBtn.setVisible(true);
	}

	public void makeAssignment() throws IOException {
		new DockTemplateController(fileName.getText()).show();
	}

	public void toggleVis() {
		fileName.setVisible(false);
		confirmBtn.setVisible(false);
		cancelBtn.setVisible(false);
	}

}
