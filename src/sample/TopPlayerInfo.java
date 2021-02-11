package sample;

public class TopPlayerInfo {
    private String playerName;
    private int playerGoals;
    public TopPlayerInfo(String pName,int pGoals){
        this.setPlayerName(pName);
        this.setPlayerGoals(pGoals);
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayerGoals() {
        return playerGoals;
    }

    public void setPlayerGoals(int playerGoals) {
        this.playerGoals = playerGoals;
    }
}
