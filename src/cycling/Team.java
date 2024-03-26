package cycling;
import java.io.Serializable;


/**
 * Represents a cycling team.
 */
public class Team implements Serializable{
    private int teamID;
    private String teamName;
    private String description;
    private static int nextID = 1;

    /**
     * Constructor for creating a team.
     * @param teamName The name of the team.
     * @param description The description of the team.
     */
    public Team(String teamName, String description) {
        this.teamID = nextID++;
        this.teamName = teamName;
        this.description = description;
    }
    /**
     * sets the counter after a load
     * @param counter
     */
    public static void setCounter(int counter) {
        if (counter == 0) {
            nextID = 1;
        } else {
            nextID = counter + 1;
        }
    }
    /**
     * gets the teamId
     * @return an integer of the id
     */
    public int getTeamId() {
        return teamID;
    }
    /**
     * gets the team description
     * @return a string of the description
     */
    public String getDescription(){
        return description;
    }
    /**
     * gets the name of the team
     * @return a string of the name
     */
    public String getTeamName() {
        return teamName;
    }    
}
