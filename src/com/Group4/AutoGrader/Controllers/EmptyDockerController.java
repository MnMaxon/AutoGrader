package com.Group4.AutoGrader.Controllers;

import com.Group4.AutoGrader.Model.Assignment;
import javafx.scene.control.TextArea;

public class EmptyDockerController extends Controller {

	private final Assignment assignment;
	public TextArea textArea;

	public EmptyDockerController(Assignment assignment) {
		this.assignment = assignment;
		textArea.setText(this.assignment.getDocker().getDockerText());
	}

	public void save() {
		assignment.getDocker().setDockerText(textArea.getText());
		assignment.save();
		new EditController(assignment).show();
	}

	public void home() {
		new MainController().show();
	}

	/**
	 * Gives the xml file name, eg: MainView.fxml
	 *
	 * @return "EmptyDockerView.fxml"
	 */
	@Override
	public String getFileName() {
		return "EmptyDockerView.fxml";
	}


}
