package controller.servlets;

import java.io.IOException;

import controller.Validations;
import controller.handler.RequestResponseHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.Gson;

import controller.handler.email.*;

/**
 * Servlet implementation class EmailTest
 */
public class ChangePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	RequestResponseHandler rrh = new RequestResponseHandler();

    public ChangePasswordServlet() {
        super();
    }
    @Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rrh.configureCors(response);
		String requestBody = RequestResponseHandler.getRequestBody(request);
		Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(requestBody, JsonObject.class);
        JsonObject emailObject = jsonObject.getAsJsonObject();
        String email = emailObject.get("email").getAsString();
		ChangePassword cp = new ChangePassword();
		if(cp.send(email)) {
			rrh.sendOkResponse(response);
		}else {
			rrh.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Validations.EMAIL_NOT_SENT);
		}
	}
    @Override
	public void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rrh.configureCors(response);
	}
}
