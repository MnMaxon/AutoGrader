package com.Group4.AutoGrader.Controllers;

public class ResultsController extends Controller {
	/**
	 * Gives the xml file name, eg: MainView.fxml
	 *
	 * @return "ResultView.fxml"
	 */
	@Override
	public String getFileName() {
		return "ResultsView.fxml";
	}
	public void home(){
		new MainController().show();
	}
}
