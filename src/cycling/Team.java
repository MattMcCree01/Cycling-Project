package cycling;
import java.util.ArrayList;


public class Team {
    private int teamID;
    private String teamName;
    private String description;
    private static int nextID = 0;
      // Is this correct?

    public Team(String teamName, String description) {
        this.teamID = nextID++;
        this.teamName = teamName;
        this.description = description;
        
    }

    public int getTeamId() {
        return teamID;
    }
    public String getTeamName() {
        return teamName;
    }

    // Assume this isnt needed as we have list of teams in CyclingPortalImpl
    

    
}
