package cycling;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;


public class Stage {
    private static int stageIdCounter = 0;
    private int stageId;
    private int raceID;
    private String stageName;
    private StageType stageType;
    private String stageDescription;
    private double stageLength;
    private LocalTime startTime;
    private String status;
    private Checkpoint[] checkpoints; 
    private ArrayList<Rider> participatingRiders;

    public Stage(int raceID, String stageName, String stageDescription, double stageLength, LocalTime startTime, StageType stageType) {
        this.stageId = stageIdCounter++;
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
    public Checkpoint getCheckpoint(int checkpointId) {
        for (int i = 0; i < checkpoints.length; i++) {
            if (checkpoints[i].getCheckpointID() == checkpointId) {
                return checkpoints[i];
            }
        }
        return null;
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
    public String getStageStatus() {
        return status;
    }
    public void addParticipatingRider(Rider rider) {
        participatingRiders.add(rider);
        Comparator<Rider> comparator = Comparator.comparing(r -> r.getRiderResultsInStage(stageId)[r.getRiderResultsInStage(stageId).length-1]);
        participatingRiders.sort(comparator);
        

    }
    public int[] getRiderPoints() {
        int[] outArray = new int[participatingRiders.size()];
        for (int i = 0; i < participatingRiders.size(); i++) {
            outArray[i] = participatingRiders.get(i).getRiderStagePoints(stageId);        
        }
        return outArray;
    }
    public int[] getRiderMountainPoints() {
        int[] outArray = new int[participatingRiders.size()];
        for (int i = 0; i < participatingRiders.size(); i++) {
            outArray[i] = participatingRiders.get(i).getMountainPoints();        
        }
        return outArray;
    }
    public Rider[] getParticipatingRiders() {
        return participatingRiders.toArray(new Rider[participatingRiders.size()]);
    }
}