package com.extrieve.imaging.sdk;

import com.extrieve.imaging.sdk.ImageWizHelperJNI.ResetOption;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static String[] input = new String[1];
    static String outputFile;
    static BufferedReader csvFileReader;
    static ImageWizHelperJNI objWizHelper;

    public Main() {
    }

    static void ProcessingThreadManager() throws Exception {
        Thread.sleep(100L);

        while(true) {
            boolean success = false;
            String line;
            synchronized(csvFileReader) {
                line = csvFileReader.readLine();
                if (line == null) {
                    return;
                }
            }

            String[] data = line.split(",");
            String inputFile = data[0];
            String outputFile = data[1];
            success = PDFtoTiff(inputFile, outputFile);
            if (success) {
                System.out.println("Conversion successful for " + inputFile);
            } else {
                System.out.println("Conversion failed for " + inputFile);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        try {
            objWizHelper = new ImageWizHelperJNI();
            int numThreads = 1;
            String csvFile = "/home/lentraadmin/Desktop/idearasdll.csv";

            for(int index = 0; index < args.length; ++index) {
                String var4;
                switch((var4 = args[index].toUpperCase()).hashCode()) {
                    case 1462:
                        if (var4.equals("-C")) {
                            if (index == args.length) {
                                return;
                            }

                            ++index;
                            csvFile = args[index];
                        }
                        break;
                    case 1479:
                        if (var4.equals("-T")) {
                            if (index == args.length) {
                                return;
                            }

                            ++index;
                            numThreads = Integer.parseInt(args[index]);
                        }
                }
            }

            if (!Files.exists(Paths.get(csvFile), new LinkOption[0])) {
                throw new RuntimeException("CSV FILE DOSE NOT EXITS !!!!");
            }

            csvFileReader = new BufferedReader(new FileReader(csvFile));
            List<Thread> threadArray = new ArrayList();

            int i;
            for(i = 0; i <= numThreads; ++i) {
                Thread thread = new Thread(() -> {
                    try {
                        ProcessingThreadManager();
                    } catch (Exception var1) {
                        var1.printStackTrace();
                    }

                });
                threadArray.add(thread);
            }

            for(i = 0; i < threadArray.size(); ++i) {
                ((Thread)threadArray.get(i)).start();
                ((Thread)threadArray.get(i)).join();
            }
        } catch (Exception var11) {
            System.out.println(var11.toString());
        } finally {
            System.gc();
            System.exit(0);
        }

    }

    static boolean PDFtoTiff(String InputFile, String OutputFile) {
        try {
            int ret = objWizHelper.CompressToTIFF(new String[]{InputFile}, OutputFile, ResetOption.ResetAllDPI);
            if (ret != 0) {
                System.out.println(objWizHelper.GetErrorDescription(ret));
                return false;
            } else {
                return true;
            }
        } catch (Exception var4) {
            throw new RuntimeException(var4.toString());
        }
    }
}