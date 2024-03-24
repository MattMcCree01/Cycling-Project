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
    private LocalTime adjustedElapsedTime;

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
    public int getStageId() {
        return stageId;
    }
    public LocalTime[] getResults() {
        return results;
    }
    public int getStagePoints() {
        return stageSprintPoints;
    }
    public int getStageMountainPoints() {
        return stageMountainPoints;
    }
    public void setStageSprintPoints(int stagePoints) {
        this.stageSprintPoints = stagePoints;
    }
    public void setStageMountainPoints(int points) {
        this.stageMountainPoints = points;
    }
    public void addStageMountainPoints(int points) {
        this.stageMountainPoints += points;
    }
    public void addStageSprintPoints(int points) {
        this.stageSprintPoints += points;
    }
    public LocalTime getStageTime() {
        LocalTime finishtime = results[results.length-1];
        LocalTime starttime = results[0];
        LocalTime stageTime = finishtime.minusHours(starttime.getHour()).minusMinutes(starttime.getMinute()).minusSeconds(starttime.getSecond());
        elapsedTime = stageTime;
        return stageTime;
        
    }
    
}

