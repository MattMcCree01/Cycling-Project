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
    
    public static void removeStage(int stageID) {

    }

    public void addClimbToStage(int stageId, double location, double averageGradient, double length) {
        Checkpoint newCheckpoint = new Checkpoint(stageId, location, averageGradient, length);
        Checkpoint[] newCheckpoints = new Checkpoint[checkpoints.length + 1];
        for (int i = 0; i < checkpoints.length; i++) {
            newCheckpoints[i] = checkpoints[i];
        }
        newCheckpoints[checkpoints.length] = newCheckpoint;
        checkpoints = newCheckpoints;
    }

    public void addSprintToStage(int stageId, double location) {
        Checkpoint newCheckpoint = new Checkpoint(stageId, location);
        Checkpoint[] newCheckpoints = new Checkpoint[checkpoints.length + 1];
        for (int i = 0; i < checkpoints.length; i++) {
            newCheckpoints[i] = checkpoints[i];
        }
        newCheckpoints[checkpoints.length] = newCheckpoint;
        checkpoints = newCheckpoints;
    }

    public void removeCheckpointFromStage(int checkpointId) throws IDNotRecognisedException, InvalidStageStateException {
        if (status.equals("waiting for results")) {
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
        } else {
            throw new IDNotRecognisedException("Checkpoint ID not recognised");
        }
    }

    public int[] getStageCheckpoints(int stageId) {
        int[] outArray = new int[checkpoints.length];
        for (int i = 0; i < checkpoints.length; i++) {
            outArray[i] = checkpoints[i].getCheckpointID();
        
        }
        return outArray;
    }

    public void concludeStagePreparation(int stageId) {
        this.status = "waiting for results";
    }
}
