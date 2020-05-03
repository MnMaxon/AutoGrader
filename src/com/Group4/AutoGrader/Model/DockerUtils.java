package com.Group4.AutoGrader.Model;

import com.Group4.AutoGrader.AutoGrader;
import com.Group4.AutoGrader.Controllers.MultipleResultsController;
import org.apache.commons.io.FileUtils;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DockerUtils {
	public static void test() {
		try {
			File f = new File("autoTest");
			if (f.exists()) f.delete();
			Assignment assignment = Assignment.load("autoTest");
			assignment.getDocker().setDockerInfo("openjdk:13", "", "java -jar artifacts/untitled_jar/untitled.jar");
			assignment.getQuestions().add(new Question(0, "", "0.0"));
			assignment.getQuestions().add(new Question(0, "rrr", "0.0"));
			assignment.getQuestions().add(new Question(0, "", "0.0"));
			assignment.getQuestions().add(new Question(0, "", "0.0"));
			assignment.getQuestions().add(new Question(0, "", "0.0"));
			assignment.getQuestions().add(new Question(0, "", "0.0"));
			assignment.getQuestions().add(new Question(0, "", "0.0"));

			List<String> answers = runProject("C:\\Users\\User\\IdeaProjects\\untitled\\out", assignment);
			ArrayList<String> projectStrings = new ArrayList<String>();
			projectStrings.add(f.getName());
			projectStrings.add(f.getName());
			List<List<String>> answersList = new ArrayList<>();
			answersList.add(answers);
			answersList.add(answers);
			new MultipleResultsController(null, assignment, projectStrings, answersList).show();
//			new ResultsController(null, assignment, answers).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<String> runProject(String projectLocation, Assignment assignment) {
		generateDockerFile(assignment, projectLocation);
		generateImage();
		String s = runDocker();
		System.out.println(")))))))))))))This is the s:\n'" + s + "'");

		if (s.startsWith(VirtualDocker.DELIM)) s = "\n" + s;
		if (s.endsWith(VirtualDocker.DELIM)) s = s + "\n";
		s = s.replace(VirtualDocker.DELIM + "\n" + VirtualDocker.DELIM, VirtualDocker.DELIM + "\n\n" + VirtualDocker.DELIM);
		System.out.println(")))))))))))))This is the s:\n'" + s + "'");
		List<String> strings =new ArrayList<>();
		while (s.startsWith("\n" + VirtualDocker.DELIM + "\n")){
			s=s.replaceFirst("\n" + VirtualDocker.DELIM + "\n","");
			strings.add("");
		}
		int end = 0;
		while (s.endsWith("\n" + VirtualDocker.DELIM + "\n")){
			s=s.substring(0,s.length()-("\n" + VirtualDocker.DELIM + "\n").length());
			end++;
		}
		strings.addAll(Arrays.asList(s.split("\n" + VirtualDocker.DELIM + "\n")));
		for (int i = 0;i<end;i++)strings.add("");

		return strings;
	}

	public static void generateDockerFile(Assignment assignment, String projectLocation) {
		File proj = new File(projectLocation);
		if (!proj.exists()) return;
		if (!proj.isDirectory()) proj = new File(proj.getParent());
		File copytoFile = new File("projects/" + proj.getName());
		try {
			if (copytoFile.exists()) FileUtils.deleteDirectory(copytoFile);
			FileUtils.copyDirectory(proj, copytoFile);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		try {
			File file = new File("Dockerfile");
			if (file.exists()) file.delete();
			file.createNewFile();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			FileWriter writer = new FileWriter("Dockerfile");
			writer.write(assignment.getDocker().generateFinal(copytoFile.getPath(), assignment.getQuestions()));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void generateImage() {
		generateImage("./");
	}

	public static void generateImage(String dockerPath) {
		File f = new File(dockerPath);
	}

	public static String runDocker() {
		return runDocker("autograder");
	}

	public static String runDocker(String image) {
		return AutoGrader.runCommandString("docker run " + image);
	}
}
