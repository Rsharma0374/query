import org.apache.commons.lang3.StringUtils;

public class CharacterCount {

    public static void main(String[] args) {
        String s = "vfvbtrte ";
        if (StringUtils.isBlank(s)) {
            System.out.println("Blank");
        } else {
            System.out.println("Not Blank");
        }
    }
}
