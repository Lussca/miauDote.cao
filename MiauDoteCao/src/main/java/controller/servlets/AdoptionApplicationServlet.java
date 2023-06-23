package controller.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.handler.RequestResponseHandler;
import controller.Validations;
import model.Dao;

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
		int animalId = Integer.parseInt(request.getParameter("idAnimal"));
		int adopterId = Integer.parseInt(request.getParameter("idUser"));
			if(dao.adoptionApplication(String.valueOf(animalId), String.valueOf(adopterId))) {
				rrh.sendOkResponse(response);
				}
			}catch(NumberFormatException | ClassNotFoundException e){
				rrh.sendErrorResponse(response, HttpServletResponse.SC_NOT_IMPLEMENTED, Validations.SERVER_ERROR);
		}	
	}
	@Override
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rrh.configureCors(response);
	}
}
