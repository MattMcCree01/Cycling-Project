package cycling;
import java.util.ArrayList;
import java.io.Serializable;
import java.time.LocalTime;

/**
 * Represents a rider participating in races.
 */
public class Rider implements Serializable{
    private static int idCounter = 0;
    private int id;
    private String name;
    private int BirthYear;
    private int teamID;
    private ArrayList<stageResults> stageResults;
    
    /**
     * Constructor for creating a rider.
     * @param teamId The ID of the team the rider belongs to.
     * @param name The name of the rider.
     * @param yearOfBirth The year of birth of the rider.
     */
    public Rider (int teamId, String name, int yearOfBirth) {
        this.id = idCounter++;
        this.teamID = teamId;
        this.name = name;
        this.BirthYear = yearOfBirth;
        this.stageResults = new ArrayList<stageResults>();
        
    }
    public static void setCounter(int counter) {
        idCounter = counter;
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
    public void removeStageResults(int stageId) {
        for (int i = 0; i < stageResults.size(); i++) {
            if (stageResults.get(i).getStageId() == stageId) {
                stageResults.remove(i);
            }
        }
    }
    public LocalTime[] getSpecificStageResults(int stageId) {
        // Returns the results of a specific stage
        for (stageResults stageResult : stageResults) {
            if (stageResult.getStageId() == stageId) {
                return stageResult.getResults();
            }
        }
        return null;
    }
    public void setRiderMountainPoints(int stageId, int points) {
        for (stageResults stageResult : stageResults) {
            if (stageResult.getStageId() == stageId) {
                stageResult.setStageMountainPoints(points);
            }
        }
    }
    public void setRiderSprintStagePoints(int stageId, int points) {
        for (stageResults stageResult : stageResults) {
            if (stageResult.getStageId() == stageId) {
                stageResult.setStageSprintPoints(points);
            }
        }
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
        // TODO: Implement this method
    }
    public int getRacePoints(Race currentRace) {
        int points = 0;
        Stage[] stages = currentRace.loadStages();
        for (Stage stage : stages) {
            int stageId = stage.getStageId();
            for (stageResults stageResults : stageResults) {
                if (stageResults.getStageId() == stageId) {
                    points += stageResults.getStagePoints();
                }
            }
        }
        return points;
    }
    public int getRaceMountainPoints(Race currentRace) {
        int points = 0;
        Stage[] stages = currentRace.loadStages();
        for (Stage stage : stages) {
            int stageId = stage.getStageId();
            for (stageResults stageResults : stageResults) {
                if (stageResults.getStageId() == stageId) {
                    points += stageResults.getStageMountainPoints();
                }
            }
        }
        return points;
    }
    public int getStageMountainPoints(int stageId) {
        for (stageResults stageResults : stageResults) {
            if (stageResults.getStageId() == stageId) {
                return stageResults.getStageMountainPoints();
            }
        }
        return 0;
    }
    public LocalTime getElapsedTime() {
        LocalTime elapsedTime = LocalTime.of(0, 0, 0);
        // Loop through the stage results and add the time to the total elapsed time
        for (stageResults stageResults : stageResults) {
            elapsedTime = elapsedTime.plusHours(stageResults.getStageTime().getHour());
            elapsedTime = elapsedTime.plusMinutes(stageResults.getStageTime().getMinute());
            elapsedTime = elapsedTime.plusSeconds(stageResults.getStageTime().getSecond());
        }
        return elapsedTime;
    }
    public LocalTime getStageElapsedTime(int stageId) {
        LocalTime elapsedTime = LocalTime.of(0, 0, 0);
        // Loop through the stage results and add the time to the total elapsed time
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
                stageResults.addStageMountainPoints(points);
            }
        }        
    }
    public void addStageSprintPoints(int stageId, int points) {
        for (stageResults stageResults : stageResults) {
            if (stageResults.getStageId() == stageId) {
                stageResults.addStageSprintPoints(points);
                
            }
        }        
    }
    public void setTeamId(int teamId) {
        this.teamID = teamId;
    }
    public LocalTime getRiderRaceElapsedTime(Race currentRace) {
        LocalTime elapsedTime = LocalTime.of(0, 0, 0);
        Stage[] stages = currentRace.loadStages();
        for (Stage stage : stages) {
            int stageId = stage.getStageId();
            for (stageResults stageResults : stageResults) {
                if (stageResults.getStageId() == stageId) {
                    elapsedTime = elapsedTime.plusHours(stageResults.getStageTime().getHour());
                    elapsedTime = elapsedTime.plusMinutes(stageResults.getStageTime().getMinute());
                    elapsedTime = elapsedTime.plusSeconds(stageResults.getStageTime().getSecond());
                }
            }
        }
        return elapsedTime;
    }


}
