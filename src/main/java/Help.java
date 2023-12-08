

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Help {

    public static void main(String[] args) throws Exception {
        String directory = "/home/lentraadmin/CKYC/MissingADVJantoOct";
        List<String> lines = FileUtils.readLines(new File(directory + "/gng_names_ids.csv"));
        String timeContext = "MissingAdvJan2Oct";
        ekycDataQueries(lines, "gonogo_ekyc_" + timeContext, directory + "/gonogo_ekyc_" + timeContext + "_queries.sh");
        nonEkycDataQueries(lines, "gonogo_non_ekyc_" + timeContext, directory + "/gonogo_non_ekyc_" + timeContext + "_queries.sh");
        hblLoanBookingInfoQueries(lines, "hblLoanBookingInfo_" + timeContext, directory + "/hblLoanBookingInfo_" + timeContext + "_queries.sh");
        thirdPartyTransactionLogQueries(lines, "thirdPartyTransactionLog_" + timeContext, directory + "/thirdPartyTransactionLog_" + timeContext + "_queries.sh");
    }

    public static void ekycDataQueries(List<String> lines, String fileFor, String outputFileName) throws Exception {
        List<String> queries = Lists.newArrayList();
        int i = 1;
        for (List<String> partition : Lists.partition(lines, 3000)) {
            String ids = partition.stream().map(s -> "\"" + s + "\"").collect(Collectors.joining(","));
            String query = "mongoexport --host ${hostName} --port 27017 -u ${userName} -p ${pwdValue} -d GNG_CORE_AARAMB -c goNoGoCustomerApplication -q '{\"_id\":{\"$in\":[" + ids + "]}, \"applicationRequest.request.applicant.ekycDone\":true}' --fields \"_id\",\"dateTime\",\"applicationRequest.request.applicant.ekycDone\",\"applicationRequest.request.applicant.ekycMode\",\"applicationRequest.request.applicant.applicantName.firstName\",\"applicationRequest.request.applicant.applicantName.middleName\",\"applicationRequest.request.applicant.applicantName.lastName\",\"applicationRequest.request.applicant.dateOfBirth\",\"applicationRequest.request.applicant.gender\",\"applicationRequest.request.applicant.phone.0.phoneNumber\",\"applicationRequest.request.applicant.email.0.emailAddress\",\"applicationRequest.request.applicant.ekycResponse.uidData.proofofAddress.careOf\",\"applicationRequest.request.applicant.ekycResponse.uidData.proofofAddress.building\",\"applicationRequest.request.applicant.ekycResponse.uidData.proofofAddress.street\",\"applicationRequest.request.applicant.ekycResponse.uidData.proofofAddress.landmark\",\"applicationRequest.request.applicant.ekycResponse.uidData.proofofAddress.locality\",\"applicationRequest.request.applicant.ekycResponse.uidData.proofofAddress.vtc\",\"applicationRequest.request.applicant.ekycResponse.uidData.proofofAddress.district\",\"applicationRequest.request.applicant.ekycResponse.uidData.proofofAddress.state\",\"applicationRequest.request.applicant.ekycResponse.uidData.proofofAddress.pinCode\",\"applicationRequest.request.applicant.ekycResponse.uidData.proofofAddress.country\",\"applicationRequest.request.applicant.ekycResponse.returnCode.txn\",\"applicationRequest.request.applicant.ekycResponse.returnCode.code\",\"applicationRequest.request.applicant.ekycModeResponse.data.fName\",\"applicationRequest.request.applicant.ekycModeResponse.data.mName\",\"applicationRequest.request.applicant.ekycModeResponse.data.lName\",\"applicationRequest.request.applicant.ekycModeResponse.data.dob\",\"applicationRequest.request.applicant.ekycModeResponse.data.gender\",\"applicationRequest.request.applicant.ekycModeResponse.data.addLine1\",\"applicationRequest.request.applicant.ekycModeResponse.data.addLine2\",\"applicationRequest.request.applicant.ekycModeResponse.data.addLine3\",\"applicationRequest.request.applicant.ekycModeResponse.data.landmark\",\"applicationRequest.request.applicant.ekycModeResponse.data.city\",\"applicationRequest.request.applicant.ekycModeResponse.data.state\",\"applicationRequest.request.applicant.ekycModeResponse.data.pincode\",\"applicationRequest.request.applicant.ekycModeResponse.data.advRefNo\",\"applicationRequest.request.applicant.ekycModeResponse.filler1\",\"applicationRequest.appMetaData.dsaName.firstName\",\"applicationRequest.appMetaData.dsaName.lastName\" --type=csv --out " + fileFor + "_" + (i++) + ".csv";
            queries.add(query);
        }
        writeQueriesToFile(fileFor, outputFileName, queries);
    }

    public static void nonEkycDataQueries(List<String> lines, String fileFor, String outputFileName) throws Exception {
        List<String> queries = Lists.newArrayList();
        int i = 1;
        for (List<String> partition : Lists.partition(lines, 4000)) {
            String ids = partition.stream().map(s -> "\"" + s + "\"").collect(Collectors.joining(","));
            String query = "mongoexport --host ${hostName} --port 27017 -u ${userName} -p ${pwdValue} -d GNG_CORE_AARAMB -c goNoGoCustomerApplication -q '{\"_id\":{\"$in\":[" + ids + "]}, \"applicationRequest.request.applicant.ekycDone\":false}' --fields \"_id,applicationRequest.currentStageId,dateTime,applicationRequest.request.applicant.ekycDone,applicationRequest.request.applicant.applicantName.firstName,applicationRequest.request.applicant.applicantName.middleName,applicationRequest.request.applicant.applicantName.lastName,applicationRequest.request.applicant.dateOfBirth,applicationRequest.request.applicant.gender,applicationRequest.request.applicant.phone.0.phoneNumber,applicationRequest.request.applicant.email.0.emailAddress,applicationRequest.request.applicant.address.0.addressType,applicationRequest.request.applicant.address.0.addressLine1,applicationRequest.request.applicant.address.0.addressLine2,applicationRequest.request.applicant.address.0.line3,applicationRequest.request.applicant.address.0.landMark,applicationRequest.request.applicant.address.0.locality,applicationRequest.request.applicant.address.0.area,applicationRequest.request.applicant.address.0.city,applicationRequest.request.applicant.address.0.pin,applicationRequest.request.applicant.address.0.state,applicationRequest.request.applicant.address.0.country,applicationRequest.request.applicant.address.1.addressType,applicationRequest.request.applicant.address.1.addressLine1,applicationRequest.request.applicant.address.1.addressLine2,applicationRequest.request.applicant.address.1.line3,applicationRequest.request.applicant.address.1.landMark,applicationRequest.request.applicant.address.1.locality,applicationRequest.request.applicant.address.1.area,applicationRequest.request.applicant.address.1.city,applicationRequest.request.applicant.address.1.pin,applicationRequest.request.applicant.address.1.state,applicationRequest.request.applicant.address.1.country,applicationRequest.request.applicant.address.2.addressType,applicationRequest.request.applicant.address.2.addressLine1,applicationRequest.request.applicant.address.2.addressLine2,applicationRequest.request.applicant.address.2.line3,applicationRequest.request.applicant.address.2.landMark,applicationRequest.request.applicant.address.2.locality,applicationRequest.request.applicant.address.2.area,applicationRequest.request.applicant.address.2.city,applicationRequest.request.applicant.address.2.pin,applicationRequest.request.applicant.address.2.state,applicationRequest.request.applicant.address.2.country,applicationRequest.appMetaData.dsaName.firstName,applicationRequest.appMetaData.dsaName.lastName,applicationRequest.request.applicant.kyc.0.kycName,applicationRequest.request.applicant.kyc.0.kycNumber,applicationRequest.request.applicant.kyc.1.kycName,applicationRequest.request.applicant.kyc.1.kycNumber,applicationRequest.request.applicant.kyc.2.kycName,applicationRequest.request.applicant.kyc.2.kycNumber,applicationRequest.request.applicant.kyc.3.kycName,applicationRequest.request.applicant.kyc.3.kycNumber,applicationRequest.request.applicant.kyc.4.kycName,applicationRequest.request.applicant.kyc.4.kycNumber,applicationRequest.request.applicant.kyc.5.kycName,applicationRequest.request.applicant.kyc.5.kycNumber,applicationRequest.request.applicant.kyc.6.kycName,applicationRequest.request.applicant.kyc.6.kycNumber,applicationRequest.request.applicant.kyc.7.kycName,applicationRequest.request.applicant.kyc.7.kycNumber,applicationRequest.request.applicant.kyc.8.kycName,applicationRequest.request.applicant.kyc.8.kycNumber,applicationRequest.request.applicant.kyc.9.kycName,applicationRequest.request.applicant.kyc.9.kycNumber,applicationRequest.request.applicant.kyc.10.kycName,applicationRequest.request.applicant.kyc.10.kycNumber,applicationRequest.request.applicant.kyc.11.kycName,applicationRequest.request.applicant.kyc.11.kycNumber,applicationRequest.request.applicant.kyc.12.kycName,applicationRequest.request.applicant.kyc.12.kycNumber,applicationRequest.request.applicant.kyc.13.kycName,applicationRequest.request.applicant.kyc.13.kycNumber,applicationRequest.request.applicant.kyc.14.kycName,applicationRequest.request.applicant.kyc.14.kycNumber,applicationRequest.request.applicant.kyc.15.kycName,applicationRequest.request.applicant.kyc.15.kycNumber,applicationRequest.request.applicant.kyc.16.kycName,applicationRequest.request.applicant.kyc.16.kycNumber,applicationRequest.request.applicant.kyc.17.kycName,applicationRequest.request.applicant.kyc.17.kycNumber,applicationRequest.request.applicant.kyc.18.kycName,applicationRequest.request.applicant.kyc.18.kycNumber,applicationRequest.request.applicant.kyc.19.kycName,applicationRequest.request.applicant.kyc.19.kycNumber\" --type=csv --out " + fileFor + "_" + (i++) + ".csv";
            queries.add(query);
        }
        writeQueriesToFile(fileFor, outputFileName, queries);
    }

    public static void hblLoanBookingInfoQueries(List<String> lines, String fileFor, String outputFileName) throws Exception {
        List<String> queries = Lists.newArrayList();
        int i = 1;
        for (List<String> partition : Lists.partition(lines, 4000)) {
            String ids = partition.stream().map(s -> "\"" + s + "\"").collect(Collectors.joining(","));
            String query = "mongoexport --host ${hostName} --port 27017 -u ${userName} -p ${pwdValue} -d GNG_CORE_AARAMB -c hblLoanBookingInfo -q '{\"refId\":{\"$in\":[" + ids + "]}}' --fields \"_id\",\"refId\",\"date\",\"stage\",\"currentStageId\",\"loanAgreementNo\",\"lanDate\" --type=csv --out " + fileFor + "_" + (i++) + ".csv";
            queries.add(query);
        }
        writeQueriesToFile(fileFor, outputFileName, queries);
    }

    public static void thirdPartyTransactionLogQueries(List<String> lines, String fileFor, String outputFileName) throws Exception {
        List<String> queries = Lists.newArrayList();
        int i = 1;
        for (List<String> partition : Lists.partition(lines, 4000)) {
            String ids = partition.stream().map(s -> "\"" + s + "\"").collect(Collectors.joining(","));
            String query = "mongoexport --host ${hostName} --port 27017 -u ${userName} -p ${pwdValue} -d GNG_CORE_AARAMB -c thirdPartyTransactionLog -q '{\"refNo\":{\"$in\":[" + ids + "]},\"transactionLogsList.serviceType\":{\"$in\":[\"BIO_EKYC\",\"FETCH_REF_KEY\",\"EKYC_RESPONSE\"]}}' --fields \"refNo\",\"date\",\"transactionLogsList.1.serviceType\",\"transactionLogsList.1.reqTimestamp\",\"transactionLogsList.1.resTimestamp\",\"transactionLogsList.1.parsedResponse.responseStringObj.referenceKeyList.referenceKey\",\"transactionLogsList.1.parsedResponse.data.advRefNo\",\"transactionLogsList.2.serviceType\",\"transactionLogsList.2.reqTimestamp\",\"transactionLogsList.2.resTimestamp\",\"transactionLogsList.2.parsedResponse.responseStringObj.referenceKeyList.referenceKey\",\"transactionLogsList.2.parsedResponse.data.advRefNo\",\"transactionLogsList.3.serviceType\",\"transactionLogsList.3.reqTimestamp\",\"transactionLogsList.3.resTimestamp\",\"transactionLogsList.3.parsedResponse.responseStringObj.referenceKeyList.referenceKey\",\"transactionLogsList.3.parsedResponse.data.advRefNo\",\"transactionLogsList.4.serviceType\",\"transactionLogsList.4.reqTimestamp\",\"transactionLogsList.4.resTimestamp\",\"transactionLogsList.4.parsedResponse.responseStringObj.referenceKeyList.referenceKey\",\"transactionLogsList.4.parsedResponse.data.advRefNo\",\"transactionLogsList.5.serviceType\",\"transactionLogsList.5.reqTimestamp\",\"transactionLogsList.5.resTimestamp\",\"transactionLogsList.5.parsedResponse.responseStringObj.referenceKeyList.referenceKey\",\"transactionLogsList.5.parsedResponse.data.advRefNo\",\"transactionLogsList.6.serviceType\",\"transactionLogsList.6.reqTimestamp\",\"transactionLogsList.6.resTimestamp\",\"transactionLogsList.6.parsedResponse.responseStringObj.referenceKeyList.referenceKey\",\"transactionLogsList.6.parsedResponse.data.advRefNo\",\"transactionLogsList.7.serviceType\",\"transactionLogsList.7.reqTimestamp\",\"transactionLogsList.7.resTimestamp\",\"transactionLogsList.7.parsedResponse.responseStringObj.referenceKeyList.referenceKey\",\"transactionLogsList.7.parsedResponse.data.advRefNo\",\"transactionLogsList.8.serviceType\",\"transactionLogsList.8.reqTimestamp\",\"transactionLogsList.8.resTimestamp\",\"transactionLogsList.8.parsedResponse.responseStringObj.referenceKeyList.referenceKey\",\"transactionLogsList.8.parsedResponse.data.advRefNo\",\"transactionLogsList.9.serviceType\",\"transactionLogsList.9.reqTimestamp\",\"transactionLogsList.9.resTimestamp\",\"transactionLogsList.9.parsedResponse.responseStringObj.referenceKeyList.referenceKey\",\"transactionLogsList.9.parsedResponse.data.advRefNo\",\"transactionLogsList.10.serviceType\",\"transactionLogsList.10.reqTimestamp\",\"transactionLogsList.10.resTimestamp\",\"transactionLogsList.10.parsedResponse.responseStringObj.referenceKeyList.referenceKey\",\"transactionLogsList.10.parsedResponse.data.advRefNo\" --type=csv --out " + fileFor + "_" + (i++) + ".csv";
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
