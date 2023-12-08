

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class gngEkycQuery {
    public static void main(String[] args) throws Exception {
        String directory = "/home/lentraadmin/CKYC/AUG/AugLot2";
        List<String> lines = FileUtils.readLines(new File(directory + "/gng_names_ids.csv"));
        String timeContext = "AugLot2Ckyc";
        ekycDataQueries(lines, "gonogo_ekyc_" + timeContext, directory + "/gonogo_ekyc_" + timeContext + "_queries.sh");
        nonEkycDataQueries(lines, "gonogo_non_ekyc_" + timeContext, directory + "/gonogo_non_ekyc_" + timeContext + "_queries.sh");

    }
    public static void ekycDataQueries(List<String> lines, String fileFor, String outputFileName) throws Exception {
        List<String> queries = Lists.newArrayList();
        int i = 1;
        for (List<String> partition : Lists.partition(lines, 3000)) {
            String ids = partition.stream().map(s -> "\"" + s + "\"").collect(Collectors.joining(","));
            String query = "mongoexport --host ${hostName} --port 27017 -u ${userName} -p ${pwdValue} -d GNG_CORE_AARAMB -c goNoGoCustomerApplication -q '{\"_id\":{\"$in\":[" + ids + "]}, \"applicationRequest.request.applicant.ekycDone\":true}' --fields \"_id\",\"applicationRequest.request.applicant.ekycDone\",\"applicationRequest.request.applicant.ekycMode\" --type=csv --out " + fileFor + "_" + (i++) + ".csv";
            queries.add(query);
        }
        writeQueriesToFile(fileFor, outputFileName, queries);
    }

    public static void nonEkycDataQueries(List<String> lines, String fileFor, String outputFileName) throws Exception {
        List<String> queries = Lists.newArrayList();
        int i = 1;
        for (List<String> partition : Lists.partition(lines, 4000)) {
            String ids = partition.stream().map(s -> "\"" + s + "\"").collect(Collectors.joining(","));
            String query = "mongoexport --host ${hostName} --port 27017 -u ${userName} -p ${pwdValue} -d GNG_CORE_AARAMB -c goNoGoCustomerApplication -q '{\"_id\":{\"$in\":[" + ids + "]}, \"applicationRequest.request.applicant.ekycDone\":false}' --fields \"_id\" --type=csv --out " + fileFor + "_" + (i++) + ".csv";
            queries.add(query);
        }
        writeQueriesToFile(fileFor, outputFileName, queries);
    }

    private static void writeQueriesToFile(String fileFor, String outputFileName, List<String> queries) throws IOException {
        FileWriter fileWriter = new FileWriter(outputFileName);
        fileWriter.write("read -p \"Enter DB host : \" hostName\n" +
                "read -p \"Enter DB userName : \" userName\n" +
                "read -p \"Enter DB pwd : \" pwdValue");
        fileWriter.write("\n\n");
        for (String query : queries) {
            fileWriter.write(query);
            fileWriter.write("\n");
        }
        fileWriter.write("\n");
        fileWriter.write("cat " + fileFor + "_*.csv > " + fileFor + "_combined.csv");
        fileWriter.close();
    }
}
