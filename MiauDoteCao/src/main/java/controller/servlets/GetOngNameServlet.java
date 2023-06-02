package controller.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Dao;
/**
 * Servlet implementation class GetOngNameServlet
 */
public class GetOngNameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Dao dao = new Dao();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetOngNameServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] ongs = dao.getOngName();
	}

}
