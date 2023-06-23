package controller.servlets;

import java.io.IOException;

import controller.Validations;
import controller.handler.RequestResponseHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    //ESTÁ COM PROBLEMA DE CORS
    //ALTERAR O MÉTODO DE ENVIO DE EMAIL
    @Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rrh.configureCors(response);
		String email = request.getParameter("email");
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
