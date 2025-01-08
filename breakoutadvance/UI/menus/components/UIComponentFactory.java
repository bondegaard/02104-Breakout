package breakoutadvance.UI.menus.components;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class UIComponentFactory {

    public static Text createText(String content, int size, Color color, Font font) {
        Text text = new Text(content);
        text.setFont(font != null ? font : Font.font("Arial", size));
        text.setFill(color != null ? color : Color.WHITE);
        return text;
    }

    public static Label createLabel(String content, int size, Font font) {
        Label label = new Label(content);
        label.setFont(font != null ? font : Font.font("Arial", size));
        label.setTextFill(Color.WHITE);
        return label;
    }

    public static CheckBox createCheckBox(String label, boolean isSelected, Font font) {
        CheckBox checkBox = new CheckBox(label);
        checkBox.setSelected(isSelected);
        checkBox.setFont(font != null ? font : Font.font("Arial", 20));
        checkBox.setStyle("-fx-padding: 10;");
        return checkBox;
    }

    public static Slider createSlider(double min, double max, double value, double width) {
        Slider slider = new Slider(min, max, value);
        slider.setPrefWidth(width);
        return slider;
    }

    public static HBox createHBox(int spacing, javafx.scene.Node... children) {
        HBox hbox = new HBox(spacing);
        hbox.getChildren().addAll(children);
        return hbox;
    }
}
