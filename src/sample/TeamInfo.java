package sample;

public class TeamInfo {
    private String  competionName;
    private int games;
    private int wins;
    private int loss;
    private int draws;
    private String rank;

    public TeamInfo(String comp, int w, int l, int d, int g, String r){
        this.setCompetionName(comp);

        this.setWins(w);
        this.setLoss(l);
        this.setDraws(d);
        this.setGames(g);
        this.setRank(r);
    }

    public String getCompetionName() {
        return competionName;
    }

    public void setCompetionName(String competionName) {
        this.competionName = competionName;
    }

    public int getGames() {
        return games;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLoss() {
        return loss;
    }

    public void setLoss(int loss) {
        this.loss = loss;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
