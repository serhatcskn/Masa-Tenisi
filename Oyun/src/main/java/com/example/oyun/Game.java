package com.example.oyun;


import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game extends Application {

    private static final int genislik = 1280;
    private static final int yukseklik = 720;
    private static final int tahta_Yuksekligi = 200;
    private static final int tahta_Genisligi = 15;
    private static final double BALL_R = 15;
    private int topY_hareket = 3;
    private int topX_hareket = 3;
    private int rastgele=0;
    private double oyuncu1Y = yukseklik / 2;
    private double oyuncu2Y = yukseklik / 2;
    private double topX = genislik / 2;
    private double topY = yukseklik / 2;
    private int skor1 = 0;
    private int skor2 = 0;
    private boolean gameStarted;
    private int oyuncu1X = 0;
    private double oyuncu2X = genislik - tahta_Genisligi;

    public void start(Stage stage) throws Exception {
        stage.setTitle("YAKALADIM");
        Canvas canvas = new Canvas(genislik, yukseklik);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
        tl.setCycleCount(Timeline.INDEFINITE);

        canvas.setOnMouseMoved(e ->  oyuncu1Y = e.getY());
        canvas.setOnMouseClicked(e ->  gameStarted = true);
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.show();
        tl.play();
    }

    private void run(GraphicsContext gc) {
        gc.setFill(Color.BLUEVIOLET);
        gc.fillRect(0, 0, genislik, yukseklik);

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(25));

        if(gameStarted) {
            topX += topX_hareket;
            topY += topY_hareket;

            if(topX < genislik - genislik / 20) {
                oyuncu2Y = topY - tahta_Yuksekligi / 2;
            }  else {
                if(topY > oyuncu2Y + tahta_Yuksekligi / 2){
                    oyuncu2Y += 1;
                }
                else {
                    oyuncu2Y -= 1;
                }
            }
            gc.fillOval(topX, topY, BALL_R, BALL_R);

        } else {
            gc.setStroke(Color.WHITE);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.strokeText("Baslamak icin TIKLA", genislik / 2, yukseklik / 2);

            topX = genislik / 2;
            topY = yukseklik / 2;

            topX_hareket = 3;
            topY_hareket = 3;
        }

        if(topY > yukseklik || topY < 0) {
            topY_hareket *= -1;
        }

        if(topX < oyuncu1X - tahta_Genisligi) {
            skor2++;
            gameStarted = false;
        }
        if(topX > oyuncu2X + tahta_Genisligi) {
            skor1++;
            gameStarted = false;
        }

        if( ((topX + BALL_R > oyuncu2X) && topY >= oyuncu2Y && topY <= oyuncu2Y + tahta_Yuksekligi) ||
                ((topX < oyuncu1X + tahta_Genisligi) && topY >= oyuncu1Y && topY <= oyuncu1Y + tahta_Yuksekligi)) {
            topY_hareket += 1 * Math.signum(topY_hareket);
            topX_hareket += 1 * Math.signum(topX_hareket);

            rastgele=new Random().nextInt(2);
            if(rastgele==0) {
                topX_hareket *= -1;
                topY_hareket *= -1;
            }
            else{
                topX_hareket *= -1;
            }
        }

        gc.fillText(skor1 + "\t\t\t\t\t\t\t\t" + skor2, genislik / 2, 100);
        gc.fillRect(oyuncu2X, oyuncu2Y, tahta_Genisligi, tahta_Yuksekligi);
        gc.fillRect(oyuncu1X, oyuncu1Y, tahta_Genisligi, tahta_Yuksekligi);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
