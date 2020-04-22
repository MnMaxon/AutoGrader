package com.Group4.AutoGrader.Controllers;

import com.Group4.AutoGrader.AutoGrader;
import com.Group4.AutoGrader.Model.Assignment;
import com.Group4.AutoGrader.Model.Question;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.io.*;

public class EditController extends Controller {
	/**
	 * Gives the xml file name, eg: MainView.fxml
	 *
	 * @return "ResultView.fxml"
	 */
	public AnchorPane questionPane;
	public ScrollPane scrollPane;
	public TextArea inputArea;
	public TextArea outputArea;
	public ListView list;
	public Button saveBtn;
	public Button deleteBtn;
	public Button replaceBtn;
	public Assignment asmt;
	public ObservableList<Question> quest = FXCollections.observableArrayList();
	public boolean replace;

	public EditController(Assignment assignment) {
		super();

		quest.addAll(assignment.getQuestions());
		//Comparator<Question> q = Comparator.comparingInt(Question::getID);
		//quest.sort(q);
		list.setItems(quest);
		this.asmt = assignment;
		replace = false;
		saveBtn.setVisible(false);
		deleteBtn.setVisible(false);
		inputArea.setVisible(false);
		outputArea.setVisible(false);
		replaceBtn.setVisible(false);

		//System.out.println(asmt.getQuestions().size());

	}

	@Override
	public String getFileName() {
		return "EditView.fxml";
	}

	public void select() {
		new SelectController().show();
	}

	public void save() throws IOException {
		if (!replace) {
			Question temp = new Question(asmt.getQuestions().size() + 1, inputArea.getText(), outputArea.getText());
			asmt.getQuestions().add(temp);
		} else {
			Question temp = (Question) list.getSelectionModel().getSelectedItem();
			Question othertemp = asmt.getQuestions().remove(temp.getID() - 1);
			othertemp.setInput(inputArea.getText());
			othertemp.setOutput(outputArea.getText());
			asmt.getQuestions().add(othertemp);
		}
		AutoGrader.saveRecents();
		asmt.save();
		new EditController(asmt).show();
	}

	public void replace() throws IOException {
		replace = true;
		save();
	}

	public void add() {
		inputArea.setVisible(true);
		outputArea.setVisible(true);
		deleteBtn.setVisible(true);
		saveBtn.setVisible(true);
		inputArea.clear();
		outputArea.clear();
		replaceBtn.setVisible(false);
	}

	public void fill() {
		add();
		Question temp = (Question) list.getSelectionModel().getSelectedItem();
		if (temp == null) return;
		inputArea.setText(temp.getInput());
		outputArea.setText(temp.getOutput());
		replaceBtn.setVisible(true);
	}

	public void savenquit() {
		try {
			save();
		} catch (IOException e) {
			e.printStackTrace();
		}
		new MainController().show();
	}

	public void delete() throws IOException {
		Question temp = (Question) list.getSelectionModel().getSelectedItem();
		asmt.getQuestions().remove(temp);
		int newID = 1;
		for (Question q : asmt.getQuestions()) {
			q.setID(newID);
			newID++;
		}
		asmt.save();
		new EditController(asmt).show();
	}
}
