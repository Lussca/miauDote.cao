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
import model.entity.Animal;
/**
 * Servlet implementation class GetAnimalByFilter
 */
public class GetAnimalByFilter extends HttpServlet {
	private static final long serialVersionUID = 1L;
	RequestResponseHandler rrh = new RequestResponseHandler();
	Dao dao = new Dao();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAnimalByFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rrh.configureCors(response);
			String filtro = request.getParameter("filtro1");
			try {
				ArrayList<Animal> animals = dao.selectAnimals(filtro, filtro);
				if(!animals.isEmpty()) {
					JsonArray jsonArray = new Gson().toJsonTree(animals).getAsJsonArray();
					JsonObject jsonObject = new JsonObject();
			        jsonObject.add("animals", jsonArray);
			        PrintWriter out = response.getWriter();
			        String jsonString = jsonObject.toString();
					out.print(jsonString);
			        out.flush();
			        out.close();
			        rrh.sendErrorResponse(response, HttpServletResponse.SC_ACCEPTED, Validations.NO_ERROR);
				}else {
					rrh.sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, Validations.NO_ANIMALS_MATCHING_FILTERS);
				}
			} catch (SQLException e) {
				rrh.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Validations.DATABASE_ERROR);
				e.printStackTrace();
			}
		}
	}
