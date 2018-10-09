package bomberman.version2;

import bomberman.OutOfBoundsException;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Game extends Application {
    private static final String TITLE = "Bomberman";
    private final int width = 21;
    private final int height = 15;
    private final int cellSize = 24;
    private final Board board;

    public Game() throws OutOfBoundsException {
        this.board = new Board(width, height);
    }

    private Cell buildWallBlock(int x, int y) {
        Cell rect = new WallBlock(x, y, board);
        rect.setX(x * cellSize);
        rect.setY(y * cellSize);
        rect.setHeight(cellSize);
        rect.setWidth(cellSize);
        rect.setFill(Color.LIGHTGRAY);
        rect.setStroke(Color.BLACK);
        return rect;
    }

    private Group buildWalls() {
        Group panel = new Group();
        // border
        for (int y = 0; y != this.height; y++) {
            addWallBlock(panel, 0, y);
            addWallBlock(panel, width - 1, y);
        }
        for (int x = 1; x != this.width - 1; x++) {
            addWallBlock(panel, x, 0);
            addWallBlock(panel, x, height - 1);
        }
        // grid
        int cycleLimX = (width % 4 != 0 ? width / 2 + 1 : width / 2);
        int cycleLimY = (height % 4 != 0 ? height / 2 + 1 : height / 2);
        for (int y = 2; y < cycleLimY; y += 2) {
            for (int x = 2; x < cycleLimX; x += 2) {
                addWallBlock(panel, x, y);
                addWallBlock(panel, x, height - y - 1);
                addWallBlock(panel, width - 1 - x, y);
                addWallBlock(panel, width - 1 - x, height - y - 1);
            }
        }
        return panel;
    }

    private void addWallBlock(Group panel, int x, int y) {
        Cell cell = this.buildWallBlock(x, y);
        try {
            this.board.init(cell);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        panel.getChildren().add(cell);
    }

    @Override
    public void start(Stage stage) {
        BorderPane border = new BorderPane();
        HBox control = new HBox();
        control.setPrefHeight(40);
        control.setSpacing(10.0);
        control.setAlignment(Pos.BASELINE_CENTER);
        Button start = new Button("Начать");
        start.setOnMouseClicked(event -> border.setCenter(this.buildWalls()));
        control.getChildren().addAll(start);
        border.setBottom(control);
        border.setCenter(this.buildWalls());
        stage.setScene(new Scene(border, 600, 400));
        stage.setTitle(TITLE);
        stage.setResizable(false);
        stage.show();
    }


}
