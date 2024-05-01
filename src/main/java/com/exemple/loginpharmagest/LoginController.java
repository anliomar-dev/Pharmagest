package com.exemple.loginpharmagest;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private Button cancelButton;
    @FXML
    private Button loginButton;
    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordField;
    @FXML
    private Label loginMessageLabel; // pour afficher les message d'erreurs lors de l'authentification

    public void initialize() {
        // Désactiver le bouton "Login" au démarrage
        loginButton.setDisable(true);
    }

    @FXML
    public void onTextChanged() {
        // on recupère les contenues des champs username et password en supprimant les espaces au debut et  à la fin
        String username = usernameTextField.getText().trim();
        String password = passwordField.getText().trim();
        // invoquer le method initialize si un des deux champs est vide
        loginButton.setDisable(username.isEmpty() || password.isEmpty());
    }

    @FXML
    public void cancelButtonOnAction(ActionEvent e) {
        //quitter le fénêtre si le bouton cancel est pressé
        Stage stage=(Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void loginButtonOnAction(ActionEvent e){

    }
}