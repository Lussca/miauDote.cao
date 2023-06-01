package model.entity;

public class Animal {
	String race;
    String name;
    long size;
    long age;

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

}
