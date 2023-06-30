package controller.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import controller.Validations;
import controller.handler.RequestResponseHandler;
import model.Dao;
import model.entity.*;
/**
 * Servlet implementation class GetAnimalsByOngId
 */
public class GetAnimalsByOngId extends HttpServlet {
	private static final long serialVersionUID = 1L;
    RequestResponseHandler rrh = new RequestResponseHandler();  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAnimalsByOngId() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rrh.configureCors(response);
		String idOng = request.getParameter("idUser");
		Dao dao = new Dao();
		ArrayList<Animal> animals = new ArrayList<Animal>();
		try {
			animals = dao.getAnimalsByOngId(idOng);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			rrh.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Validations.DATABASE_ERROR);
		}
		if(animals.isEmpty()) {
			rrh.sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, Validations.NO_ANIMALS_FOUND);
		}else {
			RequestResponseHandler.generateAndSendJson(animals, response);
		}
	}

	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rrh.configureCors(response);
	}

}
