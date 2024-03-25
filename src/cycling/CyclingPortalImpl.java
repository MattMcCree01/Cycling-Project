package cycling;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;


/**
 * Implementation of the CyclingPortal interface.
 */
public class CyclingPortalImpl implements CyclingPortal {
	public ArrayList<Race> races = new ArrayList<Race>();
	public ArrayList<Rider> riders = new ArrayList<Rider>();
	public ArrayList<Team> teams = new ArrayList<Team>();
	

	@Override
	public int[] getRaceIds() {
		// Returns the IDs of races
		int[] raceIds = new int[races.size()];
		for(int i = 0; i < races.size(); i++) {
			raceIds[i] = races.get(i).getRaceId();
		}
		if(raceIds.length > 0) {
			return raceIds;
		}	
		else {
			return new int[] {};
		}
	}

	@Override
	public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
		// Check if the name is null or contains whitespace
		if (name == null || name.contains(" ")) {
			throw new IllegalNameException("Name is null or contains whitespace");
		} 

		// Check if the name is empty
		if (name.length() < 1) {
			throw new InvalidNameException("Name is empty");
		}

		// Check if the name is too long
		if (name.length() > 30) {
			throw new InvalidNameException("Name is too long");
		}

		// Check if the name already exists
		for (Race race : races) {
			if (race.getRaceName().equals(name)) {
				throw new InvalidNameException("Name already exists");
			}
		}

		// Add the new race to the list
		if(races.add(new Race(name, description))){
			return races.get(races.size() - 1).getRaceId();
		}
		else{
			return -1;
		}
	}

	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
		boolean check = false;
		String raceDetails = "";
		// Check if the race exists
		for (Race race : races) {
			if (race.getRaceId() == raceId) {
				check = true;
				raceDetails = race.viewRaceDetails();
				break;
			}
		}

		// Throw an exception if race exists
		if (!check) {
			throw new IDNotRecognisedException("ID not recognised");
		}
		return raceDetails;
		
	}

	@Override
	public void removeRaceById(int raceId) throws IDNotRecognisedException {
		Race foundRace = null;
		// Search if race exists
		for (Race race : races) {
			if (race.getRaceId() == raceId) {
				foundRace = race;
				break;
			}
		}

		// If exists remove it
		if (foundRace != null) {
			for (Stage stage : getRaceById(raceId).loadStages()) {
				for (Rider rider : stage.getParticipatingRiders()) {
					rider.removeStageResults(stage.getStageId());
				}

				stage.deleteAllCheckpoints();
			}	

			races.remove(getRaceById(raceId));
		} else {
			throw new IDNotRecognisedException("ID not recognised");
		}
	}

	@Override
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
		Race foundRace = null;

		// Search if race exists
		for (Race race : races) {
			if (race.getRaceId() == raceId) {
				foundRace = race;
				break;
			}
		}

		// If exists return the number of stages
		if (foundRace != null) {
			return foundRace.getNumberOfStages();
		} else {
			throw new IDNotRecognisedException("ID not recognised");
		}
		
	}

	@Override
	public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime,
			StageType type)
			throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
		Race foundRace = null;
		for (Race race : races) {
			if (race.getRaceId() == (raceId)) {
				foundRace = race;
			}
		if (foundRace == null) {
				throw new IDNotRecognisedException("Race Id doesn't exist");
		}
			
		}
		if (length < 5) {
			throw new InvalidLengthException("Stage must be more than 5km");
		}
		if (stageName == null || stageName.contains(" ")) {
			throw new IllegalNameException("Name is null or contains whitespace");
		} 

		if (stageName.length() < 1) {
			throw new InvalidNameException("Name is empty");
		}

		if (stageName.length() > 30) {
			throw new InvalidNameException("Name is too long");
		}
		for (Stage stage: foundRace.loadStages()) {
			if (stage.getStageName().equals(stageName)) {
				throw new InvalidNameException("Name already exists");
			}
		}

		// Add race to the list and update the length
		foundRace.addStageToRace(raceId, stageName, description, length, startTime.toLocalTime(), type);
		foundRace.updateRaceLength();
		Stage[] stages = foundRace.loadStages();
		return stages[stages.length - 1].getStageId();
	}

	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
		Race foundRace = null;
		// Check if race exists
		for (Race race : races) {
			if (race.getRaceId() == (raceId)) {
				foundRace = race;
			}
		}
		// If exists return the stage IDs
		if (foundRace == null) {
			throw new IDNotRecognisedException("Race Id doesn't exist");
		}
		return foundRace.getStageIds();
	}

	@Override
	public double getStageLength(int stageId) throws IDNotRecognisedException {
		Stage foundStage = null;
		// Search for the stage
		for (Race race : races) {
			for (Stage stage : race.loadStages()) {
				if (stage.getStageId() == stageId) {
					foundStage = stage;
					break;
				}
			}
		}

		// If found return the length
		if (foundStage != null) {
			return foundStage.getStageLength();
		} else {
			throw new IDNotRecognisedException("ID not recognised");
		}
	}

	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {
		Stage foundStage = null;
		Race foundRace = null;
		// searching for stage and race Id
		for (Race race : races) {
			for (Stage stage : race.loadStages()) {
				if (stage.getStageId() == stageId) {
					foundStage = stage;
					foundRace = race;
					break;
				}
			}
		}

		// if stage is found, remove it
		if (foundStage != null) {
			for (Rider rider : foundStage.getParticipatingRiders()) {
				rider.removeStageResults(stageId);
			}
			foundStage.deleteAllCheckpoints();
			foundRace.removeStage(foundStage);
		} else {
			throw new IDNotRecognisedException("ID not recognised");
		}
	}

	@Override
	public int addCategorizedClimbToStage(int stageId, Double location, CheckpointType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
			InvalidStageTypeException {
		Stage foundStage = null;
		// Search for the stage
		for (Race race : races) {
			for (Stage stage : race.loadStages()) {
				if (stage.getStageId() == stageId) {
					foundStage = stage;
					break;
				}
			}
		}
		// Check if the stage exists
		if (foundStage == null) {
			throw new IDNotRecognisedException("ID not recognised");
		} else if (location < 0 || location > foundStage.getStageLength()) {
			throw new InvalidLocationException("Location is invalid");
		} else if (foundStage.getStageType() == StageType.TT) {
			throw new InvalidStageTypeException("time trial stages do not have checkpoints");
		} else {
			return foundStage.addClimbToStage(stageId, location, type, averageGradient, length);
		}
	}
	
	@Override
	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
			InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
				Stage foundStage = null;
				// Search for the stage
				for (Race race : races) {
					for (Stage stage : race.loadStages()) {
						if (stage.getStageId() == stageId) {
							foundStage = stage;
							break;
						}
					}
				}
				// Check if the stage exists if so then add the sprint
				if (foundStage == null) {
					throw new IDNotRecognisedException("ID not recognised");
				} else if (location < 0 || location > foundStage.getStageLength()) {
					throw new InvalidLocationException("Location is invalid");
				} else if (foundStage.getStageType() == StageType.TT){
					throw new InvalidStageTypeException("Time trial stages do not have checkpoints");
				} else {
					return foundStage.addSprintToStage(stageId, location);
				}
	}

	@Override
	public void removeCheckpoint(int checkpointId) throws IDNotRecognisedException, InvalidStageStateException {
		Stage foundStage = null;
		// Search for the stage
		for (Race race : races) {
			for (Stage stage : race.loadStages()) {
				for (Checkpoint checkpoint : stage.getCheckpoints()) {
					if (checkpoint.getCheckpointID() == checkpointId) {
						foundStage = stage;
						break;
					}
				}
			}
		}

		// If found remove the checkpoint
		if (foundStage != null) {
			foundStage.removeCheckpointFromStage(checkpointId);
		} else {
			throw new IDNotRecognisedException("ID not recognised");
		}
	}

	@Override
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
		// Search for the stage, if found then conclude the stage preparation
		for (Race race : races) {
			for (Stage stage : race.loadStages()) {
				if (stage.getStageId() == stageId) {
					stage.concludeStagePreparation(stageId);
					return;
				}
			}
		}
		// If not found throw an exception
		throw new IDNotRecognisedException("ID not recognised");
	}

	@Override
	public int[] getStageCheckpoints(int stageId) throws IDNotRecognisedException {
		// Search for the stage, if found then return the checkpoints
		for (Race race : races) {
			for (Stage stage : race.loadStages()) {
				if (stage.getStageId() == stageId) {
					return stage.getStageCheckpoints(stageId);
				}
			}
		}
		// If not found throw an exception
		throw new IDNotRecognisedException("ID not recognised");
	}

	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
		// Check if the name is null or contains whitespace
		if (name.length() < 1) {
			throw new InvalidNameException("Name is empty");
		} else if (name.length() > 30) {
			throw new InvalidNameException("Name is too long");
		} else if (name.contains(" ")) {
			throw new InvalidNameException("Name contains whitespace");
		}
		
		// Check if the name already exists
		for (Team team : teams) {
			if (team.getTeamName().equals(name)) {
				throw new InvalidNameException("Name already exists");
			}
		}

		// Add the new team to the list
		teams.add(new Team(name, description));
		return teams.get(teams.size() - 1).getTeamId();
	}

	@Override
	public void removeTeam(int teamId) throws IDNotRecognisedException {
		boolean found = false;
		// Search for the team, if found then remove it
		for (int i = 0; i < teams.size(); i++) {
			if (teams.get(i).getTeamId() == teamId) {
				for (Rider rider : riders) {
					if (rider.getTeamId() == teamId) {
						rider.setTeamId(0); // Set the current rider's team to 0 ('no team' team)
					}
				}
				teams.remove(i);
				found = true;
				break;
			}
		}
		// If not found throw an exception
		if (!found) {
			throw new IDNotRecognisedException("Team ID not recognised");
		}
	}

	@Override
	public int[] getTeams() {
		// Return the team IDs
		if (teams.size() > 0) {
			int[] teamIDs = new int[teams.size()];
			for (int i = 0; i < teams.size(); i++) {
				teamIDs[i] = teams.get(i).getTeamId();
			}
			return teamIDs;
		} else {
			return new int[] {};
		}
	}

	@Override
	public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
		boolean idCheck = false;
		// Check if the team exists
		for (Team team : teams) {
			if (team.getTeamId() == teamId) {
				idCheck = true;
				break;
			}
		}
		// If exists return the rider IDs
		if (!idCheck) {
			throw new IDNotRecognisedException("ID not recognised");
		}
		// Return the rider IDs
		ArrayList<Integer> teamRiders = new ArrayList<Integer>();
		for (Rider rider : riders) {
			if(rider.getTeamId() == teamId) {
				teamRiders.add(rider.getRiderId());
			}
		}
		return teamRiders.stream().mapToInt(i -> i).toArray();
		
	}

	@Override
	public int createRider(int teamID, String name, int yearOfBirth)
			throws IDNotRecognisedException, IllegalArgumentException {
		boolean idCheck = false;
		// Check if the team exists
		for (Team team : teams) {
			if (team.getTeamId() == teamID) {
				idCheck = true;
				break;
			}
		}
		// Check if the name is null or contains whitespace
		if (!idCheck) {
			throw new IDNotRecognisedException("ID not recognised");
		}
		if (name == null || name.length() < 1) {
			throw new IllegalArgumentException("Name is empty");
		}
		
		if (name.contains(" ")) {
			throw new IllegalArgumentException("Name contains whitespace");
		}
		if (yearOfBirth < 1900 || yearOfBirth > 2020) {
			throw new IllegalArgumentException("Year of birth is invalid");
		}
		
		// Add the new rider to the list
		riders.add(new Rider(teamID, name, yearOfBirth));
		return 0;
	}

	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {
		Rider foundRider = null;
		// Search for the rider
		for (Rider rider : riders) {
			if (rider.getRiderId() == riderId) {
				foundRider = rider;
				break;
			}
		}
		// If found remove the rider
		if (foundRider != null) {
			for (Race race : races) {
				for (Stage stage : race.loadStages()) {
					for (Rider rider : stage.getParticipatingRiders()) {
						if (rider.getRiderId() == riderId) {
							rider.removeStageResults(stage.getStageId());
							stage.removeParticipatingRider(riderId);
						}
					}
				}
			}
			riders.remove(foundRider);
		} else {
			throw new IDNotRecognisedException("ID not recognised");
		}
	}

	@Override
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointTimesException,
			InvalidStageStateException {
		boolean stageIdCheck = false;
		Rider currentRider = null;
		Stage currentStage = null;
		// check if the stage
		for (Race race : races) {
			Stage[] stages = race.loadStages();
			for (Stage stage : stages) {
				if (stage.getStageId() == stageId) {
					stageIdCheck = true;
					currentStage = stage;
					break;
				}
			}	
		}
		if (!stageIdCheck) {
			throw new IDNotRecognisedException("ID not recognised");
		}
		boolean riderIdCheck = false;
		for (Rider rider : riders) {
			if (rider.getRiderId() == riderId) {
				riderIdCheck = true;
				currentRider = rider;
				break;
			}
		}
		if (!riderIdCheck) {
			throw new IDNotRecognisedException("ID not recognised");
		}
		//check for duplicated results
		for (Rider rider : riders) {
			if (rider.getRiderId() == riderId) {
				for (stageResults result : rider.getStageResults()) {
					if (result.getStageId() == stageId) {
						throw new DuplicatedResultException("Duplicated result");
					}
				}
			}
		}
		//check for invalid checkpoint times
		for (LocalTime checkpoint : checkpoints) {
			if (checkpoint.isBefore(LocalTime.of(0, 0, 0))) {
				throw new InvalidCheckpointTimesException("Invalid checkpoint times");
			}
		}
		//check there are 2 more times in results than the number of checkpoints
		if (checkpoints.length != currentStage.getCheckpoints().length + 2) {
			throw new InvalidCheckpointTimesException("Invalid checkpoint times");
		}
		if (currentStage.getStageStatus() != "waiting for results") {
			throw new InvalidStageStateException("Invalid stage state");
		}

		// Register riders results and add to participating riders
		currentRider.registerRiderResultsInStage(stageId, checkpoints);
		currentStage.addParticipatingRider(currentRider);

	}
	@Override
	public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		boolean stageIdCheck = false;
		Rider currentRider = null;
		// check if the stage exists
		for (Race race : races) {
			Stage[] stages = race.loadStages();
			for (Stage stage : stages) {
				if (stage.getStageId() == stageId) {
					stageIdCheck = true;
					break;
				}
			}	
		}
		if (!stageIdCheck) {
			throw new IDNotRecognisedException("ID not recognised");
		}
		boolean riderIdCheck = false;
		// check if the rider exists
		for (Rider rider : riders) {
			if (rider.getRiderId() == riderId) {
				riderIdCheck = true;
				currentRider = rider;
				break;
			}
		}
		if (!riderIdCheck) {
			throw new IDNotRecognisedException("ID not recognised");
		}
		// return the rider results
		return currentRider.getRiderResultsInStage(stageId);
		
	}

	@Override
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		Rider foundRider = null;
		// Search for the rider
		for (Rider rider : riders) {
			if (rider.getRiderId() == riderId) {
				foundRider = rider;
				break;
			}
		}

		// If found remove the rider results
		if (foundRider != null) {
			for (Race race : races) {
				for (Stage stage : race.loadStages()) {
					if (stage.getStageId() == stageId) {
						foundRider.removeStageResults(stageId);
						stage.removeParticipatingRider(riderId);
						break;
					}
				}
				throw new IDNotRecognisedException("Rider not in stage");
			}
		} else {
			throw new IDNotRecognisedException("ID not recognised");
		}
	}

	@Override
	public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
		boolean stageIdCheck = false;
		Stage currentStage = null;
		// check if the stage exists
		for (Race race : races) {
			Stage[] stages = race.loadStages();
			for (Stage stage : stages) {
				if (stage.getStageId() == stageId) {
					stageIdCheck = true;
					currentStage = stage;
					break;
				}
			}	
		}
		if (!stageIdCheck) {
			throw new IDNotRecognisedException("ID not recognised");
		}

		// return the rider ranks
		int[] riderIds = new int[currentStage.getParticipatingRiders().length];
		int i = 0;
		for (Rider rider : currentStage.getParticipatingRiders()) {
			riderIds[i] = rider.getRiderId();
			i++;
		}

		return riderIds;
	}

	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
		boolean stageIdCheck = false;
		Stage currentStage = null;
		// check if the stage exists
		for (Race race : races) {
			Stage[] stages = race.loadStages();
			for (Stage stage : stages) {
				if (stage.getStageId() == stageId) {
					stageIdCheck = true;
					currentStage = stage;
					break;
				}
			}	
		}
		if (!stageIdCheck) {
			throw new IDNotRecognisedException("ID not recognised");
		}
		// update the stage points and return the rider points
		currentStage.updateStagePoints();
		return currentStage.getRiderPoints();
	}	

	@Override
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
		boolean stageIdCheck = false;
		Stage currentStage = null;
		// Check if stage exists
		for (Race race : races) {
			Stage[] stages = race.loadStages();
			for (Stage stage : stages) {
				if (stage.getStageId() == stageId) {
					stageIdCheck = true;
					currentStage = stage;
					break;
				}
			}	
		}
		if (!stageIdCheck) {
			throw new IDNotRecognisedException("ID not recognised");
		}
		// update the stage mountain points and return the rider mountain points
		currentStage.updateStageMountainMountainpoints();
		return currentStage.getRiderStageMountainPoints();
	}
	

	@Override
	public void eraseCyclingPortal() {
		// Clear all the lists and reset the counters
		races.clear();
		riders.clear();
		teams.clear();		

		Race.setCounter(0);
		Team.setCounter(0);
		Rider.setCounter(0);
		Stage.setCounter(0);
		Checkpoint.setCounter(0);
	}

	@Override
	public void saveCyclingPortal(String filename) throws IOException {
		ObjectOutputStream oos = null;
		try {
            // Create ObjectOutputStream to write objects into a file
            oos = new ObjectOutputStream(new FileOutputStream(filename));

            // Write the object (state of CyclingPortal) into the file
            oos.writeObject(this);
        } finally {
            // Close the ObjectOutputStream in a finally block to ensure it always gets closed
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    // Handle IOException while closing ObjectOutputStream
                    System.err.println("Error closing ObjectOutputStream: " + e.getMessage());
                }
            }
        }
	}

	@Override
	public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = null;
        CyclingPortalImpl loadedPortal = null;
        try {
            // Create ObjectInputStream to read objects from the file
            ois = new ObjectInputStream(new FileInputStream(filename));

            // Read the object from the file
            Object obj = ois.readObject();

            // Check if the object is an instance of CyclingPortalImpl
            if (obj instanceof CyclingPortalImpl) {
                // Cast the object to CyclingPortalImpl
                loadedPortal = (CyclingPortalImpl) obj;
				this.races = loadedPortal.races;
				this.riders = loadedPortal.riders;
				this.teams = loadedPortal.teams;

				// Reset the counters
				Race.setCounter(races.size());
				Team.setCounter(teams.size());
				Rider.setCounter(riders.size());

				// Reset the stage and checkpoint counters
				int stageCounter = 0;
				for (Race race : races) {
					for (Stage stage : race.loadStages()) {
						stageCounter++;
					}
				}
				Stage.setCounter(stageCounter);

				int checkpointCounter = 0;
				for (Race race : races) {
					for (Stage stage : race.loadStages()) {
						for (Checkpoint checkpoint : stage.getCheckpoints()) {
							checkpointCounter++;
						}
					}
				}
				Checkpoint.setCounter(checkpointCounter);
            } else {
                throw new IOException("The loaded object is not an instance of CyclingPortal");
            }
        } catch (ClassNotFoundException e) {
            throw e;
		} finally {
            // Close the ObjectInputStream in a finally block to ensure it always gets closed
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    // Handle IOException while closing ObjectInputStream
                    System.err.println("Error closing ObjectInputStream: " + e.getMessage());
                }
            }
        }
	}

	@Override
	public void removeRaceByName(String name) throws NameNotRecognisedException {
		Race foundRace = null;
		// Search for race by name
		for (Race race : races) {
			if (race.getRaceName() == name) {
				foundRace = race;
				break;
			}
		}

		// If found remove race
		if (foundRace != null){
			for (Stage stage : foundRace.loadStages()) {
				for (Rider rider : stage.getParticipatingRiders()) {
					rider.removeStageResults(stage.getStageId());
				}

				stage.deleteAllCheckpoints();
			}	

			races.remove(foundRace);
		} else {
			throw new NameNotRecognisedException("Name not recognised");
		}
	}

	@Override
	public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {
		Race currentRace = getRaceById(raceId);
		Stage[] stages = currentRace.loadStages();
		for (Stage stage : stages) {
			Rider[] riders = stage.getParticipatingRiders();
			for (Rider rider : riders) {
				
				int points = rider.getRacePoints(raceId);
				points
				
			}
		}

		return null;
	}

	@Override
	public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}
	public Stage getStageById(int stageId){
		Stage currentStage = null;
		for (Race race : races) {
			if(race.loadStage(stageId) != null) {
				currentStage = race.loadStage(stageId);
				return currentStage;
				
			}

		}
		return currentStage;

	}
	public Race getRaceById(int raceId){
		Race currentRace = null;
		for (Race race : races) {
			if(race.getRaceId() == raceId) {
				currentRace = race;
				return currentRace;
			}
		}
		return null;
	}
}

