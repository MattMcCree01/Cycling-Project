package cycling;
import java.util.List;

public class Team {
    private int teamID;
    private String teamName;
    private String description;
    private static int nextID = 1;
    private static List<Team> teams;    // Is this correct?

    public Team(String teamName, String description) {
        this.teamID = nextID++;
        this.teamName = teamName;
        this.description = description;
    }

    public static void removeTeam(int teamID) throws IDNotRecognisedException {
        for (int i = 0; i < teams.size(); i++) {
            if (teams.get(i).teamID == teamID) {
                teams.remove(i);
                break;
            } else
                throw new IDNotRecognisedException("Team ID not recognised");
        }
    }

    public int[] getTeams() {
        int[] teamIDs = new int[teams.size()];

        for (int i = 0; i < teams.size(); i++) {
            teamIDs[i] = teams.get(i).teamID;
        }

        return teamIDs;
    }

    public void teamRiders() {

    }
}
