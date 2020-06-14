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

import bean.Stan;
import dao.StanDAO;

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
    	context.setAttribute("stanovi", new StanDAO());
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter out = response.getWriter();
		
		ServletContext context = getServletContext();
		StanDAO stanovi = (StanDAO) context.getAttribute("stanovi");
		
		
		
		String brojStana = request.getParameter("brojStana");
		String adresa = request.getParameter("adresa");
		String grad = request.getParameter("grad");
		String brojSoba = request.getParameter("brojSoba");
		String brojKvadrata = request.getParameter("brojKvadrata");
		String grejanje = request.getParameter("grejanje");
		String cena = request.getParameter("cena");
		
		Stan stanZaDodavanje = new Stan(brojStana,adresa,grad,brojSoba,brojKvadrata,grejanje,cena,"dostupan");
		stanovi.addStan(stanZaDodavanje);
		
		
		RequestDispatcher disp = request.getRequestDispatcher("/pregledStanova.jsp");
		disp.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
