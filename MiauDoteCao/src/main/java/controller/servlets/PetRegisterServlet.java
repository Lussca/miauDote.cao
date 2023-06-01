package controller.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Validations;
import controller.handler.*;

/**
 * Servlet implementation class PetRegisterServlet
 */
public class PetRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	JwtHandler jwtH = new JwtHandler();
    RequestResponseHandler rrh = new RequestResponseHandler();
    /**
     * @see HttpServlet#HttpServlet()
     */

     public PetRegisterServlet(){
        super();
     }
     protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        rrh.configureCors(response);
        response.setHeader("Access-Control-Allow-Headers", "Authorizations, Content-Type");
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader == null || authorizationHeader.startsWith("Bearer ")) {
        	rrh.sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, Validations.INVALID_TOKEN);
        	return;
        }else {
        String jwt = authorizationHeader.substring(7);
        String login = request.getParameter("login");
        boolean isOng = Boolean.parseBoolean(request.getParameter("isOng"));
        
        try {
			if(jwtH.validateJWT(jwt, login, isOng)) {
				StringBuilder requestBody = new StringBuilder();
				try(BufferedReader reader = request.getReader()){
					String line;
					while((line = reader.readLine()) != null){
						requestBody.append(line);
					}
				}
				String urlJson = requestBody.toString();
			}
		} catch (ClassNotFoundException | IOException | SQLException e) {
			rrh.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Validations.SERVER_ERROR);
			e.printStackTrace();
				}
        	}
     	}
     }