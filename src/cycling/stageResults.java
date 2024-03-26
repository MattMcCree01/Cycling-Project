package cycling;
import java.io.Serializable;
import java.time.LocalTime;

/**
 * Represents a riders results of a stage in a cycling race.
 */
public class stageResults implements Serializable{
    private int stageId;
    private LocalTime[] results;
    private int stageSprintPoints;
    private int stageMountainPoints;
    private LocalTime elapsedTime;

    /**
     * Constructor for creating stage results.
     * @param stageId The ID of the stage.
     * @param results The results of the stage.
     */
    public stageResults(int stageId, LocalTime... results) {
        this.stageId = stageId;
        this.results = results;
        this.stageSprintPoints = 0;
        this.stageMountainPoints = 0;
    }
    /**
     * gets the id of the stage
     * @return an integer of the id
     */
    public int getStageId() {
        return stageId;
    }
    /**
     * gets an array of the riders times within a stage
     * @return an array of times
     */
    public LocalTime[] getResults() {
        return results;
    }
    /**
     * returns the stage point for the rider
     * @return an integer of points
     */
    public int getStagePoints() {
        return stageSprintPoints;
    }
    /**
     * returns the amount of mountain points a rider has for a stage
     * @return an int for mountain points
     */
    public int getStageMountainPoints() {
        return stageMountainPoints;
    }
    /**
     * sets the sprint points of a stage
     * @param stagePoints
     */
    public void setStageSprintPoints(int stagePoints) {
        this.stageSprintPoints = stagePoints;
    }
    /**
     * sets the mountain points for a stage
     * @param points
     */
    public void setStageMountainPoints(int points) {
        this.stageMountainPoints = points;
    }
    /**
     * adds mountain points for the stage
     * @param points
     */
    public void addStageMountainPoints(int points) {
        this.stageMountainPoints += points;
    }
    /**
     * adds sprint points to the stage
     * @param points
     */
    public void addStageSprintPoints(int points) {
        this.stageSprintPoints += points;
    }
    /**
     * gets the elapsed time of the stage
     * @return a time that is the elapsed time of the stage
     */
    public LocalTime getStageTime() {
        LocalTime finishtime = results[results.length-1];
        LocalTime starttime = results[0];
        LocalTime stageTime = finishtime.minusHours(starttime.getHour()).minusMinutes(starttime.getMinute()).minusSeconds(starttime.getSecond());
        elapsedTime = stageTime;
        return elapsedTime;
        
    }
    
}

