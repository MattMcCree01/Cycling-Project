package cycling;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.TreeMap;
import java.util.Map;

/**
 * Represents a stage in a race.
 */
public class Stage implements Serializable{
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

    /**
     * Constructor for creating a stage.
     * @param raceID The ID of the race the stage belongs to.
     * @param stageName The name of the stage.
     * @param stageDescription The description of the stage.
     * @param stageLength The length of the stage.
     * @param startTime The start time of the stage.
     * @param stageType The type of the stage.
     */
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
        this.participatingRiders = new ArrayList<Rider>();
    }
    /**
     * updates the counter
     * @param counter
     */
    public static void setCounter(int counter) {
        stageIdCounter = counter;
    }
    /**
     * gets the stage id
     * @return returns an integer containing the stage Id
     */
    public int getStageId() {
        return stageId;
    }
    /**
     * returns the name of the stage
     * @return a string of the name of the stage
     */
    public String getStageName() {
        return stageName;
    }
    /**
     * returns the length of the stage
     * @return a double of the length of the stage
     */
    public double getStageLength() {
        return stageLength;
    }
    /**
     * returns an array of checkpoints
     * @return an array of checkpoints
     */
    public Checkpoint[] getCheckpoints() {
        return checkpoints;
    }
    /**
     * returns the type of stage
     * @return an enum of the stage type
     */
    public StageType getStageType() {
        return stageType;
    }
    /**
     * returns the start time of the stage
     * @return the start time as type LocalTime
     */
    public LocalTime getStartTime(){
        return startTime;
    }
    /**
     * removes all the checkpoints from the stage
     */
    public void deleteAllCheckpoints () {
        checkpoints = new Checkpoint[0];
    }
    /**
     * returns the RaceId linked to the stage
     * @return an int of the RaceId
     */
    public int getStageRaceId(){
        return raceID;
    }
    /**
     * returns the description of the stage
     * @return a stirng containing the description
     */
    public String getDescription(){
        return stageDescription;
    }
    /**
     * adds a checkpoin tof type climb to the stage
     * @param stageId
     * @param location
     * @param type
     * @param averageGradient
     * @param length
     * @return the id of the checkpoint
     * @throws InvalidStageStateException
     */
    public int addClimbToStage(int stageId, double location, CheckpointType type, double averageGradient, double length) throws InvalidStageStateException{
        // Check if stage is in a valid state for this operation
        if (this.status.equals("waiting for results")) {
            throw new InvalidStageStateException("Stage is in an invalid state for this operation");
        }
        
        // Create new checkpoint
        Checkpoint newCheckpoint = new Checkpoint(stageId, location, type, averageGradient, length);
        Checkpoint[] newCheckpoints = new Checkpoint[checkpoints.length + 1];
        // Copy checkpoints to new array
        for (int i = 0; i < checkpoints.length; i++) {
            newCheckpoints[i] = checkpoints[i];
        }
        // Add new checkpoint to new array
        newCheckpoints[checkpoints.length] = newCheckpoint;
        checkpoints = newCheckpoints;
        return newCheckpoint.getCheckpointID();
    }
    /**
     * gets a specfiic checkpoint based on the Id
     * @param checkpointId
     * @return an object of Checkpoint
     */
    public Checkpoint getCheckpoint(int checkpointId) {
        for (int i = 0; i < checkpoints.length; i++) {
            if (checkpoints[i].getCheckpointID() == checkpointId) {
                return checkpoints[i];
            }
        }
        return null;
    }
    /**
     * adds a checkpoint of type sprint to the stage
     * @param stageId
     * @param location
     * @return the id of the new checkpoint
     * @throws InvalidStageStateException
     */
    public int addSprintToStage(int stageId, double location) throws InvalidStageStateException{
        // Check if stage is in a valid state for this operation
        if (this.status.equals("waiting for results")) {
            throw new InvalidStageStateException("Stage is in an invalid state for this operation");
        }

        // Create new checkpoint
        Checkpoint newCheckpoint = new Checkpoint(stageId, location);
        Checkpoint[] newCheckpoints = new Checkpoint[checkpoints.length + 1];
        // Copy checkpoints to new array
        for (int i = 0; i < checkpoints.length; i++) {
            newCheckpoints[i] = checkpoints[i];
        }
        newCheckpoints[checkpoints.length] = newCheckpoint;
        checkpoints = newCheckpoints;
        return newCheckpoint.getCheckpointID();
    }
    /**
     * removes the chekpoint from the stage
     * @param checkpointId
     * @throws InvalidStageStateException
     */
    public void removeCheckpointFromStage(int checkpointId) throws InvalidStageStateException {
        // Check if stage is in a valid state for this operation
        if (this.status.equals("waiting for results")) {
            throw new InvalidStageStateException("Stage is in an invalid state for this operation");
        }

        int indexToRemove = -1;
        // Find the index of the checkpoint to remove
        for (int i = 0; i < checkpoints.length; i++) {
            if (checkpoints[i].getCheckpointID() == checkpointId) {
                indexToRemove = i;
                break;
            }
        }
        
        // Remove the checkpoint
        if (indexToRemove != -1) {
            checkpoints[indexToRemove] = null;
            Checkpoint[] newCheckpoints = new Checkpoint[checkpoints.length - 1];
            for (int i = 0, j = 0; i < checkpoints.length; i++) {
                if (i != indexToRemove) {
                    newCheckpoints[j++] = checkpoints[i];
                }
            }
            checkpoints = newCheckpoints;
        }
    }
    /**
     * returns the checkpoint ids of the checkpoints in the stage
     * @param stageId
     * @return an array of integers
     */
    public int[] getStageCheckpoints(int stageId) {
        int[] outArray = new int[checkpoints.length];
        for (int i = 0; i < checkpoints.length; i++) {
            outArray[i] = checkpoints[i].getCheckpointID();
        
        }
        return outArray;
    }
    /**
     * sets the status to waiting for results
     * @param stageId
     * @throws InvalidStageStateException
     */
    public void concludeStagePreparation(int stageId) throws InvalidStageStateException{
        if (this.status == "waiting for results") {
            throw new InvalidStageStateException("Stage is in an invalid state for this operation");
        } else {
            this.status = "waiting for results";
        }
    }
    /**
     * returns the status of the stage
     * @return a string which contains the status
     */
    public String getStageStatus() {
        return status;
    }
    /**
     * adds the rider to the participating riders array
     * @param rider
     */
    public void addParticipatingRider(Rider rider) {
        participatingRiders.add(rider);
        Comparator<Rider> comparator = Comparator.comparing(r -> r.getRiderResultsInStage(stageId)[r.getRiderResultsInStage(stageId).length-1]);
        participatingRiders.sort(comparator);
        

    }
    /**
     * returns an array containing the riders points
     * @return an integer array that contains all the riders points
     */
    public int[] getRiderPoints() {
        int[] outArray = new int[participatingRiders.size()];
        for (int i = 0; i < participatingRiders.size(); i++) {
            outArray[i] = participatingRiders.get(i).getRiderStagePoints(stageId);        
        }
        return outArray;
    }
    /**
     * returns an array containing the riders mountain points
     * @return an integer array that contains all the riders mountain points
     */
    public int[] getRiderMountainPoints() {
        int[] outArray = new int[participatingRiders.size()];
        for (int i = 0; i < participatingRiders.size(); i++) {
            outArray[i] = participatingRiders.get(i).getStageMountainPoints(stageId);        
        }
        return outArray;
    }
    /**
     * returns an array containing the riders mountain points
     * @return an integer array that contains all the riders mountain points
     */
    public int[] getRiderStageMountainPoints() {
        int[] outArray = new int[participatingRiders.size()];
        for (int i = 0; i < participatingRiders.size(); i++) {
            outArray[i] = participatingRiders.get(i).getStageMountainPoints(stageId);        
        }
        return outArray;
    }
    /**
     * returns an array of the participating riderds
     * @return an array of the participating riders
     */
    public Rider[] getParticipatingRiders() {
        return participatingRiders.toArray(new Rider[participatingRiders.size()]);
    }
    /**
     * removes the rider from the stage
     * @param riderId
     */
    public void removeParticipatingRider(int riderId) {
        for (int i = 0; i < participatingRiders.size(); i++) {
            if (participatingRiders.get(i).getRiderId() == riderId) {
                participatingRiders.remove(i);
                break;
            }
        }
    }
    /**
     * updates the mountain points
     */
    public void updateStageMountainMountainpoints() {
        int checkpointCount = 0;

        // set all participating riders to 0 mountain points
        for (Rider rider : participatingRiders) {
            rider.setRiderMountainPoints(stageId, 0);
        }

        // Loop iterating over checkpoints
        for (Checkpoint checkpoint : this.checkpoints) {
            if (checkpoint.getType() != CheckpointType.SPRINT) {
                // Creating a new map for each checkpoint to store rider checkpoint times
                Map<Integer, LocalTime> riderCheckpointTimes = new HashMap<>();
                
                // Loop iterating over participating riders for each checkpoint
                for (Rider rider : participatingRiders) {
                    // Fetching rider's checkpoint times for the current checkpoint
                    LocalTime[] result = rider.getSpecificStageResults(this.stageId);
                    LocalTime checkpointTime = result[checkpointCount + 1];
                    riderCheckpointTimes.put(rider.getRiderId(), checkpointTime);
                }

                // Sort riders by checkpoint times
                List<Map.Entry<Integer, LocalTime>> sortedList = new ArrayList<>(riderCheckpointTimes.entrySet());
                sortedList.sort(Map.Entry.comparingByValue());

                // Assign points based on position in the sorted list
                int position = 0;
                Dictionary<Integer, Integer> pointsMap = checkpoint.getPointsPerPosition();
                for (Map.Entry<Integer, LocalTime> entry : sortedList) {
                    int riderId = entry.getKey();
                    Integer points = pointsMap.get(position);
                    if (points == null) {
                        points = 0; // Assign 0 points if position doesn't exist in pointsMap
                    }
                    Rider rider = null;
                    for (Rider r : participatingRiders) {
                        if (r.getRiderId() == riderId) {
                            rider = r;
                            break;
                        }
                    }
                    rider.addStageMountainPoints(this.stageId, points);
                    position++;
                }
            }
            checkpointCount++;
        }
    }
    /**
     * updates the stage points in the stage
     */
    public void updateStagePoints() {
        int checkpointCount = 0;

        // set all participating riders to 0 stage points
        for (Rider rider : participatingRiders) {
            rider.setRiderSprintStagePoints(stageId, 0);
        }

        Map<Integer, LocalTime> riderFinishTimes = new HashMap<>();

        for (Rider rider : participatingRiders) {
            LocalTime[] result = rider.getSpecificStageResults(this.stageId);
            LocalTime finishTime = result[result.length - 1]; 
            riderFinishTimes.put(rider.getRiderId(), finishTime);
        }

        // Convert HashMap to TreeMap with custom comparator to sort by checkpoint time
        TreeMap<Integer, LocalTime> sortedMap = new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer riderId1, Integer riderId2) {
                LocalTime time1 = riderFinishTimes.get(riderId1);
                LocalTime time2 = riderFinishTimes.get(riderId2);
                return time1.compareTo(time2);
            }
        });

        sortedMap.putAll(riderFinishTimes);
        Dictionary<Integer, Integer> pointsMap = new Hashtable<>();
        // Assign points based on position in the sorted list for the stage type
        switch(this.stageType) {
            case TT:
            case HIGH_MOUNTAIN:
                pointsMap.put(0, 20);
                pointsMap.put(1, 17);
                pointsMap.put(2, 15);
                pointsMap.put(3, 13);
                pointsMap.put(4, 11);
                pointsMap.put(5, 10);
                pointsMap.put(6, 9);
                pointsMap.put(7, 8);
                pointsMap.put(8, 7);
                pointsMap.put(9, 6);
                pointsMap.put(10, 5);
                pointsMap.put(11, 4);
                pointsMap.put(12, 3);
                pointsMap.put(13, 2);
                pointsMap.put(14, 1);
                break;
            case FLAT:
                pointsMap.put(0, 50);
                pointsMap.put(1, 30);
                pointsMap.put(2, 20);
                pointsMap.put(3, 18);
                pointsMap.put(4, 16);
                pointsMap.put(5, 14);
                pointsMap.put(6, 12);
                pointsMap.put(7, 10);
                pointsMap.put(8, 8);
                pointsMap.put(9, 7);
                pointsMap.put(10, 6);
                pointsMap.put(11, 5);
                pointsMap.put(12, 4);
                pointsMap.put(13, 3);
                pointsMap.put(14, 2);
                break;
            case MEDIUM_MOUNTAIN:
                pointsMap.put(0, 30);
                pointsMap.put(1, 25);
                pointsMap.put(2, 22);
                pointsMap.put(3, 19);
                pointsMap.put(4, 17);
                pointsMap.put(5, 15);
                pointsMap.put(6, 13);
                pointsMap.put(7, 11);
                pointsMap.put(8, 9);
                pointsMap.put(9, 7);
                pointsMap.put(10, 6);
                pointsMap.put(11, 5);
                pointsMap.put(12, 4);
                pointsMap.put(13, 3);
                pointsMap.put(14, 2);
                break;
            default:
                break;
        }
            // Iterate over sortedMap to assign points to riders
            for (Map.Entry<Integer, LocalTime> entry : sortedMap.entrySet()) {
                int riderId = entry.getKey();
                int position = sortedMap.headMap(riderId).size();
                // Check if position exists in pointsMap
                Integer points = pointsMap.get(position);
                
                if (points == null) {
                    points = 0; // Assign 0 points if position doesn't exist in pointsMap
                }

                Rider rider = null;
                for (Rider r : participatingRiders) {
                    if (r.getRiderId() == riderId) {
                        rider = r;
                        break;
                    }
                }
                rider.addStageSprintPoints(this.stageId, points);
            }
        
        
        for (Checkpoint checkpoint : this.checkpoints) {
            if (checkpoint.getType() == CheckpointType.SPRINT) {
                // Creating a new map for each checkpoint to store rider checkpoint times
                Map<Integer, LocalTime> riderCheckpointTimes = new HashMap<>();
                
                // Loop iterating over participating riders for each checkpoint
                for (Rider rider : participatingRiders) {
                    // Fetching rider's checkpoint times for the current checkpoint
                    LocalTime[] result = rider.getSpecificStageResults(this.stageId);
                    LocalTime checkpointTime = result[checkpointCount + 1];
                    riderCheckpointTimes.put(rider.getRiderId(), checkpointTime);
                }

                // Sort riders by checkpoint times
                List<Map.Entry<Integer, LocalTime>> sortedList = new ArrayList<>(riderCheckpointTimes.entrySet());
                sortedList.sort(Map.Entry.comparingByValue());

                // Assign points based on position in the sorted list
                int position = 0;
                Dictionary<Integer, Integer> sprintPointsMap = checkpoint.getPointsPerPosition();
                for (Map.Entry<Integer, LocalTime> entry : sortedList) {
                    int riderId = entry.getKey();
                    Integer points = sprintPointsMap.get(position);
                    if (points == null) {
                        points = 0; // Assign 0 points if position doesn't exist in pointsMap
                    }
                    Rider rider = null;
                    for (Rider r : participatingRiders) {
                        if (r.getRiderId() == riderId) {
                            rider = r;
                            break;
                        }
                    }
                    rider.addStageSprintPoints(this.stageId, points);
                    position++;
                }
            }
            checkpointCount++;
        }
    }
    /**
     * returns the adjusted elapsed time for the specfic rider
     * @param riderId
     * @return a LocalTime of the elapsed time
     */
    public LocalTime adjustedElapsedTime(int riderId){
        LocalTime[] newtimes = new LocalTime[participatingRiders.size()]; 
        for(int i = participatingRiders.size(); i > 0; i--){
            if(participatingRiders.get(i).getStageElapsedTime(i).minusSeconds(1).isBefore(participatingRiders.get(i-1).getStageElapsedTime(i-1))){
                newtimes[i] = participatingRiders.get(i).getStageElapsedTime(i-1);
                while(true){
                    i+=1;
                    if(participatingRiders.get(i).getStageElapsedTime(i).equals(newtimes[i-1])){
                        newtimes[i] = newtimes[i-1]; 
                    }
                    else{
                        break;
                    }
                }
            }
            else{
                newtimes[i] = participatingRiders.get(i).getStageElapsedTime(i);
            }
        }
        for (Rider rider : participatingRiders) {
            if(rider.getRiderId() == riderId){
                return newtimes[participatingRiders.indexOf(rider)];
            }
        }
        return null;
    }
}