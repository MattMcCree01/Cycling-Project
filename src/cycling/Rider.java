package cycling;
import java.util.ArrayList;
import java.time.LocalTime;

public class Rider {
    private int id = 0 ;
    private String name;
    private int BirthYear;
    private int RacePoints;
    private int MountainPoints;
    private int teamID;
    private ArrayList<Integer> stageResults;
    private LocalTime elapsedTime;

    public Rider (int teamId, String name, int yearOfBirth) {
        this.id = id++;
        this.teamID = teamId;
        this.name = name;
        this.BirthYear = yearOfBirth;
        this.RacePoints = 0;
        this.MountainPoints = 0;
        this.stageResults = new ArrayList<Integer>();
        this.elapsedTime = LocalTime.of(0, 0, 0);
    }
    public void removeRider(int riderId) {
        
    }
    public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints) {
        
    }
    public LocalTime[] getRiderResultsInStage(int stageId, int riderId) {
        return null;
    }
    public LocalTime[] getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) {
        return null;
    }
    public void deleteRiderResultsInStage(int stageId, int riderId) {
        
    }


}
