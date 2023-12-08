import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class PDFtoByte {

    public static void main(String[] args) {
        // Replace 'mongodbURI' with your MongoDB connection URI
        String mongodbURI = "mongodb://localhost:27017";
        // Replace 'dbName' with your MongoDB database name
        String dbName = "your_database_name";
        // Replace 'gridFSBucket' with your GridFS bucket name
        String gridFSBucket = "your_gridfs_bucket_name";
        // Replace 'fileID' with the ID of the GridFSDBFile you want to convert
        String fileID = "your_gridfs_file_id";

        try {
            String base64String = gridFSDBFileToBase64(mongodbURI, dbName, gridFSBucket, fileID);
            System.out.println("Base64 encoded string:\n" + base64String);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String gridFSDBFileToBase64(String mongodbURI, String dbName, String gridFSBucket, String fileID) throws IOException {
        try (MongoClient mongoClient = new MongoClient(mongodbURI)) {
            DB db = mongoClient.getDB(dbName);
            GridFS gridFS = new GridFS(db, gridFSBucket);
            GridFSDBFile gridFSDBFile = gridFS.findOne(fileID);

            if (gridFSDBFile != null) {
                InputStream inputStream = gridFSDBFile.getInputStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                byte[] fileBytes = outputStream.toByteArray();
                return Base64.getEncoder().encodeToString(fileBytes);
            } else {
                throw new IllegalArgumentException("File with ID " + fileID + " not found in GridFS.");
            }
        }
    }
}
