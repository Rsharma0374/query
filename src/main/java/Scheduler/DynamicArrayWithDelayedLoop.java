package Scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DynamicArrayWithDelayedLoop {

    private static final long DELAY_IN_MINUTES = 3;

    private static List<Integer> valueList = new ArrayList<>();

    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        // Schedule the task to run with a delay of 3 minutes
        executor.scheduleAtFixedRate(DynamicArrayWithDelayedLoop::generateAndSaveValue, 0, DELAY_IN_MINUTES, TimeUnit.MINUTES);

        // After a delay, start looping through the list and printing values
        executor.schedule(DynamicArrayWithDelayedLoop::printValuesFromList, DELAY_IN_MINUTES, TimeUnit.MINUTES);

        // Allow the loop to run for a while and then shutdown the executor
        try {
            Thread.sleep(DELAY_IN_MINUTES * 5 * 60 * 1000); // Let the loop run for 15 minutes
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }

    private static void generateAndSaveValue() {
        int randomValue = (int) (Math.random() * 100); // Generate a random value between 0 and 99
        valueList.add(randomValue);
    }

    private static void printValuesFromList() {
        System.out.println("Printing values from the list:");
        for (int value : valueList) {
            System.out.println(value);
        }
    }
}