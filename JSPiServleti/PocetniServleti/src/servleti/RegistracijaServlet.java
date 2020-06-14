package servleti;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UsersDAO;

/**
 * Servlet implementation class RegistracijaServlet
 */
@WebServlet("/RegistracijaServlet")
public class RegistracijaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistracijaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void init() throws ServletException {
    	super.init();
    	ServletContext context = getServletContext();
    	context.setAttribute("users", new UsersDAO());
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter out = response.getWriter();
		
		String broj = request.getParameter("broj");
		String ime = request.getParameter("ime");
		String prezime = request.getParameter("prezime");
		String datumRodjenja=request.getParameter("datumRodjenja");
		String zdravstveniStatus=request.getParameter("zdravstveniStatus");
		
		
		
		// redirekcija na prikaz korisnika
		RequestDispatcher disp = request.getRequestDispatcher("/users.jsp");
		disp.forward(request, response);
		//out.append("broj je: "+ broj + " ime je: " + ime+" prezime je: "+ prezime+ " datum rodjenja je :"+datumRodjenja+" zdravstveni karton je :"+zdravstveniStatus);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
