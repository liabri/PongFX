package org.nice.javafx.objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Paddle {
	private static final int PADDLE_WIDTH = 20;
	private static final int PADDLE_HEIGHT = 100;
	private static final double PADDLE_SLOWDOWN = 0.918;
	private static final int PADDLE_START_Y = 250;
	double x, y = 250, yVel = 0;
	boolean upAcc = false, downAcc = false;

	Rectangle rect = null;

	public Rectangle createPaddle(int player) {
		if (player == 1) {
			Rectangle rect = new Rectangle(5, PADDLE_START_Y, PADDLE_WIDTH, PADDLE_HEIGHT);
			rect.setFill(Color.WHITE);
			x = rect.getX();
			return rect;
		} else if (player == 2) {
			Rectangle rect = new Rectangle(775, PADDLE_START_Y, PADDLE_WIDTH, PADDLE_HEIGHT);
			rect.setFill(Color.WHITE);
			x = rect.getX();
			return rect;
		}
		return rect;
	}

	public void move() {
		//Acceleration -> Velocity
		if (upAcc) {
			yVel -= 5;
		} else if (downAcc) {
			yVel += 5;
		} else {
			yVel *= PADDLE_SLOWDOWN;
		}
		//Velocity -> Displacement
		y += yVel;
		//Max Velocity
		if (yVel >= 5) {
			yVel = 5;
		} else if (yVel <= -5) {
			yVel = -5;
		} else {
		}
		//Top.Bottom Walls
		if (y < 0)
			y = 0;
		if (y > 500)
			y = 500;
	}


	public void setUpAcc(boolean input) {
		upAcc = input;
	}

	public void setDownAcc(boolean input) {
		downAcc = input;
	}

	public int getY() {
		return (int) y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getX() {
		return (int) x;
	}

	public void setYVel(double yVel) {
		this.yVel = yVel;
	}
}