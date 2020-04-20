package com.Group4.AutoGrader;

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
}
