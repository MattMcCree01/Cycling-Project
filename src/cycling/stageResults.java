package cycling;
import java.io.Serializable;
import java.time.LocalTime;


public class stageResults implements Serializable{
    private int stageId;
    private LocalTime[] results;
    private int stageSprintPoints;
    private int stageMountainPoints;
    private LocalTime elapsedTime;
    private LocalTime adjustedElapsedTime;


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

