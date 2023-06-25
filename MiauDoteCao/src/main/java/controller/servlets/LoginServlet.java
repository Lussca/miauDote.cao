package controller.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import model.Dao;
import controller.Validations;
import controller.Encrypt;
import controller.handler.*;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Validations validations = new Validations();
	Encrypt encrypt = new Encrypt();
	JwtHandler jwtHandler = new JwtHandler();
	RequestResponseHandler rrh = new RequestResponseHandler();
	Dao dao = new Dao();

    public LoginServlet() {
        super();
    }

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        rrh.configureCors(response);
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        try {
            List<Boolean> confirmations = validations.verifyLogin(login, password);
            if (confirmations.get(0) && confirmations.get(1)) {
                setSessionAndSendResponse(request, response, login, true);
            } else if (!confirmations.get(0) && confirmations.get(1)) {
                setSessionAndSendResponse(request, response, login, false);
            } else {
                rrh.sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, Validations.WRONG_CREDENTIALS);
            }
        } catch (IOException | SQLException | NoSuchAlgorithmException | ClassNotFoundException e) {
            e.printStackTrace();
            rrh.sendErrorResponse(response, HttpServletResponse.SC_NOT_IMPLEMENTED, Validations.DATABASE_ERROR);
        } 
    }
    @Override
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rrh.configureCors(response);
	}
    
	private void setSessionAndSendResponse(HttpServletRequest request, HttpServletResponse response, String login, boolean isOng) throws IOException, SQLException, NoSuchAlgorithmException {
		    HttpSession session = request.getSession();
		    session.setAttribute("login", login);
		    session.setAttribute("isLogged", true);
		    session.setAttribute("isOng", isOng);

		    String id = idGenerator();
		    String subject = login;
		    String jwt = jwtHandler.createJWT(id, new Encrypt().toHash(subject), Encrypt.JWT_ISSUER, 3600000);
		    String userId = null;

		    try {
		        dao.insertAndUpdateJWT(jwt, isOng, login);
		        userId = dao.getUserId(login, isOng);
		    } catch (Exception e) {
		        e.printStackTrace();
		        rrh.sendErrorResponse(response, HttpServletResponse.SC_NOT_IMPLEMENTED, Validations.JWT_ERROR);
		        return;
		    }

		    Map<String, Object> sessionData = new HashMap<>();
		    sessionData.put("isLogged", session.getAttribute("isLogged"));
		    sessionData.put("isOng", session.getAttribute("isOng"));
		    sessionData.put("jwt", jwt);

		    if (userId != null) {
		        sessionData.put("userId", userId);
		        String json = new Gson().toJson(sessionData);

		        response.setContentType("application/json");
		        response.setCharacterEncoding("UTF-8");
		        response.setStatus(HttpServletResponse.SC_OK);
		        try (PrintWriter out = response.getWriter()) {
		            out.print(json);
		            out.flush();
		        } catch (IOException e) {
		            e.printStackTrace();
		            rrh.sendErrorResponse(response, HttpServletResponse.SC_NOT_IMPLEMENTED, Validations.SERVER_ERROR);
		        }
		    } else {
		       rrh.sendErrorResponse(response, HttpServletResponse.SC_NOT_IMPLEMENTED, Validations.SERVER_ERROR);
		    }
		}
		
	
	
	private String idGenerator() {
		        Random random = new Random();
		        long newId;
		        do {
		            newId = (long)(random.nextDouble() * 888888888888888L + 111111111111111L);
		        } while (hasRepeatedDigits(newId));
		        return Long.toString(newId);
		    }
	 private static boolean hasRepeatedDigits(long number) {
	        String strNumber = Long.toString(number);
	        char firstChar = strNumber.charAt(0);
	        for (int i = 1; i < strNumber.length(); i++) {
	            if (strNumber.charAt(i) != firstChar) {
	                return false;
	            }
	        }
	        return true;
	    }	
}