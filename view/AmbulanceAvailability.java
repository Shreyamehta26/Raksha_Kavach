// package com.raksha_kavach.view;

// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.scene.image.Image;
// import javafx.scene.image.ImageView;
// import javafx.scene.layout.*;
// import javafx.scene.paint.Color;
// import javafx.scene.paint.LinearGradient;
// import javafx.scene.paint.Stop;
// import javafx.scene.text.Font;
// import javafx.scene.text.Text;
// import javafx.stage.Stage;

// public class AmbulanceAvailability {

//     public void showAmbulanceScene(Stage stage) {
//         BorderPane layout = new BorderPane();
//         layout.setPrefSize( 1540, 600);

//         // Gradient Background: Blue + Green Mix
//         BackgroundFill gradientFill = new BackgroundFill(
//                 new LinearGradient(0, 0, 1, 1, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
//                         new Stop(0, Color.web("#2f3233ff")), // mint green
//                         new Stop(0.5, Color.web("#3f666fff")), // aqua
//                         new Stop(1, Color.web("#2f3233ff")) // deep blue #2C3E50, #4CA1AF
//                 ),
//                 CornerRadii.EMPTY, Insets.EMPTY);
//         layout.setBackground(new Background(gradientFill));

//         // Heading
//         Label heading = new Label("Fast and On Time....");
//         heading.setFont(Font.font("Segoe UI", 40));
//         heading.setTextFill(Color.WHITE);

//         // Image
//         ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/Assets//Images//ambulance.jpg")));
//         imageView.setFitWidth(300);
//         imageView.setPreserveRatio(true);

//         // Info Text
//         Text info = new Text("Contact us on any below numbers given:");
//         info.setStyle("-fx-font-style: italic");
//         info.setFont(Font.font(28));
//         info.setFill(Color.BLACK);

//         // Cards Grid
//         GridPane cardGrid = new GridPane();
//         cardGrid.setPadding(new Insets(30));
//         cardGrid.setHgap(20);
//         cardGrid.setVgap(20);
//         cardGrid.setAlignment(Pos.CENTER);

//         String[] numbers = {
//                 "+91 9623567 940", "+91 7823001401", "+91 7218724548",
//                 "+91 9922124120", "+91 9653677625", "+91 9850915136"
//         };

//         for (int i = 0; i < numbers.length; i++) {
//             VBox card = createCard(numbers[i]);
//             cardGrid.add(card, i % 2, i / 2);
//         }

//         // Back Button
//         Button backBtn = new Button("Back to Home");
//         backBtn.setFont(Font.font("Arial", 20));
//         backBtn.setPrefSize(200, 50);
//         backBtn.setStyle("-fx-background-color: linear-gradient(to right, #5bc0de, #6f42c1); -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 10; -fx-padding: 8px 20px;");

//         backBtn.setOnAction(e -> {
//             HomePage homePage = new HomePage();
//             homePage.setHomestage(stage);
//             Pane homeRoot = homePage.createHomePage(() -> {
//             });
//             Scene homeScene = new Scene(homeRoot, 1540,  940);
//             stage.setScene(homeScene);
//             stage.setTitle("🏠 Home Page");
//         });

//          VBox centerBox = new VBox(10, heading, imageView, info, cardGrid,backBtn);
//         centerBox.setAlignment(Pos.CENTER);
//         centerBox.setPadding(new Insets(40));

//         layout.setCenter(centerBox);
//         layout.setBottom(backBtn);
//         BorderPane.setMargin(backBtn, new Insets(20));
//         BorderPane.setAlignment(backBtn, Pos.CENTER);

//         // Final Scene
//         Scene ambulanceScene = new Scene(layout,  1540,  940);
//         stage.setScene(ambulanceScene);
//         stage.setTitle("🚑 Ambulance Availability");
//         stage.show();
//     }

//     private VBox createCard(String number) {
//         VBox card = new VBox();
//         card.setAlignment(Pos.CENTER);
//         card.setPadding(new Insets(15));
//         card.setSpacing(10);
//         card.setPrefSize(250, 80);
//         card.setStyle(
//                 "-fx-background-color: #f2f2f2;" +
//                         "-fx-border-color: #0070BB;" +
//                         "-fx-border-radius: 10;" +
//                         "-fx-background-radius: 10;" +
//                         "-fx-effect: dropshadow(one-pass-box, rgba(0,0,0,0.2), 5, 0.0, 0, 4);");

//         Label phone = new Label(number);
//         phone.setFont(Font.font("Segoe UI", 22));
//         phone.setTextFill(Color.DARKBLUE);

//         card.getChildren().add(phone);
//         return card;
//     }
// }
package com.raksha_kavach.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AmbulanceAvailability {

        public void showAmbulanceScene(Stage stage) {
                BorderPane layout = new BorderPane();
                layout.setPrefSize(1540, 950);

                // Gradient Background: Blue + Green Mix
                BackgroundFill gradientFill = new BackgroundFill(
                                new LinearGradient(0, 0, 1, 1, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                                                new Stop(0, Color.web("#4b85b7ff")),
                                                new Stop(1, Color.web("#182848"))),
                                CornerRadii.EMPTY, Insets.EMPTY);
                layout.setBackground(new Background(gradientFill));

                // Heading
                Label heading = new Label("Fast and On Time....");
                heading.setFont(Font.font("Segoe UI", 40));
                heading.setTextFill(Color.WHITE);

                // Image
                ImageView imageView = new ImageView(
                                new Image(getClass().getResourceAsStream("/Assets//Images//ambulance.jpg")));
                imageView.setFitWidth(300);
                imageView.setPreserveRatio(true);

                // Info Text
                Text info = new Text("Contact us on any below numbers given:");
                info.setStyle("-fx-font-style: italic");
                info.setFont(Font.font(28));
                info.setFill(Color.BLACK);

                // Cards Grid
                GridPane cardGrid = new GridPane();
                cardGrid.setPadding(new Insets(30));
                cardGrid.setHgap(20);
                cardGrid.setVgap(20);
                cardGrid.setAlignment(Pos.CENTER);

                String[] numbers = {
                                "+91 9623567 940", "+91 7823001401", "+91 7218724548",
                                "+91 9922124120", "+91 9653677625", "+91 9850915136"
                };

                for (int i = 0; i < numbers.length; i++) {
                        VBox card = createCard(numbers[i]);
                        cardGrid.add(card, i % 2, i / 2);
                }

                // Back Button
                Button backBtn = new Button("Back to Home");
                backBtn.setFont(Font.font("Arial", 20));
                backBtn.setPrefSize(200, 50);
                backBtn.setStyle(
                                "-fx-background-color: linear-gradient(to right, #5bc0de, #6f42c1); -fx-text-fill: white;  -fx-background-radius: 10;");

                backBtn.setOnAction(e -> {
                        HomePage homePage = new HomePage();
                        homePage.setHomestage(stage);
                        Pane homeRoot = homePage.createHomePage(() -> {
                        });
                        Scene homeScene = new Scene(homeRoot, 1540, 940);
                        stage.setScene(homeScene);
                        stage.setTitle("🏠 Home Page");
                });

                // Wrap content in VBox
                VBox centerBox = new VBox(10, heading, imageView, info, cardGrid);
                centerBox.setAlignment(Pos.CENTER);
                centerBox.setPadding(new Insets(20));

                // ScrollPane for center content
                ScrollPane scrollPane = new ScrollPane(centerBox);
                scrollPane.setFitToWidth(true);
                scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

                layout.setCenter(scrollPane);
                layout.setBottom(backBtn);
                BorderPane.setMargin(backBtn, new Insets(20));
                BorderPane.setAlignment(backBtn, Pos.CENTER);

                // Final Scene
                Scene ambulanceScene = new Scene(layout, 1540, 900);
                stage.setScene(ambulanceScene);
                stage.setTitle("🚑 Ambulance Availability");
                stage.show();
        }

        private VBox createCard(String number) {
                VBox card = new VBox();
                card.setAlignment(Pos.CENTER);
                card.setPadding(new Insets(15));
                card.setSpacing(10);
                card.setPrefSize(250, 80);
                card.setStyle(
                                "-fx-background-color: #1f1c1cff;" +
                                                "-fx-border-color: #0070BB;" +
                                                "-fx-border-radius: 10;" +
                                                "-fx-background-radius: 10;" +
                                                "-fx-effect: dropshadow(one-pass-box, rgba(0,0,0,0.2), 5, 0.0, 0, 4);");

                Label phone = new Label(number);
                phone.setFont(Font.font("Segoe UI", 22));
                phone.setTextFill(Color.BLACK); // ✅ Updated color

                card.getChildren().add(phone);
                return card;
        }

}
