package application;
	



import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;


public class Main extends Application {
	
	// размер пол€
	private static final int width = 800;
	private static final int height = 600;
	
	// ширина и высота ракетка
	private static final int RACKET_WIDTH = 10;
	private static final int RACKET_HEIGHT = 90;
	
	// радиус м€ча
	private static final int BALL_RAD = 30;
	
	// начальные координаты ракетки игрока
	double playerX=0;
	double playerY = height/2;
	
	// начальные координаты ракетки компа
	double compX = width - RACKET_WIDTH;
	double compY = height/2;
	
	// координаты м€ча
	double ballX = width/2;
	double ballY = height/2;
	
	// инструмент рисовани§
	GraphicsContext gc;
	
	// скорость м€ча
	double ballYSpeed = 1.5;
	double ballXSpeed = 1.5;
	int a = 0;
	int b = 0;
	int count = 0;

	
	// игровой цикл
	boolean gameStarted;
	
	private void drawTable() {
		// рисуем поле
		gc.setFill(Color.GREEN);
		gc.fillRect(0, 0, width, height);			
		// рисуем разделительную линию
		gc.setFill(Color.YELLOW);
		gc.fillRect(width/2, 0, 2, height);
		//
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(2.5);
		gc.strokeText("0 : " + count, width/2, 30);	
		//
		gc.setFill(Color.YELLOW);
		// рисуем м§ч(ballX <= (height +2.24*BALL_RAD))
		if(gameStarted) {
			//____________________________
			if (ballY+BALL_RAD == height) {
				a = 1;
			} 
			if (ballY == 0) {
				a = 0;
			} 
			if (ballX+BALL_RAD >= width-5 ) {
				b = 1;
			} 
			if ((ballY > playerY-RACKET_HEIGHT) && (ballY < playerY+RACKET_HEIGHT ) && (ballX >= 0) && (ballX <= 5)) {
				b = 0;
			}
			
			//____________________________
			if (a == 1 ) {
				ballYSpeed = -1.5;
			}
			if (a == 0 ) {
				ballYSpeed = 1.5;
			}
			if (b == 1) {
				ballXSpeed = -1.5;
			}
			if (b == 0) {
				ballXSpeed = 1.5;
			}
			
			//_____________________________
			ballX+=ballXSpeed;
			ballY+=ballYSpeed;
			
			if (ballX < -30) {
				gameStarted = false;
				ballX = width/2;
				ballY = height/2;
				ballYSpeed = 3;
				ballXSpeed = 3;
				count+=1;
				
				
				
			}
			// логика - комп отбивает м€ч
			if(ballX < width) {
				compY = ballY - RACKET_HEIGHT/2;
			}
			gc.fillOval(ballX, ballY, BALL_RAD, BALL_RAD);
		} else {
			gc.setStroke(Color.YELLOW);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setLineWidth(1);
			gc.strokeText("Click to start", width/2, height/2);			
		}
	
		// рисуем ракетки
		gc.fillRect(playerX, playerY, RACKET_WIDTH, RACKET_HEIGHT);
		gc.fillRect(compX, compY, RACKET_WIDTH, RACKET_HEIGHT);	
	}
	
	@Override
	public void start(Stage root) {
		Canvas canvas = new Canvas(width,height);
		gc = canvas.getGraphicsContext2D();
		drawTable();		
		Timeline t1 = new Timeline(new KeyFrame(Duration.millis(10), e->drawTable()));
		t1.setCycleCount(Timeline.INDEFINITE);
		
		canvas.setOnMouseClicked(e -> gameStarted = true);
		canvas.setOnMouseMoved(e -> playerY = e.getY());
		
		root.setScene(new Scene(new StackPane(canvas)));
		root.setTitle("Ping-pong");
		root.show();
		t1.play();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
