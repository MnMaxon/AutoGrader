package com.Group4.AutoGrader.Controllers.Examples;

import com.Group4.AutoGrader.Controllers.Controller;
import com.Group4.AutoGrader.Model.Album;
import com.Group4.AutoGrader.AutoGrader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

/**
 * Controller class for Album.fxml
 *
 * @author Mason Grosset
 * @author Jared Neal
 */
public class AlbumController extends Controller {
	/**
	 * The button of the currently selected image
	 */
	private Button selected = null;
	/**
	 * The currently selected album to copy/move to
	 */
	private Album selectedAlbum = null;
	/**
	 * The button of the currently selected album
	 */
	private Button selectedAlbumBtn = null;
	/**
	 * The index of the currently selected image
	 */
	int selectedI = -1;
	/**
	 * AnchorPane containing all of the images
	 */
	public AnchorPane a1;
	/**
	 * The button the user presses to go back to the album select screen
	 */
	public Button backBtn;
	/**
	 * The button the user presses to logout
	 */
	public Button logOutBtn;
	/**
	 * The text containing the albumName
	 */
	public Text nameTxt;
	/**
	 * The button that lets you add a picture
	 */
	public Button addBtn;
	/**
	 * The button that lets you delete a picture
	 */
	public Button deleteBtn;
	/**
	 * The button that lets you view a picture
	 */
	public Button displayBtn;
	/**
	 * The button that lets move a picture to another album
	 */
	public Button moveBtn;
	/**
	 * The button that lets copy a picture to another album
	 */
	public Button copyBtn;
	/**
	 * The button that lets you add a tag to a picture
	 */
	public Button addTagBtn;
	/**
	 * The button that lets you add a caption to a picture
	 */
	public Button captionBtn;
	/**
	 * The text field to edit the picture's caption
	 */
	public TextField captionField;
	/**
	 * The anchor containing the image's tags
	 */
	public AnchorPane tagAnchor;
	/**
	 * The anchor containing all of the user's albums
	 */
	public AnchorPane albumAnchor;
	/**
	 * The text field where the user inputs the new tag
	 */
	public TextField tagField;
	/**
	 * The text field where the user inputs the tag value
	 */
	public TextField valueField;
	/**
	 * The button that creates the new tag
	 */
	public Button createTagBtn;
	/**
	 * The box that contains all of the user's tags
	 */
	public ComboBox tagBox;
	/**
	 * Box that defines if a tag can have one or multiple values
	 */
	public ComboBox typeBox;

	/**
	 * Initializes the controller, sets up JavaFX for the selected album
	 *
	 * @param album The album to be displayed
	 */
	public AlbumController(Album album) {
		this(album, null);
	}

	/**
	 * Initializes the controller, sets up JavaFX for the selected album, with an image selected
	 *
	 * @param album  The album to be displayed
	 * @param curImg The currently selected image
	 */
	@SuppressWarnings("unchecked")
	public AlbumController(Album album, com.Group4.AutoGrader.Model.Image curImg) {
		deleteBtn.setDisable(true);
		displayBtn.setDisable(true);
		captionBtn.setDisable(true);
		captionField.setDisable(true);
		moveBtn.setDisable(true);
		copyBtn.setDisable(true);
		addTagBtn.setDisable(true);
		valueField.setDisable(true);
		tagBox.setDisable(true);

		for (String s : album.getUser().multiTags) tagBox.getItems().add(s);
		for (String s : album.getUser().singleTags) tagBox.getItems().add(s);
		typeBox.getItems().addAll("Single Value", "Multiple Values");

		//noinspection IntegerDivisionInFloatingPointContext
		a1.setMinHeight(140 * ((album.images.size() + 2) / 3));
		for (int i = 0; i < album.images.size(); i++) {
			final com.Group4.AutoGrader.Model.Image image = album.images.get(i);
			Button b = new Button("Select");
			b.setTranslateX(120 * (i % 3) + 32);
			//noinspection IntegerDivisionInFloatingPointContext
			b.setTranslateY(140 * (i / 3) + 100);
			ImageView imageView = new ImageView();
			imageView.setImage(new Image("file:" + album.images.get(i).getLocation()));
			imageView.setPreserveRatio(true);
			imageView.setFitHeight(80);
			imageView.setFitWidth(100);
			imageView.setTranslateX(120 * (i % 3) + 10);
			//noinspection IntegerDivisionInFloatingPointContext
			imageView.setTranslateY(140 * (i / 3) + 10);
			b.setId(i + "");
			final int finalI = i;
			b.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					moveBtn.setDisable(true);
					copyBtn.setDisable(true);
					addTagBtn.setDisable(false);
					valueField.setDisable(false);
					tagBox.setDisable(false);
					if (selected != null) {
						selected.setDisable(false);
						selected.setText("Select");
					}
					b.setDisable(true);
					b.setText("Selected");
					selectedI = finalI;
					selected = b;

					tagAnchor.getChildren().clear();
					int j = 0;
					for (Map.Entry<String, String> e : image.getTags()) {
						Text txt = new Text();
						txt.setX(5);
						txt.setY(18 * (j + 1));
						txt.setText(e.getKey() + ": " + e.getValue());
						Button b = new Button();
						b.setText("X");
						b.setTranslateY(18 * (j + .2));
						b.setTranslateX(150);
						b.setScaleY(.7);
						b.setScaleX(.7);

						b.setOnAction(new EventHandler<ActionEvent>() {
							public void handle(ActionEvent ev) {
								image.getTags().remove(e);
								new AlbumController(album, image).show();
								AutoGrader.save();
							}
						});

						tagAnchor.getChildren().add(txt);
						tagAnchor.getChildren().add(b);
						j++;
					}
					tagAnchor.setMinHeight(18 * (j + .5));
					tagAnchor.setMaxWidth(10);

					albumAnchor.getChildren().clear();
					j = 0;
					for (Album a : image.getAlbum().getUser().getAlbums()) {
						Text txt = new Text();
						txt.setX(5);
						txt.setY(18 * (j + 1));
						txt.setText(a.getName());
						Button b = new Button();
						b.setText("   ");
						b.setTranslateY(18 * (j + .2));
						b.setTranslateX(100);
						b.setScaleY(.7);
						b.setScaleX(.7);

						b.setOnAction(new EventHandler<ActionEvent>() {
							public void handle(ActionEvent ev) {
								if (selectedAlbumBtn != null) {
									selectedAlbumBtn.setDisable(false);
									selectedAlbumBtn.setText("   ");
								}
								selectedAlbum = a;
								selectedAlbumBtn = b;
								b.setDisable(true);
								b.setText("O");
								moveBtn.setDisable(false);
								copyBtn.setDisable(false);
							}
						});

						albumAnchor.getChildren().add(txt);
						albumAnchor.getChildren().add(b);
						j++;
					}
					albumAnchor.setMinHeight(18 * (j + .5));
					albumAnchor.setMaxWidth(10);

					deleteBtn.setDisable(false);
					displayBtn.setDisable(false);
					captionBtn.setDisable(false);
					captionField.setDisable(false);
					captionField.setText(album.images.get(selectedI).getDescription());
				}
			});
			if (image.equals(curImg)) b.fire();
			//noinspection IntegerDivisionInFloatingPointContext
			Rectangle r = new Rectangle(120 * (i % 3) + 5, 140 * (i / 3) + 5, 120, 140);
			r.setFill(Color.TRANSPARENT);
			r.setStroke(Color.BLACK);
			a1.getChildren().add(r);
			a1.getChildren().add(b);
			a1.getChildren().add(imageView);
		}

		nameTxt.setText(album.getName());
		logOutBtn.setOnAction(e -> new LoginController(AutoGrader.users).show());
		backBtn.setOnAction(e -> new HomeController(album.getUser()).show());
		addBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open Resource File");
				File file = fileChooser.showOpenDialog(new Stage());
				if (file != null) {
					com.Group4.AutoGrader.Model.Image img = new com.Group4.AutoGrader.Model.Image(file.getAbsolutePath(), "", new ArrayList<>());
					img.setAlbum(album);
					album.images.add(img);
					AutoGrader.save();
					if (selectedI >= 0) new AlbumController(album, album.images.get(selectedI)).show();
					new AlbumController(album).show();
				}
			}
		});
		deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				album.images.remove(selectedI);
				new AlbumController(album).show();
				AutoGrader.save();
			}
		});
		moveBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				album.images.get(selectedI).setAlbum(selectedAlbum);
				selectedAlbum.images.add(album.images.get(selectedI));
				album.images.remove(selectedI);
				AutoGrader.save();
				new AlbumController(album).show();
			}
		});
		copyBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				com.Group4.AutoGrader.Model.Image old = album.images.get(selectedI);
				com.Group4.AutoGrader.Model.Image copy = new com.Group4.AutoGrader.Model.Image(old.getLocation(), old.getDescription(), new ArrayList<>(old.getTags()));
				copy.setAlbum(selectedAlbum);
				selectedAlbum.images.add(copy);
				AutoGrader.save();
				new AlbumController(album).show();
			}
		});
		createTagBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				Object type = typeBox.getSelectionModel().getSelectedItem();
				if (type == null || tagField.getText().equals("")) return;
				if (!album.getUser().singleTags.contains(tagField.getText()) && !album.getUser().multiTags.contains(tagField.getText()))
					if (type.equals("Single Value")) album.getUser().singleTags.add(tagField.getText());
					else album.getUser().multiTags.add(tagField.getText());
				AutoGrader.save();
				if (selectedI >= 0) new AlbumController(album, album.images.get(selectedI)).show();
				new AlbumController(album).show();
			}
		});
		addTagBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				Object tag = tagBox.getSelectionModel().getSelectedItem();
				if (tag == null || valueField.getText().equals("")) return;
				if (album.getUser().singleTags.contains((String) tag))
					for (Map.Entry<String, String> ent : album.images.get(selectedI).getTags())
						if (ent.getKey().equals(tag)) {
							album.images.get(selectedI).getTags().remove(ent);
							break;
						}
				album.images.get(selectedI).getTags().add(new AbstractMap.SimpleEntry<>((String) tag, valueField.getText()));
				AutoGrader.save();
				new AlbumController(album, album.images.get(selectedI)).show();
			}
		});
		captionBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				album.images.get(selectedI).setDescription(captionField.getText());
				AutoGrader.save();
			}
		});
		displayBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				new DisplayImgController(album.images.get(selectedI)).show();
			}
		});
	}

	/**
	 * Gives the xml file name, eg: album.fxml
	 *
	 * @return "Album.fxml"
	 */
	@Override
	public String getFileName() {
		return "Album.fxml";
	}
}
