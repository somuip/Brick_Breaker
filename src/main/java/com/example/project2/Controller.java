package com.example.project2;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    @FXML
    private AnchorPane scene;


    @FXML
    private Circle circle;

    double deltaX = 1;

    double deltaY = 1;

    private Rectangle slider;
    private Button right,left;

    ArrayList<Rectangle> all_bricks = new ArrayList<>();

    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            // TODO - move the ball, and add collision features
            circle.setLayoutX(circle.getLayoutX() + deltaX);
            circle.setLayoutY(circle.getLayoutY() + deltaY);

            // check collision of the ball inside the scene
            check_scene_collision();
            check_brick_collision();

            // check slider collision as well
            check_slider_collision();

            // check if any bricks are available ? if not game is over
            if(all_bricks.isEmpty()){
                // you have won , you can proceed to next level
                System.exit(10);
            }

        }
    }));

    public void check_slider_collision(){
        if(circle.getBoundsInParent().intersects(slider.getBoundsInParent())){
            deltaY *= -1;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    // we will initialize all bricks
        timeline.setCycleCount(Animation.INDEFINITE);

        createBricks();

        timeline.play();

        add_slider();
        add_buttons();
    }

    public void check_brick_collision(){
        if(!all_bricks.isEmpty()){
            all_bricks.removeIf(component -> check_collision(component));
        }
        else{
            System.exit(1);
        }
    }

    public boolean check_collision(Rectangle current_brick){
        if(circle.getBoundsInParent().intersects(current_brick.getBoundsInParent())){

            double leftborder = current_brick.getLayoutX();
            double rightborder = current_brick.getLayoutX() + current_brick.getWidth();
            double centre = circle.getLayoutX();
            boolean upside = false;
            boolean bottomside = false;
            boolean rightside = false;
            boolean leftside = false;
            if((centre <= rightborder && centre >= leftborder)) {
                 rightside = circle.getLayoutX() >= ((current_brick.getLayoutX() + current_brick.getWidth()) + circle.getRadius());
                 leftside = circle.getLayoutX() <= ((current_brick.getLayoutX()) - circle.getRadius());
            }else {
                 upside = circle.getLayoutY() <= ((current_brick.getLayoutY()) - circle.getRadius());
                 bottomside = circle.getLayoutY() >= ((current_brick.getLayoutY()) + current_brick.getHeight() - circle.getRadius());
            }
            if(rightside || leftside){
                deltaX *= -1;
            }
            if(upside || bottomside){
                deltaY *= -1;
            }

            scene.getChildren().remove(current_brick);
            return true;
        }
        else return false;
    }
    public void check_scene_collision(){
        Bounds bounds = scene.getBoundsInLocal();
        boolean rightside = circle.getLayoutX() >= (bounds.getMaxX() - circle.getRadius());
        boolean leftside = circle.getLayoutX() <= (bounds.getMinX() + circle.getRadius());
        boolean upside = circle.getLayoutY() <= (bounds.getMinY() + circle.getRadius());
        boolean bottomside = circle.getLayoutY() >= (bounds.getMaxY() - circle.getRadius());

        if(rightside || leftside){
            deltaX *= -1;
        }
        if(upside ){
            deltaY *= -1;
        }
        if(bottomside){
            // game is over
            System.exit(1);
            //all_bricks.clear();
        }
    }
    public void createBricks(){
        int flag = 1;
        for(int i=270;i>0;i-=50){
            for(int j=440;j>0;j-=30){
                if(flag%2==0){
                    Rectangle rectangle = new Rectangle(j,i,40,40);
                    if(flag%3==0){
                        rectangle.setFill(Color.RED);
                    }
                    else if (flag%3 == 1){
                        rectangle.setFill(Color.GREEN);
                    }
                    else {
                        rectangle.setFill(Color.BLUE);
                    }
                    scene.getChildren().add(rectangle);
                    all_bricks.add(rectangle);
                }
                flag++;
            }
        }
    }

    public void add_slider(){
        slider = new Rectangle(300,475,70,15);
        slider.setFill(Color.BLACK);
        scene.getChildren().add(slider);
    }

    public void add_buttons(){
        right = new Button("RIGHT");
        right.setLayoutX(480);
        right.setLayoutY(450);

        left = new Button("LEFT");
        left.setLayoutX(20);
        left.setLayoutY(450);

        right.setOnAction(moveright);
        left.setOnAction(moveleft);

        scene.getChildren().add(right);
        scene.getChildren().add(left);

    }

    EventHandler<ActionEvent> moveright = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            slider.setLayoutX(slider.getLayoutX() + 20);
        }
    };

    EventHandler<ActionEvent> moveleft = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            slider.setLayoutX(slider.getLayoutX() - 20);
        }
    };
}
