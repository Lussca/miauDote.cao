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

import model.Dao;
import model.entity.UserOng;
import controller.Validations;
import controller.handler.*;

public class GetOngNameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Dao dao = new Dao();
	Gson gson = new Gson();
	RequestResponseHandler rrh = new RequestResponseHandler();

    public GetOngNameServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rrh.configureCors(response);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		try {
			ArrayList<UserOng> ongs = dao.getOngName();
				JsonArray jsonArray = new Gson().toJsonTree(ongs).getAsJsonArray();
				JsonObject jsonObject = new JsonObject();
		        jsonObject.add("names", jsonArray);
		        PrintWriter out = response.getWriter();
		        String jsonString = jsonObject.toString();
				out.print(jsonString);
		        out.flush();
		        out.close();
		        rrh.sendOkResponse(response);   
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
			rrh.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Validations.DATABASE_ERROR);
			}
		}
	}
