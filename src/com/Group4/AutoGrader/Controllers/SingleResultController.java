package com.Group4.AutoGrader.Controllers;

import com.Group4.AutoGrader.Model.Assignment;
import com.Group4.AutoGrader.Model.Question;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.List;

public class SingleResultController extends Controller {
	private final Controller lastController;
	public GridPane grid;
	private ArrayList<ArrayList<VBox>> vboxes = new ArrayList<>();
	private ArrayList<VBox> selected = new ArrayList<>();

	public TextArea inputArea, expectedArea, actualArea;

	public SingleResultController(Controller lastController, String input, String expected, String actual) {
		this.lastController = lastController;
		inputArea.setText(input);
		expectedArea.setText(expected);
		actualArea.setText(actual);
	}

	public void back() {
		lastController.show();
	}

	/**
	 * Gives the xml file name, eg: MainView.fxml
	 *
	 * @return "SingleResultView.fxml"
	 */
	@Override
	public String getFileName() {
		return "SingleResultView.fxml";
	}

	public void home() {
		new MainController().show();
	}
}
