package com.Group4.AutoGrader.Controllers.Examples;

import com.Group4.AutoGrader.Controllers.Controller;
import com.Group4.AutoGrader.Model.Image;
import com.Group4.AutoGrader.AutoGrader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * Controller class for search DisplayImg.fxml
 *
 * @author Mason Grosset
 * @author Jared Neal
 */
public class DisplayImgController extends Controller {
	/**
	 * The button the user presses to logout
	 */
	public Button logOutBtn;
	/**
	 * The button the user presses to go back to album display
	 */
	public Button backBtn;
	/**
	 * Goes left on the slideshow
	 */
	public Button leftBtn;
	/**
	 * Goes right on the slideshow
	 */
	public Button rightBtn;
	/**
	 * The ImageView being displayed
	 */
	public ImageView img;
	/**
	 * The text field containing the image's caption
	 */
	public Text captionText;
	/**
	 * The text field containing the image's tags
	 */
	public Text tagText;
	/**
	 * The text field containing the image's last modified date
	 */
	public Text timeText;

	/**
	 * Sets up controller, and fills in fields based on the image
	 *
	 * @param image The image being displayed
	 */
	public DisplayImgController(Image image) {
		super();
		img.setImage(new javafx.scene.image.Image("file:" + image.getLocation()));
		captionText.setText(image.getDescription());
		StringBuilder tagStr = new StringBuilder();
		for (Map.Entry<String, String> e : image.getTags())
			tagStr.append(" (").append(e.getKey()).append(": ").append(e.getValue()).append(")");
		tagText.setText("Tags:" + tagStr);
		timeText.setText(new SimpleDateFormat("MM/dd/yyyy hh:mm a").format(image.getDate()));
		logOutBtn.setOnAction(e -> new LoginController(AutoGrader.users).show());
		backBtn.setOnAction(e -> new AlbumController(image.getAlbum()).show());
		int i;
		for (i = 0; i < image.getAlbum().images.size(); i++)
			if (image.getAlbum().images.get(i).equals(image)) break;
		int finalI = i;
		if (i == 0) leftBtn.setVisible(false);
		else leftBtn.setOnAction(e -> new DisplayImgController(image.getAlbum().images.get(finalI - 1)).show());
		if (i >= image.getAlbum().images.size()-1) rightBtn.setVisible(false);
		else rightBtn.setOnAction(e -> new DisplayImgController(image.getAlbum().images.get(finalI + 1)).show());
	}

	/**
	 * Gives the xml file name, eg: album.fxml
	 *
	 * @return "DisplayImg.fxml"
	 */
	@Override
	public String getFileName() {
		return "DisplayImg.fxml";
	}
}
