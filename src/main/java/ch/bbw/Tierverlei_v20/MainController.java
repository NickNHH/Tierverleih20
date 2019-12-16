package ch.bbw.Tierverlei_v20;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @GetMapping("/index")
    public String homepage(Model model) {
        try {
            URL url = new URL("http://jsonplaceholder.typicode.com/users");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            Gson gson = new Gson();
            List<User> users = new ArrayList<>();
            JsonReader reader = new JsonReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            reader.beginArray();
            while (reader.hasNext()) {
                User user = gson.fromJson(reader, User.class);
                users.add(user);
            }
            users.forEach(user -> System.out.println("user " + user.getId() + "\n" + "-------------------------\n" + user.getName() + "\n" + user.getUsername() + "\n" + user.getEmail() + "\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "index";
    }
}
