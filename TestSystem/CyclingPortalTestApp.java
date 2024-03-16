import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
		CyclingPortal portal2 = new CyclingPortalImpl();

		assert (portal1.getRaceIds().length == 0)
				: "Innitial Portal not empty as required or not returning an empty array.";
		assert (portal1.getTeams().length == 0)
				: "Innitial Portal not empty as required or not returning an empty array.";

		try {
			portal1.createTeam("TeamOne", "My favorite");
			portal2.createTeam("TeamOne", "My favorite");
		} catch (IllegalNameException e) {
			e.printStackTrace();
		} catch (InvalidNameException e) {
			e.printStackTrace();
		}

		assert (portal1.getTeams().length == 1)
				: "Portal1 should have one team.";

		assert (portal2.getTeams().length == 1)
				: "Portal2 should have one team.";

		
		// Test the creation of race, both exceptions are successful
		System.out.println("-------------------Testing the creation");
		try {
            int raceId = portal1.createRace("Race3", "race3 description");
            if (raceId != -1) {
                System.out.println("Race created successfully. Race ID: " + raceId);
            } else {
                System.out.println("Failed to create race.");
            }
        } catch (IllegalNameException e) {
            System.out.println("IllegalNameException: " + e.getMessage());
        } catch (InvalidNameException e) {
            System.out.println("InvalidNameException: " + e.getMessage());
        }

		// Test the removal of a race
		// TODO - test the removal

		// Test the addition of a stage
		System.out.println("-------------------Testing the addition of a stage");
		try{
			LocalDateTime time = LocalDateTime.now();
			int raceId = 0;
			int stageId = portal1.addStageToRace(raceId, "Stage1", "Stage1 description", 10, time, StageType.HIGH_MOUNTAIN);
			if (stageId != -1) {
				System.out.println("Stage added successfully. Stage ID: " + stageId);
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
		// TODO - The correct length is not being returned
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
		// TODO - Returns ("Stages: [I@1b6d3586")
		System.out.println("-------------------Testing the getRaceStages");
		try {
			int radeId = 0;
			int[] stages = portal1.getRaceStages(radeId);
			if (stages != null) {
				System.out.println("Stages: " + stages);
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
			int climbId = portal1.addCategorizedClimbToStage(stageId, 1.0, CheckpointType.C4, 4.7, 5.0);
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
		// TODO - Sprint is not being added as Mountain stage
		System.out.println("-------------------Testing the addition of a sprint");
		try {
			int stageId = 0;
			int checkpointId = portal1.addIntermediateSprintToStage(stageId, 1.5);
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
		// TODO - Same issue with the getRaceStages
		System.out.println("-------------------Testing the getStageCheckpoints");
		try {
			int stageId = 0;
			int[] checkpoints = portal1.getStageCheckpoints(stageId);
			if (checkpoints != null) {
				System.out.println("Checkpoints: " + checkpoints);
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
	}



}
