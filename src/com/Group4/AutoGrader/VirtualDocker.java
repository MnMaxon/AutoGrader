package com.Group4.AutoGrader;

import java.util.List;

public class VirtualDocker {
	private String image;
	private String cmd;
	private String inputPrefix;

	private String dockerText = null;

	public VirtualDocker(String dockerText) {
		this.dockerText = dockerText;
	}

	public VirtualDocker(String image, String cmd, String inputPrefix) {
		this.image = image;
		this.cmd = cmd;
		this.inputPrefix = inputPrefix;
	}

	public String generateFinal(String projectLocation, List<Question> questions) {
		if (inputPrefix == null) inputPrefix = "";
		else inputPrefix += " ";
		String ret = "";
		if (dockerText != null) {
			ret = dockerText.replace("**PROJ_LOC**", projectLocation);
		} else {
			ret = "FROM " + image;
			ret += "\nWORKDIR usr/local/runme";
			ret += "\nCOPY " + projectLocation + " /";
			ret += "\nCMD " + cmd;
		}
		if (questions.size() == 0) return ret;
		ret += "\nRUN ";
		ret += questions.get(0).input;
		for (int i = 1; i < questions.size(); i++)
			ret += " && " + inputPrefix + questions.get(i).input;
		return ret;
	}
}
