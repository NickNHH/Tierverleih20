package ch.bbw.Tierverlei_v20;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    private String getToken() throws IOException {
        Reader reader = new InputStreamReader(Runtime.getRuntime().exec("curl -d \"grant_type=client_credentials&client_id=jDJ52F5MFz8ErRlpEC1QEslmqLMaJxeUNb8oIIqlVue6WXGpyJ&client_secret=gTnNRhPPaFCSLrxb1yT9dwUqKb3zxEqX0bZEnJsv\" https://api.petfinder.com/v2/oauth2/token").getInputStream());
        JsonElement jsonElement = new JsonParser().parse(reader);
        JsonObject rootObject = jsonElement.getAsJsonObject();

        return rootObject.get("access_token").getAsString();
    }

    @GetMapping("/index")
    public String homepage(Model model) {
        try {
            String token = getToken();
            String authHeader = "Bearer " + token;

            URL url = new URL("https://api.petfinder.com/v2/animals");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", authHeader);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) { // success
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                Gson gson = new Gson();
                List<Animal> animals = new ArrayList<>();

                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();
                String json = sb.toString();

                Animals animalList = gson.fromJson(json, Animals.class);
                System.out.println(animalList.getAnimals());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "index";
    }

}
