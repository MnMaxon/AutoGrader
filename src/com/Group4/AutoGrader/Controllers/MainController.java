package com.Group4.AutoGrader.Controllers;

public class MainController extends Controller {	/**
 * Gives the xml file name, eg: MainView.fxml
 *
 * @return "MainView.fxml"
 */
@Override
public String getFileName() {
	return "MainView.fxml";
}
public void manage(){
	new SelectController().show();
}
public void grade(){
	new RunController().show();
}
}
