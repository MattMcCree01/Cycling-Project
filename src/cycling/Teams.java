package cycling;

public class Teams {
    private int teamID;
    private String teamName;
    private static int nextID = 1;

    public Teams(String teamName) {
        this.teamID = nextID++;
        this.teamName = teamName;
    }

    public static void removeTeam(int teamID) {

    }

    public int getTeam() {
        return teamID;
    }

    public void teamRiders() {

    }
}
