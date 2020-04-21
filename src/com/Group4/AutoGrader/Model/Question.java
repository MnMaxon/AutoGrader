package com.Group4.AutoGrader.Model;

import java.io.Serializable;

public class Question implements Serializable {
    int id;
    String input;
    String output;
    public Question(){
        id = 0;
        input = null;
        output = null;
    }
    public Question(int number, String input, String output){
        id = number;
        this.input = input;
        this.output = output;
    }
    public String getInput(){
        return this.input;
    }
    public String getOutput(){
        return this.output;
    }
    public int getID(){
        return this.id;
    }
    public void setInput(String input){
        this.input = input;
    }
    public void setOutput(String output){
        this.output = output;
    }
    public void setID(int id){
        this.id = id;
    }

    @Override
    public String toString(){
        return "Question " + id;
    }
}
