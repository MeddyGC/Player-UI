package sample;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class PlayerInfo {
    private int playerNum;
    private String playerName;
    private String playerPos;
    private int playerAge;
    private int playerGoals;
    private int playerApps;
    private String playerClub;

    public PlayerInfo(int num, String name, String pos, int age, int goals, int apps, String clubs) {
        this.setPlayerNum(num);
        this.setPlayerName(name);
        this.setPlayerPos(pos);
        this.setPlayerAge(age);
        ;
        this.setPlayerGoals(goals);
        this.setPlayerApps(apps);
        this.setPlayerClub(clubs);
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerPos() {
        return playerPos;
    }

    public void setPlayerPos(String playerPos) {
        this.playerPos = playerPos;
    }

    public int getPlayerAge() {
        return playerAge;
    }

    public void setPlayerAge(int playerAge) {
        this.playerAge = playerAge;
    }

    public int getPlayerGoals() {
        return playerGoals;
    }

    public void setPlayerGoals(int playerGoals) {
        this.playerGoals = playerGoals;
    }

    public int getPlayerApps() {
        return playerApps;
    }

    public void setPlayerApps(int playerApps) {
        this.playerApps = playerApps;
    }

    public String getPlayerClub() {
        return playerClub;
    }

    public void setPlayerClub(String playerClub) {
        this.playerClub = playerClub;
    }
}

