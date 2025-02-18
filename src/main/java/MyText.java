
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Properties;

public class MyText {

    private static final String successTitle = "Успех!";
    private static final String errorTitle = "Зараза!";
    private static String[] successPhrases;
    private static String[] errorPhrases;
    private static int invokeSuccessCounter;
    private static int invokeErrorCounter;
    private static Properties properties;

    static {

        initializeProperties();
        initializeCounters();
        initializePhrases();
    }

    private static void initializeProperties() {
        properties = ResourceSupplier.getProperties("MyText.properties","/config/");
    }

    private static void initializeCounters() {
        invokeSuccessCounter = Integer.parseInt(properties.get("invokeSuccessCounter").toString());
        invokeErrorCounter = Integer.parseInt(properties.get("invokeErrorCounter").toString());
    }

    private static void initializePhrases() {
        successPhrases = readPhrasesFromFile("SuccessPhrases.txt");
        errorPhrases = readPhrasesFromFile("ErrorPhrases.txt");
    }

    private static String[] readPhrasesFromFile(String fileName) {
        byte[] bytes = ResourceSupplier.getByteArrayFromTextFile(fileName);
        return createPhrases(bytes);
    }

    private static String[] createPhrases(byte[] bytes) {
        String[] tempPhrases = new String(bytes, StandardCharsets.UTF_8).split("\\d\\.");
        ArrayList<String> arrayWithoutSpaceElement = new ArrayList<>();
        for(String phrase: tempPhrases) {
            if(!phrase.isEmpty()) {
               arrayWithoutSpaceElement.add(phrase);
            }
        }
        return arrayWithoutSpaceElement.toArray(new String[0]);

    }

    public static String getSuccessPhrase() {
        if(isLastElement(invokeSuccessCounter, successPhrases))
            invokeSuccessCounter = 0;
        ResourceSupplier.setConfigFile("MyText.properties","/config/","invokeSuccessCounter", String.valueOf((invokeSuccessCounter + 1)));
        return successPhrases[invokeSuccessCounter++];
    }

    public static String getErrorPhrase() {
        if(isLastElement(invokeErrorCounter, errorPhrases))
            invokeErrorCounter = 0;
        ResourceSupplier.setConfigFile("MyText.properties","/config/","invokeErrorCounter", String.valueOf((invokeErrorCounter + 1)));
        return errorPhrases[invokeErrorCounter++];
    }

    private static boolean isLastElement(int counter, String[] array) {
        return counter + 1 >= array.length;
    }

    public static String getSuccessTitle() {
        return successTitle;
    }

    public static String getErrorTitle() {
        return errorTitle;
    }


}
