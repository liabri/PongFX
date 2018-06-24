package org.nice.javafx.scenes;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

public class WelcomeScene extends Pane {
	Text title = new Text(250, 130, "PONG");
	Text play = new Text(325, 280, "PLAY");
	Text settings = new Text(255, 365, "SETTINGS");
	Text exit = new Text(330, 450, "EXIT");

	private Runnable onStart = () -> {
	};
	private Runnable onSettings = () -> {
	};

	public WelcomeScene() {
		getStylesheets().add("resources/styles.css");

		title.getStyleClass().add("title");
		play.getStyleClass().add("select");
		play.setStyle("-fx-font-size: 75px;");
		settings.getStyleClass().add("select");
		exit.getStyleClass().add("select");

		Polygon triangle = new Polygon();
		triangle.getPoints().addAll(new Double[]{5.0, 0.0, 5.0, 60.0, 50.0, 30.0});
		triangle.setFill(Color.WHITE);
		triangle.relocate(200, 225);
		triangle.setScaleX(.5);
		triangle.setScaleY(.5);
		triangle.setScaleZ(.5);

		getChildren().addAll(title, play, settings, exit, triangle);

		moveSelectOnKeyPress(triangle);
	}


	public void moveSelectOnKeyPress(final Polygon triangle) {
		setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
					case UP:
						if (triangle.getLayoutY() == 400) {
							triangle.relocate(200, 313);
							settings.setStyle("-fx-font-size: 75px;");
							exit.setStyle("-fx-font-size: 70px;");
						} else {
							triangle.relocate(200, 225);
							play.setStyle("-fx-font-size: 75px;");
							settings.setStyle("-fx-font-size: 70px;");
						}
						break;
					case DOWN:
						if (triangle.getLayoutY() == 225) {
							triangle.relocate(200, 313);
							play.setStyle("-fx-font-size: 70px;");
							settings.setStyle("-fx-font-size: 75px;");
						} else {
							triangle.relocate(200, 400);
							settings.setStyle("-fx-font-size: 70px;");
							exit.setStyle("-fx-font-size: 75px;");
						}
						break;

					case W:
						if (triangle.getLayoutY() == 400) {
							triangle.relocate(200, 313);
						} else {
							triangle.relocate(200, 225);
						}
						break;
					case S:
						if (triangle.getLayoutY() == 225) {
							triangle.relocate(200, 313);
						} else {
							triangle.relocate(200, 400);
						}
						break;

					case ENTER:
						if (triangle.getLayoutY() == 400) {
							Platform.exit();
						} else if (triangle.getLayoutY() == 225) {
							onStart.run();
						} else if (triangle.getLayoutY() == 313) {
							onSettings.run();
						}
						break;
				}
			}
		});
	}

	public void setOnStart(Runnable onStart) {
		this.onStart = onStart;
	}

	public void setOnSettings(Runnable onSettings) {
		this.onSettings = onSettings;
	}
}
