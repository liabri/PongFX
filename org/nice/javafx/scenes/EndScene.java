package org.nice.javafx.scenes;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

public class EndScene extends Pane {

	Text winner = new Text(265, 280, "PLAYER  " + "playerID" + "  WINS!");
	Text game = new Text(255, 130, "GAME");
	Text over = new Text(255, 220, "OVER");
	Text mainmenu = new Text(250, 450, "MAIN MENU");
	Text exit = new Text(330, 550, "EXIT");

	private Runnable onStart = () -> {
	};

	public EndScene() {
		getStylesheets().add("resources/styles.css");

		game.getStyleClass().add("title");
		over.getStyleClass().add("title");
		mainmenu.getStyleClass().add("select");
		mainmenu.setStyle("-fx-font-size: 75px;");
		exit.getStyleClass().add("select");

		Polygon triangle = new Polygon();
		triangle.getPoints().addAll(new Double[]{5.0, 0.0, 5.0, 60.0, 50.0, 30.0});
		triangle.setFill(Color.WHITE);
		triangle.relocate(200, 400);
		triangle.setScaleX(.5);
		triangle.setScaleY(.5);
		triangle.setScaleZ(.5);

		getChildren().add(game);
		getChildren().add(over);
		getChildren().add(exit);
		getChildren().add(mainmenu);
		getChildren().add(triangle);

		moveTriangleOnKeyPress(triangle);
	}


	public void moveTriangleOnKeyPress(final Polygon triangle) {
		setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
					case UP:
						triangle.relocate(200, 400);
						mainmenu.setStyle("-fx-font-size: 75px;");
						exit.setStyle("-fx-font-size: 70px;");
						break;
					case DOWN:
						triangle.relocate(200, 500);
						mainmenu.setStyle("-fx-font-size: 70px;");
						exit.setStyle("-fx-font-size: 75px;");
						break;
					case W:
						triangle.relocate(200, 400);
						break;
					case S:
						triangle.relocate(200, 500);
						break;
					case ENTER:
						if (triangle.getLayoutY() == 500) {
							Platform.exit();
						} else {
							onStart.run();
						}
						break;
				}
			}
		});
	}

	public void updateWinner(int playerId) {
		getChildren().remove(winner);
		winner.setText("PLAYER  " + playerId + "  WINS!");
		winner.getStyleClass().add("winner");
		getChildren().remove(winner);
		getChildren().add(winner);
	}

	public void setOnStart(Runnable onStart) {
		this.onStart = onStart;
	}
}
