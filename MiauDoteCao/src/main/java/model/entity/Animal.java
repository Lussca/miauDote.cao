package model.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Animal {
	
	public static final String SEXO_MACHO = "1";
	public static final String SEXO_FEMEA = "2";
	public static final String RACA_CACHORRO = "1";
	public static final String RACA_GATO = "2";
	public static final String TAMANHO_PEQUENO = "1";
	public static final String TAMANHO_MEDIO = "2";
	public static final String TAMANHO_GRANDE = "3";
	public static final String PELO_PEQUENO = "1";
	public static final String PELO_MEDIO = "2";
	public static final String PELO_LONGO = "3";
	public static final String CONVIVENCIA_MANSO = "1";
	public static final String CONVIVENCIA_AGRESSIVO = "2";
	public static final String CONVICENCIA_AGITADO = "3";
	public static final String CONVIVENCIA_AMIGAVEL = "4";
	public static final String CONVICENCIA_AMEDRONTADO = "5";
	public static final String COR_MARROM = "1";
	public static final String COR_BRANCO = "2";
	public static final String COR_PRETO = "3";
	public static final String COR_DOURADO = "4";
	public static final String COR_BEGE = "5";
	public static final String COR_LARANJA = "6";
	public static final String COR_RAJADO = "7";
	public static final String COR_CINZA = "8";
	public static final String COR_BICOLOR = "9";
	public static final String COR_TRICOLOR = "10";

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
    String ongName;
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
	public String getOngName() {
		return ongName;
	}
	public void setOngName(String ongName) {
		this.ongName = ongName;
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
	public Animal(String idAnimal) {
		this.id = idAnimal;
	}
	
	public static Animal parseAnimalJson(String jsonPayLoad) {
		Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonPayLoad, JsonObject.class);

        JsonObject animalObject = jsonObject.getAsJsonObject("Animal");
        
        try {
            int race = animalObject.get("race").getAsInt();
            String name = animalObject.get("nome").getAsString();
            int size = animalObject.get("porte").getAsInt();
            int hairType = animalObject.get("pelagem").getAsInt();
            int animalToAnimal = animalObject.get("caa").getAsInt();
            int animalToPerson = animalObject.get("cah").getAsInt();
            int sex = animalObject.get("sexo").getAsInt();
            int age = animalObject.get("idade").getAsInt();
            int idOng = animalObject.get("idOng").getAsInt();
            int color = animalObject.get("cor").getAsInt();
            String description = animalObject.get("descricao").getAsString();
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
	            String name = animalObject.get("nome").getAsString();
	            int size = animalObject.get("porte").getAsInt();
	            int hairType = animalObject.get("pelagem").getAsInt();
	            int animalToAnimal = animalObject.get("caa").getAsInt();
	            int animalToPerson = animalObject.get("cah").getAsInt();
	            int sex = animalObject.get("sexo").getAsInt();
	            int age = animalObject.get("idade").getAsInt();
	            int idOng = animalObject.get("idOng").getAsInt();
	            int color = animalObject.get("cor").getAsInt();
	            String description = animalObject.get("descricao").getAsString();
	            List<String> links = new ArrayList<>();
	            
	            if (animalObject.has("Links")) {
	                JsonObject linksObject = animalObject.getAsJsonObject("Links");
	                for (String key : linksObject.keySet()) {
	                    String link = linksObject.get(key).getAsString();
	                    links.add(link);
	                }
	            }
	            return new Animal(String.valueOf(idAnimal), race, name, String.valueOf(size), String.valueOf(hairType), String.valueOf(animalToAnimal),
	                    String.valueOf(animalToPerson), String.valueOf(sex), String.valueOf(age), String.valueOf(idOng), String.valueOf(color), description, links, number);
	        	}catch(NumberFormatException e) {
	        	e.printStackTrace();
	        	return null;
	        }
     }
	 public static Animal convertValues(Animal animal) {
		    String race = (animal.getRace().equals(RACA_CACHORRO)) ? "Cachorro" : "Gato";
		    animal.setRace(race);
		    
		    String size = "";
		    switch (animal.getSize()) {
		        case TAMANHO_PEQUENO:
		            size = "Pequeno";
		            break;
		        case TAMANHO_MEDIO:
		            size = "Médio";
		            break;
		        case TAMANHO_GRANDE:
		            size = "Grande";
		            break;
		    }
		    animal.setSize(size);
		    
		    String hairType = "";
		    switch (animal.getHairType()) {
		        case PELO_PEQUENO:
		            hairType = "Curto";
		            break;
		        case PELO_MEDIO:
		            hairType = "Médio";
		            break;
		        case PELO_LONGO:
		            hairType = "Longo";
		            break;
		    }
		    animal.setHairType(hairType);
		    
		    String animalToAnimal = "";
		    switch (animal.getAnimalToAnimal()) {
		        case CONVIVENCIA_MANSO:
		            animalToAnimal = "Manso";
		            break;
		        case CONVIVENCIA_AGRESSIVO:
		            animalToAnimal = "Agressivo";
		            break;
		        case CONVICENCIA_AGITADO:
		            animalToAnimal = "Agitado";
		            break;
		        case CONVIVENCIA_AMIGAVEL:
		            animalToAnimal = "Amigável";
		            break;
		        case CONVICENCIA_AMEDRONTADO:
		            animalToAnimal = "Amedrontado";
		            break;
		    }
		    animal.setAnimalToAnimal(animalToAnimal);
		    
		    String animalToPerson = "";
		    switch (animal.getAnimalToPerson()) {
		        case CONVIVENCIA_MANSO:
		            animalToPerson = "Manso";
		            break;
		        case CONVIVENCIA_AGRESSIVO:
		            animalToPerson = "Agressivo";
		            break;
		        case CONVICENCIA_AGITADO:
		            animalToPerson = "Agitado";
		            break;
		        case CONVIVENCIA_AMIGAVEL:
		            animalToPerson = "Amigável";
		            break;
		        case CONVICENCIA_AMEDRONTADO:
		            animalToPerson = "Amedrontado";
		            break;
		    }
		    animal.setAnimalToPerson(animalToPerson);
		    
		    String color = "";
		    switch (animal.getColor()) {
		        case COR_MARROM:
		            color = "Marrom";
		            break;
		        case COR_BRANCO:
		            color = "Branco";
		            break;
		        case COR_PRETO:
		            color = "Preto";
		            break;
		        case COR_DOURADO:
		            color = "Dourado";
		            break;
		        case COR_BEGE:
		            color = "Bege";
		            break;
		        case COR_LARANJA:
		            color = "Laranja";
		            break;
		        case COR_RAJADO:
		            color = "Rajado";
		            break;
		        case COR_CINZA:
		            color = "Cinza";
		            break;
		        case COR_BICOLOR:
		            color = "Bicolor";
		            break;
		        case COR_TRICOLOR:
		            color = "Tricolor";
		            break;
		    }
		    animal.setColor(color);
		    
		    String sex = "";
		    switch (animal.getSex()) {
		        case SEXO_MACHO:
		            sex = "Macho";
		            break;
		        case SEXO_FEMEA:
		            sex = "Fêmea";
		            break;
		    }
		    animal.setSex(sex);
		    
		    return animal;
		}


	 
}
