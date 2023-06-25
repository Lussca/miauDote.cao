package controller.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import controller.handler.RequestResponseHandler;
import controller.Validations;
import model.Dao;
import model.entity.Animal;

public class AdoptionApplicationServlet extends HttpServlet {
	 
	private static final long serialVersionUID = 1L;
	RequestResponseHandler rrh = new RequestResponseHandler();
	Validations validations = new Validations();
	Dao dao = new Dao();

    public AdoptionApplicationServlet() {
        super();
    }
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rrh.configureCors(response);
		try {
			String requestBody = RequestResponseHandler.getRequestBody(request);
			Gson gson = new Gson();
	        JsonObject json = gson.fromJson(requestBody, JsonObject.class);
	        String idUser = json.get("idUser").getAsString();
	        String idAnimal = json.get("idAnimal").getAsString();
	        boolean alreadyExists = dao.checkForDuplicityAdoptionApplication(idUser, idAnimal);
			if(alreadyExists) {
				rrh.sendResponse(response, HttpServletResponse.SC_CONFLICT, Validations.APPLICATION_ALREADY_EXISTS);
			}else {
	        if(dao.adoptionApplication(idAnimal,idUser)) {
				rrh.sendOkResponse(response);
					}
				}
			}catch(NumberFormatException | ClassNotFoundException e){
				e.printStackTrace();
				rrh.sendErrorResponse(response, HttpServletResponse.SC_NOT_IMPLEMENTED, Validations.SERVER_ERROR);
			}	
		}
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	rrh.configureCors(response);
    	try {
    		String requestBody = RequestResponseHandler.getRequestBody(request);
    		Gson gson = new Gson();
	        JsonObject json = gson.fromJson(requestBody, JsonObject.class);
	        String idUser = json.get("idUser").getAsString();
	        String idAnimal = json.get("idAnimal").getAsString();
	        int result = dao.deleteApplication(idUser, idAnimal);
	        if(result > 0) {
	        	rrh.sendOkResponse(response);
	        }else {
	        	rrh.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, Validations.APPLICATION_DO_NOT_EXISTS);
	        }
    	}catch(NumberFormatException | ClassNotFoundException e){
			e.printStackTrace();
			rrh.sendErrorResponse(response, HttpServletResponse.SC_NOT_IMPLEMENTED, Validations.SERVER_ERROR);
		}
    }
    @Override 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rrh.configureCors(response);
		String idUser = request.getParameter("idUser");
		ArrayList<Animal> animals = new ArrayList<Animal>();
		try {
			animals = dao.getApplications(idUser);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			rrh.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Validations.SERVER_ERROR);
		}
		if(animals.isEmpty()) {
			rrh.sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, Validations.NO_APPLICATIONS_FOUND);
		}else {
			RequestResponseHandler.generateAndSendJson(animals, response);
		}
	}
	@Override
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rrh.configureCors(response);
	}
	
	
	
}
