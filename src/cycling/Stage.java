package cycling;

import java.time.LocalTime;

public class Stage {
    private int stageID;
    private int raceID;
    private String stageName;
    private StageType stageType;
    private String stageDescription;
    private double stageLength;
    private LocalTime startTime;
    private String status;
    private Checkpoint[] checkpoints; 
    private static int nextID = 0;

    public Stage(int raceID, String stageName, StageType stageType, String stageDescription, double stageLength, LocalTime startTime) {
        this.stageID = nextID++;
        this.raceID = raceID;
        this.stageName = stageName;
        this.stageType = stageType;
        this.stageDescription = stageDescription;
        this.stageLength = stageLength;
        this.startTime = startTime;
        this.checkpoints = new Checkpoint[0];
    }
    
    public int getStageId() {
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
        int[] outArray = new int[checkpoints.length];
        for (int i = 0; i < checkpoints.length; i++) {
            outArray[i] = checkpoints[i].getCheckpointID();
        
        }
        return outArray;
    }

    public void concludeStagePreparation(int stageID) {

    }
}
