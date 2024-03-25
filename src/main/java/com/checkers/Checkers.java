package com.checkers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static com.checkers.Constants.*;

public class Checkers extends Application {
    @Override
    public void start(Stage primaryStage){
        StackPane root = new StackPane();
        Board board = new Board();
        root.getChildren().add(board);

        Scene scene = new Scene(root, Color.valueOf(BACKGROUND_COLOR));
        primaryStage.setScene(scene);

        scene.setOnKeyPressed((e) -> keyHandler(e, primaryStage));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private void keyHandler(KeyEvent e, Stage primaryStage) {
        switch (e.getCode()) {
            case F -> changeFullscreen(primaryStage);
            case Q -> System.exit(0);
        }
    }

    private void changeFullscreen(Stage primaryStage) {
        primaryStage.setFullScreen(!primaryStage.isFullScreen());
    }
}