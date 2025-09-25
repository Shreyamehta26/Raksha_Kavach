package com.raksha_kavach.utilities;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.control.Label;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Snackbar {
    public static void show(Stage stage, String message) {
        Label c2w_ai_label = new Label(message);
        c2w_ai_label.setStyle(
                "-fx-background-color: #323232; -fx-text-fill:white; -fx-padding: 10 20 10 20; -fx-background-radius: 20;");
        c2w_ai_label.setOpacity(0);
        Popup c2w_ai_popup = new Popup();
        c2w_ai_popup.getContent().add(c2w_ai_label);
        c2w_ai_popup.setAutoFix(true);
        c2w_ai_popup.setAutoHide(true);
        c2w_ai_popup.setHideOnEscape(true);
        // Calculate position: bottom center of stage
        double c2w_ai_x = stage.getX() + (stage.getWidth() / 2) - 100;
        double c2w_ai_y = stage.getY() + stage.getHeight() - 100;
        c2w_ai_popup.show(stage, c2w_ai_x, c2w_ai_y);
        // Fade in
        FadeTransition c2w_ai_fadeIn = new FadeTransition(Duration.millis(300),
                c2w_ai_label);
        c2w_ai_fadeIn.setFromValue(0);
        c2w_ai_fadeIn.setToValue(1);
        // Wait
        PauseTransition c2w_ai_wait = new PauseTransition(Duration.seconds(2));
        // Fade out
        FadeTransition c2w_ai_fadeOut = new FadeTransition(Duration.millis(300), c2w_ai_label);
        c2w_ai_fadeOut.setFromValue(1);
        c2w_ai_fadeOut.setToValue(0);
        c2w_ai_fadeOut.setOnFinished(e -> c2w_ai_popup.hide());
        // Play all
        SequentialTransition c2w_ai_seq = new SequentialTransition(c2w_ai_fadeIn, c2w_ai_wait, c2w_ai_fadeOut);
        c2w_ai_seq.play();
    }
}