package controller.servlets;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.handler.*;
import controller.Validations;
import controller.Encrypt;
import model.Dao;
import model.entity.*;


/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Dao dao = new Dao();
	Encrypt encrypt = new Encrypt();
	Validations validations = new Validations();
	RequestResponseHandler rrh = new RequestResponseHandler();
	int responseToClient = -1;
	boolean hasError = true;


    public RegisterServlet() {
        super();
    }
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rrh.configureCors(response);
		request.setCharacterEncoding("UTF-8");
		
		boolean isOng = Boolean.parseBoolean(request.getParameter("isOng"));
		
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String cpf = request.getParameter("cpf");
		String birth = request.getParameter("birth");
		System.err.println(birth);
		String state = request.getParameter("state");
		String city = request.getParameter("city");
		String neighborhood = request.getParameter("neighborhood");
		String cep = request.getParameter("cep");
		String street = request.getParameter("street");
		String number = request.getParameter("number");
		String ongName;
		int validate = 0;
		if(isOng) {
			ongName = request.getParameter("ongName");
		}else {
			ongName = "NaoEUmaOng";
		}
		
		try {
			validate = validations.validateInputs(city, neighborhood, cep, login, password, name, cpf, birth, ongName, street, number, isOng);
		} catch (ClassNotFoundException | IOException | ParseException e2) {
			e2.printStackTrace();
		}	
		switch(validate) {
		case Validations.INVALID_CITY:
			rrh.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, Validations.INVALID_CITY);
			break;
		case Validations.INVALID_CEP:
			rrh.sendErrorResponse(response, 422, Validations.INVALID_CEP);
			break;
		case Validations.INVALID_LOGIN:
			rrh.sendErrorResponse(response, 422, Validations.INVALID_LOGIN);
			break;
		case Validations.INVALID_PASSWORD:
			rrh.sendErrorResponse(response, 422, Validations.INVALID_PASSWORD);
			break;
		case Validations.INVALID_CPF:
			rrh.sendErrorResponse(response, 422, Validations.INVALID_CPF);
			break;
		case Validations.INVALID_BIRTH:
			rrh.sendErrorResponse(response, 422, Validations.INVALID_BIRTH);
			break;
		case Validations.FIELD_IS_EMPTY:
			rrh.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, Validations.FIELD_IS_EMPTY);
			break;
		case Validations.CPF_ALREADY_EXISTS:
			rrh.sendErrorResponse(response, HttpServletResponse.SC_CONFLICT, Validations.CPF_ALREADY_EXISTS);
			break;
		case Validations.ONG_ALREADY_EXISTS:
			rrh.sendErrorResponse(response, HttpServletResponse.SC_CONFLICT, Validations.ONG_ALREADY_EXISTS);
			break;
		case Validations.LOGIN_ALREADY_EXISTS:
			rrh.sendErrorResponse(response, HttpServletResponse.SC_CONFLICT, Validations.LOGIN_ALREADY_EXISTS);
			break;
		case Validations.NO_ERROR:
			if(isOng) {
				UserOng ong = new UserOng();
				Adress adress = new Adress();
				ong.setLogin(login);
				try {
					ong.setPw(encrypt.toHash(password));
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				}
				ong.setUsername(name);
				ong.setCpf(cpf);
				try {
					ong.setBirth(Validations.dateFormat(birth));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ong.setOngName(ongName);
				
				List<String> keys = encrypt.generateKeys(password);
				String publicKey = keys.get(0);
				String privateKey = keys.get(1);
				
				ong.setPublicKey(publicKey);
				ong.setPrivateKey(privateKey);
				
				adress.setState(state);
				adress.setCity(city);
				adress.setNeighborhood(neighborhood);
				adress.setCep(cep.replaceAll("-", ""));
				adress.setStreet(street);
				adress.setNumber(number);
				
				try {
					
					int id = dao.registerAdress(adress);
					hasError = dao.registerUserOng(ong, id);
					if(hasError) {
						rrh.sendOkResponse(response);
					}
					else {
						rrh.sendErrorResponse(response, HttpServletResponse.SC_NOT_IMPLEMENTED, Validations.DATABASE_ERROR);
					}
				} catch (ClassNotFoundException | SQLException | IOException e) {
					rrh.sendErrorResponse(response, HttpServletResponse.SC_NOT_IMPLEMENTED, Validations.DATABASE_ERROR);
					e.printStackTrace();
					}
			}else {
				UserAdopter adopter = new UserAdopter();
				Adress adress = new Adress();
				adopter.setLogin(login);
				try {
					adopter.setPw(encrypt.toHash(password));
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				}
				adopter.setUsername(name);
				adopter.setCpf(cpf);
				try {
					adopter.setBirth(Validations.dateFormat(birth));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				List<String> keys = encrypt.generateKeys(password);
				String publicKey = keys.get(0);
				String privateKey = keys.get(1);
				

				adopter.setPublicKey(publicKey);
				adopter.setPrivateKey(privateKey);
				
				adress.setState(state);
				adress.setCity(city);
				adress.setNeighborhood(neighborhood);
				adress.setCep(cep);
				adress.setStreet(street);
				adress.setNumber(number);
				try {
					int idAdress = dao.registerAdress(adress);
					hasError = dao.registerUserAdopter(adopter, idAdress);
					if(hasError) {
						rrh.sendOkResponse(response);
					} else {
						rrh.sendErrorResponse(response, HttpServletResponse.SC_NOT_IMPLEMENTED, Validations.DATABASE_ERROR);
					}
				} catch (ClassNotFoundException | SQLException | IOException e) {
					e.printStackTrace();
					rrh.sendErrorResponse(response, HttpServletResponse.SC_NOT_IMPLEMENTED, Validations.DATABASE_ERROR);
				}
			}
			break;
		case -1:
			break;
		}
			
	}
}