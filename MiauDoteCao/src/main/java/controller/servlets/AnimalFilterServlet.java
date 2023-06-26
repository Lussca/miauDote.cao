package controller.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import controller.Validations;
import controller.handler.RequestResponseHandler;
import model.Dao;
import model.entity.Adress;
import model.entity.Animal;

/**
 * Servlet implementation class AnimalFilterServlet
 */
public class AnimalFilterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	RequestResponseHandler rrh = new RequestResponseHandler();
	Dao dao = new Dao();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnimalFilterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rrh.configureCors(response);
		System.out.println("entrou na servlet");
			String race = request.getParameter("race");
			String size = request.getParameter("size");
			String hairType = request.getParameter("hairType");
			String animalToPerson = request.getParameter("animalToPerson");
			String animalToAnimal = request.getParameter("animalToAnimal");
			String sex = request.getParameter("sex");
			String age = request.getParameter("age");
			String userId = request.getParameter("userId");	
			try {
				Adress a = dao.getUserAdress(userId);
				ArrayList<Animal> animals = dao.selectAnimals(race, size, hairType, animalToAnimal, animalToPerson, sex, age, userId, a.getCity(), a.getState());
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
					rrh.sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, Validations.NO_ANIMALS_MATCHING_FILTERS);
					System.out.println("debug");
					System.out.println("deu erro");
				}
			} catch (SQLException e) {
				rrh.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Validations.DATABASE_ERROR);
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				rrh.sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, Validations.DATA_NOT_FOUND);
				e.printStackTrace();
			}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rrh.configureCors(response);
		String requestBody = RequestResponseHandler.getRequestBody(request);
		Gson gson = new Gson();
        JsonObject json = gson.fromJson(requestBody, JsonObject.class);
        String animalToAnimal = json.get("caa").getAsString();
        String animalToPerson = json.get("cah").getAsString();
        String especies = json.get("especie").getAsString();
        String idUser = json.get("idUser").getAsString();
        String furType = json.get("pelagem").getAsString();
        String size = json.get("porte").getAsString();
        String sex = json.get("sexo").getAsString();
        //String age = json.get("age").getAsString();
			try {
				Adress a = dao.getUserAdress(idUser);
				ArrayList<Animal> animals = dao.selectAnimals(especies, size, furType, animalToAnimal, animalToPerson, sex, "0", idUser, a.getCity(), a.getState());
				if(!animals.isEmpty()) {
					RequestResponseHandler.generateAndSendJson(animals, response);
				}else {
					rrh.sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, Validations.NO_ANIMALS_MATCHING_FILTERS);
				}
			} catch (SQLException e) {
				rrh.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Validations.DATABASE_ERROR);
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				rrh.sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, Validations.DATA_NOT_FOUND);
				e.printStackTrace();
			}
	}
	/**
	 * @see HttpServlet#doOptions(HttpServletRequest, HttpServletResponse)
	 */
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 rrh.configureCors(response);
	}

}
