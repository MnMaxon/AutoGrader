package com.Group4.AutoGrader.Controllers;

public class SelectController extends Controller {
	/**
	 * Gives the xml file name, eg: MainView.fxml
	 *
	 * @return "ResultView.fxml"
	 */
	@Override
	public String getFileName() {
		return "SelectAssignmentView.fxml";
	}
	public void home(){
		new MainController().show();
	}
	public void edit(){
		//TODO open file that they select
		new EditController().show();
	}
}
