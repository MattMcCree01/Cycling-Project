package cycling;

public class Checkpoint {
    private int checkpointID = 0;
    private int stageID;
    private Double location;
    private Double averageGradient;
    private Double length;


    public Checkpoint(int stageId, Double location, Double averageGradient, Double length){
        this.checkpointID = checkpointID++;
        this.stageID = stageId;
        this.location = location;
        this.averageGradient = averageGradient;
        this.length = length;

    }
    public Checkpoint(int stageId, Double location){
        this.checkpointID = checkpointID++;
        this.stageID = stageId;
        this.location = location;
    }
    public int getCheckpointID() {
        return checkpointID;
    }
}
