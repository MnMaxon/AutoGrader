package com.Group4.AutoGrader.Controllers.Examples;

import com.Group4.AutoGrader.Controllers.Controller;
import com.Group4.AutoGrader.Model.Album;
import com.Group4.AutoGrader.Model.User;
import com.Group4.AutoGrader.AutoGrader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

/**
 * Controller class for home screen.fxml
 *
 * @author Mason Grosset
 * @author Jared Neal
 */

public class HomeController extends Controller {
	/**
	 * The button of the currently selected album
	 */
	private Button selected = null;
	/**
	 * The index of the currently selected album
	 */
	int selectedI = -1;

	public AnchorPane a1;
	/**
	 * The button the user presses to create an album
	 */
	public Button createBut;
	/**
	 * The button the user presses to delete an album
	 */
	public Button deleteBut;
	/**
	 * The button the user presses to rename an album
	 */
	public Button renameBut;
	/**
	 * The textfield that users fill in when creating an album
	 */
	public TextField createField;
	/**
	 * The textfield users fill in when renaming an album
	 */
	public TextField renameField;
	/**
	 * The button the user presses to logout
	 */
	public Button logoutBut;
	/**
	 * The button the user presses to view an album
	 */
	public Button viewBut;
	/**
	 * The button the user presses to go to the search screen
	 */
	public Button searchBut;

	/**
	 * Sets up controller and loads proper fxml
	 */
	public HomeController(User user) {
		super();
		renameBut.setDisable(true);
		renameField.setDisable(true);
		deleteBut.setDisable(true);
		viewBut.setDisable(true);
		a1.setMinHeight(140 * ((user.getAlbums().size() + 1) / 2));
		for (int i = 0; i < user.getAlbums().size(); i++) {
			Button b = new Button(user.getAlbums().get(i).getName());
			b.setTranslateX(120 * (i % 2) + 32);
			//noinspection IntegerDivisionInFloatingPointContext
			b.setTranslateY(140 * (i / 2) + 100);
			ImageView imageView = new ImageView();
			if (user.getAlbums().get(i).images.size() != 0)
				imageView.setImage(new Image("file:" + user.getAlbums().get(i).images.get(0).getLocation()));
			imageView.setPreserveRatio(true);
			imageView.setFitHeight(80);
			imageView.setFitWidth(100);
			imageView.setTranslateX(120 * (i % 2) + 10);
			//noinspection IntegerDivisionInFloatingPointContext
			imageView.setTranslateY(140 * (i / 2) + 10);
			b.setId(i + "");
			b.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					renameBut.setDisable(false);
					renameField.setDisable(false);
					deleteBut.setDisable(false);
					viewBut.setDisable(false);
					if (selected != null) {
						selected.setDisable(false);
						selected.setText("Open");
					}
					b.setDisable(true);
					b.setText("Selected");
					selectedI = Integer.parseInt(b.getId());
					selected = b;
				}
			});
			//noinspection IntegerDivisionInFloatingPointContext
			Rectangle r = new Rectangle(120 * (i % 2) + 5, 140 * (i / 2) + 5, 120, 140);
			r.setFill(Color.TRANSPARENT);
			r.setStroke(Color.BLACK);
			a1.getChildren().add(r);
			a1.getChildren().add(b);
			a1.getChildren().add(imageView);
		}
		viewBut.setOnAction(e -> new AlbumController(user.getAlbums().get(selectedI)).show());
		createBut.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				boolean add = true;
				for (Album albums : user.getAlbums()) {
					if (createField.getText().equals(albums.getName()) || createField.getText() == null) {
						createField.setText("Error, try another name");
						add = false;
						break;
					}
				}
				if (add) {
					Album temp = new Album(createField.getText(), new ArrayList<com.Group4.AutoGrader.Model.Image>());
					temp.setUser(user);
					user.getAlbums().add(temp);
					AutoGrader.save();
					new HomeController(user).show();
				}
			}
		});
		renameBut.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
			    boolean rename = true;
				if (selectedI > -1) {
                    for (Album a : user.getAlbums())
                        if (renameField.getText().equals(a.getName()) || renameField.getText() == null) {
                            renameField.setText("Error, try another name");
                            rename = false;
                            break;
                        }
                    if(rename){
                        user.getAlbums().get(selectedI).setName(renameField.getText());
                        AutoGrader.save();
                        new HomeController(user).show();
                    }
				}
			}
		});
		deleteBut.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				if (selectedI > -1) {
					user.getAlbums().remove(selectedI);
					AutoGrader.save();
					new HomeController(user).show();
				}
			}
		});
		searchBut.setOnAction(e -> new SearchController(user).show());
		logoutBut.setOnAction(e -> new LoginController(AutoGrader.users).show());
	}

	/**
	 * Gives the fxml filename, eg: album.fxml
	 *
	 * @return "home screen.fxml"
	 */
	@Override
	public String getFileName() {
		return "home screen.fxml";
	}
}
