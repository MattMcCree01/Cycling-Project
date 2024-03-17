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
    private static ArrayList<Race> races = new ArrayList<Race>();

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
    public int addStageToRace(int RaceId, String stageName, String description, double length, LocalTime startTime, StageType type){
        
        Stage newStage = new Stage(raceId, stageName, description, length, startTime, type);
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
        return null;
    }
    public Stage[] loadStages(){
        int[] stageIds = getStageIds();
        Stage[] stages = new Stage[stageIds.length]; // Add semicolon at the end
        for(int i = 0; i < stageIds.length; i++){ // Add missing variable declaration
            stages[i] = loadStage(stageIds[i]); // Use stageIds[i] instead of id
        }
        return stages;
    }
    public int getRaceId(){
        return this.raceId;
    }
    public String getRaceName(){
        return this.raceName;
    }
    public void addStage(Stage stage){
        stages.add(stage);
    }
    public void updateRaceLength(){
        double length = 0;
        for (Stage stage : stages) {
            length += stage.getStageLength();
        }
        this.totalLength = length;
    }
}
