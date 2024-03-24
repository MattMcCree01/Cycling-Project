import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.io.IOException;

import cycling.*;

public class SavedCyclingPortals {
    public static void main(String[] args) {
        System.out.println("New cycling portal: ");
        CyclingPortal newPortal = new CyclingPortalImpl();


        System.out.println("Adding new rider data: ");

        try {
            int team = newPortal.createTeam("newPortalTeam", "Some description");
            if (team != -1) {
                System.out.println("Team created successfully");
            } else {
                System.out.println("Team creation failed");
            }
        } catch (IllegalNameException e) {
            System.out.println("IllegalNameException: " + e.getMessage());
        } catch (InvalidNameException e) {
            System.out.println("InvalidNameException: " + e.getMessage());
        }


        try { 
            int rider = newPortal.createRider(1, "newPortalRider", 1999);
            if (rider != -1) {
                System.out.println("Rider created successfully");
            } else {
                System.out.println("Rider creation failed");
            }
        } catch (IDNotRecognisedException e) {
            System.out.println("IllegalNameException: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("InvalidNameException: " + e.getMessage());
        }

        System.out.println("Saving Portal");
        try {
            newPortal.saveCyclingPortal("newPortal.ser");
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }

        System.out.println("Loading Portal");
        try {
            newPortal.loadCyclingPortal("portal.ser");
            System.out.println("Portal loaded successfully");
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException: " + e.getMessage());
        }

        try {
            int[] teams = newPortal.getTeams();
            System.out.println("Teams: " + Arrays.toString(teams));
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        try {
            int team = newPortal.createTeam("loadedPortalTeam", "Some description");
            if (team != -1) {
                System.out.println("Team created successfully TeamId: " + team);
            } else {
                System.out.println("Team creation failed");
            }
        } catch (IllegalNameException e) {
            System.out.println("IllegalNameException: " + e.getMessage());
        } catch (InvalidNameException e) {
            System.out.println("InvalidNameException: " + e.getMessage());
        }

        try { 
            int rider = newPortal.createRider(1, "loadedPortalRider", 1999);
            if (rider != -1) {
                System.out.println("Rider created successfully RiderId: " + rider);
            } else {
                System.out.println("Rider creation failed");
            }
        } catch (IDNotRecognisedException e) {
            System.out.println("IllegalNameException: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("InvalidNameException: " + e.getMessage());
        }

        try {
            int race = newPortal.createRace("loadedPortalRace", "Some description");
            if (race != -1) {
                System.out.println("Race created successfully, RaceId: " + race);
            } else {
                System.out.println("Race not created");
            }
        } catch (IllegalNameException e) {
            System.out.println("IllegalNameException: " + e.getMessage());
        } catch (InvalidNameException e) {
            System.out.println("InvalidNameException: " + e.getMessage());
        }
    }
}
