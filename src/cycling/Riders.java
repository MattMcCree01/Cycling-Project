package cycling;

import java.time.LocalTime;

public class Riders {
    private int id;
    private String name;
    private int BirthYear;
    private int RacePoints;
    private int MountainPoints;
    private int teamID;
    private int[] stageResults;
    private LocalTime elapsedTime;

    public Riders (int teamId, String name, int yearOfBirth) {
        this.teamID = teamId;
        this.name = name;
        this.BirthYear = yearOfBirth;
        this.RacePoints = 0;
        this.MountainPoints = 0;
        this.stageResults = new int[0];
        this.elapsedTime = LocalTime.of(0, 0, 0);
    }
    public void removeRider(int riderId) {
        
    }
    public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints) {
        
    }
    public LocalTime[] getRiderResultsInStage(int stageId, int riderId) {
        
    }
    public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) {
        
    }
    public void deleteRiderResultsInStage(int stageId, int riderId) {
        
    }


}
