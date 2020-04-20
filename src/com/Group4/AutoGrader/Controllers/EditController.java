package com.Group4.AutoGrader.Controllers;

import com.Group4.AutoGrader.Question;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class EditController extends Controller {
	public String asmt;
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
	public ArrayList<Question> questions = new ArrayList<>();
	public ObservableList<Question> quest = FXCollections.observableArrayList();
	public boolean replace;
	public EditController(String file) throws IOException {
		super();
		replace = false;
		saveBtn.setVisible(false);
		deleteBtn.setVisible(false);
		inputArea.setVisible(false);
		outputArea.setVisible(false);
		replaceBtn.setVisible(false);
		asmt = file;

		try {
			if(new File(asmt).exists()) {
				FileInputStream fin = new FileInputStream(asmt);
				ObjectInputStream in = new ObjectInputStream(fin);
				questions = (ArrayList<Question>) in.readObject();
				quest.addAll(questions);
				//Comparator<Question> q = Comparator.comparingInt(Question::getID);
				//quest.sort(q);
				list.setItems(quest);
				System.out.println("giunksbrljntb");
			}else {
				File fil = new File(asmt);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println(questions.size());

	}
	@Override
	public String getFileName() {
		return "EditView.fxml";
	}
	public void select(){
		new SelectController().show();
	}
	public void save() throws IOException {
		if(questions == null){
			Question temp = new Question(1, inputArea.getText(), outputArea.getText());
			questions.add(temp);
		}
		else if(!replace){
			Question temp = new Question(questions.size()+1, inputArea.getText(), outputArea.getText());
			questions.add(temp);
		}else{
			Question temp = (Question) list.getSelectionModel().getSelectedItem();
			Question othertemp = questions.remove(temp.getID()-1);
			othertemp.setInput(inputArea.getText());
			othertemp.setOutput(outputArea.getText());
			questions.add(othertemp);
		}

		FileOutputStream file = new FileOutputStream(asmt);
		ObjectOutputStream out = new ObjectOutputStream(file);
		out.writeObject(questions);
		new EditController(asmt).show();
	}
	public void replace() throws IOException {
		replace = true;
		save();
	}
	public void add(){
		inputArea.setVisible(true);
		outputArea.setVisible(true);
		deleteBtn.setVisible(true);
		saveBtn.setVisible(true);
		inputArea.clear();
		outputArea.clear();
		replaceBtn.setVisible(false);
	}
	public void fill(){
		add();
		Question temp = (Question) list.getSelectionModel().getSelectedItem();
		inputArea.setText(temp.getInput());
		outputArea.setText(temp.getOutput());
		replaceBtn.setVisible(true);
	}
	public void savenquit(){
		try {
			save();
		} catch (IOException e) {
			e.printStackTrace();
		}
		new MainController().show();
	}
	public void delete() throws IOException {
		Question temp = (Question) list.getSelectionModel().getSelectedItem();
		questions.remove(temp);
		int newID = 1;
		for(Question q: questions){
			q.setID(newID);
			newID++;
		}
		FileOutputStream file = new FileOutputStream(asmt);
		ObjectOutputStream out = new ObjectOutputStream(file);
		out.writeObject(questions);
		new EditController(asmt).show();
	}
}
