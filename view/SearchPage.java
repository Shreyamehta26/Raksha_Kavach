package com.raksha_kavach.view;

import com.raksha_kavach.Controller.AiApiController;
import com.raksha_kavach.Controller.FormatController;
import com.raksha_kavach.Controller.NotesController;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.text.TextContentRenderer;
import org.fxmisc.richtext.InlineCssTextArea;

public class SearchPage {

    private Stage c2w_ai_stage;
    private VBox c2w_ai_chatBox = new VBox(15);
    private ScrollPane c2w_ai_scrollPane;
    private String c2w_ai_userName;

    private final Parser markdownParser = Parser.builder().build();
    private final TextContentRenderer renderer = TextContentRenderer.builder().build();
    private final AiApiController aiController = new AiApiController();
    private final FormatController formatController = new FormatController();
    // private final NotesController notesController = new NotesController();

    public void setC2w_ai_stage(Stage stage) {
        this.c2w_ai_stage = stage;
    }

    public void setC2w_ai_userName(String userName) {
        this.c2w_ai_userName = userName;
    }

    public Parent getView(Runnable onBack) {
        TextField inputField = new TextField();
        inputField.setPromptText("Ask something...");
        inputField.setFont(Font.font("Segoe UI", 14));
        inputField.setPrefHeight(35);
        inputField.setPrefWidth(500);

        Button sendBtn = new Button("Send");
        sendBtn.setFont(Font.font("Segoe UI Semibold", 14));
        sendBtn.setStyle("-fx-background-color: #0078D4; -fx-text-fill:white; -fx-background-radius: 20;");
        sendBtn.setPrefHeight(35);
        sendBtn.setDefaultButton(true);

        sendBtn.setOnAction(e -> {
            String question = inputField.getText();
            if (!question.isBlank()) {
                addUserBubble(question);
                String response = aiController.callGeminiAPI(question);
                addBotBubble(question, response);
                inputField.clear();
            }
        });

        c2w_ai_scrollPane = new ScrollPane(c2w_ai_chatBox);
        c2w_ai_scrollPane.setFitToWidth(true);
        c2w_ai_scrollPane.setPrefWidth(400);
        c2w_ai_scrollPane.setPrefHeight(600);
        c2w_ai_scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        c2w_ai_scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        c2w_ai_scrollPane.setStyle("-fx-background: transparent;");
        c2w_ai_chatBox.setPadding(new Insets(20));

        Label title = new Label("What can I Help With?");
        title.setStyle("-fx-text-fill: white;-fx-font-style: italic;-fx-font-size: 20px");
        // title.setStyle("-fx-background-color: linear-gradient(to right, #5bc0de,
        // #6f42c1");

        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-background-color: linear-gradient(to right, #5bc0de, #6f42c1); "
                + "-fx-text-fill: white; -fx-font-size: 16px; "
                + "-fx-background-radius: 10; -fx-padding: 8px 20px;");
        backBtn.setOnAction(e -> goBack());

        HBox titleBar = new HBox(550, backBtn, title);
        titleBar.setPadding(new Insets(10));
        titleBar.setAlignment(Pos.CENTER_LEFT);
        titleBar.setStyle("-fx-background-color:rgb(38, 38, 41);");

        HBox inputBar = new HBox(10, inputField, sendBtn);
        inputBar.setPadding(new Insets(10));
        inputBar.setAlignment(Pos.CENTER);
        inputBar.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        HBox scrollContainer = new HBox(c2w_ai_scrollPane);
        scrollContainer.setAlignment(Pos.CENTER);
        scrollContainer.setPrefWidth(700); // desired width
        scrollContainer.setMaxWidth(1500);
        scrollContainer.setMaxHeight(700);

        VBox root = new VBox(10, titleBar, scrollContainer, inputBar);
        VBox.setVgrow(scrollContainer, Priority.ALWAYS);
        root.setStyle("-fx-background-color: black;");
        // root.setMaxSize(400, 400);

        if (c2w_ai_stage != null) {
            root.prefWidthProperty().bind(c2w_ai_stage.widthProperty());
            root.prefHeightProperty().bind(c2w_ai_stage.heightProperty());
        }

        return root;
    }

    private void addUserBubble(String text) {
        Label label = new Label(text);
        label.setWrapText(true);
        label.setStyle(
                "-fx-background-color:rgba(190, 19, 233, 0.42); -fx-padding: 10; -fx-background-radius: 10; -fx-font-size: 14px; -fx-text-fill: white;");

        HBox container = new HBox(label);
        container.setAlignment(Pos.CENTER_RIGHT);
        container.setPadding(new Insets(2));

        c2w_ai_chatBox.getChildren().add(container);
    }

    private void addBotBubble(String question, String markdown) {
        String plainText = renderer.render(markdownParser.parse(markdown));

        InlineCssTextArea area = new InlineCssTextArea();
        area.replaceText(plainText);
        area.setWrapText(true);
        area.setEditable(false);
        area.setPrefWidth(600);
        // area.setPrefHeight(700);
        area.setStyle(
                "-fx-background-color:rgba(31, 88, 144, 0.42); -fx-font-family:'Segoe UI'; -fx-font-size:14px; -fx-padding:10px; -fx-background-radius:12;");

        formatController.formatAndDisplayAIResponse(area, plainText);
        Platform.runLater(() -> area.requestFollowCaret());

        area.textProperty().addListener((obs, oldVal, newVal) -> area.setPrefHeight(area.getTotalHeightEstimate()));

        HBox container = new HBox(area);
        container.setAlignment(Pos.CENTER);

        // Label addToNotes = new Label("Add to Notes");
        // addToNotes.setStyle("-fx-font-size: 10px; -fx-text-fill: gray;");

        VBox vb = new VBox(10, container);
        vb.setAlignment(Pos.CENTER);
        vb.setPadding(new Insets(2));

        // addToNotes.setOnMouseClicked(event -> {
        // Notes note = new Notes();
        // note.setQuestion(question);
        // note.setAnswer(plainText);
        // note.setUserName(c2w_ai_userName);
        // Snackbar.show(c2w_ai_stage, "Note Successfully Added");
        // vb.getChildren().remove(addToNotes);
        // });

        c2w_ai_chatBox.getChildren().add(vb);
        Platform.runLater(() -> c2w_ai_scrollPane.setVvalue(1.0));
    }

    private void goBack() {
        HomePage homePage = new HomePage();
        homePage.setHomestage(c2w_ai_stage);
        Scene homeScene = new Scene(homePage.createHomePage(null), 1540, 940);
        c2w_ai_stage.setScene(homeScene);
    }
}
