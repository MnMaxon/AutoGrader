package com.Group4.AutoGrader.Controllers.Examples;

import com.Group4.AutoGrader.Controllers.Controller;
import com.Group4.AutoGrader.Model.Album;
import com.Group4.AutoGrader.Model.User;
import com.Group4.AutoGrader.AutoGrader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Controller class for admin screen.fxml
 *
 * @author Mason Grosset
 * @author Jared Neal
 */
public class AdminController extends Controller {
    /**
     * The button of the currently selected user
     */
    private Button selected = null;
    /**
     * The index of the currently selected user
     */
    int selectedI = -1;
    /**
     * The button an admin can press to create a user
     */
    public Button createBut;
    /**
     * The textfield that allows an admin to type a name in for the new user
     */
    public TextField userField;
    /**
     * The button that confirms the new user's name
     */
    public Button confirmBut;
    /**
     * The button that can cancel the creation of a new user
     */
    public Button cancelBut;
    /**
     * The button an admin presses to logout
     */
    public Button logoutBut;
    /**
     * The anchorpane that will show all the users for the admin
     */
    public AnchorPane a1;

    /**
     * Sets up controller and loads proper fxml
     */
    public AdminController(ArrayList<User> users) {
        super();
        confirmBut.setVisible(false);
        cancelBut.setVisible(false);
        logoutBut.setOnAction(e -> new LoginController(users).show());
        userField.setVisible(false);

        a1.setMinHeight(140 * ((users.size() + 1) / 2));
        for (int i = 0; i < users.size(); i++) {
            Button b = new Button("Delete");
            b.setTranslateX(120 * (i % 2) + 32);
            //noinspection IntegerDivisionInFloatingPointContext
            b.setTranslateY(140 * (i / 2) + 100);
            Text name = new Text();
            name.setText(users.get(i).getName());
            name.setTranslateX(120 * (i % 2) + 10);
            //noinspection IntegerDivisionInFloatingPointContext
            name.setTranslateY(140 * (i / 2) + 30);
            b.setId(i + "");
            b.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    if (selected != null) {
                        selected.setDisable(false);
                        selected.setText("Delete");
                    }
                    b.setDisable(true);
                    b.setText("Deleted");
                    selectedI = Integer.parseInt(b.getId());
                    selected = b;
                    if(!users.get(selectedI).getName().equals("admin")) users.remove(selectedI);
                    AutoGrader.save();
                    new AdminController(users).show();
                }
            });
            Rectangle r = new Rectangle(120 * (i % 2) + 5, 140 * (i / 2) + 5, 120, 140);
            r.setFill(Color.TRANSPARENT);
            r.setStroke(Color.BLACK);
            a1.getChildren().add(r);
            a1.getChildren().add(b);
            a1.getChildren().add(name);
        }

        createBut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                userField.setVisible(true);
                confirmBut.setVisible(true);
                cancelBut.setVisible(true);
            }
        });
        confirmBut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                boolean add = true;
                for (User user : users) {
                    if (Objects.equals(userField.getText(), user.getName()) || userField.getText() == null) {
                        userField.setText("Error, try another name.");
                        add = false;
                        break;
                    }
                }
                if (add) {
                    User temp = new User(userField.getText(), new ArrayList<Album>());
                    users.add(temp);
                    AutoGrader.save();
                    new AdminController(users).show();
                }

            }
        });
        cancelBut.setOnAction(e -> new AdminController(users).show());
    }

    /**
     * Gives the fxml filename, eg: album.fxml
     *
     * @return "admin screen.fxml"
     */
    @Override
    public String getFileName() {
        return "admin screen.fxml";
    }
}
