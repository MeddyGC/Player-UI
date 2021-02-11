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

public class addPlayerController  {

    @FXML
    private TextField playerNamefield;

    @FXML
    private TextField numberField;

    @FXML
    private TextField clubField;

    @FXML
    private TextField ageField;

    @FXML
    private Button addPlayerButton;
    @FXML
    private Text errorText;
    @FXML
    private ChoiceBox<String> posBox;
    @FXML
    private Button backButton;


    private ObservableList<String> choiceList = FXCollections.observableArrayList("GK", "DF", "MF", "AT");
    String playerName = "";
    String pos = "", club = "";
    int playerNum = 0, age = 0, apps = 0, goals = 0;


    @FXML
    void Show() {
        posBox.setValue("GK");
        posBox.setItems(choiceList);


    }

    @FXML
    public void initialize() {

        if (posBox != null) {
            Show();
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
    public static Connection connection;

    public Connection getConnection(){
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
            connection = DriverManager.getConnection (url,userName, pw);
        } catch (SQLException e) {              e.printStackTrace();
        }
        System.out.println("Connected to the database");
        return connection;
    }

    public static void closeConnection() throws SQLException{
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }catch(Exception e){             throw e;
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
    public void addPlayerButton() {
        System.out.println("In Initialize");
        if (playerNamefield.getText().isEmpty() || ageField.getText().isEmpty() || numberField.getText().isEmpty() ||clubField.getText().isEmpty() ||isAlpha(ageField.getText()) || isAlpha(numberField.getText())){
            errorText.setText("Failed!! make sure all fields are filled not numbers are entered for player's age and number.");

        }
        else {

            Connection con = getConnection();
            try {
                PreparedStatement st;
                ResultSet rs;
                try {
                    playerNum = Integer.parseInt(numberField.getText());
                    age = Integer.parseInt(ageField.getText());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }


                playerName = playerNamefield.getText() + "new player";
                pos = posBox.getValue();

                club = clubField.getText();
                st = con.prepareStatement("insert into Squad values(?,?,?,?,?,?,?)");
                st.setInt(1, playerNum);
                st.setString(2, playerName);
                st.setString(3, pos);
                st.setInt(4, age);
                st.setInt(5, goals);
                st.setInt(6, apps);
                st.setString(7, club);
                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            errorText.setText(playerName +" has successfully added to the current squad");
        }


    }
    @FXML
    void clearBtnHandler(Event e) {
        playerNamefield.clear();
        ageField.clear();
        numberField.clear();
        clubField.clear();


    }
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
}
