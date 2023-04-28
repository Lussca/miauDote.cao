package adoteCaoProjetoController;

import adoteCaoProjetoController.JwtHandler;
import adoteCaoProjetoController.RequestResponseHandler;

/**
 * Servlet implementation class PetRegisterServlet
 */

public class PetRegisterServlet extends HttpServlet{
    JwtHandler jwtH = new JwtHandler();
    RequestResponseHandler rrh = new RequestResponseHandler();
    /**
     * @see HttpServlet#HttpServlet()
     */

     public PetRegisterServlet(){
        super();
     }

     protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException{

     }
     protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException{
        rrh.configureCors(response);
        String jwtRequest = request.getParameter("jwt");
        String login = request.getParameter("login");
        boolean isOng = request.getParameter("isOng");
       
   
        if(jwtH.validateJWT(jwtRequest, login, isOng)){
           String body = getBody(request);
           JSONObject jObj = new JSONObject(body);

           Iterator<String> it = jObj.keys();
           while(it.hasNext()){
            String key = it.next();
            Object jwt = jObj.get(key);
           }
        }else{
           rrh.sendErrorResponse(response, 401, Validations.SESSION_EXPIRED);
        }
        
     }

     private static String getBody(HttpServletRequest request)  {

        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
    
        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            // throw ex;
            return "";
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
    
                }
            }
        }
    
        body = stringBuilder.toString();
        return body;
    }
     
}