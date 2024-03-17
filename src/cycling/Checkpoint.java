package cycling;

import java.time.LocalTime;
import java.util.ArrayList;

public class Checkpoint {
    private int checkpointID = 0;
    private int stageID;
    private Double location;
    private CheckpointType type;
    private Double averageGradient;
    private Double length;
    private ArrayList<Rider> RiderPointsRankInCheckpoint;//will help adjust points more easily


    public Checkpoint(int stageId, Double location, CheckpointType type, Double averageGradient, Double length){
        this.checkpointID = checkpointID++;
        this.stageID = stageId;
        this.location = location;
        this.type = type;
        this.averageGradient = averageGradient;
        this.length = length;
        this.RiderPointsRankInCheckpoint = new ArrayList<Rider>();
    }
    public Checkpoint(int stageId, Double location){
        this.checkpointID = checkpointID++;
        this.stageID = stageId;
        this.location = location;
        this.type = CheckpointType.SPRINT;
        this.RiderPointsRankInCheckpoint = new ArrayList<Rider>();
    }
    public int getCheckpointID() {
        return checkpointID;
    }
    public void addRiderToRank(Rider inpRider){
        for (Rider rider : RiderPointsRankInCheckpoint) {
            if(inp)
        }
    }
}
