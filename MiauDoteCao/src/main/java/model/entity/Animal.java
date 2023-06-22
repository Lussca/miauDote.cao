package model.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Animal {
	
	public static int SEXO_MACHO = 1;
	public static int SEXO_FEMEA = 2;
	public static int RACA_CACHORRO = 1;
	public static int RACA_GATO = 2;
	public static int TAMANHO_PEQUENO = 1;
	public static int TAMANHO_MEDIO = 2;
	public static int TAMANHO_GRANDE =3;
	public static int PELO_PEQUENO = 1;
	public static int PELO_MEDIO = 2;
	public static int PELO_LONGO = 3;
	public static int CONVIVENCIA_MANSO = 1;
	public static int CONVIVENCIA_AGRESSIVO = 2;
	public static int CONVICENCIA_AGITADO = 3;
	public static int CONVIVENCIA_AMIGAVEL = 4;
	public static int CONVICENCIA_AMEDRONTADO = 5;
	public static int COR_MARROM = 1;
	public static int COR_BRANCO = 2;
	public static int COR_PRETO = 3;
	public static int COR_DOURADO = 4;
	public static int COR_BEGE = 5;
	public static int COR_LARANJA = 6;
	public static int COR_RAJADO = 7;
	public static int COR_CINZA = 8;
	public static int COR_BICOLOR = 9;
	public static int COR_TRICOLOR = 10;
	//TODO finalizar a conversão dos valores na requisicao de animais com filtro
	
	String id;
	String race;
    String name;
    String size;
    String hairType;
    String animalToAnimal;
    String animalToPerson;
    String sex;
    String age;
    String idOng;
    String insertionDate;
    String color;
    String animalDescription;
    String imageUrl;
    List<String> links;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRace() {
		return race;
	}
	public void setRace(String race) {
		this.race = race;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getHairType() {
		return hairType;
	}
	public void setHairType(String hairType) {
		this.hairType = hairType;
	}
	public String getAnimalToAnimal() {
		return animalToAnimal;
	}
	public void setAnimalToAnimal(String animalToAnimal) {
		this.animalToAnimal = animalToAnimal;
	}
	public String getAnimalToPerson() {
		return animalToPerson;
	}
	public void setAnimalToPerson(String animalToPerson) {
		this.animalToPerson = animalToPerson;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getIdOng() {
		return idOng;
	}
	public void setIdOng(String idOng) {
		this.idOng = idOng;
	}
	public String getInsertionDate() {
		return insertionDate;
	}
	public void setInsertionDate(String insertionDate) {
		this.insertionDate = insertionDate;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public List<String> getLinks() {
		return links;
	}
	public void setLinks(List<String> links) {
		this.links = links;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getAnimalDescription() {
		return animalDescription;
	}
	public void setAnimalDescription(String animalDescription) {
		this.animalDescription = animalDescription;
	}
	public Animal(String race, String name, String size, String hairType, String animalToAnimal,
			String animalToPerson, String sex, String age, String idOng, String insertionDate, String color, String animalDescription,
			List<String> links) {
		super();
		this.race = race;
		this.name = name;
		this.size = size;
		this.hairType = hairType;
		this.animalToAnimal = animalToAnimal;
		this.animalToPerson = animalToPerson;
		this.sex = sex;
		this.age = age;
		this.idOng = idOng;
		this.insertionDate = insertionDate;
		this.color = color;
		this.animalDescription = animalDescription;
		this.links = links;
	}
	public Animal(String idAnimal, String race, String name, String size, String hairType, String animalToAnimal,
			String animalToPerson, String sex, String age, String idOng, String color, String animalDescription,
			List<String> links, int number) {
		super();
		this.id = idAnimal;
		this.race = race;
		this.name = name;
		this.size = size;
		this.hairType = hairType;
		this.animalToAnimal = animalToAnimal;
		this.animalToPerson = animalToPerson;
		this.sex = sex;
		this.age = age;
		this.idOng = idOng;
		this.color = color;
		this.animalDescription = animalDescription;
		this.links = links;
	}
	public Animal() {
		super();
	}
	public static Animal parseAnimalJson(String jsonPayLoad) {
		Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonPayLoad, JsonObject.class);

        JsonObject animalObject = jsonObject.getAsJsonObject("Animal");
        
        try {
            int race = animalObject.get("race").getAsInt();
            String name = animalObject.get("name").getAsString();
            int size = animalObject.get("size").getAsInt();
            int hairType = animalObject.get("hairType").getAsInt();
            int animalToAnimal = animalObject.get("animalToAnimal").getAsInt();
            int animalToPerson = animalObject.get("animalToPerson").getAsInt();
            int sex = animalObject.get("sex").getAsInt();
            int age = animalObject.get("age").getAsInt();
            int idOng = animalObject.get("idOng").getAsInt();
            int color = animalObject.get("color").getAsInt();
            String description = animalObject.get("description").getAsString();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime ldt = LocalDateTime.now();
            String insertionDate = ldt.format(formatter);
            List<String> links = new ArrayList<>();
            JsonObject linksObject = animalObject.getAsJsonObject("Links");
            for (String key : linksObject.keySet()) {
                String link = linksObject.get(key).getAsString();
                links.add(link);
            }
            return new Animal(String.valueOf(race), name, String.valueOf(size), String.valueOf(hairType), String.valueOf(animalToAnimal),
                    String.valueOf(animalToPerson), String.valueOf(sex), String.valueOf(age), String.valueOf(idOng), insertionDate, String.valueOf(color), description, links);
        	}catch(NumberFormatException e) {
        	e.printStackTrace();
        	return null;
        }
       
	}
	 public static Animal parseAnimalJson(String jsonPayLoad, int number) {
		 Gson gson = new Gson();
	        JsonObject jsonObject = gson.fromJson(jsonPayLoad, JsonObject.class);

	        JsonObject animalObject = jsonObject.getAsJsonObject("Animal");
	        
	        try {
	        	int idAnimal = animalObject.get("idAnimal").getAsInt();
	            String race = animalObject.get("race").getAsString();
	            String name = animalObject.get("name").getAsString();
	            int size = animalObject.get("size").getAsInt();
	            int hairType = animalObject.get("hairType").getAsInt();
	            int animalToAnimal = animalObject.get("animalToAnimal").getAsInt();
	            int animalToPerson = animalObject.get("animalToPerson").getAsInt();
	            int sex = animalObject.get("sex").getAsInt();
	            int age = animalObject.get("age").getAsInt();
	            int idOng = animalObject.get("idOng").getAsInt();
	            int color = animalObject.get("color").getAsInt();
	            String description = animalObject.get("description").getAsString();
	            List<String> links = new ArrayList<>();
	            JsonObject linksObject = animalObject.getAsJsonObject("Links");
	            for (String key : linksObject.keySet()) {
	                String link = linksObject.get(key).getAsString();
	                links.add(link);
	            }
	            return new Animal(String.valueOf(idAnimal), race, name, String.valueOf(size), String.valueOf(hairType), String.valueOf(animalToAnimal),
	                    String.valueOf(animalToPerson), String.valueOf(sex), String.valueOf(age), String.valueOf(idOng), String.valueOf(color), description, links, number);
	        	}catch(NumberFormatException e) {
	        	e.printStackTrace();
	        	return null;
	        }
     }
}
