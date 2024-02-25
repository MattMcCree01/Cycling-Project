package cycling;

import java.time.LocalTime;

public class Stages {
    private int stageID;
    private int raceID;
    private String stageName;
    private StageType stageType;
    private String stageDescription;
    private double stageLength;
    private LocalTime startTime;
    private int[] checkpoints; //Unsure on the datatype this lists needs to be
    private static int nextID = 1;

    public Stages(int raceID, String stageName, StageType stageType, String stageDescription, double stageLength, LocalTime startTime) {
        this.stageID = nextID++;
        this.raceID = raceID;
        this.stageName = stageName;
        this.stageType = stageType;
        this.stageDescription = stageDescription;
        this.stageLength = stageLength;
        this.startTime = startTime;
        this.checkpoints = new int[0];
    }
    
    public int getStage() {
        return stageID;
    }
    
    public static void removeStage(int stageID) {

    }

    public void addClimbToStage(int stageID, int checkpointID) {

    }

    public void addSprintToStage(int stageID, int checkpointID) {

    }

    public void removeCheckpointFromStage(int stageID, int checkpointID) {

    }

    public int[] getStageCheckpoints(int stageID) {
        return checkpoints;
    }

    public void concludeStagePreparation(int stageID) {

    }
}
