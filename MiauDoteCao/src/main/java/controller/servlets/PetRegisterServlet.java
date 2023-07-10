package controller.servlets;


import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
     @Override
     protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	rrh.configureCors(response);
    	String requestBody = RequestResponseHandler.getRequestBody(request);
        
        	String jsonPayLoad = requestBody.toString();
        	try {
        	Animal animal = Animal.parseAnimalJson(jsonPayLoad);
        	boolean jwtValidator = false;
	        try {
				jwtValidator = RequestResponseHandler.jwtValidator(request, animal.getIdOng(), true);
			} catch (IOException | SQLException | ClassNotFoundException e) {
				e.printStackTrace();
				rrh.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, Validations.JWT_ERROR);
			}
	        
	        if(!jwtValidator) {
	        	rrh.sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, Validations.JWT_ERROR);
	        	return;
	        }else {
        	int idAnimal = dao.insertAnimal(animal);
        	
        	if(idAnimal == -1) {
        		rrh.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Validations.DATABASE_ERROR);
        		}else {
        			for(int i = 0; i< animal.getLinks().size(); i++) {
    	        		int newImageId = dao.insertImages(animal, idAnimal, i);
    	        		System.out.println("Imagem inserida");
    	        		if(newImageId == -1) {
    	        			rrh.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Validations.ERROR_ON_IMAGE_INSERTION);
    	        			break;
    	        		}else {
    	        			rrh.sendOkResponse(response);
    	        		}
        			}
        		}
        	}
        	}catch(NumberFormatException e) {
        		e.printStackTrace();
        		rrh.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, Validations.INVALID_ANIMAL);
        		}
        	}
     
     @Override
     protected void doOptions(HttpServletRequest request, HttpServletResponse response) {
    	 rrh.configureCors(response);
     }
}