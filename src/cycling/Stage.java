package cycling;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Stage {
    private int stageId = 0;
    private int raceID;
    private String stageName;
    private StageType stageType;
    private String stageDescription;
    private double stageLength;
    private LocalTime startTime;
    private String status;
    private Checkpoint[] checkpoints; 

    public Stage(int raceID, String stageName, String stageDescription, double stageLength, LocalTime startTime, StageType stageType) {
        this.stageId = stageId++;
        this.raceID = raceID;
        this.stageName = stageName;
        this.stageType = stageType;
        this.stageDescription = stageDescription;
        this.stageLength = stageLength;
        this.status = "preparation";
        this.startTime = startTime;
        this.checkpoints = new Checkpoint[0];
    }
    
    public int getStageId() {
        return stageId;
    }
    public String getStageName() {
        return stageName;
    }
    public double getStageLength() {
        return stageLength;
    }
    public Checkpoint[] getCheckpoints() {
        return checkpoints;
    }
    public StageType getStageType() {
        return stageType;
    }
    
    public static void removeStage(int stageID) {

    }

    public int addClimbToStage(int stageId, double location, CheckpointType type, double averageGradient, double length) throws InvalidStageStateException{
        if (this.status.equals("waiting for results")) {
            throw new InvalidStageStateException("Stage is in an invalid state for this operation");
        }
        
        Checkpoint newCheckpoint = new Checkpoint(stageId, location, type, averageGradient, length);
        Checkpoint[] newCheckpoints = new Checkpoint[checkpoints.length + 1];
        for (int i = 0; i < checkpoints.length; i++) {
            newCheckpoints[i] = checkpoints[i];
        }
        newCheckpoints[checkpoints.length] = newCheckpoint;
        checkpoints = newCheckpoints;
        return newCheckpoint.getCheckpointID();
    }

    public int addSprintToStage(int stageId, double location) throws InvalidStageStateException{
        if (this.status.equals("waiting for results")) {
            throw new InvalidStageStateException("Stage is in an invalid state for this operation");
        }

        Checkpoint newCheckpoint = new Checkpoint(stageId, location);
        Checkpoint[] newCheckpoints = new Checkpoint[checkpoints.length + 1];
        for (int i = 0; i < checkpoints.length; i++) {
            newCheckpoints[i] = checkpoints[i];
        }
        newCheckpoints[checkpoints.length] = newCheckpoint;
        checkpoints = newCheckpoints;
        return newCheckpoint.getCheckpointID();
    }

    public void removeCheckpointFromStage(int checkpointId) throws InvalidStageStateException {
        if (this.status.equals("waiting for results")) {
            throw new InvalidStageStateException("Stage is in an invalid state for this operation");
        }

        int indexToRemove = -1;
        for (int i = 0; i < checkpoints.length; i++) {
            if (checkpoints[i].getCheckpointID() == checkpointId) {
                indexToRemove = i;
                break;
            }
        }

        if (indexToRemove != -1) {
            checkpoints[indexToRemove] = null; // Set the reference to null
            Checkpoint[] newCheckpoints = new Checkpoint[checkpoints.length - 1];
            for (int i = 0, j = 0; i < checkpoints.length; i++) {
                if (i != indexToRemove) {
                    newCheckpoints[j++] = checkpoints[i];
                }
            }
            checkpoints = newCheckpoints;
        }
    }

    public int[] getStageCheckpoints(int stageId) {
        int[] outArray = new int[checkpoints.length];
        for (int i = 0; i < checkpoints.length; i++) {
            outArray[i] = checkpoints[i].getCheckpointID();
        
        }
        return outArray;
    }

    public void concludeStagePreparation(int stageId) throws InvalidStageStateException{
        if (this.status == "waiting for results") {
            throw new InvalidStageStateException("Stage is in an invalid state for this operation");
        } else {
            this.status = "waiting for results";
        }
    }
}