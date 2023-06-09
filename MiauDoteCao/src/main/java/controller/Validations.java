package controller;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Dao;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Validations {
	Encrypt encrypt = new Encrypt();
	Dao dao = new Dao();
	    public static final int INVALID_CITY = 1;
	    public static final int INVALID_CEP = 3;
	    public static final int INVALID_LOGIN = 4;
	    public static final int INVALID_PASSWORD = 5;
	    public static final int INVALID_CPF = 7;
	    public static final int INVALID_BIRTH = 8;
	    public static final int INVALID_ONG_NAME = 9;
	    public static final int LOGIN_ALREADY_EXISTS = 10;
	    public static final int CPF_ALREADY_EXISTS = 11;
	    public static final int ONG_ALREADY_EXISTS = 12;
	    public static final int FIELD_IS_EMPTY = 13;
	    public static final int DATABASE_ERROR = 14;
	    public static final int WRONG_CREDENTIALS = 15;
	    public static final int SERVER_ERROR = 16;
		public static final int SESSION_EXPIRED = 17;
		public static final int INVALID_TOKEN = 18;
		public static final int JWT_EXPIRED = 19;
		public static final int INVALID_DATE = 20;
		public static final int NO_ANIMALS_MATCHING_FILTERS = 21;
		public static final int DATA_NOT_FOUND = 22;
		public static final int JWT_ERROR = 23;
		public static final int INVALID_ANIMAL = 24;
		public static final int ERROR_ON_IMAGE_INSERTION = 25;
		public static final int BAD_REQUEST = 26;
		public static final int NO_IMAGES_FOUND = 27;
		public static final int NO_ANIMALS_FOUND = 28;
		public static final int EMAIL_NOT_SENT = 29;
		public static final int APPLICATION_ALREADY_EXISTS = 30;
		public static final int APPLICATION_DO_NOT_EXISTS = 31;
		public static final int NO_APPLICATIONS_FOUND = 32;
		public static final int ANIMAL_NOT_DELETED = 33;
		public static final int APPLICATION_ADDED_AND_EMAIL_NOT_SENT = 34;
		public static final int PHONE_ALREADY_EXISTS = 35;
		public static final int INVALID_PHONE_NUMBER = 36;
	    public static final int NO_ERROR = 0;
	    
	    private final String[]DDDS = {
	            "11", "12", "13", "14", "15", "16", "17", "18", "19",
	            "21", "22", "24", "27", "28", "31", "32", "33", "34",
	            "35", "37", "38", "41", "42", "43", "44", "45", "46",
	            "47", "48", "49", "51", "53", "54", "55", "61", "62",
	            "63", "64", "65", "66", "67", "68", "69", "71", "73",
	            "74", "75", "77", "79", "81", "82", "83", "84", "85",
	            "86", "87", "88", "89", "91", "92", "93", "94", "95",
	            "96", "97", "98", "99"};
		
		
		
		
		
		

		


	
	public int validateInputs(String city, String neighborhood, String cep,
			String login, String password, String name, String cpf, String birth, String ongName, String street, String number, boolean isOng, String phoneNumber)
					throws ClassNotFoundException, IOException, ParseException {
		 String[] inputs = {city, neighborhood, cep, login, password, name, cpf, birth, street, number};
		int errorMessage = -1;
		for(int i = 0; i < inputs.length; i++) {
			if(inputs[i] == null || inputs[i].isEmpty()) {
				errorMessage = FIELD_IS_EMPTY;
				System.out.println("Campo com erro: "+i);
				return errorMessage;
			}
		}
			if(isOng) {
				if(ongName == null || ongName.isEmpty()) {
					errorMessage = FIELD_IS_EMPTY;
					System.out.println("ong vazio");
					return errorMessage;
				}
			
			}
		
		if(!validateCep(cep)) {
			errorMessage = INVALID_CEP;
			System.out.println("erro no cep");
			return errorMessage;
		}
		else if(!validateLogin(login)) {
			errorMessage = INVALID_LOGIN;
			System.out.println("erro no login");
			return errorMessage;
		}else if(dao.checkForDuplicityOngEmail(login)){	
			errorMessage = LOGIN_ALREADY_EXISTS;
			System.out.println("email ja existe");
			return errorMessage;
		}
		else if(dao.checkForDuplicityAdopterEmail(login)){	
			errorMessage = LOGIN_ALREADY_EXISTS;
			System.out.println("email ja existe");
			return errorMessage;
		}else if(!validatePassword(password)) {
			errorMessage = INVALID_PASSWORD;
			System.out.println("senha invalida");
			return errorMessage;
		}
		else if(!validateCPF(cpf)) {
			errorMessage = INVALID_CPF;
			System.out.println("cpf invalido");
			return errorMessage;
		}
		else if(dao.checkForDuplicityOngCPF(cpf)) {
			errorMessage = CPF_ALREADY_EXISTS;
			System.out.println("cpf ja existe");
		} 
		else if(dao.checkForDuplicityAdopterCPF(cpf)) {
			errorMessage = CPF_ALREADY_EXISTS;
			System.out.println("cpf ja existe");
		} 
		else if(!validateBirth(birth)) {
			errorMessage = INVALID_BIRTH;
			System.out.println("data invalida");
			return errorMessage;
		}else if(dao.checkForDuplicityOngName(ongName)){
			errorMessage = ONG_ALREADY_EXISTS;
			System.out.println("ong ja existe");
			return errorMessage;
		}else if(dao.checkForDuplicityAdopterPhoneNumber(phoneNumber)) {
			errorMessage = PHONE_ALREADY_EXISTS;
			System.out.println("telefone ja existe");
			return errorMessage;
		}else if(dao.checkForDuplicityOngPhoneNumber(phoneNumber)) {
			errorMessage = PHONE_ALREADY_EXISTS;
			System.out.println("telefone ja existe");
			return errorMessage;
		}else if(validatePhoneNumber(phoneNumber)) {
			errorMessage = INVALID_PHONE_NUMBER;
			System.out.println("telefone invalido");
			return errorMessage;
		}
		else {
			errorMessage = NO_ERROR;
			System.out.println("semm erros");
			return errorMessage;
		}
		return errorMessage;
	}
	
	public boolean validatePassword(String password) {
		boolean upperCase = false;
		boolean lowerCase = false;
		boolean hasSpecialChar = false;
		boolean hasNumber = false;
		if(password.length() < 4 || password.length() > 15) {
			if(password.length() < 4) {
				System.out.println("senha curta");
			}else if(password.length() > 15) {
				System.out.println("Senha longa");
			}
			return false;
		}
		 for (int i = 0; i < password.length(); i++) {
		        if (Character.isUpperCase(password.charAt(i))) {
		        	upperCase = true;
		        	System.out.println("Tem +");
		        	break;
		        }
		        
		    }
		 for (int i = 0; i < password.length(); i++) {
		        if (Character.isLowerCase(password.charAt(i))) {
		            lowerCase = true;
		            System.out.println("tem menos");
		            break;
		        }
		 }
		 for (int i = 0; i < password.length(); i++) {
		        char c = password.charAt(i);
		        if (!Character.isLetterOrDigit(c) && !Character.isWhitespace(c)) {
		            hasSpecialChar = true;
		            System.out.println("tem especial");
		            break;
		        }
		 }
		 for (int i = 0; i < password.length(); i++) {
		        if (Character.isDigit(password.charAt(i))) {
		            hasNumber = true;
		            System.out.println("tem numero");
		            break;
		        }
		    }
		 if(upperCase && lowerCase && hasSpecialChar && hasNumber) {
			 return true;
		 }else {
			 return false;
		 }
	}
	
	private boolean validateCep(String cep) {
		String noMaskCep = cep.replaceAll("-", "");
		if(noMaskCep.length() == 8) {
	    return true;
		}else {
			return false;
		}
	}
	private boolean validateLogin(String login) {
		 String emailPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
		    Pattern pattern = Pattern.compile(emailPattern);
		    Matcher matcher = pattern.matcher(login);
		    return matcher.matches();
	}
	private boolean validateCPF(String cpf) {
		cpf = cpf.replaceAll("\\D+", "");
	    if (cpf.length() != 11) {
	        return false;
	    }
	    // VERIFICA SE TODOS OS CARACTERES SAO IGUAIS
	    if (cpf.matches("(\\d)\\1{10}")) {
	    	return false;
	    }
	    // PRIMEIRO NUMERO DE VERIFICACAO
	    int sum = 0;
	    for (int i = 0; i < 9; i++) {
	        sum += (cpf.charAt(i) - '0') * (10 - i);
	    }
	    int firstDigit = 11 - (sum % 11);
	    if (firstDigit > 9) {
	        firstDigit = 0;
	    }
	    // SEGUNDO NUMERO DE VERIFICACAO
	    sum = 0;
	    for (int i = 0; i < 10; i++) {
	        sum += (cpf.charAt(i) - '0') * (11 - i);
	    }
	    int secondDigit = 11 - (sum % 11);
	    if (secondDigit > 9) {
	        secondDigit = 0;
	    }
	    // VERIFICA AMBOS OS NUMEROS DE VERIFICACAO
	    if (firstDigit == (cpf.charAt(9) - '0') && secondDigit == (cpf.charAt(10) - '0')) {
	        return true;
	    } else {
	        return false;
	    }
	}
	
	private boolean validateBirth(String birth) throws ParseException{
		try {
			 
			   
			String formatedBirth = dateFormat(birth);
            LocalDate date = LocalDate.parse(formatedBirth);
            LocalDate earliestValidDate = LocalDate.of(1900, 1, 1);
            LocalDate latestValidDate = LocalDate.now();
            return !date.isBefore(earliestValidDate) && !date.isAfter(latestValidDate);
        } catch (DateTimeParseException e) {
            return false;
        }
    }
	
	private boolean validatePhoneNumber(String phoneNumber) {
		boolean size = false;
		boolean ddd = false;
		String noMask = phoneNumber.replaceAll("[()\\-]", "");
		noMask.replace(" ", "");
		if(noMask.length() == 11) {	
			size = true;
			System.out.println("TAMANHO");
		}
		for(int i = 0; i < DDDS.length; i ++) {
			if(noMask.substring(0,2) == DDDS[i]) {
				ddd = true;
				System.out.println("DDD ENCONTRADO");
				break;
				}
			}
		if(size && ddd) {
			return true;
		}else {
			return false;
		}
    }
	
	public List<Boolean> verifyLogin(String login, String password) throws NoSuchAlgorithmException, ClassNotFoundException, IOException{
		List<Boolean> result = new ArrayList<Boolean>();
		String hashPassword = encrypt.toHash(password);
		boolean isOng = false;
		boolean confirmation = false;
		if(dao.validateLoginOng(hashPassword, login)) {
			isOng = true;
			confirmation = true;
		}else if(dao.validateLoginAdopter(hashPassword, login)) {
			isOng = false;
			confirmation = true;
		}
		result.add(isOng);
		result.add(confirmation);
		return result;
	}
	
	public static String dateFormat(String birth) throws ParseException {
		    String newBirth = birth.replaceAll("/", "-");
		    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		return  sdf2.format(sdf.parse(newBirth));
	}

	public static int randomNumber(int min, int max) {
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		return randomNum;
	}
	
}
