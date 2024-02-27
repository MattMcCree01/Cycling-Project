package cycling;

public class Team {
    private int teamID;
    private String teamName;
    private String description;
    private static int nextID = 1;

    public Team(String teamName, String description) {
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
