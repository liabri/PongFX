package org.nice.javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.nice.javafx.scenes.EndScene;
import org.nice.javafx.scenes.GameScene;
import org.nice.javafx.scenes.SettingsScene;
import org.nice.javafx.scenes.WelcomeScene;

public class Main extends Application {
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Font.loadFont(ClassLoader.getSystemResource("resources/ARCADECLASSIC.TTF").toExternalForm(), 10);

		Pane pane = new Pane();
		WelcomeScene welcomeScene = new WelcomeScene();
		SettingsScene settingsScene = new SettingsScene();
		GameScene gameScene = new GameScene();
		EndScene endScene = new EndScene();

		pane.getChildren().add(welcomeScene);

		welcomeScene.setOnStart(() ->
		{
			pane.getChildren().clear();
			gameScene.defaultGame();
			gameScene.setStart(true);
			gameScene.initializeGame();
			gameScene.setMaxRounds(settingsScene.getMaxRounds());
			gameScene.setBallMultiplier(settingsScene.getBallSpeed());
			pane.getChildren().add(gameScene);
			gameScene.requestFocus();
		});

		welcomeScene.setOnSettings(() ->
		{
			settingsScene.setStartStop(true, false);
			settingsScene.defaultSet();
			pane.getChildren().clear();
			settingsScene.highOrLow();
			settingsScene.runAnimation();
			pane.getChildren().add(settingsScene);
			settingsScene.requestFocus();
		});

		settingsScene.setOnStart(() ->
		{
			pane.getChildren().clear();
			pane.getChildren().add(welcomeScene);
			welcomeScene.requestFocus();
		});

		gameScene.setOnStart(() ->
		{
			pane.getChildren().clear();
			endScene.updateWinner(gameScene.getPlayerID());
			pane.getChildren().add(endScene);
			endScene.requestFocus();
		});

		endScene.setOnStart(() ->
		{
			pane.getChildren().clear();
			pane.getChildren().add(welcomeScene);
			welcomeScene.requestFocus();
		});

		Scene scene = new Scene(pane, WIDTH, HEIGHT, Color.BLACK);

		stage.setTitle("PongFX");
		stage.setScene(scene);
		welcomeScene.requestFocus();
		stage.show();
	}
}