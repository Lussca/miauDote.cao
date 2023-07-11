package controller.servlets;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.Gson;

import controller.Validations;
import controller.handler.RequestResponseHandler;
import model.Dao;

public class ValidateChangePasswordCode extends HttpServlet {
	private static final long serialVersionUID = 1L;
	RequestResponseHandler rrh = new RequestResponseHandler();
	Dao dao = new Dao();

    public ValidateChangePasswordCode() {
        super();

    }
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rrh.configureCors(response);
		String requestBody = RequestResponseHandler.getRequestBody(request);
		Gson gson = new Gson();
		JsonObject jsonObject = gson.fromJson(requestBody, JsonObject.class);
           JsonObject dataObject = jsonObject.getAsJsonObject("data");
            String email = dataObject.get("email").getAsString();
            String validationCode = dataObject.get("validationCode").getAsString();
            String newPassword = dataObject.get("newPassword").getAsString();
            boolean validateNewPassword = new Validations().validatePassword(newPassword);
           
            ArrayList<String> data = new ArrayList<String>();
            data = dao.getUserIdAndType(email);
            try {
            	if(validateNewPassword) {
					if(dao.compareValidationCode(validationCode, Boolean.parseBoolean(data.get(1)), data.get(0))) {
						System.out.println("CÓDIGO CERTO");
						if(dao.changePassword(newPassword, Boolean.parseBoolean(data.get(1)), data.get(0)) > 0) {
							System.out.println("SENHA ALTERADA");
							if(dao.deleteValidationCode(Boolean.parseBoolean(data.get(1)), data.get(0)) > 0){
								System.out.println("CODIGO DELETADO");
								rrh.sendOkResponse(response);
							}else {
								System.out.println("deu erro");
							}
						}
					}
            	}
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	}
    @Override
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rrh.configureCors(response);
	}
}
