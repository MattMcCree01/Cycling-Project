import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

import cycling.*;


/**
 * A short program to illustrate an app testing some minimal functionality of a
 * concrete implementation of the CyclingPortal interface -- note you
 * will want to increase these checks, and run it on your CyclingPortalImpl class
 * (not the BadCyclingPortal class).
 *
 * 
 * @author Diogo Pacheco
 * @version 2.0
 */
public class CyclingPortalTestApp {

	/**
	 * Test method.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		System.out.println("The system compiled and started the execution...");

		// TODO replace BadMiniCyclingPortalImpl by CyclingPortalImpl
		CyclingPortal portal1 = new CyclingPortalImpl();

		// Test the creation of race, both exceptions are successful
		System.out.println("-------------------Testing the creation of a race");
		try {
            int raceId = portal1.createRace("Race1", "race1 description");
			int raceId2 = portal1.createRace("Race2-ToBeDeleted", "race2 description-ToBeDeleted");
            if (raceId != -1 & raceId2 != -1) {
                System.out.println("Race created successfully. Race ID: " + raceId + " and " + raceId2);
            } else {
                System.out.println("Failed to create race.");
            }
        } catch (IllegalNameException e) {
            System.out.println("IllegalNameException: " + e.getMessage());
        } catch (InvalidNameException e) {
            System.out.println("InvalidNameException: " + e.getMessage());
        }

		// Test the getRaces
		System.out.println("-------------------Testing the getRaces");
		System.out.println(Arrays.toString(portal1.getRaceIds()));
		
		// Test the removal of a race
		// TODO - test the removal

		// Test the addition of a stage
		System.out.println("-------------------Testing the addition of a stage");
		try{
			LocalDateTime time = LocalDateTime.now();
			int raceId = 0;
			int stageId = portal1.addStageToRace(raceId, "Stage1", "Stage1 description", 10, time, StageType.HIGH_MOUNTAIN);
			int stageId2 = portal1.addStageToRace(raceId, "Stage2", "Stage2 description", 10, time, StageType.HIGH_MOUNTAIN);
			if (stageId != -1 & stageId2 != -1) {
				System.out.println("Stage added successfully. Stage ID: " + stageId + " and " + stageId2);
			} else {
				System.out.println("Failed to add stage.");
			}
		} catch (IDNotRecognisedException e) {
			e.printStackTrace();
		} catch (IllegalNameException e) {
			e.printStackTrace();
		} catch (InvalidNameException e) {
			e.printStackTrace();
		} catch (InvalidLengthException e) {
			e.printStackTrace();
		}

		// Test the get stage length
		System.out.println("-------------------Testing the get stage length");
		try{
			int stageId = 0;
			double length = portal1.getStageLength(stageId);
			if (length != -1) {
				System.out.println("Stage length: " + length);
			} else {
				System.out.println("Failed to get stage length.");
			}
		} catch (IDNotRecognisedException e) {
			e.printStackTrace();
		}

		// Test the get number of stages
		System.out.println("-------------------Testing the get number of stages");
		try{
			int raceId = 0;
			int numStages = portal1.getNumberOfStages(raceId);
			if (numStages != -1) {
				System.out.println("Number of stages: " + numStages);
			} else {
				System.out.println("Failed to get number of stages.");
			}
		} catch (IDNotRecognisedException e) {
			e.printStackTrace();
		}

		// Test the viewing of race details, expection is successful
		System.out.println("-------------------Testing the viewing of race details");
		try {
			String details = portal1.viewRaceDetails(0);
			if (details != null) {
				System.out.println(details);
			} else {
				System.out.println("Failed to view details");
			}
		} catch (IDNotRecognisedException e) {
			e.printStackTrace();
		}

		/// Test the getRaceStages, exception is successful
		System.out.println("-------------------Testing the getRaceStages");
		try {
			int raceId = 0;
			int[] stages = portal1.getRaceStages(raceId);
			if (stages != null) {
				System.out.println("Stages: " + Arrays.toString(stages));
			} else {
				System.out.println("Failed to get stages");
				
			}
		} catch (IDNotRecognisedException e) {
			e.printStackTrace();
		}

		// Test removeStage
		// TODO - test the removal of stages

		// Test the addition of a climb checkpoint
		System.out.println("-------------------Testing the addition of a climb");
		try {
			int stageId = 0;
			int climbId = portal1.addCategorizedClimbToStage(stageId, 1.0, CheckpointType.C4, 4.7, 2.0);
			int climbId2 = portal1.addCategorizedClimbToStage(stageId, 2.0, CheckpointType.C2, 8.0, 5.0);
			int climbId3 = portal1.addCategorizedClimbToStage(stageId, 2.5, CheckpointType.C1, 5.0, 20.0);
			if (climbId != -1) {
				System.out.println("Climb added successfully. Climb ID: " + climbId);
			} else {
				System.out.println("Failed to add climb.");
			}
		} catch (IDNotRecognisedException e) {
			e.printStackTrace();
		} catch (InvalidLocationException e) {
			e.printStackTrace();
		} catch (InvalidStageStateException e) {
			e.printStackTrace();
		} catch (InvalidStageTypeException e) {
			e.printStackTrace();
		}

		// Test the addition of a Sprint chechpoint
		System.out.println("-------------------Testing the addition of a sprint");
		try {
			int stageId = 0;
			int checkpointId = portal1.addIntermediateSprintToStage(stageId, 3);
			int checkpointId2 = portal1.addIntermediateSprintToStage(stageId, 3.5);
			if (checkpointId != -1) {
				System.out.println("Sprint added successfully. Sprint ID: " + checkpointId);
			} else {
				System.out.println("Failed to add sprint.");
			}
		} catch (IDNotRecognisedException e) {
			e.printStackTrace();
		} catch (InvalidLocationException e) {
			e.printStackTrace();
		} catch (InvalidStageStateException e) {
			e.printStackTrace();
		} catch (InvalidStageTypeException e) {
			e.printStackTrace();
		}

		// Test the removal of a checkpoint
		// TODO - test the removal of checkpoints

		// Test the getStageCheckpoints
		System.out.println("-------------------Testing the getStageCheckpoints");
		try {
			int stageId = 0;
			int[] checkpoints = portal1.getStageCheckpoints(stageId);
			if (checkpoints != null) {
				System.out.println("Checkpoints: " + Arrays.toString(checkpoints));
			} else {
				System.out.println("Failed to get checkpoints");
			}
		} catch (IDNotRecognisedException e) {
			e.printStackTrace();
		}

		// Test the conclusion of stage preparation
		System.out.println("-------------------Testing the conclusion of stage preparation");
		try {
			int stageId = 0;
			portal1.concludeStagePreparation(stageId);
			System.out.println("Stage preparation concluded successfully.");
		} catch (IDNotRecognisedException e) {
			e.printStackTrace();
		} catch (InvalidStageStateException e) {
			e.printStackTrace();
		}

		// Test the creation of a team
		System.out.println("-------------------Testing the creation of a team");
		try {
			int teamId = portal1.createTeam("TestTeamName", "TestTeam description");
			int teamId2 = portal1.createTeam("TestTeamName-ToBeDeleted", "TestTeam description-ToBeDeleted");
			if (teamId != -1 & teamId2 != -1) {
				System.out.println("Team created successfully. Team ID: " + teamId + " and " + teamId2);
			} else {
				System.out.println("Failed to create team.");
			}
		} catch (IllegalNameException e) {
			e.printStackTrace();
		} catch (InvalidNameException e) {
			e.printStackTrace();
		}

		// Test the getTeams
		System.out.println("-------------------Testing the getTeams");
		System.out.println("Teams: " + Arrays.toString(portal1.getTeams()));

		// Test the removal of a team
		System.out.println("-------------------Testing the removal of a team");
		try {
			int teamId = 2;
			portal1.removeTeam(teamId);
			System.out.println("Team removed successfully.");
		} catch (IDNotRecognisedException e) {
			e.printStackTrace();
		}

		// Test the getTeamRiders
		System.out.println("-------------------Testing the getTeamRiders");
		try {
			int teamId = 1;
			int[] riders = portal1.getTeamRiders(teamId);
			if (riders != null) {
				System.out.println("Riders: " + Arrays.toString(riders));
			} else {
				System.out.println("Failed to get riders");
			}
		} catch (IDNotRecognisedException e) {
			e.printStackTrace();
		}		

		// Test the creation of a rider
		System.out.println("-------------------Testing the creation of a rider");
		try {
			int riderId = portal1.createRider(1, "TestRider1", 2000);
			int riderId2 = portal1.createRider(1, "Test", 1990);
			if (riderId != -1) {
				System.out.println("Rider created successfully. Rider ID: " + riderId);
			} else {
				System.out.println("Failed to create rider.");
			}
		} catch (IDNotRecognisedException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		// Test the removal of a rider
		// TODO - test the removal of riders

		// Test the registering of rider results in stage
		System.out.println("-------------------Testing the registering of rider results in stage");
		try{
			int stageId = 0;
			int riderId = 0;
			LocalTime[] times = new LocalTime[7];
			times[0] = LocalTime.of(9, 0);
			times[1] = LocalTime.of(9, 15);
			times[2] = LocalTime.of(9, 30);
			times[3] = LocalTime.of(9, 45);
			times[4] = LocalTime.of(10, 0);
			times[5] = LocalTime.of(10, 15);
			times[6] = LocalTime.of(10, 30);

			portal1.registerRiderResultsInStage(stageId, riderId, times);
		} catch (IDNotRecognisedException e) {
			e.printStackTrace();
		} catch (DuplicatedResultException e) {
			e.printStackTrace();
		} catch (InvalidCheckpointTimesException e) {
			e.printStackTrace();
		} catch (InvalidStageStateException e) {
			e.printStackTrace();
		}

		// Test the getStageResults
		System.out.println("-------------------Testing the getStageResults");
		try {
			int stageId = 0;
			int riderId = 0;
			LocalTime[] results = portal1.getRiderResultsInStage(stageId, riderId);
			if (results != null) {
				System.out.println("Results: " + Arrays.toString(results));
			} else {
				System.out.println("Failed to get results");
			}
		} catch (IDNotRecognisedException e) {
			e.printStackTrace();
		}

		try{
			int stageId = 0;
			int riderId = 1;
			LocalTime[] times = new LocalTime[7];
			times[0] = LocalTime.of(9, 0);
			times[1] = LocalTime.of(9, 10);
			times[2] = LocalTime.of(9, 20);
			times[3] = LocalTime.of(9, 40);
			times[4] = LocalTime.of(10, 5);
			times[5] = LocalTime.of(10, 10);
			times[6] = LocalTime.of(10, 25);

			portal1.registerRiderResultsInStage(stageId, riderId, times);
		} catch (IDNotRecognisedException e) {
			e.printStackTrace();
		} catch (DuplicatedResultException e) {
			e.printStackTrace();
		} catch (InvalidCheckpointTimesException e) {
			e.printStackTrace();
		} catch (InvalidStageStateException e) {
			e.printStackTrace();
		}

		// Test the getStageResults
		System.out.println("-------------------Testing the getStageResults");
		try {
			int stageId = 0;
			int riderId = 1;
			LocalTime[] results = portal1.getRiderResultsInStage(stageId, riderId);
			if (results != null) {
				System.out.println("Results: " + Arrays.toString(results));
			} else {
				System.out.println("Failed to get results");
			}
		} catch (IDNotRecognisedException e) {
			e.printStackTrace();
		}
		//Test getRidersPointsInRace
		System.out.println("-------------------Testing the getRidersPointsInRace");
		try {
			int raceId = 0;
			int riderId = 0;
			int[] points = portal1.getRidersPointsInRace(raceId);
			if (points.length > 0) {
				System.out.println("Points: " + points);
			} else {
				System.out.println("Failed to get points");
			}
		} catch (IDNotRecognisedException e) {
			e.printStackTrace();
		}
	}

	

}
