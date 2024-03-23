package cycling;
import java.io.Serializable;
import java.util.ArrayList;


public class Team implements Serializable{
    private int teamID;
    private String teamName;
    private String description;
    private static int nextID = 1;

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
    
}
