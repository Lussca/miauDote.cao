package controller.servlets;

import java.io.IOException;

import controller.handler.email.CreateEmail;
import controller.handler.email.CreateMessage;
import controller.handler.email.EmailSender;
import controller.handler.email.SendMessage;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.api.services.gmail.model.Message;

/**
 * Servlet implementation class EmailTestServlet
 */
public class EmailTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmailTestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		try {
			MimeMessage mimeMessage = CreateEmail.createEmail(email, "miaudote.cao@gmail.com", "Codigo de verificacao", "Aqui esta seu codigo de verificacao: 1234");
			Message message = CreateMessage.createMessageWithEmail(mimeMessage);
			SendMessage.sendEmail("miauDote.Cao@gmail.com", email);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
