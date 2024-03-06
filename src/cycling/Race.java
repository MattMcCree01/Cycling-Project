package cycling;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class Race {
    
    private int raceId = 0;
    private String raceName;
    private String raceDescription;
    private double totalLength;
    private ArrayList<Stage> stages;
    private ArrayList<Rider> GeneralClassification;
    private ArrayList<Rider> pointsClassification;
    private ArrayList<Rider> mountainClassification;
    private ArrayList<Race> races = new ArrayList<Race>();

    public Race(String raceName, String raceDescription){
        this.raceName = raceName;
        this.raceDescription = raceDescription;
        this.raceId = raceId++;
        this.totalLength = 0;
        this.stages = new ArrayList<Stage>();
        this.GeneralClassification = new ArrayList<Rider>();
        this.pointsClassification = new ArrayList<Rider>();
        this.mountainClassification = new ArrayList<Rider>();
        
    }
    public String viewRaceDetails(){
        return "Race Name: " + this.raceName + 
               "\nRace Description: " + this.raceDescription + 
               "\nNumber of Stages: " + this.getNumberOfStages() + 
               "\nTotal Length: " + this.totalLength;
    }
    
        

    
    public int getNumberOfStages(){
        return stages.size();
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
        Stage[] newStages = new Stage[stages.size() + 1];
        for(int i = 0; i < stages.size(); i++) {
            newStages[i] = stages.get(i);
        }
        newStages[stages.size()] = newStage;
        stages = new ArrayList<>(Arrays.asList(newStages)); // Convert array to ArrayList
        return newStage.getStageId();

    }
    public int[] getStageIds() {
        int[] stageIds = new int[stages.size()];
        for (int i = 0; i < stages.size(); i++) {
            stageIds[i] = stages.get(i).getStageId();
        }
        return stageIds;
    }

    public Stage loadStage(int StageId){
        for(int i=0; i< stages.size();i++){
            if (stages.get(i).getStageId() == StageId){
                return stages.get(i);
            }

        }
        return new Stage(-1, "No Stage", StageType.FLAT, "No Description", 0, LocalTime.of(0, 0, 0));
    }
    public int getRaceId(){
        return this.raceId;
    }
    public static ArrayList<Race> getRaces(){
        return races;
    }
}
