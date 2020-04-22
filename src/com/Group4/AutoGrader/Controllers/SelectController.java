package com.Group4.AutoGrader.Controllers;

import com.Group4.AutoGrader.AutoGrader;
import com.Group4.AutoGrader.Model.Assignment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
	public TextField locationText;
	public ComboBox combo;

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
		FileChooser filechooser = new FileChooser();
		filechooser.setTitle("Open Auto-Grader Project");
		Stage stage = new Stage();
		File file = filechooser.showOpenDialog(stage);
		if (file != null) {
			Assignment temp = Assignment.load(file.getAbsolutePath());
			System.out.println(AutoGrader.files.size());
			new EditController(temp).show();
		}
	}

	public void openRecent() {
		String file = (String) combo.getSelectionModel().getSelectedItem();
		Assignment temp = Assignment.load(file);
		new EditController(temp).show();
	}

	public void recents() {
		ObservableList<String> fs = FXCollections.observableArrayList();
		fs.addAll(AutoGrader.files);
		combo.setItems(fs);
	}

	public void openByText() {
		String loc = locationText.getText();
		if (!loc.isEmpty()) {
			new DockTemplateController(loc).show();
		}
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
