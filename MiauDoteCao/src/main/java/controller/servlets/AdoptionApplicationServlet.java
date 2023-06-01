package controller.servlets;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.handler.RequestResponseHandler;
import controller.Validations;
import model.Dao;

/**
 * Servlet implementation class AdoptionApplicationServlet
 */
public class AdoptionApplicationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	RequestResponseHandler rrh = new RequestResponseHandler();
	Validations validations = new Validations();
	Dao dao = new Dao();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdoptionApplicationServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
		int animalId = Integer.parseInt(request.getParameter("animalId"));
		int adopterId = Integer.parseInt(request.getParameter("adopterId"));
			if(dao.adoptionApplication(String.valueOf(animalId), String.valueOf(adopterId))) {
				rrh.sendResponse(response, HttpServletResponse.SC_OK, 0);
				}
			}catch(NumberFormatException | ClassNotFoundException e){
				rrh.sendErrorResponse(response, HttpServletResponse.SC_NOT_IMPLEMENTED, Validations.SERVER_ERROR);
		}	
	}
}
