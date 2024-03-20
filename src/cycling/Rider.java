package cycling;
import java.util.ArrayList;
import java.time.LocalTime;

public class Rider {
    private static int idCounter = 0;
    private int id;
    private String name;
    private int BirthYear;
    private int teamID;
    private ArrayList<stageResults> stageResults;
    

    public Rider (int teamId, String name, int yearOfBirth) {
        this.id = idCounter++;
        this.teamID = teamId;
        this.name = name;
        this.BirthYear = yearOfBirth;
        this.stageResults = new ArrayList<stageResults>();
        
    }
    public int getRiderId() {
        return id;
    }
    public int getTeamId() {
        return teamID;
    }
    public stageResults[] getStageResults() {
        return stageResults.toArray(new stageResults[stageResults.size()]);
    }
    public void removeRider(int riderId) {
        
    }
    public void registerRiderResultsInStage(int stageId, LocalTime... checkpoints) {
        stageResults.add(new stageResults(stageId, checkpoints));
        
    }
    public LocalTime[] getRiderResultsInStage(int stageId) {
        for (stageResults stageResults : stageResults) {
            if(stageResults.getStageId() == stageId) {
                return stageResults.getResults();
                
            }
        }
        return null;

    }
    public LocalTime[] getRiderAdjustedElapsedTimeInStage(int stageID) {
        return null;
    }
    public void deleteRiderResultsInStage(int stageId) {
        
    }
    public int getRacePoints(){
        int points = 0;
        for (stageResults stageResults : stageResults) {
            points += stageResults.getStagePoints();

        }
        return points;
    }
    public int getMountainPoints(){
        int points = 0;
        for (stageResults stageResults : stageResults) {
            points += stageResults.getStageMountainPoints();

        }
        return points;
    }
    public LocalTime getElapsedTime() {
        LocalTime elapsedTime = LocalTime.of(0, 0, 0);
        for (stageResults stageResults : stageResults) {
            elapsedTime = elapsedTime.plusHours(stageResults.getStageTime().getHour());
            elapsedTime = elapsedTime.plusMinutes(stageResults.getStageTime().getMinute());
            elapsedTime = elapsedTime.plusSeconds(stageResults.getStageTime().getSecond());
        }
        return elapsedTime;
    }
    public LocalTime getStageElapsedTime(int stageId) {
        LocalTime elapsedTime = LocalTime.of(0, 0, 0);
        for (stageResults stageResults : stageResults) {
            if (stageResults.getStageId() == stageId) {
                elapsedTime = elapsedTime.plusHours(stageResults.getStageTime().getHour());
                elapsedTime = elapsedTime.plusMinutes(stageResults.getStageTime().getMinute());
                elapsedTime = elapsedTime.plusSeconds(stageResults.getStageTime().getSecond());
            }
        }
        return elapsedTime;
    }
    public int getRiderStagePoints(int stageId) {
        for (stageResults stageResults : stageResults) {
            if (stageResults.getStageId() == stageId) {
                return stageResults.getStagePoints();
            }
        }
        return 0;
    }
    public LocalTime getRiderCheckpointTime(int checkpointId) {
        for (stageResults stageResults : stageResults) {
            for (LocalTime time : stageResults.getResults()) {
                if (time != null) {
                    return time;
                }
            }
        }
        return null;
    }
    public void addStageMountainPoints(int stageId, int points) {
        for (stageResults stageResults : stageResults) {
            if (stageResults.getStageId() == stageId) {
                stageResults.setStageMountainPoints(points);
                
            }

        }        
    }
    public void addStageSprintPoints(int stageId, int points) {
        for (stageResults stageResults : stageResults) {
            if (stageResults.getStageId() == stageId) {
                stageResults.setStageSprintPoints(points);
                
            }
        }        
    }
    public void setTeamId(int teamId) {
        this.teamID = teamId;
    }


}
