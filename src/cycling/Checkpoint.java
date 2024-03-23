package cycling;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class Checkpoint implements Serializable{
    private static final int Null = 0;
    private static int checkpointIDcounter = 0;
    private int checkpointID;
    private int stageID;
    private Double location;
    private CheckpointType type;
    private Double averageGradient;
    private Double length;
    private ArrayList<Rider> RiderPointsRankInCheckpoint;//will help adjust points more easily
    private Dictionary<Integer, Integer> PointsPerPosition;


    public Checkpoint(int stageId, Double location, CheckpointType type, Double averageGradient, Double length){
        this.checkpointID = checkpointIDcounter++;
        this.stageID = stageId;
        this.location = location;
        this.type = type;
        this.averageGradient = averageGradient;
        this.length = length;
        this.RiderPointsRankInCheckpoint = new ArrayList<Rider>();
        this.PointsPerPosition = new Hashtable<>();
        switch (this.type) {
            case C4:
                this.PointsPerPosition.put(0,1);
                break;
            case C3:
                this.PointsPerPosition.put(0,2);
                this.PointsPerPosition.put(1,1);    
                break;
            case C2:
                this.PointsPerPosition.put(0,5);
                this.PointsPerPosition.put(1,3);
                this.PointsPerPosition.put(2,2);
                this.PointsPerPosition.put(3,1);
                break;
            case C1:
                this.PointsPerPosition.put(0,10);
                this.PointsPerPosition.put(1,8);
                this.PointsPerPosition.put(2,6);
                this.PointsPerPosition.put(3,4);
                this.PointsPerPosition.put(4,2);
                this.PointsPerPosition.put(5,1);
                break;
            case HC:
                this.PointsPerPosition.put(0,20);
                this.PointsPerPosition.put(1,15);
                this.PointsPerPosition.put(2,12);
                this.PointsPerPosition.put(3,10);
                this.PointsPerPosition.put(4,8);
                this.PointsPerPosition.put(5,6);
                this.PointsPerPosition.put(6,4);
                this.PointsPerPosition.put(7,2);



                break;
            default:
                break;
        }

    }
    public Checkpoint(int stageId, Double location){
        this.checkpointID = checkpointIDcounter++;
        this.stageID = stageId;
        this.location = location;
        this.type = CheckpointType.SPRINT;
        this.RiderPointsRankInCheckpoint = new ArrayList<Rider>();
        this.PointsPerPosition = new Hashtable<>();
        this.PointsPerPosition.put(0,20);
        this.PointsPerPosition.put(1,17);
        this.PointsPerPosition.put(2,15);
        this.PointsPerPosition.put(3,13);
        this.PointsPerPosition.put(4,11);
        this.PointsPerPosition.put(5,10);
        this.PointsPerPosition.put(6,9);
        this.PointsPerPosition.put(7,8);
        this.PointsPerPosition.put(8,7);
        this.PointsPerPosition.put(9,6);
        this.PointsPerPosition.put(10,5);
        this.PointsPerPosition.put(11,4);
        this.PointsPerPosition.put(12,3);
        this.PointsPerPosition.put(13,2);
        this.PointsPerPosition.put(14,1);
    }
    public int getCheckpointID() {
            return checkpointID;
    }
    public CheckpointType getType() {
        return type;
    }
    public Dictionary<Integer, Integer> getPointsPerPosition() {
        return PointsPerPosition;
    }
    public void addRiderToRank(Rider inpRider, LocalTime inpTime){
        Boolean check = false;
        
        for (Rider rider : RiderPointsRankInCheckpoint){
            if (rider.getRiderCheckpointTime(checkpointID).isAfter(inpTime)){
                RiderPointsRankInCheckpoint.add(RiderPointsRankInCheckpoint.indexOf(rider), inpRider);
                check = true;
                break;
            }
            
        }
        if (check ==false ) {
            RiderPointsRankInCheckpoint.add(inpRider);
        
            
        }
        if(type != CheckpointType.SPRINT){
            updateMountainPoints();
        }
        else{
            updateSprintPoints();
        }        
    }
    public void updateMountainPoints(){
        for (Rider rider : RiderPointsRankInCheckpoint) {
            try{
                Integer assignedPoints = PointsPerPosition.get(RiderPointsRankInCheckpoint.indexOf(rider));
                if (assignedPoints == null){
                    assignedPoints = 0;
                }
                rider.addStageMountainPoints(stageID, assignedPoints);
                }
            finally{;
            }
            
        }
    }
    public void updateSprintPoints(){
        for (Rider rider : RiderPointsRankInCheckpoint) {
            try{
                Integer assignedPoints = PointsPerPosition.get(RiderPointsRankInCheckpoint.indexOf(rider));
                if (assignedPoints == null){
                    assignedPoints = 0;
                }
                rider.addStageSprintPoints(stageID, assignedPoints);
                }
            finally{;
            }
            
        }
    }

        
}
