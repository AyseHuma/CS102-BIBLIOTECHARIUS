import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class TriviaAPI {

    private static final String API_URL = "https://opentdb.com/api.php?amount=5&type=multiple"; // Soru çekme URL'si

    public static void main(String[] args) {
        try {
            // API'den veri çekme
            String jsonResponse = getQuestionsFromAPI();
            // JSON yanıtını işleyip soruları yazdırma
            parseQuestions(jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // API'den veri çekme fonksiyonu
    private static String getQuestionsFromAPI() throws Exception {
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        return content.toString();
    }

    // API yanıtını işleyip soruları ekrana yazdırma
    private static void parseQuestions(String jsonResponse) {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONArray questionsArray = jsonObject.getJSONArray("results");

        for (int i = 0; i < questionsArray.length(); i++) {
            JSONObject question = questionsArray.getJSONObject(i);
            String questionText = question.getString("question");
            JSONArray incorrectAnswers = question.getJSONArray("incorrect_answers");
            String correctAnswer = question.getString("correct_answer");

            System.out.println("QUESTION: " + questionText);
            System.out.println("CORRECT ANSWER: " + correctAnswer);
            System.out.println("WRONG ANSWERS: " + incorrectAnswers.join(", "));
            System.out.println("-------------------------------------");
        }
    }
}
