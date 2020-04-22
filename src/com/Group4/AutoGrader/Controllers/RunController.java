package com.Group4.AutoGrader.Controllers;

import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class RunController extends Controller {
	public RadioButton singleRadio;
	public RadioButton multipleRadio;
	public TextField projectLoc;
	public TextField asgmtLoc;

	public void singleClick() {
		multipleRadio.setSelected(false);
	}

	public void multipleClick() {
		singleRadio.setSelected(false);
	}

	/**
	 * Gives the xml file name, eg: MainView.fxml
	 *
	 * @return "RunAssignmentView.fxml"
	 */
	@Override
	public String getFileName() {
		return "RunAssignmentView.fxml";
	}

	public void home() {
		new MainController().show();
	}
}
