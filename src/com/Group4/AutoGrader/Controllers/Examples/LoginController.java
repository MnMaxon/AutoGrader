package com.Group4.AutoGrader.Controllers.Examples;

import com.Group4.AutoGrader.Controllers.Controller;
import com.Group4.AutoGrader.Model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.ArrayList;

/**
 * Controller for login screen.fxml
 *
 * @author Mason Grosset
 * @author Jared Neal
 */
public class LoginController extends  Controller {
    /**
     * The button that the user presses to login
     */
    public Button loginBut;
    /**
     * The textfield the user types their username into
     */
    public TextField userField;

    /**
     * Sets up controller and loads proper fxml
     */
    public LoginController(ArrayList<User> users){
        super();
        loginBut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(userField.getText().equals("admin")) new AdminController(users).show();
                else{
                    for(User user: users) if(user.getName().equals(userField.getText())) new HomeController(user).show();
                }
            }
        });
    }

    /**
     * Gives the fxml filename, eg: album.fxml
     *
     * @return "login screen.fxml"
     */
    @Override
    public String getFileName() {
        return "login screen.fxml";
    }
}