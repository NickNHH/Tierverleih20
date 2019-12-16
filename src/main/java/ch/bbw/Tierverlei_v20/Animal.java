package ch.bbw.Tierverlei_v20;

import java.net.URL;
import java.util.ArrayList;

public class Animal {
    private int id;
    private String type;
    private String name;
    private int age;
    private String gender;
    private ArrayList<String> breeds;
    private int size;
    private String coat;
    private String description;
    private ArrayList<String> tags;
    private URL picture;

    public Animal(int id, String type, String name, int age, String gender, ArrayList<String> breeds, int size, String coat, String description, ArrayList<String> tags, URL picture) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.breeds = breeds;
        this.size = size;
        this.coat = coat;
        this.description = description;
        this.tags = tags;
        this.picture = picture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ArrayList<String> getBreeds() {
        return breeds;
    }

    public void setBreeds(ArrayList<String> breeds) {
        this.breeds = breeds;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getCoat() {
        return coat;
    }

    public void setCoat(String coat) {
        this.coat = coat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public URL getPicture() {
        return picture;
    }

    public void setPicture(URL picture) {
        this.picture = picture;
    }
}
