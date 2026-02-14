package org.example.teleporti.SceneControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.example.teleporti.Controllers.AuthController;
import org.example.teleporti.Controllers.MessageController;
import org.example.teleporti.Controllers.UserController;
import org.example.teleporti.Entities.Message;
import org.example.teleporti.Entities.User;
import org.example.teleporti.Utils.Router;
import org.example.teleporti.Utils.classes.Conversation;

import java.util.List;
import java.util.Objects;

public class MessagesViewController {

    private final AuthController authController = new AuthController();
    private final MessageController _messageController = new MessageController();
    private final UserController _userController = new UserController();

    @FXML
    protected TextField message = new TextField();
    @FXML
    protected TextField messageSearch = new TextField();

    @FXML
    protected ListView<VBox> conversationView = new ListView<>();
    @FXML
    protected ListView<VBox> listView = new ListView<>();

    @FXML
    protected ObservableList<VBox> conversationList = FXCollections.observableArrayList();
    @FXML
    protected Conversation selectedConversation = new Conversation();

    @FXML
    private Label welcome = new Label("");

    protected User currentUser;
    protected int talkingToId = 0;

    @FXML
    public void initialize() {
        VBox box = new VBox();
        box.getChildren().add(new Label("Select a conversation"));
        conversationView.getItems().add(box);
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        welcome.setText("Welcome, " + currentUser.getPrenom() + "!");
        List<VBox> vboxList = _messageController.getAllCurrentUserConversations(currentUser.getId()).stream().map((Message message1) -> {
            boolean isSender = message1.getSenderId() == currentUser.getId();
            talkingToId = isSender ? message1.getRecieverId() : message1.getSenderId();
            VBox vBox = new VBox();
            vBox.getStyleClass().add("conversation-preview");
            vBox.getChildren().add(new Label(_userController.getUserById(isSender ? message1.getRecieverId() : message1.getSenderId()).getPrenom()));
            String label = isSender ? "Vous: " : "";
            vBox.getChildren().add(new Label(label + message1.getMessageContent()));
            vBox.onMouseClickedProperty().set(event -> {
                conversationView.getItems().clear();
                try {
                    selectedConversation.setTalkingTo(_userController.getUserById(isSender ? message1.getRecieverId() : message1.getSenderId()).getPrenom());
                    selectedConversation.setMessages(_messageController.getConversationBetweenTwoUsers(currentUser.getId(), isSender ? message1.getRecieverId() : message1.getSenderId()));
                    VBox conversationBox = new VBox();
                    selectedConversation.getMessages().forEach((Message message2) -> {
                        VBox messageBox = new VBox();
                        messageBox.getStyleClass().add("message");
                        boolean isSentByTalkingTo = message2.getSenderId() == talkingToId;
                        Label messageLabel = new Label(message2.getMessageContent());
                        Label senderLabel = new Label(_userController.getUserById(message2.getSenderId()).getPrenom());
                        messageLabel.getStyleClass().add("message-content");
                        senderLabel.getStyleClass().add("message-sender");
                        messageLabel.setAlignment(isSentByTalkingTo ? Pos.CENTER_LEFT : Pos.CENTER_RIGHT);
                        senderLabel.setAlignment(isSentByTalkingTo ? Pos.CENTER_LEFT : Pos.CENTER_RIGHT);
                        senderLabel.setStyle("-fx-font-weight: bold");
                        messageBox.setAlignment(isSentByTalkingTo ? Pos.CENTER_LEFT : Pos.CENTER_RIGHT);
                        messageBox.setStyle(isSentByTalkingTo
                                ? "-fx-background-color: #919090"
                                : "-fx-background-color: #96dbff");
                        messageBox.getChildren().add(senderLabel);
                        messageBox.getChildren().add(messageLabel);
                        conversationBox.getChildren().add(messageBox);
                    });
                    conversationView.getItems().addAll(conversationBox);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            return vBox;
        }).distinct().toList().stream().filter(Objects::nonNull).collect(java.util.stream.Collectors.collectingAndThen(java.util.stream.Collectors.toMap(vbox -> ((Label) vbox.getChildren().get(0)).getText(), vbox -> vbox, (existing, replacement) -> existing), map -> new java.util.ArrayList<>(map.values())));
        conversationList.addAll(vboxList);
        if (!conversationList.isEmpty()) {
            listView.getItems().addAll(conversationList);
        }
    }

    @FXML
    public void onGoToProfile() {
        try {
            Router.goToProfile(currentUser, welcome);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void onGoToHome() {
        try {
            if (currentUser.getType().equals("Admin")) {
                Router.goToDashboard(currentUser, welcome);
            } else {
                Router.goToUser(currentUser, welcome);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onGoToMessages() {
        try {
            Router.goToMessages(currentUser, welcome);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onLogout() {
        Router.handleLogout(currentUser, welcome, authController);
    }

    @FXML
    protected void sendMessage() {
        Message message1 = new Message();
        try {
            if (message.getText().isEmpty()) {
                Router.openErrorMessageModal("Please enter a message.");
                return;
            }
            if (selectedConversation.getTalkingTo().isEmpty()) {
                Router.openErrorMessageModal("Please select a conversation.");
                return;
            }
            message1.setSenderId(currentUser.getId());
            message1.setRecieverId(talkingToId);
            message1.setMessageContent(message.getText());
            _messageController.ajout(message1);
            selectedConversation.getMessages().add(message1);
            message.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
