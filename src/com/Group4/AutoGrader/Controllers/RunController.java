package com.Group4.AutoGrader.Controllers;

import com.Group4.AutoGrader.AutoGrader;
import com.Group4.AutoGrader.Model.Assignment;
import com.Group4.AutoGrader.Model.DockerUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RunController extends Controller {
	public RadioButton singleRadio;
	public RadioButton multipleRadio;
	public TextField projLoc;
	public TextField asgmtLoc;
	public Button runBtn;
	public boolean assignment = false;
	public boolean project = false;

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

	public void open() {
		//TODO open file that they select
		//new EditController().show();
		FileChooser filechooser = new FileChooser();
		filechooser.setTitle("Choose Assignment File");
		Stage stage = new Stage();
		File file = filechooser.showOpenDialog(stage);
		if (file != null) {
			Assignment temp = Assignment.load(file.getAbsolutePath());
			asgmtLoc.setText(file.getAbsolutePath());
			assignment = true;
		}
	}

	public void openProj() {
		//TODO open file that they select
		//new EditController().show();
		DirectoryChooser filechooser = new DirectoryChooser();
		filechooser.setTitle("Choose Project Folder");
		Stage stage = new Stage();
		File file = filechooser.showDialog(stage);
		if (file.isDirectory()) {
			project = true;
			projLoc.setText(file.getPath());
		}
	}

	public void run() {
		if (!asgmtLoc.getText().isEmpty()) assignment = true;
		if (!projLoc.getText().isEmpty()) project = true;
		if (assignment && project) {
			if (multipleRadio.isSelected()) {
				File file = new File(projLoc.getText());
				List<String> projects = Arrays.asList(file.list());
				List<List<String>> answers = new ArrayList<>();
				for (String s : projects) {
					List<String> temp = DockerUtils.runProject(file.getPath() + "\\" + s, Assignment.load(asgmtLoc.getText()));
					System.out.println("---------------------------------" + s);
					for (String s2 : temp)
						System.out.println(s2);
					answers.add(temp);
				}
				new MultipleResultsController(this, Assignment.load(asgmtLoc.getText()), projects, answers).show();
			} else {
				System.out.println("******************************");
				List<String> answers = DockerUtils.runProject(projLoc.getText(), Assignment.load(asgmtLoc.getText()));
				new ResultsController(this, Assignment.load(asgmtLoc.getText()), answers).show();
			}
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error Running");
			alert.setHeaderText(null);
			alert.setContentText("Please enter valid assignment and project file locations");
		}
	}
}
