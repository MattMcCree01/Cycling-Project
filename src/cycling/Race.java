package cycling;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * Represents a race consisting of multiple stages.
 */
public class Race implements Serializable{
    private static int raceIdCounter = 0;
    private int raceId;
    private String raceName;
    private String raceDescription;
    private double totalLength;
    private ArrayList<Stage> stages;
    private ArrayList<Rider> GeneralClassification;
    private ArrayList<Rider> pointsClassification;
    private ArrayList<Rider> mountainClassification;
    

    /**
     * Constructor for creating a race.
     * @param raceName The name of the race.
     * @param raceDescription The description of the race.
     */
    public Race(String raceName, String raceDescription){
        this.raceName = raceName;
        this.raceDescription = raceDescription;
        this.raceId = raceIdCounter++;
        this.totalLength = 0;
        this.stages = new ArrayList<Stage>();
        this.GeneralClassification = new ArrayList<Rider>();
        this.pointsClassification = new ArrayList<Rider>();
        this.mountainClassification = new ArrayList<Rider>();
        
    }
    public static void setCounter(int counter) {
        raceIdCounter = counter;
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

    /**
     * Adds a stage to the race.
     */
    public int addStageToRace(int RaceId, String stageName, String description, double length, LocalTime startTime, StageType type){
        // Create new stage
        Stage newStage = new Stage(raceId, stageName, description, length, startTime, type);
        Stage[] newStages = new Stage[stages.size() + 1];
        // Copy stages to new array
        for(int i = 0; i < stages.size(); i++) {
            newStages[i] = stages.get(i);
        }
        // Add new stage to new array
        newStages[stages.size()] = newStage;
        // Convert array to ArrayList
        stages = new ArrayList<>(Arrays.asList(newStages));
        return newStage.getStageId();
    }

    public int[] getStageIds() {
        // Create an array to store the stage IDs
        int[] stageIds = new int[stages.size()];
        // Loop through the stages and add the stage ID to the array
        for (int i = 0; i < stages.size(); i++) {
            stageIds[i] = stages.get(i).getStageId();
        }
        return stageIds;
    }

    public Stage loadStage(int StageId){
        // Loop through the stages and return the stage with the matching ID
        for(int i=0; i< stages.size();i++){
            if (stages.get(i).getStageId() == StageId){
                return stages.get(i);
            }
        }
        return null;
    }
    public Stage[] loadStages(){
        // Create an array to store the stages
        int[] stageIds = getStageIds();
        Stage[] stages = new Stage[stageIds.length];
        // Loop through the stage IDs and load the stages
        for(int i = 0; i < stageIds.length; i++){
            stages[i] = loadStage(stageIds[i]);
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
        // Loop through the stages and add the length of each stage to the total length
        for (Stage stage : stages) {
            length += stage.getStageLength();
        }
        this.totalLength = length;
    }
    public void removeStage(Stage stage) {
        stages.remove(stage);
        this.updateRaceLength();
    }
    public Rider[] getGeneralClassification(){
        return GeneralClassification.toArray(new Rider[GeneralClassification.size()]);
    }

    public void addRiderToClassification(Rider rider){
        if(GeneralClassification.contains(rider)){
        }
        else{
            GeneralClassification.add(rider);
        }
        
        Collections.sort(GeneralClassification, new Comparator<Rider>() {
            @Override
            public int compare(Rider r1, Rider r2) {
                return r1.getRiderRaceElapsedTime(Race.this).compareTo(r2.getRiderRaceElapsedTime(Race.this));
            }
        });
    }
    public void addRiderToPointsClassification(Rider rider, Race race){
        if(pointsClassification.contains(rider)){
        }
        else{
            pointsClassification.add(rider);
        }
        
        Collections.sort(pointsClassification, new Comparator<Rider>() {
            @Override
            public int compare(Rider r1, Rider r2) {
                return r1.getRacePoints(race) - r2.getRacePoints(race);
            }
        });
    }
    public void addRiderToMountainPointsClassification(Rider rider, Race race){
        if(mountainClassification.contains(rider)){

        }
        else{
            pointsClassification.add(rider);
        }
        Collections.sort(mountainClassification, new Comparator<Rider>() {
            @Override
            public int compare(Rider r1,Rider r2){
                return r1.getRaceMountainPoints(race) - r2.getRaceMountainPoints(race);
            }            
        });
    }
    public int[] getPointsClassification(){
        int[] riderIds = new int[pointsClassification.size()];
        int i=0;
        for (Rider rider : pointsClassification) {
            
            riderIds[i++] = rider.getRiderId();
            
        }
        return riderIds;
    }
    public int[] getMountainPointsClassification(){
        int[] riderIds = new int[mountainClassification.size()];
        int i = 0;
        for(Rider rider: mountainClassification){
            riderIds[i++] = rider.getRiderId();
        }
        return riderIds;
    }

    
}
    

