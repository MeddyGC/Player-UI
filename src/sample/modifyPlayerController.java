package sample;

import Connectivity.ConnectionClass;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class modifyPlayerController implements Initializable {

    @FXML
    private TextField playerNamefield;

    @FXML
    private TextField numberField;

    @FXML
    private TextField clubField;

    @FXML
    private TextField ageField;

    @FXML
    private Button updateBtn;

    @FXML
    private TextField appsField;

    @FXML
    private TextField goalsField;

    @FXML
    private ChoiceBox<String> posBox;
    @FXML
    private Text errorText;

    @FXML
    private Button clearBtn;

    @FXML
    private Button backButton;
    String test = "";

    @FXML
    void backBtnHandler(Event e) throws IOException {
        Stage stage = new Stage();
        Parent root = null;
        if (e.getSource() == backButton) {
            stage = (Stage) backButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void clearBtnHandler(Event e) {
        playerNamefield.clear();
        ageField.clear();
        numberField.clear();
        clubField.clear();
        goalsField.clear();
        appsField.clear();
        appsField.setVisible(false);
        ageField.setVisible(false);
        clubField.setVisible(false);
        goalsField.setVisible(false);
        numberField.setVisible(false);
        posBox.setVisible(false);
        errorText.setText("");


    }







    ResultSet rs;
    String p = "";

    private ObservableList<String> choiceList = FXCollections.observableArrayList("GK", "DF", "MF", "AT");
    String playerName = "";
    String pos = "", club = "";
    int playerNum = 0, age = 0, apps = 0, goals = 0 ,queryCheck;


    public static Connection connection;

    public Connection getConnection() {
        String url = "jdbc:mysql://puff:3306/condemo_syliDB?useLegacyDatetimeCode=false&serverTimezone=America/Chicago";
        //String url = "jdbc:mysql://localhost:3306/condemo_books";
        String userName = "condemo";
        String pw = "gRoayOhk2";
        System.out.println("Loading driver...");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot find driver", e);
        }

        System.out.println("Trying to connect to database ...");
        try {
            connection = DriverManager.getConnection(url, userName, pw);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Connected to the database");
        return connection;
    }

    public static void closeConnection() throws SQLException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @FXML
    public void searchBtnHandler() throws SQLException {
        System.out.println("In Initialize");

        Connection con = getConnection();
        try {
            playerName = playerNamefield.getText();
        }
        catch (NumberFormatException e){
        }
        try {
            PreparedStatement st = connection.prepareStatement("select Player_Number,Player_Position,Player_Age,Player_Goals,Player_Apps,Player_Club  from Squad where Player_Name =?");
            st.setString(1, playerName);
            rs = st.executeQuery();
            while (rs.next()){
                p = rs.getString("Player_Position");
                ageField.setText(String.valueOf(rs.getInt("Player_Age")));
                goalsField.setText(String.valueOf(rs.getInt("Player_Goals")));
                appsField.setText(String.valueOf(rs.getInt("Player_Apps")));
                clubField.setText(rs.getString("Player_Club"));
                numberField.setText(String.valueOf(rs.getInt("Player_Number")));
                errorText.setText("Player found");


            }



        }
        catch (NumberFormatException e){
            System.out.println(playerName);


        }
        if(errorText.getText().isEmpty()){
            errorText.setText("Name does not exist please enter a different name");
            appsField.setVisible(false);
            ageField.setVisible(false);
            clubField.setVisible(false);
            goalsField.setVisible(false);
            numberField.setVisible(false);
            posBox.setVisible(false);
        }
        else{
            appsField.setVisible(true);
            ageField.setVisible(true);
            clubField.setVisible(true);
            goalsField.setVisible(true);
            numberField.setVisible(true);
            posBox.setVisible(true);

        }
            //System.out.println("\nAuthors with last name Deitel");




        disPlayBox();



    }





    @FXML
    void Show() {
        posBox.setValue(p);
        System.out.println(p);
        posBox.setItems(choiceList);


    }

    @FXML
    public void disPlayBox() {
        Show();
        if (posBox != null) {

            posBox.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->
            {
                if (newValue == "GK") {
                    pos = "GK";

                }
                if (newValue == "DF") {
                    pos = "DF";
                }
                if (newValue == "MF") {
                    pos = "MF";
                }
                if (newValue == "AT") {
                    pos = "AT";
                    System.out.println(posBox.getValue());
                }
            });
        }

    }
    public boolean isAlpha(String name) {
        boolean Found = true;

        char[] chars = name.toCharArray();

        for (char c : chars) {
            if (!Character.isLetter(c)) {
                Found = false;
            }
        }

        return Found;
    }

    @FXML
    public void upDateBtnHandler(){
        if (playerNamefield.getText().isEmpty() || ageField.getText().isEmpty() ||appsField.getText().isEmpty() ||goalsField.getText().isEmpty() || numberField.getText().isEmpty() ||clubField.getText().isEmpty() ||isAlpha(ageField.getText()) ||isAlpha(appsField.getText()) ||isAlpha(goalsField.getText()) || isAlpha(numberField.getText())){
            errorText.setText("Failed!! make sure all fields are filled not numbers are entered for player's goals, apps, age and number.");

        }
        else {
            Connection con = getConnection();
            try {
                PreparedStatement st;
                ResultSet rs;
                try {
                    playerNum = Integer.parseInt(numberField.getText());
                    age = Integer.parseInt(ageField.getText());
                    goals = Integer.parseInt(goalsField.getText());
                    apps = Integer.parseInt(appsField.getText());

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }


                playerName = playerNamefield.getText();
                pos = posBox.getValue();

                club = clubField.getText();
                st = con.prepareStatement("UPDATE Squad SET Player_Number=?,Player_Age=?,Player_Goals=?,Player_Apps=?,Player_Club=?,Player_Position=? WHERE Player_Name =?");
                st.setInt(1, playerNum);
                st.setString(6, pos);
                st.setInt(2, age);
                st.setInt(3, goals);
                st.setInt(4, apps);
                st.setString(5, club);
                st.setString(7, playerName);
                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Important Information");
            alert.setHeaderText("PLAYER UPDATE SUCCESSFUL");
            alert.setContentText("You may stay on page to update more player or return to main page");
            alert.showAndWait();
            playerNamefield.clear();
            ageField.clear();
            numberField.clear();
            clubField.clear();
            goalsField.clear();
            appsField.clear();
            appsField.setVisible(false);
            ageField.setVisible(false);
            clubField.setVisible(false);
            goalsField.setVisible(false);
            numberField.setVisible(false);
            posBox.setVisible(false);
            errorText.setText("");
        }


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        appsField.setVisible(false);
        ageField.setVisible(false);
        clubField.setVisible(false);
        goalsField.setVisible(false);
        numberField.setVisible(false);
        posBox.setVisible(false);

    }
}
