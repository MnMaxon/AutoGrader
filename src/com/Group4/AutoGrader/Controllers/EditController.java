package com.Group4.AutoGrader.Controllers;

public class EditController extends Controller {
	/**
	 * Gives the xml file name, eg: MainView.fxml
	 *
	 * @return "ResultView.fxml"
	 */
	@Override
	public String getFileName() {
		return "EditView.fxml";
	}
	public void select(){
		new SelectController().show();
	}
}
