import java.util.*;
import java.io.*;

// [####-------]; something like this
class Main {

    public static volatile int progress = 0;

    public static void main(String[] args) {
        Thread progressBarThread = new Thread(new ProgressBar());
        progressBarThread.start();

        for (int i = 0; i <= 100; i++) {
            progress = i;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Thread was interrupted");
            }
        }

        try {
            progressBarThread.join();
        } catch (InterruptedException e) {
            System.out.println("Something went wrong...");
        }

        System.out.println("Download complete!");
    }
}

class ProgressBar implements Runnable {
    public void run() {
        while (Main.progress < 100) {
            printProgress(Main.progress);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Something went wrong...");
            }
        }
        printProgress(100);
    }

    public void printProgress(int progress) {
        int width = 50;
        int filled = (progress * width) / 100;
        StringBuilder bar = new StringBuilder();
        bar.append("\r[");

        for (int i = 0; i < width; i++) {
            if (i < filled) bar.append("#");
            else bar.append("-");
        }
        bar.append("] ").append(progress).append("%");
        System.out.println(bar);
        System.out.flush();
    }
}
