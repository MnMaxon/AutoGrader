package com.Group4.AutoGrader.Controllers;

import com.Group4.AutoGrader.Model.Assignment;
import com.Group4.AutoGrader.Model.Question;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.List;

public class ResultsController extends Controller {
	private final Controller lastController;
	private final Assignment assignment;
	private final List<String> answers;
	public GridPane grid;
	public CheckBox filterCheck;
	private ArrayList<ArrayList<VBox>> vboxes = new ArrayList<>();
	private ArrayList<ArrayList<String>> strings = new ArrayList<>();
	private ArrayList<VBox> selected = new ArrayList<>();

	public ResultsController(Controller lastController, Assignment assignment, List<String> answers) {
		this(lastController, assignment, answers, false);
	}

	public ResultsController(Controller lastController, Assignment assignment, List<String> answers, boolean checked) {
		this.lastController = lastController;
		this.assignment = assignment;
		this.answers = answers;
		filterCheck.setSelected(checked);
		int correct = 0;
		int shown = 0;
		for (int i = 0; i < assignment.getQuestions().size(); i++) {
			Question q = assignment.getQuestions().get(i);
			if (i < answers.size() && answers.get(i).equals(q.getOutput())) {
				if (filterCheck.isSelected()) continue;
				addLabel("Correct", Color.GREEN, 0, shown);
				correct++;
			} else {
				addLabel("Incorrect", Color.RED, 0, shown);
			}
			addLabel(q.getInput(), 1, shown);
			addLabel(q.getOutput(), 2, shown);
			addLabel(answers.get(i), 3, shown);
			shown++;
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
		addLabel(s, Color.BLACK, x, y);
	}

	public void filterClick() {
		new ResultsController(lastController, assignment, answers, filterCheck.isSelected()).show();
	}

	public void addLabel(String s, Paint p, int x, int y) {
		VBox vb = new VBox();
		if (x == 0) {
			vboxes.add(new ArrayList<>());
			strings.add(new ArrayList<>());
		}
		vboxes.get(y).add(vb);
		strings.get(y).add(s);

		vb.setFillWidth(true);
		Label l = new Label(s);
		String style = "-fx-font-size: 12;";
		l.setTextFill(p);
		l.setStyle(style);
		Controller cont = this;
		vb.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				Node source = (Node) e.getSource();
				Integer colIndex = GridPane.getColumnIndex(source);
				Integer rowIndex = GridPane.getRowIndex(source);
				if (e.getClickCount() == 2 && selected.equals(vboxes.get(y))) {
					List<String> sList = strings.get(y);
					new SingleResultController(cont, sList.get(1), sList.get(2), sList.get(3)).show();
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
	 * @return "ResultView.fxml"
	 */
	@Override
	public String getFileName() {
		return "ResultsView.fxml";
	}

	public void home() {
		new MainController().show();
	}
}
