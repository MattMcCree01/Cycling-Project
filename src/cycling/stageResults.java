package cycling;
import java.time.LocalTime;


public class stageResults {
    private int stageId;
    private LocalTime[] results;
    private int stagePoints;
    private int stageMountainPoints;
    private LocalTime elapsedTime;
    private LocalTime adjustedElapsedTime;


    public stageResults(int stageId, LocalTime... results) {
        this.stageId = stageId;
        this.results = results;
        this.stagePoints = 0;
        this.stageMountainPoints = 0;
    }
    public int getStageId() {
        return stageId;
    }
    public LocalTime[] getResults() {
        return results;
    }
    public int getStagePoints() {
        return stagePoints;
    }
    public int getStageMountainPoints() {
        return stageMountainPoints;
    }
    public void setStagePoints(int stagePoints) {
        this.stagePoints = stagePoints;
    }
    public void setStageMountainPoints(int stageMountainPoints) {
        this.stageMountainPoints = stageMountainPoints;
    }
    public LocalTime getStageTime() {
        LocalTime finishtime = results[-1];
        LocalTime starttime = results[0];
        LocalTime stageTime = finishtime.minusHours(starttime.getHour()).minusMinutes(starttime.getMinute()).minusSeconds(starttime.getSecond());
        elapsedTime = stageTime;
        return stageTime;
        
    }
    
}

