import java.util.Map;

public class SystemTesting {
    public static void main(String[] args) {
        Map<String, String> envVariables = System.getenv();
        String env = System.getenv("API_OBSERV_ENV");
        System.out.println(env);
        System.out.println(envVariables);
        for (Map.Entry<String, String> entry : envVariables.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
