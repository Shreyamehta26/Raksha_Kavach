package com.raksha_kavach.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text. Text;
import javafx.stage.Stage;
public class EyeDonation {
  

    Scene homScene;
    Stage homStage;
    public void sethomeScene(Scene homScene) {
        this.homScene = homScene;
    }
    public void sethomeStage(Stage homStage) {
        this.homStage = homStage;
    }
    public VBox createScene(Runnable back){
        Text tx=new Text("Second page");
        Button prevButton=new Button("Previous page");
        prevButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                back.run();
            }
            
        });
        VBox vb=new VBox(50,tx,prevButton);
        vb.setStyle("-fx-alignment:top_center");
        return vb;
    }
    

}


