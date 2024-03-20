package cycling;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;


/**
 * BadCyclingPortal is a minimally compiling, but non-functioning implementor
 * of the CyclingPortal interface.
 * 
 * @author Diogo Pacheco
 * @version 2.0
 *
 */
public class CyclingPortalImpl implements CyclingPortal {
	public ArrayList<Race> races = new ArrayList<Race>();
	public ArrayList<Rider> riders = new ArrayList<Rider>();
	public ArrayList<Team> teams = new ArrayList<Team>();
	

	@Override
	public int[] getRaceIds() {
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
		if (name == null || name.contains(" ")) {
			throw new IllegalNameException("Name is null or contains whitespace");
		} 

		if (name.length() < 1) {
			throw new InvalidNameException("Name is empty");
		}

		if (name.length() > 30) {
			throw new InvalidNameException("Name is too long");
		}
		for (Race race : races) {
			if (race.getRaceName().equals(name)) {
				throw new InvalidNameException("Name already exists");
			}
		}
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
		for (Race race : races) {
			if (race.getRaceId() == raceId) {
				check = true;
				raceDetails = race.viewRaceDetails();
				break;
			}
		}
		if (!check) {
			throw new IDNotRecognisedException("ID not recognised");
		}
		return raceDetails;
		
	}

	@Override
	public void removeRaceById(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub 
	}

	@Override
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
		// the ID you're looking for
		Race foundRace = null;

		for (Race race : races) {
			if (race.getRaceId() == raceId) {
				foundRace = race;
				break;
			}
		}

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
		foundRace.addStageToRace(raceId, stageName, description, length, startTime.toLocalTime(), type);
		foundRace.updateRaceLength();
		Stage[] stages = foundRace.loadStages();
		return stages[stages.length - 1].getStageId();
	}

	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
		Race foundRace = null;
		for (Race race : races) {
			if (race.getRaceId() == (raceId)) {
				foundRace = race;
			}
		}
		if (foundRace == null) {
			throw new IDNotRecognisedException("Race Id doesn't exist");
		}
		return foundRace.getStageIds();
	}

	@Override
	public double getStageLength(int stageId) throws IDNotRecognisedException {
		// TODO Annotate
		Stage foundStage = null;
		for (Race race : races) {
			for (Stage stage : race.loadStages()) {
				if (stage.getStageId() == stageId) {
					foundStage = stage;
					break;
				}
			}
		}

		if (foundStage != null) {
			return foundStage.getStageLength();
		} else {
			throw new IDNotRecognisedException("ID not recognised");
		}
	}

	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public int addCategorizedClimbToStage(int stageId, Double location, CheckpointType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
			InvalidStageTypeException {
		Stage foundStage = null;
		for (Race race : races) {
			for (Stage stage : race.loadStages()) {
				if (stage.getStageId() == stageId) {
					foundStage = stage;
					break;
				}
			}
		}
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
				for (Race race : races) {
					for (Stage stage : race.loadStages()) {
						if (stage.getStageId() == stageId) {
							foundStage = stage;
							break;
						}
					}
				}
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
		// TODO Annotate
		Stage foundStage = null;
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

		if (foundStage != null) {
			foundStage.removeCheckpointFromStage(checkpointId);
		} else {
			throw new IDNotRecognisedException("ID not recognised");
		}
	}

	@Override
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
		// TODO Annotate
		for (Race race : races) {
			for (Stage stage : race.loadStages()) {
				if (stage.getStageId() == stageId) {
					stage.concludeStagePreparation(stageId);
					return;
				}
			}
		}
		throw new IDNotRecognisedException("ID not recognised");
	}

	@Override
	public int[] getStageCheckpoints(int stageId) throws IDNotRecognisedException {
		// TODO Annotate
		for (Race race : races) {
			for (Stage stage : race.loadStages()) {
				if (stage.getStageId() == stageId) {
					return stage.getStageCheckpoints(stageId);
				}
			}
		}
		throw new IDNotRecognisedException("ID not recognised");
	}

	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
		// TODO Annotate
		if (name.length() < 1) {
			throw new InvalidNameException("Name is empty");
		} else if (name.length() > 30) {
			throw new InvalidNameException("Name is too long");
		} else if (name.contains(" ")) {
			throw new InvalidNameException("Name contains whitespace");
		}
		
		for (Team team : teams) {
			if (team.getTeamName().equals(name)) {
				throw new InvalidNameException("Name already exists");
			}
		}

		teams.add(new Team(name, description));
		return teams.get(teams.size() - 1).getTeamId();
	}

	@Override
	public void removeTeam(int teamId) throws IDNotRecognisedException {
		// TODO Annotate
		boolean found = false;
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
		if (!found) {
			throw new IDNotRecognisedException("Team ID not recognised");
		}
	}

	@Override
	public int[] getTeams() {
		// TODO Annotate
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
		for (Team team : teams) {
			if (team.getTeamId() == teamId) {
				idCheck = true;
				break;
			}
		}if (!idCheck) {
			throw new IDNotRecognisedException("ID not recognised");
		}
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
		for (Team team : teams) {
			if (team.getTeamId() == teamID) {
				idCheck = true;
				break;
			}
		}
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
		
		riders.add(new Rider(teamID, name, yearOfBirth));
		return 0;
	}

	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointTimesException,
			InvalidStageStateException {
		boolean stageIdCheck = false;
		Rider currentRider = null;
		Stage currentStage = null;
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
		if (checkpoints.length != 2 * currentRider.getStageResults().length) {
			throw new InvalidCheckpointTimesException("Invalid checkpoint times");
		}
		if (currentStage.getStageStatus() != "waiting for results") {
			throw new InvalidStageStateException("Invalid stage state");
		}

		currentRider.registerRiderResultsInStage(stageId, checkpoints);
		currentStage.addParticipatingRider(currentRider);

		Checkpoint[] currentCheckpoints = currentStage.getCheckpoints(); 
		for (int i = 0; i < checkpoints.length; i++) {
			currentCheckpoints[i].addRiderToRank(currentRider, checkpoints[i]);
			currentCheckpoints[i].updateMountainPoints();
			currentCheckpoints[i].updateSprintPoints();
		}
		
		

		
	}
	@Override
	public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		boolean stageIdCheck = false;
		Rider currentRider = null;
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
		return currentRider.getRiderResultsInStage(stageId);
		
	}

	@Override
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
		boolean stageIdCheck = false;
		Stage currentStage = null;
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
		ArrayList<Rider> orderedRiders = new ArrayList<Rider>();
		for (Rider rider : currentStage.getParticipatingRiders()) {
			if (orderedRiders.size() == 0) {
				orderedRiders.add(rider);
			} else {
				for (int i = 0; i < orderedRiders.size(); i++) {
					if (rider.getStageElapsedTime(stageId).isBefore(orderedRiders.get(i).getStageElapsedTime(stageId))) {
						orderedRiders.add(i, rider);
						break;
					}
				}
				
			}
		}

		return orderedRiders.stream().mapToInt(i -> i.getRiderId()).toArray();
		
			
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
		return currentStage.getRiderPoints();
	}
		

	@Override
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
		boolean stageIdCheck = false;
		Stage currentStage = null;
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
		return currentStage.getRiderMountainPoints();
	}
	

	@Override
	public void eraseCyclingPortal() {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveCyclingPortal(String filename) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeRaceByName(String name) throws NameNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
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
}

