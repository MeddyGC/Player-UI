package sample;
import Connectivity.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.management.RuntimeErrorException;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class slideViewController implements Initializable {
    static ResultSet rs;


    public Button btnPrev;
    public Button btnNext;
    public Label lblID;
    public Label lblFirstName;
    public Label lblLastName;
    public Label lblFirst;
    public Button btnShowFirst;
    @FXML
    private Text playerNameText;

    @FXML
    private Text appsText;

    @FXML
    private Text goalsText;

    @FXML
    private Text gamesText;

    @FXML
    private Text ageText;
    @FXML
    private Text posText;
    @FXML
    private Text clubText;

    @FXML
    private ImageView playerImage;

    @FXML
    private Button backButton;

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

    void setImage(String pName) throws IOException {
         System.out.println(pName);
         if (pName.contains("new player")){
             Image img = new Image("No-Image-Yet.jpg");
             playerImage.setImage(img);
         }
         else{
             Image img = new Image(pName + ".jpg");
             playerImage.setImage(img);
         }


     }



    public void prevCustClicked(ActionEvent actionEvent) throws SQLException, IOException {
        if (!rs.isFirst()){
            rs.previous();
        }
        playerNameText.setText(rs.getString("Player_Name"));
        posText.setText(rs.getString("Player_Position"));
        ageText.setText(String.valueOf(rs.getInt("Player_Age")));
        goalsText.setText(String.valueOf(rs.getInt("Player_Goals")));
        appsText.setText(String.valueOf(rs.getInt("Player_Apps")));
        clubText.setText(rs.getString("Player_Club"));

        setImage(rs.getString("Player_Name"));



    }

    public void nextCustClicked (ActionEvent actionEvent) throws Exception {
        if (!rs.isLast()){
            rs.next();
        }
        playerNameText.setText(rs.getString("Player_Name"));
        posText.setText(rs.getString("Player_Position"));
        ageText.setText(String.valueOf(rs.getInt("Player_Age")));
        goalsText.setText(String.valueOf(rs.getInt("Player_Goals")));
        appsText.setText(String.valueOf(rs.getInt("Player_Apps")));
        clubText.setText(rs.getString("Player_Club"));

        setImage(rs.getString("Player_Name"));

    }//end nextClicked

    public void initialize(URL location,ResourceBundle resources) {
        System.out.println("In Initialize");
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        try{
            rs = connection.createStatement().executeQuery("select * from Squad");
            rs.first();

        }catch(SQLException e){
            e.printStackTrace();
        }



    }





/*
    ObservableList<AddressInfo> obsList = FXCollections.observableArrayList(
            new AddressInfo(100,"John","Jones","jones@gmail.com","111-111-1111"),
            new AddressInfo(100,"Sue","Sorenson","sue@gmail.com","222-222-2222")

    );
    */
}
