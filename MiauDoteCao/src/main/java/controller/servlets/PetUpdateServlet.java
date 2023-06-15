package controller.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Validations;
import controller.handler.RequestResponseHandler;
import model.entity.Animal;
import model.Dao;

public class PetUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	RequestResponseHandler rrh = new RequestResponseHandler();
	Dao dao = new Dao();

    public PetUpdateServlet() {
        super();
    }
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rrh.configureCors(response);
        StringBuilder requestBody = new StringBuilder();
        BufferedReader reader = request.getReader();
        	String line;
        	while((line = reader.readLine()) != null) {
        		requestBody.append(line);
        	}
        	String jsonPayLoad = requestBody.toString();
        	try {
        	Animal animal = Animal.parseAnimalJson(jsonPayLoad, 1);
        	int hasErrorAnimal = dao.updateAnimal(animal);
        	int hasErrorImage = dao.updateAnimalImages(animal);
        	if(hasErrorAnimal == 0 && hasErrorImage == 0) {
        		rrh.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Validations.DATABASE_ERROR);
        		}else {
        			rrh.sendOkResponse(response);
        		}
        	}catch(NumberFormatException | ClassNotFoundException | SQLException e) {
        		e.printStackTrace();
        		rrh.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, Validations.INVALID_ANIMAL);
        	}
		}
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) {
   	 rrh.configureCors(response);
    }
}
