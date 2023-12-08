package Scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyScheduler {

    @Scheduled(cron = "0 */1 * * * ?")
    public void firstScheduler() {
        // Your first scheduler's task code here
        System.out.println("First scheduler executed");

        // Schedule the second task after 5 minutes
        scheduleSecondTask();
    }

    private void scheduleSecondTask() {
        long delay = 60 * 1000; // 5 minutes in milliseconds

        // Use a separate thread or executor for the second task to avoid blocking the scheduler
        Thread secondTaskThread = new Thread(() -> {
            try {
                Thread.sleep(delay);
                // Your second scheduler's task code here
                System.out.println("Second scheduler executed");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        secondTaskThread.start();
    }
}
