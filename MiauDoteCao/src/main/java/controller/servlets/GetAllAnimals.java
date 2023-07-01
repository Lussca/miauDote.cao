package controller.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Validations;
import controller.handler.*;
import model.Dao;
import model.entity.Animal;

public class GetAllAnimals extends HttpServlet {
	private static final long serialVersionUID = 1L;
	RequestResponseHandler rrh = new RequestResponseHandler();
    Dao dao = new Dao();

    public GetAllAnimals() {
        super();
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rrh.configureCors(response);
		try {
			ArrayList<Animal> animals = dao.getAllAnimais();
			if(!animals.isEmpty()) {
			RequestResponseHandler.generateAndSendJson(animals, response);
			}else {
				rrh.sendErrorResponse(response, HttpServletResponse.SC_NO_CONTENT, Validations.NO_ANIMALS_FOUND);
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			rrh.sendErrorResponse(response, HttpServletResponse.SC_BAD_GATEWAY, Validations.DATABASE_ERROR);
		}
	}
}
