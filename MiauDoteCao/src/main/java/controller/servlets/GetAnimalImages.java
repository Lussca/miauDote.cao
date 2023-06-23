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
import controller.handler.RequestResponseHandler;
import model.Dao;

public class GetAnimalImages extends HttpServlet {
	private static final long serialVersionUID = 1L;
	RequestResponseHandler rrh = new RequestResponseHandler();
	Dao dao = new Dao();

    public GetAnimalImages() {
        super();
    }
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rrh.configureCors(response);
		try{
			int id = Integer.parseInt(request.getParameter("idAnimal"));
			ArrayList<String> images = dao.getAnimalImages(String.valueOf(id));
			if(!images.isEmpty()) {
				JsonArray jsonArray = new Gson().toJsonTree(images).getAsJsonArray();
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
				rrh.sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, Validations.NO_IMAGES_FOUND);
			}
		}catch(NumberFormatException | ClassNotFoundException e) {
			rrh.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, Validations.BAD_REQUEST);
		}
	}
}
