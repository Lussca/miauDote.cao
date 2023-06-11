package controller.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import controller.Validations;

public class RequestResponseHandler {
	public void configureCors(HttpServletResponse response) {
		//Acesso permitido para qualquer origem por enquanto
	    response.setHeader("Access-Control-Allow-Origin", "*");
	    response.setHeader("Access-Control-Allow-Methods", "GET, POST");
	    response.setHeader("Access-Control-Allow-Headers", "Content-Type");
	    
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
}