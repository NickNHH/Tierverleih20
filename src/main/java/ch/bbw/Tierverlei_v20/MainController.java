package ch.bbw.Tierverlei_v20;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

@Controller
public class MainController {
    String json = "";
    String lastType = "";
    Animal removingAnimal;
    ArrayList<Animal> animals = new ArrayList<>();
    ArrayList<Animal> savedAnimals = new ArrayList<>();

    private String getToken() throws IOException {
        Reader reader = new InputStreamReader(Runtime.getRuntime().exec("curl -d \"grant_type=client_credentials&client_id=jDJ52F5MFz8ErRlpEC1QEslmqLMaJxeUNb8oIIqlVue6WXGpyJ&client_secret=gTnNRhPPaFCSLrxb1yT9dwUqKb3zxEqX0bZEnJsv\" https://api.petfinder.com/v2/oauth2/token").getInputStream());
        JsonElement jsonElement = new JsonParser().parse(reader);
        JsonObject rootObject = jsonElement.getAsJsonObject();

        return rootObject.get("access_token").getAsString();
    }

    private String makeRequest() {
        StringBuilder sb = null;
        try {
            String token = getToken();
            String authHeader = "Bearer " + token;

            URL url = new URL("https://api.petfinder.com/v2/animals");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", authHeader);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) { // success
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                sb = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert sb != null;
        return sb.toString();
    }

    private ArrayList<Animal> getAnimalList(String json, String animalType) {
        Gson gson = new Gson();
        ArrayList<Animal> animalListCorrectTypes = new ArrayList<>();

        Animals animalList = gson.fromJson(json, Animals.class);
        System.out.println(animalList.getAnimals());

        for (Animal animal : animalList.getAnimals()) {
            if (animal.getType().equals(animalType)) {
                animalListCorrectTypes.add(animal);
            }
            if (animalType.equals("")) {
                return animalList.getAnimals();
            }
        }

        return animalListCorrectTypes;
    }

    @GetMapping("/index")
    public String homepage() {
        if (animals.size() == 0) {
            json = makeRequest();
            animals = getAnimalList(json, "");
        }

        return "index";
    }

    @GetMapping(value = "/warenkorb")
    public String warenkorb(Model model) {
        savedAnimals.remove(removingAnimal);
        model.addAttribute("warenkorb", savedAnimals);

        return "warenkorb";
    }

    @GetMapping(value = "/warenkorb/{id}")
    public String warenkorbAdd(Model model, @PathVariable(name = "id") int id) {
        ArrayList<Animal> animals = getAnimalList(json, "");

        for (Animal animal : animals) {
            if (animal.getId() == id && !savedAnimals.contains(animal)) {
                savedAnimals.add(animal);
            }
        }
        model.addAttribute("warenkorb", savedAnimals);

        return "redirect:/warenkorb";
    }

    @PostMapping(value = "/warenkorb/{id}")
    public String warenkorbRemove(Model model, @PathVariable(name = "id") int id) {
        ArrayList<Animal> animals = getAnimalList(json, "");

        for (Animal animal : animals) {
            if (animal.getId() == id) {
                removingAnimal = animal;
            }
        }
        model.addAttribute("warenkorb", savedAnimals);

        return "redirect:/warenkorb";
    }

    @GetMapping(value = "/animal")
    public String animal(Model model) {
        ArrayList<Animal> animals = getAnimalList(json, lastType);
        model.addAttribute("animals", animals);

        return "animal";
    }

    @GetMapping("/animal/{type}")
    public String animalType(@PathVariable String type, Model model) {
        ArrayList<Animal> animals = getAnimalList(json, type);
        lastType = type;
        model.addAttribute("animals", animals);

        return "redirect:/animal";
    }
}
