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
    /**
     * sets the counter after a load
     * @param counter
     */
    public static void setCounter(int counter) {
        idCounter = counter;
    }
    /**
     * returns the rider Id
     * @return an int of id
     */
    public int getRiderId() {
        return id;
    }
    /**
     * returns the teamId of the rider
     * @return an int of id
     */
    public int getTeamId() {
        return teamID;
    }
    /**
     * rturns the name of the rider
     * @return a string containing name
     */
    public String getName(){
        return name;
    }
    /**
     * returns the birth year of the rider
     * @return an int of the year
     */
    public int getBirthYear(){
        return BirthYear;
    }
    /**
     * returns the stage results for the rider 
     * @return an array of stageResults
     */
    public stageResults[] getStageResults() {
        return stageResults.toArray(new stageResults[stageResults.size()]);
    }
    /**
     * removes the stage results for a specific stage from the rider
     * @param stageId
     */
    public void removeStageResults(int stageId) {
        for (int i = 0; i < stageResults.size(); i++) {
            if (stageResults.get(i).getStageId() == stageId) {
                stageResults.remove(i);
            }
        }
    }
    /**
     * returns the list of times within a specific stage
     * @param stageId
     * @return a list of times
     */
    public LocalTime[] getSpecificStageResults(int stageId) {
        // Returns the results of a specific stage
        for (stageResults stageResult : stageResults) {
            if (stageResult.getStageId() == stageId) {
                return stageResult.getResults();
            }
        }
        return null;
    }
    /**
     * sets the mountain points for a stage
     * @param stageId
     * @param points
     */
    public void setRiderMountainPoints(int stageId, int points) {
        for (stageResults stageResult : stageResults) {
            if (stageResult.getStageId() == stageId) {
                stageResult.setStageMountainPoints(points);
            }
        }
    }
    /**
     * sets the sprint points for a specific stage
     * @param stageId
     * @param points
     */
    public void setRiderSprintStagePoints(int stageId, int points) {
        for (stageResults stageResult : stageResults) {
            if (stageResult.getStageId() == stageId) {
                stageResult.setStageSprintPoints(points);
            }
        }
    }
    /**
     * registers the riders results in the stage
     * @param stageId
     * @param checkpoints
     */
    public void registerRiderResultsInStage(int stageId, LocalTime... checkpoints) {
        stageResults.add(new stageResults(stageId, checkpoints));
        
    }
    /**
     * gets the results for a certain stage
     * @param stageId
     * @return an array of times
     */
    public LocalTime[] getRiderResultsInStage(int stageId) {
        for (stageResults stageResults : stageResults) {
            if(stageResults.getStageId() == stageId) {
                return stageResults.getResults();
            }
        }
        return null;

    }
    /**
     * returns the elapsed time of a rider in a certain stage
     * @param stageID
     * @return
     */
    public LocalTime[] getRiderAdjustedElapsedTimeInStage(int stageID) {
        return null;
    }
    /**
     * gets the race points of the rider
     * @param currentRace
     * @return an integer of the points
     */
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
    /**
     * gets the mountain points for a specific race
     * @param currentRace
     * @return an int of the mountain points
     */
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
    /**
     * gets the mountain points of a specific stage
     * @param stageId
     * @return an integer of the mountain points for the stage
     */
    public int getStageMountainPoints(int stageId) {
        for (stageResults stageResults : stageResults) {
            if (stageResults.getStageId() == stageId) {
                return stageResults.getStageMountainPoints();
            }
        }
        return 0;
    }
    /**
     * gets the elpased time for a rider
     * @return a time of the elapsed time
     */
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
    /**
     * gets the elapsed time for a stage
     * @param stageId
     * @return a time of elapsed time
     */
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
    /**
     * returns the stage points for the rider
     * @param stageId
     * @return an integer of the points
     */
    public int getRiderStagePoints(int stageId) {
        for (stageResults stageResults : stageResults) {
            if (stageResults.getStageId() == stageId) {
                return stageResults.getStagePoints();
            }
        }
        return 0;
    }
    /**
     * gets the elapsed time in a checkpoint
     * @param checkpointId
     * @return returns a time of the elapsed time
     */
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
    /**
     * adds mountain points to the stage
     * @param stageId
     * @param points
     */
    public void addStageMountainPoints(int stageId, int points) {
        for (stageResults stageResults : stageResults) {
            if (stageResults.getStageId() == stageId) {
                stageResults.addStageMountainPoints(points);
            }
        }        
    }
    /**
     * adds sprint points for the stage
     * @param stageId
     * @param points
     */
    public void addStageSprintPoints(int stageId, int points) {
        for (stageResults stageResults : stageResults) {
            if (stageResults.getStageId() == stageId) {
                stageResults.addStageSprintPoints(points);
                
            }
        }        
    }
    /**
     * sets the teamId for the rider
     * @param teamId
     */
    public void setTeamId(int teamId) {
        this.teamID = teamId;
    }
    /**
     * gets the elapsed time for the race
     * @param currentRace
     * @return a time of the elapsed time
     */
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
