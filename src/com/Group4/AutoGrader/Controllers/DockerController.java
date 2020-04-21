package com.Group4.AutoGrader.Controllers;

import com.Group4.AutoGrader.Model.Assignment;
import com.Group4.AutoGrader.Model.VirtualDocker;
import javafx.scene.control.TextField;

public class DockerController extends Controller {
	public TextField cmdField;
	public TextField prefixField;
	public TextField fromField;

	private Assignment assignment;

	public DockerController(Assignment assignment) {
		this.assignment = assignment;
		VirtualDocker d = assignment.getDocker();
		fromField.setText(d.getImage());
		prefixField.setText(d.getPrefix());
		cmdField.setText(d.getCmd());
	}

	public void save() {
		assignment.getDocker().setDockerInfo(fromField.getText(), cmdField.getText(), prefixField.getText());
		assignment.save();
		new EditController(assignment).show();
	}

	/**
	 * Gives the xml file name, eg: MainView.fxml
	 *
	 * @return "DockerView.fxml"
	 */
	@Override
	public String getFileName() {
		return "DockerView.fxml";
	}

	public void home() {
		new MainController().show();
	}


}
