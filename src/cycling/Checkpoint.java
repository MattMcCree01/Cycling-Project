package cycling;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Represents a checkpoint in a cycling stage.
 */
public class Checkpoint implements Serializable{
    private static int checkpointIDcounter = 0;
    private int checkpointID;
    private int stageID;
    private Double location;
    private CheckpointType type;
    private Double averageGradient;
    private Double length;
    private ArrayList<Rider> RiderPointsRankInCheckpoint;
    private Dictionary<Integer, Integer> PointsPerPosition;

    /**
     * Constructor for creating a checkpoint.
     * @param stageId The ID of the stage.
     * @param location The location of the checkpoint.
     * @param type The type of checkpoint.
     * @param averageGradient The average gradient at the checkpoint.
     * @param length The length of the checkpoint.
     */
    public Checkpoint(int stageId, Double location, CheckpointType type, Double averageGradient, Double length){
        this.checkpointID = checkpointIDcounter++;
        this.stageID = stageId;
        this.location = location;
        this.type = type;
        this.averageGradient = averageGradient;
        this.length = length;
        this.RiderPointsRankInCheckpoint = new ArrayList<Rider>();
        this.PointsPerPosition = new Hashtable<>();

        // Assigning points based on checkpoint type
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

    /**
     * Constructor for creating a sprint checkpoint.
     * @param stageId The ID of the stage.
     * @param location The location of the checkpoint.
     */
    public Checkpoint(int stageId, Double location){
        this.checkpointID = checkpointIDcounter++;
        this.stageID = stageId;
        this.location = location;
        this.type = CheckpointType.SPRINT;
        this.RiderPointsRankInCheckpoint = new ArrayList<Rider>();
        this.PointsPerPosition = new Hashtable<>();

        // Assigning points for sprint checkpoints
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
    /**
     * sets the counter if needed after a load
     * @param counter
     */
    public static void setCounter(int counter) {
        checkpointIDcounter = counter;
    }
    /**
     * returns the id of the checkpoint
     * @return an int of id
     */
    public int getCheckpointID() {
        return checkpointID;
    }
    /**
     * returns the type of the checkpoint
     * @return an enum based on CheckpointType
     */
    public CheckpointType getType() {
        return type;
    }
    /**
     * gets the points for a specific position
     * @return a dictionary of position and points
     */
    public Dictionary<Integer, Integer> getPointsPerPosition() {
        return PointsPerPosition;
    }
    /**
     * adds the rider to the rank of the stage
     * @param inpRider
     * @param inpTime
     */
    public void addRiderToRank(Rider inpRider, LocalTime inpTime){
        // Flag to check if rider is added
        Boolean check = false;

        // Iterate through existing riders to find position to insert new rider
        for (Rider rider : RiderPointsRankInCheckpoint){
            if (rider.getRiderCheckpointTime(checkpointID).isAfter(inpTime)){
                // Insert rider
                RiderPointsRankInCheckpoint.add(RiderPointsRankInCheckpoint.indexOf(rider), inpRider);
                check = true;
                break;
            }
            
        }

        // Add rider if not inserted into position
        if (check ==false ) {
            RiderPointsRankInCheckpoint.add(inpRider);
        }
        
        // Update points based on checkpoint type
        if(type != CheckpointType.SPRINT){
            updateMountainPoints();
        }
        else{
            updateSprintPoints();
        }        
    }
    /**
     * updates the mountain points once a rider is added
     */
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
    /**
     * updates the sprint points once a rider is added
     */
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
    /**
     * gets the location of the checkpoint
     * @return a double that is the location of the checkpoint
     */
    public double getLocation(){
        return location;
    }
    /**
     * gets the average gradient of the chekpoint
     * @return a double that is the average gradient
     */
    public double getAverageGradient(){
        return averageGradient;
    }
    /**
     * gets the length of the checkpoint
     * @return a double which is the length
     */
    public double getLength(){
        return length; 
    }
    
}
