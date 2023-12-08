import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class CSVFilter {

    public static void main(String[] args) {
        String inputCsvFilename = "/home/lentraadmin/Music/Retention/subvention-live-report.csv"; // Replace with your input CSV file path
        String outputCsvFilename = "/home/lentraadmin/Music/Retention/output.csv"; // Replace with the desired output CSV file name
//        String appIdToMatch = "8814A04174"; // Replace with the ID you want to match
        String[] appIdArray = {"31212A04316","51252A04739","31328A08507","12423A01607","31294A04972","31212A04313","15672A06671","46294A02569","12684A02868","20772A02340","25594A03662","20773A01008","45349A01753","20772A02361","20772A02300","51567A00556","2629A03282","2993A49199","2993A49303","2993A49303","2993A49199","8344A12679","8344A12619","39051A05839","39067A02477","45940A09294","81895A00114","72526A00499","39068A12583","39070A04359","39082A04144","39114A09727","55398A00322","9662A01074","45930A02261","39084A16362","1091A00975","55398A00323","76000A00431","2694A06560","2308A03158","6485A00364","6485A00326","30278A03696","59351A09922","6485A00380","6485A00375","76160A00458","1726A13673","35651A05766","57131A01046","57131A01046","51161A00215","21347A01752","29631A10192","54964A01912","34389A01326","15572A05870","15572A05869","73280A02786","3453A01152","7130A04021","20928A17941","20937A29519","17272A07376","27352A04463","54177A00972","20937A29521","19529A05372","8155A01432","29209A42933","30752A02515","30752A02516","15202A02614","11334A02291","11334A02286","11334A02292","32958A04241","22218A04147","44956A02989","1216A01832","37534A04605","38104A00625","5952A05029","30233A01238","39094A14048","63320A02555","34218A05977","31876A03754","21932A03222","51719A01942","33373A01468","69146A01051","5940A03164","41808A00324","76192A00453","39057A23874","2699A23311","5658A05516","42706A04563","38100A03033","77494A00669","48771A00609","32952A00189","60511A02602","5866A12450","14478A01109","4366A00661","31995A00616","15787A00648","77003A00426","56575A07139","9759A02051","75368A01607","30664A02520","54850A02761","30664A02520","78073A00993","77177A00055","55198A00323","46557A03874","78387A00130"};

        try {
            FileReader reader = new FileReader(inputCsvFilename);
            FileWriter writer = new FileWriter(outputCsvFilename);

            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());

            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);

            for (CSVRecord csvRecord : csvParser) {
                String appId = csvRecord.get("Application ID");

                if (Arrays.asList(appIdArray).contains(appId)) {
                    // Write the matching row to the output CSV file
                    csvPrinter.printRecord(csvRecord);
                }
            }

            csvPrinter.close();
            writer.close();
            reader.close();

            System.out.println("Filtered data written to " + outputCsvFilename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
