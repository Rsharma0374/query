import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

public class PDFDimensions {

    public static void main(String[] args) {
        String pdfFilePath = "/home/lentraadmin/Desktop/13307A00506_DO_1.pdf";

        try {
            PDDocument document = PDDocument.load(new File(pdfFilePath));
            int numPages = document.getNumberOfPages();

            for (int pageNum = 0; pageNum < numPages; pageNum++) {
                PDPage page = document.getPage(pageNum);
                float widthPt = page.getMediaBox().getWidth();
                float heightPt = page.getMediaBox().getHeight();

                // Assuming a default 72 dpi resolution, convert points to pixels
                int widthPx = Math.round(widthPt * 72 / 72);
                int heightPx = Math.round(heightPt * 72 / 72);

                System.out.println("Page " + (pageNum + 1) + ":");
                System.out.println("Width in pixels: " + widthPx);
                System.out.println("Height in pixels: " + heightPx);
                System.out.println();
            }

            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}