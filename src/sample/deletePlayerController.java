package sample;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class deletePlayerController {
    String playerName = "";
    int queryCheck;
    ResultSet rs;
    ArrayList<String> playerNamesList = new ArrayList<>();
    @FXML
    private TextField removePlayerField;

    @FXML
    private Button removePlayerButton;

    @FXML
    private Button returnBtn;
    @FXML
    private Button clearBtn;
    @FXML
    private Text errorText;


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

    @FXML
    void removeBtnHandler(Event event) throws SQLException {

        System.out.println("In Initialize");


        Connection con = getConnection();
        try{
            rs = con.createStatement().executeQuery("select * from Squad");
            rs.first();

        }catch(SQLException e){
            e.printStackTrace();
        }
        try (
                ResultSet rs =
                        con.createStatement().executeQuery("select * from Squad")){
            while(rs.next()){
                playerNamesList.add(rs.getString("Player_Name"));

            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        try {
            PreparedStatement st;

            try {
                playerName = removePlayerField.getText();

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }




            st = con.prepareStatement("Delete from Squad where Player_Name = ?");
            st.setString(1, playerName);

            st.executeUpdate();

            System.out.println(queryCheck);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Not today");
        }

        

        if(playerNamesList.contains(playerName)){
            errorText.setText(playerName +" was removed from the Current squad");

        }
        else{
            errorText.setText(playerName+" does not exist");
        }




    }

    @FXML
    void returnBtnHandler(Event e) throws IOException {
        Stage stage = new Stage();
        Parent root = null;
        if (e.getSource() == returnBtn) {
            stage = (Stage) returnBtn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void clearBtnHandler(Event e){
        removePlayerField.clear();
    }


}
