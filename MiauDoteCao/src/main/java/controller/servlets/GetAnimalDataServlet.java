package controller.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
			JSONObject animalJson = new JSONObject();
			animalJson.put("race", animal.getRace());
	        animalJson.put("nome", animal.getName());
	        animalJson.put("porte", animal.getSize());
	        animalJson.put("pelagem", animal.getHairType());
	        animalJson.put("caa", animal.getAnimalToAnimal());
	        animalJson.put("cah", animal.getAnimalToPerson());
	        animalJson.put("sexo", animal.getSex());
	        animalJson.put("idade", animal.getAge());
	        animalJson.put("idOng", animal.getIdOng());
	        animalJson.put("cor", animal.getColor());
	        animalJson.put("descricao", animal.getAnimalDescription());
	        String jsonString = animalJson.toString();
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        OutputStream outputStream = response.getOutputStream();
	        outputStream.write(jsonString.getBytes());

	        outputStream.close();		
		} catch (ClassNotFoundException | IOException e) {
			rrh.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Validations.SERVER_ERROR);
			e.printStackTrace();
		}
	}
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rrh.configureCors(response);
	}
}
