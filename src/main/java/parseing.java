import org.bson.Document;

public class parseing {
    public static void main(String[] args) {
        String s = "{\"Message\":\"frced\",\"StatusCode\":\"500\"}";
        Document document =  Document.parse(s);
        System.out.println(document);
    }


}
