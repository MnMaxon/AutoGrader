package com.Group4.AutoGrader.Controllers;

public class EmptyDockerController extends Controller {

	public void home() {
		new MainController().show();
	}

	public void customDocker() {
		//TODO
	}

	public void emptyTemplate() {
		new DockerController("", "").show();
	}

	public void jdk13() {
		new DockerController("openjdk:13", "java -jar FILE.jar").show();
	}

	public void jdk8() {
		new DockerController("openjdk:8", "java -jar FILE.jar").show();
	}

	public void python3() {
		new DockerController("python:3", "python ./SCRIPT.py").show();
	}

	public void ubuntu() {
		new DockerController("ubuntu:latest", "").show();
	}

	/**
	 * Gives the xml file name, eg: MainView.fxml
	 *
	 * @return "EmptyDockerView.fxml"
	 */
	@Override
	public String getFileName() {
		return "EmptyDockerView.fxml";
	}


}
