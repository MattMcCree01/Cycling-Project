package cycling;
import java.time.LocalTime;
import java.util.List;

public class Race {
    private int raceId = 0;
    private String raceName;
    private String raceDescription;
    private double totalLength;
    private Stage[] stages;
    private Rider[] GeneralClassification;
    private Rider[] pointsClassification;
    private Rider[] mountainClassification;

    public Race(String raceName, String raceDescription){
        this.raceName = raceName;
        this.raceDescription = raceDescription;
        this.raceId = raceId++;
        this.totalLength = 0;
        this.stages = new Stage[0];
        this.GeneralClassification = new Rider[0];
        this.pointsClassification = new Rider[0];
        this.mountainClassification = new Rider[0];
        
    }
    
        

    
    public int getNumberOfStages(){
        return stages.length;
    }
    public String viewRaceDetails(int raceId){
        if(this.raceId == raceId) {
            return "Race ID: " + this.raceId + "\n" +
                   "Race Name: " + this.raceName + "\n" +
                   "Race Description: " + this.raceDescription + "\n" +
                   "Number of Stages: " + getNumberOfStages() + "\n" +
                   "Total Length: " + this.totalLength;
        } else {
            return "No race found with ID: " + raceId;
        }
    }
    public int addStageToRace(int RaceId, String stageName, String description, double length, LocalTime startTime, StageType type){
        
        Stage newStage = new Stage(raceId, stageName, type, description, length, startTime);
        Stage[] newStages = new Stage[stages.length + 1];
        for(int i = 0; i < stages.length; i++) {
            newStages[i] = stages[i];
        }
        newStages[stages.length] = newStage;
        stages = newStages;
        return newStage.getStageId();
    
    }
    public getRaceStages(){// need to decide how to return stage Ids

    }

}
