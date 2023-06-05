package model.entity;

public class Animal {
	String id;
	String race;
    String name;
    long size;
    long age;
    String[] imagesUrl;
    

    public String getRace(){
        return race;
    }
    public void setRace(String race){
        this.race = race;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public long getSize(){
        return size;
    }
    public void setSize(long size){
        this.size = size;
    }
    public long getAge(){
        return age;
    }
    public void setAge(long age){
        this.age = age;
    }
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String[] getImagesUrl() {
		return imagesUrl;
	}
	public void setImagesUrl(String[] imagesUrl) {
		this.imagesUrl = imagesUrl;
	}

}
