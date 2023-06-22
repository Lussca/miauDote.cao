package controller.servlets;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;


import controller.Validations;
import controller.handler.*;
import model.entity.Animal;
import model.Dao;


public class PetRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	JwtHandler jwtH = new JwtHandler();
    RequestResponseHandler rrh = new RequestResponseHandler();
    Dao dao = new Dao();

     public PetRegisterServlet(){
        super();
       
     }
     protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	response.setContentType("text/plain");
 	    response.setStatus(200);
 	    response.getWriter().println("MENSAGEM");
 	    response.getWriter().flush();
 	    response.getWriter().close();
     }
     protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	rrh.configureCors(response);
    	 /*String authorizationHeader = request.getHeader("Authorization");
    	 System.out.println("PEGOU O TOKEN");
    	 if(authorizationHeader == null || authorizationHeader.startsWith("Bearer ")) {
    	 	rrh.sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, Validations.INVALID_TOKEN);
    	 	return;
    	 }else {
    	 String jwt = authorizationHeader.substring(7);
    	 String login = request.getParameter("login");
    	 boolean isOng = Boolean.parseBoolean(request.getParameter("isOng"));

    	 try {
    	 	if(jwtH.validateJWT(jwt, login, isOng)) {
    	 		StringBuilder requestBody = new StringBuilder();
    	 		try(BufferedReader reader = request.getReader()){
    	 			String line;
    	 			while((line = reader.readLine()) != null){
    	 				requestBody.append(line);
    	 			}
    	 		}
    	 		Gson gson = new Gson();
    	 		String urlJson = requestBody.toString();
    	 		String json = gson.toJson(urlJson);
    	 		//teste para extração de link das imagens de um animal
    	 		Type urlListType = new TypeToken<ArrayList<String>>(){}.getType();
    	 		ArrayList<String> urlArray = gson.fromJson(json, urlListType);

    	 	}
    	 	}catch(Exception e) {
    	 		
    	 	}*/
    	 
     
    	System.out.println("CHEGOU AQUI");
        StringBuilder requestBody = new StringBuilder();
        BufferedReader reader = request.getReader();
        	String line;
        	while((line = reader.readLine()) != null) {
        		requestBody.append(line);
        	}
        	String jsonPayLoad = requestBody.toString();
        	try {
        	Animal animal = Animal.parseAnimalJson(jsonPayLoad);
        	int idAnimal = dao.insertAnimal(animal);
        	if(idAnimal == -1) {
        		rrh.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Validations.DATABASE_ERROR);
        		}else {
        			for(int i = 0; i< animal.getLinks().size(); i++) {
    	        		int newImageId = dao.insertImages(animal, idAnimal, i);
    	        		if(newImageId == -1) {
    	        			rrh.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Validations.ERROR_ON_IMAGE_INSERTION);
    	        			break;
    	        		}else {
    	        			rrh.sendOkResponse(response);
    	        		}
        			}
        		}
        	}catch(NumberFormatException e) {
        		e.printStackTrace();
        		rrh.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, Validations.INVALID_ANIMAL);
        		}
        	}
     
     
     protected void doOptions(HttpServletRequest request, HttpServletResponse response) {
    	 rrh.configureCors(response);
     }
}
/*String authorizationHeader = request.getHeader("Authorization");
if(authorizationHeader == null || authorizationHeader.startsWith("Bearer ")) {
	rrh.sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, Validations.INVALID_TOKEN);
	return;
}else {
String jwt = authorizationHeader.substring(7);
String login = request.getParameter("login");
boolean isOng = Boolean.parseBoolean(request.getParameter("isOng"));

try {
	if(jwtH.validateJWT(jwt, login, isOng)) {
		StringBuilder requestBody = new StringBuilder();
		try(BufferedReader reader = request.getReader()){
			String line;
			while((line = reader.readLine()) != null){
				requestBody.append(line);
			}
		}
		Gson gson = new Gson();
		String urlJson = requestBody.toString();
		String json = gson.toJson(urlJson);
		//teste para extração de link das imagens de um animal
		Type urlListType = new TypeToken<ArrayList<String>>(){}.getType();
		ArrayList<String> urlArray = gson.fromJson(json, urlListType);

	}
} catch (ClassNotFoundException | IOException | SQLException e) {
	rrh.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Validations.SERVER_ERROR);
	e.printStackTrace();
		}
	}*/