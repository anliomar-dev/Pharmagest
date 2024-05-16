package com.example.pharmagest;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class IndexController {

    @FXML
    private Button cancelButton;
    @FXML
    private Button loginButton;
    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordField;
    @FXML
    private Label loginMessageLabel; // pour afficher les message d'erreurs lors de l'authentification
    private String[] blacklist = {"*", "'", "/", "-", "@", "=", "<", ">", "\""};
    public void initialize() {
        // Désactiver le bouton "Login" au démarrage
        loginButton.setDisable(true);
    }

    @FXML
    public void onTextChanged() {
        // on recupère les contenues des champs username et password en supprimant les espaces au debut et  à la fin
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        // invoquer le method initialize si un des deux champs est vide
        loginButton.setDisable(username.trim().isEmpty() || password.trim().isEmpty());
    }

    @FXML
    public void cancelButtonOnAction(ActionEvent e) {
        //quitter le fénêtre si le bouton cancel est pressé
        Stage stage=(Stage) cancelButton.getScene().getWindow();
        stage.close();
    }


    public static class LoginValidator {
        public static boolean validateLogin(String username, String password) {
            String query = "SELECT * FROM utilisateurs WHERE username = ? AND motdepasse = ?";

            try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next(); // Si un enregistrement correspondant est trouvé, les informations d'identification sont valides
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false; // En cas d'erreur de connexion ou de requête SQL
            }
        }
    }



    public void loginButtonOnAction(ActionEvent e){
        String username = usernameTextField.getText().trim();
        String password = passwordField.getText().trim();
        // Vérifier si le nom d'utilisateur ou le mot de passe contient des caractères interdits
        boolean usernameBlacklisted = isCaractereDansBlacklist(username, blacklist);
        boolean passwordBlacklisted = isCaractereDansBlacklist(password, blacklist);
        // Vérifier les informations d'identification en utilisant la méthode validateLogin si aucun caractère interdit n'est présent
        if (!usernameBlacklisted && !passwordBlacklisted) {
            boolean loginValid = LoginValidator.validateLogin(username, password);

            if (loginValid) {
                // Rediriger vers une autre page ou effectuer d'autres actions si les informations d'identification sont valides
                loginMessageLabel.setText("connexion réussi! ");
            } else {
                // Afficher un message d'erreur si les informations d'identification sont invalides
                loginMessageLabel.setText("Nom d'utilisateur ou mot de passe incorrect.");
            }
        } else {
            // Afficher un message d'erreur si le nom d'utilisateur ou le mot de passe contient des caractères interdits
            loginMessageLabel.setText("le Nom d'utilisateur et/ou le mot de passe contienent des caractères interdits.");
        }
    }


    // Méthode pour vérifier si un caractère est dans la liste noire
    private boolean isCaractereDansBlacklist(String chaine, String[] blacklist) {
        for (int i = 0; i < chaine.length(); i++) {
            char lettre = chaine.charAt(i);
            for (String caractere : blacklist) {
                if (caractere.equals(String.valueOf(lettre))) {
                    return true; // Si on trouve un caractère dans la liste noire, on retourne true
                }
            }
        }
        return false; // Si aucun caractère de la chaîne n'est dans la liste noire, on retourne false
    }
}
