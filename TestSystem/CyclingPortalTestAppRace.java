import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.io.IOException;
import java.lang.reflect.Array;

import cycling.*;

public class CyclingPortalTestAppRace {
    public static void main(String[] args) {
        System.out.println("Trail Race");

        CyclingPortal portal = new CyclingPortalImpl();

        // Create Race
        try {
            int raceId = portal.createRace("TrailRace", "A short race of 4 stages");
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

        // Add stages
        try{
			LocalDate today = LocalDate.now();
            LocalTime time = LocalTime.of(9, 0);
            LocalTime time2 = LocalTime.of(12, 0);
            LocalTime time3 = LocalTime.of(15, 0);
            LocalDateTime stage1StartTime = LocalDateTime.of(today, time);
            LocalDateTime stage2StartTime = LocalDateTime.of(today, time2);
            LocalDateTime stage3StartTime = LocalDateTime.of(today, time3);

			int raceId = 0;
			int stageId = portal.addStageToRace(raceId, "Stage1", "Flat stage ", 20, stage1StartTime, StageType.FLAT);
			int stageId2 = portal.addStageToRace(raceId, "Stage2", "High mountain stage", 10, stage2StartTime, StageType.HIGH_MOUNTAIN);
            int stageId3 = portal.addStageToRace(raceId, "Stage3", "Time trial", 30, stage3StartTime, StageType.FLAT);
			if (stageId != -1 & stageId2 != -1 & stageId3 != -1) {
				System.out.println("Stage added successfully. Stage ID: " + stageId + " and " + stageId2 + " and " + stageId3);
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

        // Add checkpoints
        // Stage 1
        try {
            int stageId = 0;
            int checkpointId = portal.addIntermediateSprintToStage(stageId, 5.0);
            int checkpointId2 = portal.addIntermediateSprintToStage(stageId, 15.0);
            if (checkpointId != -1 & checkpointId2 != -1) {
                System.out.println("Checkpoint added successfully. Checkpoint ID: " + checkpointId + " and " + checkpointId2);
            } else {
                System.out.println("Failed to add checkpoint.");
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

        // Stage 2
        try {
            int stageId = 1;
            int checkpointId3 = portal.addCategorizedClimbToStage(stageId, 2.5, CheckpointType.C4, 3.0, 5.5);
            int checkpointId4 = portal.addCategorizedClimbToStage(stageId, 8.0, CheckpointType.HC, 10.0, 2.0);
            if (checkpointId3 != -1 & checkpointId4 != -1) {
                System.out.println("Checkpoint added successfully. Checkpoint ID: " + checkpointId3 + " and " + checkpointId4);
            } else {
                System.out.println("Failed to add checkpoint.");
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

        // Stage 3
        try {
            int stageId = 2;
            int checkpointId5 = portal.addIntermediateSprintToStage(stageId, 7.5);
            int checkpointId6 = portal.addCategorizedClimbToStage(stageId, 10.0, CheckpointType.C1, 5.0, 5.0);
            if (checkpointId5 != -1 & checkpointId6 != -1) {
                System.out.println("Checkpoint added successfully. Checkpoint ID: " + checkpointId5 + " and " + checkpointId6);
            } else {
                System.out.println("Failed to add checkpoint.");
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


        // Conclude stage set up
        try {
			portal.concludeStagePreparation(0);
            portal.concludeStagePreparation(1);
            portal.concludeStagePreparation(2);
			System.out.println("Stage preparation concluded successfully.");
		} catch (IDNotRecognisedException e) {
			e.printStackTrace();
		} catch (InvalidStageStateException e) {
			e.printStackTrace();
		}


        // Add teams
        try {
			int teamId = portal.createTeam("Team1", "Team 1 of racers");
			int teamId2 = portal.createTeam("Team2", "Team 2 of more racers");
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

        // Add racers
        try {
			int riderId = portal.createRider(1, "Rider1", 2000);
            int riderId1 = portal.createRider(1, "Rider2", 1987);
            int riderId2 = portal.createRider(2, "Rider3", 1990);

			if (riderId != -1 & riderId1 != -1 & riderId2 != -1) {
				System.out.println("Rider created successfully. Rider ID: " + riderId);
			} else {
				System.out.println("Failed to create rider.");
			}
		} catch (IDNotRecognisedException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

        // Register results
        // Rider 1
        // Stage 1
        try{
            LocalTime[] times = new LocalTime[4];
            times[0] = LocalTime.of(9, 00);
            times[1] = LocalTime.of(9, 35);
            times[2] = LocalTime.of(10, 34);
            times[3] = LocalTime.of(11, 23);

            portal.registerRiderResultsInStage(0, 0, times);
        } catch (IDNotRecognisedException e) {
			e.printStackTrace();
		} catch (DuplicatedResultException e) {
			e.printStackTrace();
		} catch (InvalidCheckpointTimesException e) {
			e.printStackTrace();
		} catch (InvalidStageStateException e) {
			e.printStackTrace();
		}

        // Stage 2
        try{
            LocalTime[] times = new LocalTime[4];
            times[0] = LocalTime.of(12, 00);
            times[1] = LocalTime.of(12, 43);
            times[2] = LocalTime.of(13, 20);
            times[3] = LocalTime.of(13, 42);

            portal.registerRiderResultsInStage(1, 0, times);
        } catch (IDNotRecognisedException e) {
			e.printStackTrace();
		} catch (DuplicatedResultException e) {
			e.printStackTrace();
		} catch (InvalidCheckpointTimesException e) {
			e.printStackTrace();
		} catch (InvalidStageStateException e) {
			e.printStackTrace();
		}

        // Stage 3
        try{
            LocalTime[] times = new LocalTime[4];
            times[0] = LocalTime.of(15, 00);
            times[1] = LocalTime.of(15, 22);
            times[2] = LocalTime.of(15, 59);
            times[3] = LocalTime.of(16, 11);

            portal.registerRiderResultsInStage(2, 0, times);
        } catch (IDNotRecognisedException e) {
			e.printStackTrace();
		} catch (DuplicatedResultException e) {
			e.printStackTrace();
		} catch (InvalidCheckpointTimesException e) {
			e.printStackTrace();
		} catch (InvalidStageStateException e) {
			e.printStackTrace();
		}

        // Rider 2
        // Stage 1
        try{
            LocalTime[] times = new LocalTime[4];
            times[0] = LocalTime.of(9, 00);
            times[1] = LocalTime.of(9, 32);
            times[2] = LocalTime.of(10, 32);
            times[3] = LocalTime.of(10, 54);

            portal.registerRiderResultsInStage(0, 1, times);
        } catch (IDNotRecognisedException e) {
			e.printStackTrace();
		} catch (DuplicatedResultException e) {
			e.printStackTrace();
		} catch (InvalidCheckpointTimesException e) {
			e.printStackTrace();
		} catch (InvalidStageStateException e) {
			e.printStackTrace();
		}

        // Stage 2
        try{
            LocalTime[] times = new LocalTime[4];
            times[0] = LocalTime.of(12, 00);
            times[1] = LocalTime.of(12, 39);
            times[2] = LocalTime.of(13, 23);
            times[3] = LocalTime.of(13, 31);

            portal.registerRiderResultsInStage(1, 1, times);
        } catch (IDNotRecognisedException e) {
			e.printStackTrace();
		} catch (DuplicatedResultException e) {
			e.printStackTrace();
		} catch (InvalidCheckpointTimesException e) {
			e.printStackTrace();
		} catch (InvalidStageStateException e) {
			e.printStackTrace();
		}

        // Stage 3
        try{
            LocalTime[] times = new LocalTime[4];
            times[0] = LocalTime.of(15, 00);
            times[1] = LocalTime.of(15, 19);
            times[2] = LocalTime.of(16, 8);
            times[3] = LocalTime.of(16, 26);

            portal.registerRiderResultsInStage(2, 1, times);
        } catch (IDNotRecognisedException e) {
			e.printStackTrace();
		} catch (DuplicatedResultException e) {
			e.printStackTrace();
		} catch (InvalidCheckpointTimesException e) {
			e.printStackTrace();
		} catch (InvalidStageStateException e) {
			e.printStackTrace();
		}

        // Rider 3
        // Stage 1
        try{
            LocalTime[] times = new LocalTime[4];
            times[0] = LocalTime.of(9, 00);
            times[1] = LocalTime.of(9, 24);
            times[2] = LocalTime.of(10, 29);
            times[3] = LocalTime.of(11, 9);

            portal.registerRiderResultsInStage(0, 2, times);
        } catch (IDNotRecognisedException e) {
			e.printStackTrace();
		} catch (DuplicatedResultException e) {
			e.printStackTrace();
		} catch (InvalidCheckpointTimesException e) {
			e.printStackTrace();
		} catch (InvalidStageStateException e) {
			e.printStackTrace();
		}

        // Stage 2
        try{
            LocalTime[] times = new LocalTime[4];
            times[0] = LocalTime.of(12, 00);
            times[1] = LocalTime.of(12, 34);
            times[2] = LocalTime.of(13, 18);
            times[3] = LocalTime.of(13, 33);

            portal.registerRiderResultsInStage(1, 2, times);
        } catch (IDNotRecognisedException e) {
			e.printStackTrace();
		} catch (DuplicatedResultException e) {
			e.printStackTrace();
		} catch (InvalidCheckpointTimesException e) {
			e.printStackTrace();
		} catch (InvalidStageStateException e) {
			e.printStackTrace();
		}

        // Stage 3
        try{
            LocalTime[] times = new LocalTime[4];
            times[0] = LocalTime.of(15, 00);
            times[1] = LocalTime.of(15, 20);
            times[2] = LocalTime.of(16, 04);
            times[3] = LocalTime.of(16, 22);

            portal.registerRiderResultsInStage(2, 2, times);
        } catch (IDNotRecognisedException e) {
			e.printStackTrace();
		} catch (DuplicatedResultException e) {
			e.printStackTrace();
		} catch (InvalidCheckpointTimesException e) {
			e.printStackTrace();
		} catch (InvalidStageStateException e) {
			e.printStackTrace();
		}
        
        // Get results
        System.out.println("Results for rider 1 in stage 2:");
        try {
            LocalTime[] RiderResults = portal.getRiderResultsInStage(1, 0);
            if (RiderResults != null) {
                System.out.println("Results: " + Arrays.toString(RiderResults));
            } else {
                System.out.println("Failed to get results.");
            }
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
        }

        // Get Mountain points
        System.out.println("Mountain points for stage 1, 2 and 3:");
        try {
            int [] RiderMountainPoints = portal.getRidersMountainPointsInStage(1);
            if (RiderMountainPoints != null) {
                System.out.println("Results: " + Arrays.toString(RiderMountainPoints));
            } else {
                System.out.println("Failed to get mountain points.");
            }
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
        }

        try {
            int [] RiderMountainPoints = portal.getRidersMountainPointsInStage(2);
            if (RiderMountainPoints != null) {
                System.out.println("Results: " + Arrays.toString(RiderMountainPoints));
            } else {
                System.out.println("Failed to get mountain points.");
            }
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
        }

        // Get stage points
        System.out.println("Points for stage 1, 2 and 3: ");
        try {
            int [] RiderPoints = portal.getRidersPointsInStage(0);
            if (RiderPoints != null) {
                System.out.println("Results: " + Arrays.toString(RiderPoints));
            } else {
                System.out.println("Failed to get points.");
            }
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
        }

        try {
            int [] RiderPoints = portal.getRidersPointsInStage(1);
            if (RiderPoints != null) {
                System.out.println("Results: " + Arrays.toString(RiderPoints));
            } else {
                System.out.println("Failed to get points.");
            }
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
        }

        try {
            int [] RiderPoints = portal.getRidersPointsInStage(2);
            if (RiderPoints != null) {
                System.out.println("Results: " + Arrays.toString(RiderPoints));
            } else {
                System.out.println("Failed to get points.");
            }
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
        }

        // Get riders rank in stage
        System.out.println("Rank for stage 3:");
        try {
            int [] RiderRank = portal.getRidersRankInStage(2);
            if (RiderRank != null) {
                System.out.println("Results: " + Arrays.toString(RiderRank));
            } else {
                System.out.println("Failed to get rank.");
            }
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
        }

        // Get riders classification
        System.out.println("General classification:");
        try {
            int [] RiderClassification = portal.getRidersGeneralClassificationRank(0);
            if (RiderClassification != null) {
                System.out.println("Results: " + Arrays.toString(RiderClassification));
            } else {
                System.out.println("Failed to get classification.");
            }
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
        }

        // Get riders mountain points in race
        System.out.println("Mountain points in race:");
        try {
            int[] RiderMountainPoints = portal.getRidersMountainPointsInRace(0);
            if (RiderMountainPoints != null) {
                System.out.println("Results: " + Arrays.toString(RiderMountainPoints));
            } else {
                System.out.println("Failed to get mountain points.");
            }
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
        }

        // Get riders points in race
        System.out.println("Points in race:");
        try {
            int[] RiderPoints = portal.getRidersPointsInRace(0);
            if (RiderPoints != null) {
                System.out.println("Results: " + Arrays.toString(RiderPoints));
            } else {
                System.out.println("Failed to get mountain points.");
            }
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
        }

        // Get general classification times
        System.out.println("General classification times:");
        try {
            LocalTime[] RiderTimes = portal.getGeneralClassificationTimesInRace(0);
            if (RiderTimes != null) {
                System.out.println("Results: " + Arrays.toString(RiderTimes));
            } else {
                System.out.println("Failed to get general classification times.");
            }
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
        }
    }
}