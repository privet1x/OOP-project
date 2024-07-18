package pja.edu.s22326.elements;

import java.time.LocalDate;

public class TimeSimulator extends Thread {
    private static LocalDate currentDate;
    private HousingEstate estate;
    private int dayCounter = 0;
    private static boolean running = true;

    public TimeSimulator(HousingEstate estate) {
        this.estate = estate;
        currentDate = LocalDate.now(); // Or a specified start date
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1000);  // Move date forward by 1 day every 5 seconds
                currentDate = currentDate.plusDays(1);
                System.out.println("Today's date is: " + currentDate);
                dayCounter++;

                if (dayCounter % 2 == 0) {
                    estate.handleOverdueRentals(currentDate);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Time Simulator was interrupted.");
            }
        }
    }

    public void stopRunning() {
        running = false;
    }

    public static LocalDate getCurrentDate() {
        return currentDate;
    }
}
