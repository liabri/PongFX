package org.nice.javafx.scenes;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.nice.javafx.objects.Ball;
import org.nice.javafx.objects.Paddle;

public class GameScene extends Pane {
	private Paddle p1 = new Paddle();
	private Paddle p2 = new Paddle();
	private Ball b1 = new Ball();
	private EndScene e1 = new EndScene();
	private SettingsScene s1 = new SettingsScene();

	private Rectangle paddle1 = p1.createPaddle(1);
	private Rectangle paddle2 = p2.createPaddle(2);
	private Circle ball = b1.createCircle();
	private Line mid = new Line(400, 0, 400, 700);
	private Text scorel = new Text(250, 130, "0");
	private Text scorer = new Text(507, 130, "0");

	private int sl = 0, sr = 0, playerId, maxrounds;
	private double ballspeedmul;
	private boolean stop, start;

	private Runnable onStart = () -> {
	};

	public void setOnStart(Runnable onStart) {
		this.onStart = onStart;
	}

	public GameScene() {
		if (start) {
			initializeGame();
		}
	}

	public void initializeGame() {
		getStylesheets().add("resources/styles.css");
		scorel.getStyleClass().add("score");
		scorer.getStyleClass().add("score");

		mid.getStrokeDashArray().addAll(25d, 10d);
		mid.setStroke(Color.WHITE);

		getChildren().addAll(paddle1, paddle2, ball, mid, scorel, scorer);
		runGame();
	}

	private void runGame() {
		moveRectOnKeyPress();
		moveRectOnKeyRelease();

		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				createCollision();
				roundOver();

				p1.move();
				p2.move();
				b1.move();
				paddle1.setY(p1.getY());
				paddle2.setY(p2.getY());
				ball.setCenterY(b1.getY());
				ball.setCenterX(b1.getX());

				gameOver();
				if (stop) {
					defaultGame();
					this.stop();
				}
			}
		};
		if (start) {
			timer.start();
		}
	}

	public void defaultGame() {
		getChildren().clear();
		p1.setUpAcc(false);
		p1.setDownAcc(false);
		p2.setUpAcc(false);
		p2.setDownAcc(false);
		p1.setYVel(0);
		p2.setYVel(0);
		p1.setY(250);
		p2.setY(250);
		sl = 0;
		sr = 0;
		scorel.setText(Integer.toString(0));
		scorer.setText(Integer.toString(0));
		stop = false;
	}

	private void updateGame() {
		updateScore();
		Thread thread = new Thread(() -> {
			try {
				Thread.sleep(1500);
				Platform.runLater(() -> {
					b1.ballStats();
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		thread.start();
	}

	public void updateScore() {
		getChildren().removeAll(scorel);
		getChildren().removeAll(scorer);
		scorel.setText(Integer.toString(sl));
		scorer.setText(Integer.toString(sr));
		getChildren().addAll(scorel, scorer);
	}

	public void gameOver() {
		if (sr == maxrounds) {
			stop = true;
			playerId = 2;
			onStart.run();
		} else if (sl == maxrounds) {
			stop = true;
			playerId = 1;
			onStart.run();
		}
	}

	private void roundOver() {
		if ((ball.intersects(5, 0, 5, 700))) {
			b1.setX(400);
			b1.setY(-50);
			sr++;
			updateGame();
		} else if ((ball.intersects(795, 0, 795, 700))) {
			b1.setX(400);
			b1.setY(-50);
			sl++;
			updateGame();
		}
	}

	private void createCollision() {
		if ((ball.intersects(paddle1.getBoundsInParent())) | (ball.intersects(paddle2.getBoundsInParent()))) {
			b1.setXVel(-b1.getXVel() * ballspeedmul);
		}
	}

	private void moveRectOnKeyPress() {
		setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
					case W:
						p1.setUpAcc(true);
						break;
					case S:
						p1.setDownAcc(true);
						break;
					case UP:
						p2.setUpAcc(true);
						break;
					case DOWN:
						p2.setDownAcc(true);
						break;
					case ESCAPE:
						Platform.exit();
						break;
				}
			}
		});
	}

	private void moveRectOnKeyRelease() {
		setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
					case W:
						p1.setUpAcc(false);
						break;
					case S:
						p1.setDownAcc(false);
						break;
					case UP:
						p2.setUpAcc(false);
						break;
					case DOWN:
						p2.setDownAcc(false);
						break;
				}
			}
		});
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public int getPlayerID() {
		return playerId;
	}

	public void setMaxRounds(int maxrounds) {
		this.maxrounds = maxrounds;
	}

	public void setBallMultiplier(double ballspeedmul) {
		this.ballspeedmul = ballspeedmul;
	}
}
