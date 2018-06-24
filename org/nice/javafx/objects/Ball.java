package org.nice.javafx.objects;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

public class Ball {
	Random random = new Random();
	double xVel, yVel, x, y;

	public Ball() {
		ballStats();
	}

	public void ballStats() {
		double xvrandom = (random.nextDouble() + 1.5) + 2;
		boolean xvpon = (random.nextBoolean());
		if (xvpon) {
			xVel = -xvrandom;
		} else {
			xVel = xvrandom;
		}

		double yvrandom = (random.nextDouble() + 1.5) + 2;
		boolean yvpon = (random.nextBoolean());
		if (yvpon) {
			yVel = -yvrandom;
		} else {
			yVel = yvrandom;
		}

		int ypon = (random.nextInt(500 + 1 - 100) + 100);
		y = ypon;

		x = 400;
	}

	public Circle createCircle() {
		Circle ball = new Circle(400, 300, 10, Color.WHITE);
		ball.setFill(javafx.scene.paint.Color.WHITE);
		return ball;
	}

	public void move() {
		x += xVel;
		y += yVel;

		if (y < 10)
			yVel = -yVel;
		else if (y > 590)
			yVel = -yVel;
	}

	public int getX() {
		return (int) x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return (int) y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getXVel() {
		return xVel;
	}

	public double setXVel(double xVel) {
		this.xVel = xVel;
		return xVel;
	}
}

