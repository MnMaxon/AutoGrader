package com.Group4.AutoGrader.Controllers;

import com.Group4.AutoGrader.Model.Assignment;
import com.Group4.AutoGrader.Model.VirtualDocker;

public class DockTemplateController extends Controller {

	private String fileName;
	private Assignment assignment;

	public DockTemplateController(String fileName) {
		this(Assignment.load(fileName));
	}

	public DockTemplateController(Assignment assignment) {
		this.assignment = assignment;
		VirtualDocker d = this.assignment.getDocker();
		if (d.getDockerText() != null) {
			cancelShow = true;
			new EmptyDockerController(assignment).show();
		} else if (d.getRun() != null && d.getImage() != null && d.getPrefix() != null) {
			cancelShow = true;
			new DockerController(assignment).show();
		}
	}

	public void home() {
		new MainController().show();
	}

	public void customDocker() {
		assignment.getDocker().setDockerText(
				"FROM openjdk:13\n\n" +
						"WORKDIR /usr/local/runme\n\n" +
						"COPY **PROJ_LOC** project\n" +
						"WORKDIR project");
		new EmptyDockerController(assignment).show();
	}

	public void emptyTemplate() {
		openDC("", "");
	}

	public void jdk13() {
		openDC("openjdk:13", "java -jar FILE.jar ");
	}

	public void jdk8() {
		openDC("openjdk:8", "java -jar FILE.jar ");
	}

	public void python3() {
		openDC("python:3", "python ./SCRIPT.py ");
	}

	public void ubuntu() {
		openDC("ubuntu:latest", "");
	}

	private void openDC(String image, String cmdPrefix) {
		assignment.getDocker().setDockerInfo(image, "", cmdPrefix);
		new DockerController(assignment).show();
	}

	/**
	 * Gives the xml file name, eg: MainView.fxml
	 *
	 * @return "DockTemplateView.fxml"
	 */
	@Override
	public String getFileName() {
		return "DockTemplateView.fxml";
	}
}
