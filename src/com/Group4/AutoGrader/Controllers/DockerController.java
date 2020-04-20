package com.Group4.AutoGrader.Controllers;

public class DockerController extends Controller {

	public DockerController(String image, String cmdPrefix) {
		//TODO
	}

	/**
	 * Gives the xml file name, eg: MainView.fxml
	 *
	 * @return "DockerView.fxml"
	 */
	@Override
	public String getFileName() {
		return "DockerView.fxml";
	}

	public void home() {
		new MainController().show();
	}


}
