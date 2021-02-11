package sample;
import Connectivity.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class topPlayerController implements Initializable {
    @FXML
    private Button backButton;

    static ResultSet rs;
    ObservableList<TopPlayerInfo> obList = FXCollections.observableArrayList();

    @FXML
    private TableView<TopPlayerInfo> topPlayerTable;

    @FXML
    private TableColumn<TopPlayerInfo,String> playerColumn;

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
    private TableColumn<TopPlayerInfo,Integer> goalColumn;
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("In Initialize");
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        try{
            rs = connection.createStatement().executeQuery("SELECT Player_Name,Player_Goals FROM Squad order by Player_Goals DeSC");
            rs.first();

        }catch(SQLException e){
            e.printStackTrace();
        }
        try (
                ResultSet rs =
                        connection.createStatement().executeQuery("SELECT Player_Name,Player_Goals FROM Squad order by Player_Goals DeSC")){
            while(rs.next()){
                obList.add(new TopPlayerInfo(rs.getString("Player_Name"),

                        rs.getInt("Player_Goals")));


            }
        }catch(SQLException e){
            e.printStackTrace();
        }


        playerColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        goalColumn.setCellValueFactory(new PropertyValueFactory<>("playerGoals"));

        //tblView.setItems(obsList);
        topPlayerTable.setItems(obList);
    }

}
