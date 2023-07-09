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
import model.entity.Animal;

public class GetAnimalDataServlet extends HttpServlet {
	RequestResponseHandler rrh = new RequestResponseHandler();
	private static final long serialVersionUID = 1L;

    public GetAnimalDataServlet() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rrh.configureCors(response);
		String idAnimal = request.getParameter("idAnimal");
		Dao dao = new Dao();
		try {
			Animal animal = dao.getAnimalData(new Animal(idAnimal));
			ArrayList<Animal> animalArray = new ArrayList<Animal>();
			animalArray.add(animal);
			RequestResponseHandler.generateAndSendJson(animalArray, response);
		} catch (ClassNotFoundException | IOException e) {
			rrh.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Validations.SERVER_ERROR);
			e.printStackTrace();
		}
	}
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rrh.configureCors(response);
	}

}
