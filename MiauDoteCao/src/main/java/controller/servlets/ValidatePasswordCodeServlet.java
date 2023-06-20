package controller.servlets;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Validations;
import controller.handler.RequestResponseHandler;
import model.Dao;

public class ValidatePasswordCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	RequestResponseHandler rrh = new RequestResponseHandler();
	Dao dao = new Dao();

    public ValidatePasswordCodeServlet() {
        super();

    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rrh.configureCors(response);
		String email = request.getParameter("email");
		String validationCode = request.getParameter("validationCode");
		ArrayList<String> data = new ArrayList<String>();
			try {
				data = dao.getUserId(email);
				boolean validate = dao.compareValidationCode(validationCode, Boolean.parseBoolean(data.get(1)), data.get(0));
				if(validate) {
					rrh.sendOkResponse(response);
				}else {
					rrh.sendErrorResponse(response, HttpServletResponse.SC_FORBIDDEN, Validations.WRONG_CREDENTIALS);
				}
			} catch (SQLException | NoSuchAlgorithmException e) {
				e.printStackTrace();
				rrh.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Validations.SERVER_ERROR);
			}
	}

	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rrh.configureCors(response);
	}

}
