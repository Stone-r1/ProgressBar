import java.util.*;
import java.io.*;

// [####-------]; something like this
class Main {

    public static volatile int progress = 0;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String path, extension;

        System.out.println("Enter the full path to the directory");
        path = scan.nextLine().trim();

        File dir = new File(path);
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("It's not a valid directory.");
            return;
        }

        System.out.println("Enter file extension to search (cpp, py, etc.)");
        extension = scan.nextLine().trim(); 

        List<File> files = new ArrayList<>();
        findFiles(dir, extension, files);

        System.out.println("Found " + files.size() + " files");
        Thread progressBarThread = new Thread(new ProgressBar(files.size()));
        progressBarThread.start();

        int lines = 0;
        for (File file : files) {
            lines += countLines(file);
            progress++;
        }

        try {
            progressBarThread.join();
        } catch (InterruptedException e) {
            System.out.println("Something went wrong...");
        }

        System.out.println();
        System.out.println("There are exactly " + lines + " of code in total!");
    }

    public static void findFiles(File dir, String extension, List<File> result) {
        File[] files = dir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                findFiles(file, extension, result);
            } else if (file.getName().toLowerCase().endsWith("." + extension.toLowerCase())) {
                result.add(file);
            }
        }
    }

    public static int countLines(File dir) {
        int lines = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(dir))) {
            while (reader.readLine() != null) lines++;
        } catch (IOException e) {
            System.out.println("Error while reading the file");
        }

        return lines;
    }
}

class ProgressBar implements Runnable {
    private int total;

    public ProgressBar(int total) {
        this.total = total;
    }
    
    public void run() {
        while (Main.progress < total) {
            printProgress((Main.progress * 100) / total);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Something went wrong...");
            }
        }

        printProgress(100);
    }

    public void printProgress(int progress) {
        int width = 30;
        int filled = (progress * width) / 100;
        StringBuilder bar = new StringBuilder();
        bar.append("\r[");

        for (int i = 0; i < width; i++) {
            if (i < filled) bar.append("#");
            else bar.append("-");
        }
        bar.append("] ").append(progress).append("%");
        System.out.print(bar);
        System.out.flush();
    }
}
