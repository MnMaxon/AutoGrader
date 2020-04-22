package com.Group4.AutoGrader.Model;

import java.io.Serializable;
import java.util.List;

public class VirtualDocker implements Serializable {
	final public static String DELIM = "aF3g1EFhonD67MHpkHm49xki6DLUUZgAKegw5IAz";

	private String image;
	private String run;
	private String inputPrefix;

	private String dockerText = null;

	public void setDockerText(String dockerText) {
		this.dockerText = dockerText;
	}

	public String getDockerText() {
		return dockerText;
	}

	public void setDockerInfo(String image, String cmd, String inputPrefix) {
		if (image == null) image = "";
		if (cmd == null) cmd = "";
		if (inputPrefix == null) inputPrefix = "";
		this.image = image;
		this.run = cmd;
		this.inputPrefix = inputPrefix;
	}

	public String generateFinal(String projectLocation, List<Question> questions) {
		System.out.println("-"+projectLocation);
		projectLocation = '\"' + projectLocation.replace("\\", "/") + '\"';
		String ret = "";
		if (dockerText != null) {
			ret = dockerText.replace("**PROJ_LOC**", projectLocation);
		} else {
			ret = "FROM " + image;
			ret += "\nWORKDIR usr/local/runme";
			ret += "\nCOPY " + projectLocation + " project";
			ret += "\nWORKDIR project";
			if (!run.equals(""))
				ret += "\nRUN " + run;
		}
		if (questions.size() == 0) return ret;
		ret += "\nCMD ";
		ret += inputPrefix + questions.get(0).input;
		for (int i = 1; i < questions.size(); i++)
			ret += " ; echo " + DELIM + "; ls " + DELIM + " ; " + inputPrefix + questions.get(i).input;
		return ret;
	}

	public String getImage() {
		return image;
	}

	public String getRun() {
		return run;
	}

	public String getPrefix() {
		return inputPrefix;
	}
}
