package com.vols.gestionvols.controllers;

import com.vols.gestionvols.ConnexionDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterController {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnClear;

    @FXML
    private ToggleGroup sexe;

    @FXML
    private TextField tadresse;

    @FXML
    private PasswordField tconfirm;

    @FXML
    private RadioButton tfemme;

    @FXML
    private RadioButton thomme;

    @FXML
    private TextField tmail;

    @FXML
    private TextField tnom;

    @FXML
    private TextField tpass;

    @FXML
    private PasswordField tpassword;

    @FXML
    public void back() {
        try {
            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.close();
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/Fxml/LoginUser.fxml"));
            primaryStage.setTitle("Welcome");
            primaryStage.setScene(new Scene(root, 750, 450));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loginUser() {
        try {
            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.close();
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/Fxml/LoginUser.fxml"));
            primaryStage.setTitle("Welcome");
            primaryStage.setScene(new Scene(root, 750, 450));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }}

    public void register() {
        PreparedStatement st = null;
        Connection conn = null;
        String username = tnom.getText();
        String numPass= tpass.getText();
        String mail= tmail.getText();
        String mdp= tpassword.getText();
        String confirm =tconfirm.getText();
        String adresse=tadresse.getText();
        String sexe;
        if (tfemme.isSelected()){
             sexe="femme";
        }
        else {
            sexe="homme";
        }

        if (username.isEmpty() || numPass.isEmpty() || mail.isEmpty() || mdp.isEmpty() || confirm.isEmpty() || sexe.isEmpty() || adresse.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill Data");
            alert.showAndWait();
        } else {
            if(mdp.equals(confirm)){
            try {
                conn = ConnexionDB.getConnectiion();
                PreparedStatement checkSt=conn.prepareStatement("SELECT COUNT(*) FROM client WHERE numPass = ? or username= ?");
                checkSt.setString(1,numPass);
                checkSt.setString(2,username.toLowerCase());
                ResultSet checkRt=checkSt.executeQuery();
                checkRt.next();
                int count=checkRt.getInt(1);
                if(count>0){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Airport with the same name already exists");
                    alert.showAndWait();
                }
                else {
                    st = conn.prepareStatement("INSERT INTO `client`(`username`, `password`, `sexe`, `email`, `numPass`, `etat`, `adresse`) VALUES (?,?,?,?,?,?,?)");
                    st.setString(1, username);
                    st.setString(2, mdp);
                    st.setString(3, sexe);
                    st.setString(4, mail);
                    st.setString(5, numPass);
                    st.setString(6, "client");
                    st.setString(7, adresse);
                    int rowsAffected = st.executeUpdate();
                    if (rowsAffected > 0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setContentText("Airport added successfully");
                        alert.showAndWait();
                        loginUser();
                    }}
                checkSt.close();
                st.close();
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }}
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Vérifier votre mot de passe");
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void clear() {
        tnom.setText("");
        tpass.setText("");
        tmail.setText("");
        tadresse.setText("");
    }
}
