package controller.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.Gson;

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
    @Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rrh.configureCors(response);
        String requestBody = RequestResponseHandler.getRequestBody(request);
        	String jsonPayLoad = requestBody.toString();
        	try {
        	Animal animal = Animal.parseAnimalJson(jsonPayLoad, 1);
        	boolean jwtValidator = false;
	        try {
				jwtValidator = RequestResponseHandler.jwtValidator(request, animal.getIdOng(), true);
			} catch (IOException | SQLException e) {
				e.printStackTrace();
				rrh.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, Validations.JWT_ERROR);
			}
	        
	        if(!jwtValidator) {
	        	rrh.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, Validations.JWT_ERROR);
	        }else {
        	int hasErrorAnimal = dao.updateAnimal(animal);
        	int hasErrorImage = dao.updateAnimalImages(animal);
        	if(hasErrorAnimal == 0 && hasErrorImage == 0) {
        		rrh.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Validations.DATABASE_ERROR);
        		}else {
        			rrh.sendOkResponse(response);
        			}
	        	}
        	}catch(NumberFormatException | ClassNotFoundException | SQLException e) {
        		e.printStackTrace();
        		rrh.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, Validations.INVALID_ANIMAL);
        	}
		}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException{
		rrh.configureCors(response);
		String requestBody = RequestResponseHandler.getRequestBody(request);
		String jsonPayload = requestBody.toString();
		
			Gson gson = new Gson();
        	JsonObject jsonObject = gson.fromJson(jsonPayload, JsonObject.class);

        	JsonObject animalObject = jsonObject.getAsJsonObject();
			
			String idAnimal = animalObject.get("idAnimal").getAsString();
			String idUser = animalObject.get("idUser").getAsString();
			boolean jwtValidator = false;
	        try {
				jwtValidator = RequestResponseHandler.jwtValidator(request, idUser, true);
			} catch (IOException | SQLException | ClassNotFoundException e) {
				e.printStackTrace();
				rrh.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, Validations.JWT_ERROR);
			}
	        
	        if(!jwtValidator) {
	        	rrh.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, Validations.JWT_ERROR);
	        }else {
			try {
				int result = dao.deleteAnimal(idAnimal, idUser);
				if(result > 0) {
					rrh.sendOkResponse(response);
				}else {
					rrh.sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, Validations.ANIMAL_NOT_DELETED);
				}
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
				}	
			}
	}

    @Override
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) {
   	 rrh.configureCors(response);
    }
}
