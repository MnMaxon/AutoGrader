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
	public ArrayList<Question> questions;
	public ObservableList<Question> quest = FXCollections.observableArrayList();
	public EditController(String file) throws IOException {
		super();
		saveBtn.setVisible(false);
		deleteBtn.setVisible(false);
		inputArea.setVisible(false);
		outputArea.setVisible(false);
		asmt = file;
//		Scanner sc = new Scanner(asmt);
//		int count = 0;
//		while(sc.hasNextLine()){
//			if(sc.next().equals("\n") && count == 0) break;
//			String [] arr = sc.nextLine().split("\t");
//			Question temp = new Question(count, arr[0], arr[1]);
//			if(!questions.contains(temp)) questions.add(temp);
//			quest.add("Question " + count);
//			count++;
//		}
		try {
			if(new File(asmt).exists()) {
				FileInputStream fin = new FileInputStream(asmt);
				ObjectInputStream in = new ObjectInputStream(fin);
				questions = (ArrayList<Question>) in.readObject();
				quest.addAll(questions);
				list.setItems(quest);
				System.out.println("giunksbrljntb");
			}else {
				File fil = new File(asmt);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}


	}
	@Override
	public String getFileName() {
		return "EditView.fxml";
	}
	public void select(){
		new SelectController().show();
	}
	public void editAsmt(){
		if(new File(asmt).exists()) System.out.println("lvkjsnvskj");
	}
	public void save() throws IOException {
		if(questions == null){
			questions = new ArrayList<Question>();
			Question temp = new Question(1, inputArea.getText(), outputArea.getText());
			questions.add(temp);
		}
		else {
			Question temp = new Question(questions.size()+1, inputArea.getText(), outputArea.getText());
			questions.add(temp);
		}

		FileOutputStream file = new FileOutputStream(asmt);
		ObjectOutputStream out = new ObjectOutputStream(file);
		out.writeObject(questions);
		new EditController(asmt).show();
	}
	public void add(){
		inputArea.setVisible(true);
		outputArea.setVisible(true);
		deleteBtn.setVisible(true);
		saveBtn.setVisible(true);
	}
	public void fill(){
		add();
		Question temp = (Question) list.getSelectionModel().getSelectedItem();
		inputArea.setText(temp.getInput());
		outputArea.setText(temp.getOutput());

	}
}
