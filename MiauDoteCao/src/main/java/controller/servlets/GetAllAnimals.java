package controller.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import controller.Validations;
import controller.handler.*;
import model.Dao;
import model.entity.Animal;

/**
 * Servlet implementation class GetAllAnimals
 */
public class GetAllAnimals extends HttpServlet {
	private static final long serialVersionUID = 1L;
	RequestResponseHandler rrh = new RequestResponseHandler();
    Dao dao = new Dao();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllAnimals() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rrh.configureCors(response);
		try {
			ArrayList<Animal> animals = dao.getAllAnimais();
			if(!animals.isEmpty()) {
			JsonArray jsonArray = new Gson().toJsonTree(animals).getAsJsonArray();
			JsonObject jsonObject = new JsonObject();
	        jsonObject.add("animals", jsonArray);
	        response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
	        PrintWriter out = response.getWriter();
	        String jsonString = jsonObject.toString();
			out.print(jsonString);
	        out.flush();
	        out.close();
	        rrh.sendOkResponse(response);
			}else {
				rrh.sendErrorResponse(response, HttpServletResponse.SC_NO_CONTENT, Validations.NO_ANIMALS_FOUND);
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			rrh.sendErrorResponse(response, HttpServletResponse.SC_BAD_GATEWAY, Validations.DATABASE_ERROR);
		}
	}
}
