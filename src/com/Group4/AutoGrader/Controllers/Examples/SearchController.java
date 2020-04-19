package com.Group4.AutoGrader.Controllers.Examples;

import com.Group4.AutoGrader.Controllers.Controller;
import com.Group4.AutoGrader.Model.Album;
import com.Group4.AutoGrader.Model.Image;
import com.Group4.AutoGrader.Model.User;
import com.Group4.AutoGrader.AutoGrader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.*;

/**
 * Controller class for search screen.fxml
 *
 * @author Mason Grosset
 * @author Jared Neal
 */
public class SearchController extends Controller {
	/**
	 * The button that starts a search by date
	 */
	public Button dateSearch;
	/**
	 * The button that starts a search by one or two tags
	 */
	public Button tagSearch;
	/**
	 * The button that creates an album by search results
	 */
	public Button createBtn;
	/**
	 * The button the user presses to logout
	 */
	public Button logOutBtn;
	/**
	 * The button the user presses to go back to home
	 */
	public Button backBtn;
	/**
	 * The pane containing the searched images
	 */
	public AnchorPane imagePane;
	/**
	 * The starting month for the search in MM format
	 */
	public TextField monthFrom;
	/**
	 * The starting day for the search in dd format
	 */
	public TextField dayFrom;
	/**
	 * The starting year for the search in yyyy format
	 */
	public TextField yearFrom;
	/**
	 * The ending month for the search in MM format
	 */
	public TextField monthTo;
	/**
	 * The ending day for the search in dd format
	 */
	public TextField dayTo;
	/**
	 * The ending year for the search in yyyy format
	 */
	public TextField yearTo;
	/**
	 * The first searched tag
	 */
	public ComboBox tag1;
	/**
	 * The second searched tag
	 */
	public ComboBox tag2;
	/**
	 * The first searched tag value
	 */
	public TextField value1;
	/**
	 * The second searched tag value
	 */
	public TextField value2;
	/**
	 * The album name of the album created by the search
	 */
	public TextField albumName;
	/**
	 * The box containing the search options for the tags
	 */
	public ComboBox combo;

	/**
	 * Sets up controller and jfx based on a user's pictures
	 *
	 * @param user The user doing a search
	 */
	public SearchController(User user) {
		this(user, new ArrayList<>());
	}

	/**
	 * Sets up controller and jfx based on a user's pictures
	 * Shows the list of searched images
	 *
	 * @param user   The user doing a search
	 * @param images The images returned by a search
	 */
	@SuppressWarnings("unchecked")
	public SearchController(User user, ArrayList<Image> images) {
		createBtn.setDisable(images.isEmpty());
		albumName.setDisable(images.isEmpty());
		combo.getItems().add("Ignored");
		combo.getItems().add("And");
		combo.getItems().add("Or");
		combo.getSelectionModel().select(0);

		for (String s : user.multiTags) {
			tag1.getItems().add(s);
			tag2.getItems().add(s);
		}
		for (String s : user.multiTags) {
			tag1.getItems().add(s);
			tag2.getItems().add(s);
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		monthTo.setText(cal.get(Calendar.MONTH) + "");
		dayTo.setText(cal.get(Calendar.DAY_OF_MONTH) + "");
		yearTo.setText(cal.get(Calendar.YEAR) + "");

		imagePane.setMinHeight(140 * ((images.size() + 1) / 2));
		for (int i = 0; i < images.size(); i++) {
			ImageView imageView = new ImageView();
			imageView.setImage(new javafx.scene.image.Image("file:" + images.get(i).getLocation()));
			imageView.setPreserveRatio(true);
			imageView.setFitHeight(80);
			imageView.setFitWidth(100);
			imageView.setTranslateX(120 * (i % 2) + 10);
			//noinspection IntegerDivisionInFloatingPointContext
			imageView.setTranslateY(140 * (i / 2) + 10);
			//noinspection IntegerDivisionInFloatingPointContext
			Rectangle r = new Rectangle(120 * (i % 2) + 5, 140 * (i / 2) + 5, 120, 140);
			r.setFill(Color.TRANSPARENT);
			r.setStroke(Color.BLACK);
			imagePane.getChildren().add(r);
			imagePane.getChildren().add(imageView);
		}

		combo.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				boolean b = combo.getSelectionModel().getSelectedIndex() == 0;
				tag2.setDisable(b);
				value2.setDisable(b);
			}
		});
		combo.fireEvent(new ActionEvent());

		dateSearch.setOnAction(new EventHandler<ActionEvent>() {
			@SuppressWarnings("MagicConstant")
			public void handle(ActionEvent e) {
				Calendar from = Calendar.getInstance();
				Calendar to = Calendar.getInstance();
				try {
					from.set(Integer.parseInt(yearFrom.getText()), Integer.parseInt(monthFrom.getText()), Integer.parseInt(dayFrom.getText()) - 1, 23, 59, 59);
					from.set(Calendar.MILLISECOND, 999);
					to.set(Integer.parseInt(yearTo.getText()), Integer.parseInt(monthTo.getText()), Integer.parseInt(dayTo.getText()) + 1, 0, 0, 0);
					to.set(Calendar.MILLISECOND, 0);
				} catch (Exception ex) {
					return;
				}
				ArrayList<Image> images = new ArrayList<>();
				System.out.println(from.get(Calendar.MONTH) + "/" + from.get(Calendar.DAY_OF_MONTH) + " <-> " + to.get(Calendar.MONTH) + "/" + to.get(Calendar.DAY_OF_MONTH));
				System.out.println(from.get(Calendar.MILLISECOND));
				System.out.println(to.get(Calendar.MILLISECOND));
				for (Album a : user.getAlbums())
					for (Image image : a.images) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(image.getDate());
						if (cal.after(from) && cal.before(to)) images.add(image);
						System.out.println(cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.DAY_OF_MONTH));
					}
				new SearchController(user, images).show();
			}
		});
		tagSearch.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				ArrayList<Image> images = new ArrayList<>();
				Object t1 = tag1.getSelectionModel().getSelectedItem();
				Object t2 = tag1.getSelectionModel().getSelectedItem();
				int c = combo.getSelectionModel().getSelectedIndex();
				if (t1 == null || value1.getText().equals("")) return;
				if (c != 0 && (t2 == null || value2.getText().equals(""))) return;
				for (Album a : user.getAlbums())
					for (Image i : a.images) {
						boolean b1 = false;
						boolean b2 = false;
						for (Map.Entry<String, String> tag : i.getTags()) {
							if (tag.getKey().equals(t1) && tag.getValue().equals(value1.getText()))
								b1 = true;
							if (tag.getKey().equals(t2) && tag.getValue().equals(value2.getText()))
								b2 = true;
						}
						if ((b1 && (c != 1 || b2)) || (b2 && c == 2)) images.add(i);
					}
				new SearchController(user, images).show();
			}
		});
		createBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				String name = albumName.getText();
				if (name == null || name.equals("")) return;
				ArrayList<Image> copied = new ArrayList<>();
				for (Image image : images)
					copied.add(new Image(image.getLocation(), image.getDescription(), new ArrayList<>(image.getTags())));
				Album album = new Album(name, copied);
				album.setUser(user);
				user.getAlbums().add(album);
				AutoGrader.save();
				createBtn.setDisable(true);
				albumName.setDisable(true);
			}
		});
		logOutBtn.setOnAction(e -> new LoginController(AutoGrader.users).show());
		backBtn.setOnAction(e -> new HomeController(user).show());
	}

	/**
	 * Gives the xml file name, eg: album.fxml
	 *
	 * @return "search screen.fxml"
	 */
	@Override
	public String getFileName() {
		return "search screen.fxml";
	}
}
