package com.Group4.AutoGrader.Controllers;

import com.Group4.AutoGrader.Model.Assignment;
import com.Group4.AutoGrader.Model.Question;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.List;

public class MultipleResultsController extends Controller {
	private final Controller lastController;
	private final Assignment assignment;
	private final List<List<String>> answerList;
	public GridPane grid;
	public CheckBox filterCheck;
	private ArrayList<ArrayList<VBox>> vboxes = new ArrayList<>();
	private ArrayList<VBox> selected = new ArrayList<>();

	public MultipleResultsController(Controller lastController, Assignment assignment, List<String> projectNames, List<List<String>> answerList) {
		this.lastController = lastController;
		this.assignment = assignment;
		this.answerList = answerList;
		for (int j = 0; j < projectNames.size(); j++) {
			int correct = 0;
			List<String> answers = answerList.get(j);
			for (int i = 0; i < assignment.getQuestions().size(); i++) {
				Question q = assignment.getQuestions().get(i);
				if (i < answers.size() && answers.get(i).equals(q.getOutput()))
					correct++;
			}
			addLabel(projectNames.get(j), 0, j);
			addLabel((int) (1000. * (correct * 10.) / (assignment.getQuestions().size() * .1)) / 1000. + "", 1, j);
		}
		grid.getStyleClass().add("grid");
		grid.setGridLinesVisible(true);
	}

	@FXML
	private void mouseClick(MouseEvent e) {
		Node source = (Node) e.getSource();
		Integer colIndex = GridPane.getColumnIndex(source);
		Integer rowIndex = GridPane.getRowIndex(source);
//		System.out.printf("Mouse entered cell [%d, %d]%n", colIndex.intValue(), rowIndex.intValue());
	}

	public void addLabel(String s, int x, int y) {
		VBox vb = new VBox();
		if (x == 0)
			vboxes.add(new ArrayList<>());
		vboxes.get(y).add(vb);

		vb.setFillWidth(true);
		Label l = new Label(s);
		String style = "-fx-font-size: 12;";
		l.setStyle(style);
		Controller cont = this;
		vb.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				Node source = (Node) e.getSource();
				Integer colIndex = GridPane.getColumnIndex(source);
				Integer rowIndex = GridPane.getRowIndex(source);
				if (e.getClickCount() == 2 && selected.equals(vboxes.get(y))) {
					new ResultsController(cont, assignment, answerList.get(y)).show();
					return;
				}
				for (VBox b : selected) b.setStyle("-fx-background-color: white");
				selected = vboxes.get(y);
				for (VBox b : selected) {
					b.setBackground(Background.EMPTY);
					b.setStyle("-fx-background-color: lightgray");
				}
			}
		});
		grid.add(vb, x, y);
		grid.add(l, x, y);
	}

	public void back() {
		lastController.show();
	}

	/**
	 * Gives the xml file name, eg: MainView.fxml
	 *
	 * @return "MultipleResultsView.fxml"
	 */
	@Override
	public String getFileName() {
		return "MultipleResultsView.fxml";
	}

	public void home() {
		new MainController().show();
	}
}
