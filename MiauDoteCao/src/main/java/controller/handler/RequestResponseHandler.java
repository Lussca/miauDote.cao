package controller.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import controller.Validations;

import model.Dao;

public class RequestResponseHandler {
	
	public void configureCors(HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin","*");
		response.addHeader("Access-Control-Allow-Methods","POST,GET,OPTIONS,PUT,DELETE,HEAD");
		response.addHeader("Access-Control-Allow-Headers","X-PINGOTHER,Origin,X-Requested-With,Content-Type,Accept");
		response.addHeader("Access-Control-Max-Age","1728000");
		response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
		}
	public void sendErrorResponse(HttpServletResponse response, int status, int message) throws IOException {
	    response.setContentType("text/plain");
	    response.setStatus(status);
	    response.getWriter().println(message);
	    response.getWriter().flush();
	    response.getWriter().close();
	}
	public void sendResponse(HttpServletResponse response, int status, int message) throws IOException {
	    response.setContentType("text/plain");
	    response.setStatus(status);
	    response.getWriter().println(message);
	    response.getWriter().flush();
	    response.getWriter().close();
	}
	public void sendOkResponse(HttpServletResponse response) throws IOException {
		response.setContentType("text/plain");
	    response.setStatus(HttpServletResponse.SC_OK);
	    response.getWriter().println(Validations.NO_ERROR);
	    response.getWriter().flush();
	    response.getWriter().close();
	}
	public static String getRequestBody(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
	public static <T> void generateAndSendJson(ArrayList<T> arrayList, HttpServletResponse response) throws IOException {
		JsonArray jsonArray = new Gson().toJsonTree(arrayList).getAsJsonArray();
		JsonObject jsonObject = new JsonObject();
        jsonObject.add("animals", jsonArray);
        response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String jsonString = jsonObject.toString();
		out.print(jsonString);
        out.flush();
        out.close();
        new RequestResponseHandler().sendOkResponse(response);
	}
	@SuppressWarnings("unused")
	public static boolean jwtValidator(HttpServletRequest request, String idUser, boolean isOng) throws ClassNotFoundException, IOException, SQLException {
		Dao dao = new Dao();
		String authorizationHeader = request.getHeader("Authorization");
   	 	System.out.println("PEGOU O TOKEN: "+authorizationHeader);
   	 	if(authorizationHeader.startsWith("Bearer ") || authorizationHeader  != null) {
   	 		System.out.println("Primeira validação feita.");
   	 		String reqJWT = authorizationHeader.substring(7);
   	 		System.out.println("O token é: "+reqJWT);
   	 		ArrayList<String> userTypeAndEmail = dao.getUserEmailAndType(idUser, isOng);
   	 		String email = userTypeAndEmail.get(0);
   	 		JwtHandler jh = new JwtHandler();
   	 		boolean validate = jh.validateJWT(reqJWT, email, isOng);
   	 		if(validate) {
   	 			System.out.println("VALIDO");
   	 			return true;
   	 			}else {
   	 				System.out.println("INVALIDO");
   	 				return false;
   	 			}
   	 		}
		return false;
		}
}