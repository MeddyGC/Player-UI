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
import javafx.scene.chart.PieChart;
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


public class Controller implements Initializable {
    static ResultSet rs;
    ObservableList<PlayerInfo> obList = FXCollections.observableArrayList();
    ObservableList<TeamInfo> obList2 = FXCollections.observableArrayList();
    double wins = 0,losses = 0,draws = 0,games = 0;




    @FXML
    private TableView<PlayerInfo> squadTableView;

    @FXML
    private TableColumn<PlayerInfo,Integer> playerNumberColumn;

    @FXML
    private TableColumn<PlayerInfo,String> playerClmn;

    @FXML
    private TableColumn<PlayerInfo,String> posColumn;

    @FXML
    private TableColumn<PlayerInfo,Integer> ageColumn;

    @FXML
    private TableColumn<PlayerInfo,Integer> appsColumn;

    @FXML
    private TableColumn<PlayerInfo,Integer> goalsColumn;

    @FXML
    private TableColumn<PlayerInfo,Integer> clubColumn;

    @FXML private Button viewTopPlayersBtn;

    @FXML private Button viewMostCappedPlayerBtn;

    @FXML private Button addnewPlayerButton;

    @FXML private Button removePlayerBtn;

    @FXML
    private TableView<TeamInfo> teamRankTable;

    @FXML
    private TableColumn<?, ?> compColumn;

    @FXML
    private TableColumn<?, ?> gamesColummn;

    @FXML
    private TableColumn<?, ?> winsColumn;

    @FXML
    private TableColumn<?, ?> drawsColumn;

    @FXML
    private TableColumn<?, ?> lossColumn;

    @FXML
    private TableColumn<?, ?> rankColumn;

    @FXML
    private PieChart performancePercentage;

    @FXML
    private Button modifyBtn;

    @FXML
    private Button viewSlideViewBtn;


    @FXML
    void viewBtnHandler(Event e) throws IOException {
        Stage stage = new Stage();
        Parent root = null;
        if (e.getSource() == viewTopPlayersBtn) {
            stage = (Stage) viewTopPlayersBtn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("TopPlayersView.fxml"));
        }
        if (e.getSource() == viewMostCappedPlayerBtn) {
            stage = (Stage) viewMostCappedPlayerBtn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("MostCappedPlayerView.fxml"));
        }
        if (e.getSource() == addnewPlayerButton) {
            stage = (Stage) addnewPlayerButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("addPlayerView.fxml"));
        }
        if (e.getSource() == removePlayerBtn) {
            stage = (Stage) removePlayerBtn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("deletePlayerView.fxml"));
        }
        if (e.getSource() == modifyBtn) {
            stage = (Stage) modifyBtn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("modifyPlayerView.fxml"));
        }
        if (e.getSource() == viewSlideViewBtn) {
            stage = (Stage) viewSlideViewBtn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("slideView.fxml"));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }

    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("In Initialize");
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        try{
            rs = connection.createStatement().executeQuery("select * from Squad");
            rs.first();

        }catch(SQLException e){
            e.printStackTrace();
        }
        try (
                ResultSet rs =
                        connection.createStatement().executeQuery("select * from Squad")){
            while(rs.next()){
                obList.add(new PlayerInfo(rs.getInt("Player_Number"),
                        rs.getString("Player_Name"),
                        rs.getString("Player_Position"),
                        rs.getInt("Player_Age"),
                        rs.getInt("Player_Goals"),
                        rs.getInt("Player_Apps"),
                        rs.getString("Player_Club")));

            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        playerNumberColumn.setCellValueFactory(new PropertyValueFactory<>("playerNum"));
        playerClmn.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        posColumn.setCellValueFactory(new PropertyValueFactory<>("playerPos"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("playerAge"));
        goalsColumn.setCellValueFactory(new PropertyValueFactory<>("playerGoals"));
        appsColumn.setCellValueFactory(new PropertyValueFactory<>("playerApps"));
        clubColumn.setCellValueFactory(new PropertyValueFactory<>("playerClub"));
        //tblView.setItems(obsList);
        squadTableView.setItems(obList);
        try{
            rs = connection.createStatement().executeQuery("select * from TeamRanking");
            rs.first();

        }catch(SQLException e){
            e.printStackTrace();
        }
        try (
                ResultSet rs =
                        connection.createStatement().executeQuery("select * from TeamRanking")){
            while(rs.next()){
                wins += rs.getInt("Wins");
                draws += rs.getInt("Draws");
                losses += rs.getInt("Loss");
                games += rs.getInt("Games");

                obList2.add(new TeamInfo(rs.getString("Competition"),
                        rs.getInt("Wins"),
                        rs.getInt("Loss"),
                        rs.getInt("Draws"),
                        rs.getInt("Games"),
                        rs.getString("Rank")));


            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        compColumn.setCellValueFactory(new PropertyValueFactory<>("competionName"));
        gamesColummn.setCellValueFactory(new PropertyValueFactory<>("games"));
        winsColumn.setCellValueFactory(new PropertyValueFactory<>("wins"));
        drawsColumn.setCellValueFactory(new PropertyValueFactory<>("draws"));
        lossColumn.setCellValueFactory(new PropertyValueFactory<>("loss"));
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        teamRankTable.setItems(obList2);
        System.out.println(wins);
        System.out.println(draws);
        System.out.println(losses);
        wins = ((wins/games) * 100);
        draws = ((draws/games)*100);
        losses = ((losses/games)*100);
        System.out.println(wins);
        System.out.println(draws);
        System.out.println(losses);
        ObservableList<PieChart.Data> piechartData = FXCollections.observableArrayList(new PieChart.Data("Wins",wins),
                new PieChart.Data("Draw",draws),
                new PieChart.Data("Losses",losses));
        performancePercentage.setData(piechartData);

    }
}
