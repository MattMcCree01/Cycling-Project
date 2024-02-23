package cycling;

public class Teams {
    private int teamID;
    private String teamName;
    private String description;
    private static int nextID = 1;

    public Teams(String teamName, String description) {
        this.teamID = nextID++;
        this.teamName = teamName;
        this.description = description;
    }

    public static void removeTeam(int teamID) {

    }

    public int getTeam() {
        return teamID;
    }

    public void teamRiders() {

    }
}
