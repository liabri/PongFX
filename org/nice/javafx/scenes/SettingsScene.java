package org.nice.javafx.scenes;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

import java.text.DecimalFormat;

public class SettingsScene extends Pane {
	DecimalFormat df2 = new DecimalFormat("#.00");
	private boolean stop = false, start = false;

	Polygon select = new Polygon();

	private int maxrounds = 10;
	private String maxroundstr = Integer.toString(maxrounds);
	private Text maxroundsnum = new Text(470, 260, maxroundstr);

	private double ballspeedmul = 1.1;
	private String ballspeedstr = (df2.format(ballspeedmul));
	private Text ballspeednum = new Text(470, 320, ballspeedstr);

	private Text settings = new Text(95, 130, "SETTINGS");
	private Text back = new Text(340, 530, "BACK");

	private Text maxroundst = new Text(150, 260, "MAX ROUNDS");
	private Text ballspeedmultiplier = new Text(150, 320, "BALL SPEED");
	private Polygon up = buildTriangle();
	private Polygon down = buildTriangle();

	private Runnable onStart = () -> {
	};

	public void setOnStart(Runnable onStart) {
		this.onStart = onStart;
	}

	public SettingsScene() {
		getStylesheets().add("resources/styles.css");

		settings.getStyleClass().add("title");
		maxroundst.getStyleClass().add("settings");
		maxroundst.setStyle("-fx-font-size: 55px;");
		maxroundsnum.getStyleClass().add("settings");
		ballspeedmultiplier.getStyleClass().add("settings");
		ballspeednum.getStyleClass().add("settings");
		back.getStyleClass().add("settings");

		select.getPoints().addAll(new Double[]{5.0, 0.0, 5.0, 60.0, 50.0, 30.0});
		select.setFill(Color.rgb(200, 200, 200, 0));
		select.relocate(200, 215);
		select.setScaleX(.5);
		select.setScaleY(.5);
		select.setScaleZ(.5);

		highOrLow();
		down.relocate(450, 237);
		down.getTransforms().add(new Rotate(180));

		getChildren().add(settings);
		getChildren().add(maxroundst);
		getChildren().add(maxroundsnum);
		getChildren().add(ballspeedmultiplier);
		getChildren().add(ballspeednum);
		getChildren().add(up);
		getChildren().add(down);
		getChildren().add(back);
		getChildren().add(select);

		moveSelectOnKeyPress(select);
		runAnimation();
	}

	public void highOrLow() {
		if (maxrounds >= 10) {
			up.relocate(520, 213);
		} else {
			up.relocate(493, 213);
		}
	}

	public void runAnimation() {
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				limits();
				maxroundsnum.setText(Integer.toString(maxrounds));
				ballspeednum.setText(df2.format(ballspeedmul));
				if (stop == true) {
					this.stop();
				}
			}
		};
		if (start == true) {
			timer.start();
		}
	}

	public Polygon buildTriangle() {
		Polygon triangle2 = new Polygon();
		triangle2.getPoints().addAll(new Double[]{5.0, 0.0, 5.0, 60.0, 50.0, 30.0});
		triangle2.setFill(Color.WHITE);
		triangle2.setScaleX(.4);
		triangle2.setScaleY(.4);
		triangle2.setScaleZ(.4);
		return triangle2;
	}

	public void limits() {
		if (maxrounds >= 99) {
			maxrounds = 99;
		} else if (maxrounds <= 1) {
			maxrounds = 1;
		}
		if (ballspeedmul <= 1) {
			ballspeedmul = 1;
		} else if (ballspeedmul >= 2.50) {
			ballspeedmul = 2.50;
		}
	}

	public void defaultSet() {
		select.relocate(200, 215);
		up.relocate(520, 213);
		down.relocate(450, 237);
		back.setStyle("-fx-font-size: 50px;");
		maxroundst.setStyle("-fx-font-size: 55px;");
	}

	public void downKey() {
		if (select.getLayoutY() == 215) {
			ballspeedmultiplier.setStyle("-fx-font-size: 55px;");
			maxroundst.setStyle("-fx-font-size: 50px;");
			select.relocate(200, 275);
			up.relocate(565, 273);
			down.relocate(450, 297);
		} else {
			up.relocate(9999, 999273);
			down.relocate(9999, 2999997);
			select.relocate(200, 485);
			ballspeedmultiplier.setStyle("-fx-font-size: 50px;");
			back.setStyle("-fx-font-size: 55;");
		}
	}

	public void upKey() {
		if (select.getLayoutY() == 275) {
			maxroundst.setStyle("-fx-font-size: 55px;");
			ballspeedmultiplier.setStyle("-fx-font-size: 50px;");
			select.relocate(200, 215);
			down.relocate(450, 237);
			if (maxrounds >= 10) {
				up.relocate(523, 213);
			} else {
				up.relocate(493, 213);
			}
		} else if (select.getLayoutY() == 485) {
			ballspeedmultiplier.setStyle("-fx-font-size: 55px;");
			maxroundst.setStyle("-fx-font-size: 50px;");
			back.setStyle("-fx-font-size: 50px;");
			up.relocate(565, 273);
			down.relocate(450, 297);
			select.relocate(200, 275);
		}
	}

	public void rightKey() {
		if (select.getLayoutY() == 215) {
			maxrounds++;
			if (maxrounds >= 10) {
				up.relocate(523, 213);
			} else {
				up.relocate(493, 213);
			}
		} else if (select.getLayoutY() == 275) {
			ballspeedmul += 0.05;
		}
	}

	public void leftKey() {
		if (select.getLayoutY() == 215) {
			maxrounds--;
			if (maxrounds >= 10) {
				up.relocate(523, 213);
			} else {
				up.relocate(493, 213);
			}
		} else if (select.getLayoutY() == 275) {
			ballspeedmul -= 0.05;
		}
	}

	public void moveSelectOnKeyPress(final Polygon select) {
		setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
					case UP:
						upKey();
						break;
					case DOWN:
						downKey();
						break;
					case RIGHT:
						rightKey();
						break;
					case LEFT:
						leftKey();
						break;
					case W:
						upKey();
						break;
					case S:
						downKey();
						break;
					case D:
						rightKey();
						break;
					case A:
						leftKey();
						break;
					case ENTER:
						if (select.getLayoutY() == 485) {
							onStart.run();
							stop = true;
						}
						break;
				}
			}
		});
	}

	public void setStartStop(boolean start, boolean stop) {
		this.start = start;
		this.stop = stop;
	}

	public int getMaxRounds() {
		return maxrounds;
	}

	public double getBallSpeed() {
		return ballspeedmul;
	}
}
