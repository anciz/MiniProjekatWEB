import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class Server {
	private int port;
	private ServerSocket serverSocket;

	private List<Stan> stanovi;

	public Server(int port) throws IOException {
		this.port = port;
		this.serverSocket = new ServerSocket(this.port);
		StanDAO stanoviDAO = new StanDAO();
		this.stanovi=new ArrayList<Stan>();
		for (Stan stan : stanoviDAO.findAll()) {
			this.stanovi.add(stan);
		}
	}

	public void run() {
		System.out.println("Web server running on port: " + port);
		System.out.println("Document root is: " + new File("/static").getAbsolutePath() + "\n");

		Socket socket;

		while (true) {
			try {
				// prihvataj zahteve
				socket = serverSocket.accept();
				InetAddress addr = socket.getInetAddress();

				// dobavi resurs zahteva
				String resource = this.getResource(socket.getInputStream());

				// fail-safe
				if (resource == null)
					continue;

				if (resource.equals(""))
					resource = "static/index.html";
				
				if(resource.startsWith("pretrazi")) {
					
					//pretrazi?inputPretrage=a
					String [] delovi = resource.split("\\?");
					String bitanDeo = delovi[1]; // inputPretrage=a
					
					
					String [] deloviPretrage = bitanDeo.split("=");
					String grad = deloviPretrage[1];
					
					// pomocna lista u kojoj cuvam one koji zadovoljavaju pretragu, odnosno grad
					List<Stan> zadovoljavajuciStanoviPretrage = new ArrayList<Stan>();
					
					for (Stan stan : stanovi) {
						if(stan.getGradStana().equals(grad))
							zadovoljavajuciStanoviPretrage.add(stan);
					}
					
					PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);
					
					// U slucaju greske ispisujemo 404 Stan not found a u suprotnom happy path 200 OK
					if(zadovoljavajuciStanoviPretrage.size() <= 0) {
						String personalizovanaPorukaGreske = "Stan koji je u " + grad + " ne postoji u nasoj bazi podataka";
						String errorCode = "HTTP/1.0 404 Stan not found\r\n"
								+ "Content-type: text/html; charset=UTF-8\r\n\r\n<b> Sorry: " + personalizovanaPorukaGreske + "</b>";

						out.println(errorCode);
						out.close();
						
					}else {
						out.print("HTTP/1.1 200 OK\r\nContent-type: text/html;charset=utf-8\r\n\r\n");
						out.println("<html><head>");
						out.println("<style> table,th,td{ border: 2px solid grey;} </style>");
						out.println("<meta http-equiv=\"Content-type\" value=\"text/html;charset=utf-8\"/></head><body>");
						
						out.println("<h1 style=\"color: green\">HTTP.Pregled stanova</h1>");
								
						String tabela = "<table>";
						// resavala sam heder tabele
						tabela +="<tr>"
								+ "<th><b>Broj stana</b></th>"
								+ "<th><b>Adresa</b></th>"
								+ "<th><b>Grad</b></th>"
								+ "<th><b>Broj soba</b></th>"
								+ "<th><b>Broj kvadrata</b></th>"
								+ "<th><b>Centralno grejanje</b></th>"
								+"<th><b>Cena [EUR]</b></th>"
								+"<th></th>"
								+ "</tr>";
						
						
						for (Stan stanovi : zadovoljavajuciStanoviPretrage) {
							tabela += "<tr>";	// out.append("<tr>"); ili out.println("<tr>");
							tabela += "<td>" + stanovi.getBrojStana() + "</td>";
							tabela += "<td>" + stanovi.getAdresaStana() + "</td>";
							tabela += "<td>" + stanovi.getGradStana() + "</td>";
							tabela += "<td>" + stanovi.getBrojSobaStana() + "</td>";
							tabela += "<td>" + stanovi.getBrojKvadrataStana() + "</td>";
							tabela += "<td>" + stanovi.getCentralnoGrejanjeStana() + "</td>";
							tabela+="<td>" +"<a href=\"http://localhost:80/dostupan?rezultat="+stanovi.getBrojStana()+"\">"+"Dostupan"+"</a></td>";
							tabela += "</tr>";
						}
						tabela+="</table>";
						out.println(tabela);
						out.close();
					}
					
					
					
					
				}else if(resource.startsWith("dostupan")){
					
					String [] nizBezUpit=resource.split("\\?");
					String pomocnaProm=nizBezUpit[1];
					String [] noviNiz=pomocnaProm.split("=");
					String brojStana=noviNiz[1];
					
					PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);
					out.print("HTTP/1.1 200 OK\r\nContent-type: text/html;charset=utf-8\r\n\r\n");
					out.println("<html><head>");
					out.println("<style> table,th,td{ border: 2px solid grey;} </style>"); //siva boja tabele
					out.println("<meta http-equiv=\"Content-type\" value=\"text/html;charset=utf-8\"/></head><body>");
					
					out.println("<h1 style=\"color: green\">HTTP.Pregled stanova</h1>"); //naslov
							
					String tabela = "<table>";
					// resavala sam heder tabele
					tabela +="<tr>"
							+ "<th><b>Broj stana</b></th>"
							+ "<th><b>Adresa</b></th>"
							+ "<th><b>Grad</b></th>"
							+ "<th><b>Broj soba</b></th>"
							+ "<th><b>Broj kvadrata</b></th>"
							+ "<th><b>Centralno grejanje</b></th>"
							+"<th><b>Cena [EUR]</b></th>"
							+"<th>  </th>"
							+ "</tr>";
					
						for (Stan stan : stanovi) {
							
							if(stan.getBrojStana().equals(brojStana)) {
								stan.setDostupan("dostupan");
							}
							
							
							if(stan.getDostupan().equals("dostupan")) {
								tabela += "<tr style= \"background-color: grey; \" >";			
							}else {
								tabela += "<tr>";
							}
							
							tabela += "<td>" + stan.getBrojStana() + "</td>";
							tabela += "<td>" + stan.getAdresaStana() + "</td>";
							tabela += "<td>" + stan.getGradStana() + "</td>";
							tabela += "<td>" + stan.getBrojSobaStana()+ "</td>";
							tabela += "<td>" + stan.getBrojKvadrataStana()+ "</td>";
							tabela+= "<td>" + stan.getCentralnoGrejanjeStana()+ "</td>";
							tabela += "<td>" + stan.getCenaStana()+ "</td>";
							
							
							tabela+="<td>";
							if(stan.getDostupan().equals("dostupan")) {
								tabela += " ";
							}else {
								tabela +="<a href=\"http://localhost:80/dostupan?rezultat="+stan.getBrojStana()+"\">"+"Dostupan"+"</a>";
							}
							
							
							tabela+="</td>";
							
							tabela += "</tr>";
						
					}
					
					tabela+="</table>";
					out.println(tabela);
					
					 out.println("<h2>Pretraga stana po gradu:</h2>");
					 String forma="<form action=\"http://localhost:80/pretrazi\" method=\"get\" >";
					 forma+="<label>Unesite grad:</label>" + 
					 		"  <input type=\"text\" name=\"inputPretrage\">"
							 +"<br><style>p {" + 
							 "  align: right;" + 
							 "}</style><input type=\"submit\" align=\"center\">";
					 forma+="</form>";
					 out.println(forma);
					 out.println("");
					
					out.println("</body></html>");
					
					
				
					
					out.close();
						
					
					
					
				}//ovo je kada klikne na http dugme za slanje
				else if (resource.startsWith("posalji?brojStana=")) {
				
					String[] splitUpitnik = resource.split("\\?");
					String bitanDeo = splitUpitnik[1];
					
					// bez &
					String[] bezAmp = bitanDeo.split("&");
					
					String brojStanaDeo = bezAmp[0];
					String [] broj = brojStanaDeo.split("=");
					String brojStana = broj[1]; //ovo mi je za id stana
					
					
					for (Stan stan : stanovi) {
						if(stan.getBrojStana().equals(brojStana)) {
							// neki ispis da nije jedinistveno
							PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);
							String errorCode = "HTTP/1.0 404 Vec postoji stan \r\n"
									+ "Content-type: text/html; charset=UTF-8\r\n\r\n<b> Stan je vec kreiran!</b>";

							out.println(errorCode);
							
							socket.close();
							socket = null;
							return;
						}
					}
					
					String adresaDeo = bezAmp[1];
					String [] adresa = adresaDeo.split("=");
					String adresaStana = adresa[1]; //ovo mi je za adresu stana
					
					String gradDeo=bezAmp[2];
					String [] grad=gradDeo.split("=");
					String gradStana =grad[1]; //ovo mi je za grad gde je stan smesten
					
					String brojSobaDeo=bezAmp[3];
					String[] brojSoba=brojSobaDeo.split("=");
					String brojSobaStan=brojSoba[1]; //ovo mi je za broj sobe stana
					
					String brojKvadrataDeo=bezAmp[4];
					String[] brojKvadrata=brojKvadrataDeo.split("=");
					String brojKvadrataStan=brojKvadrata[1]; //ovo mi je za broj kvadrata stana
					
					String centralnoGrejanjeDeo=bezAmp[5];
					String[] centralnoGrejanje=centralnoGrejanjeDeo.split("=");
					String centralnoGrejanjeStan=centralnoGrejanje[1]; //ovo mi je za centralno grejanje stana
					
					String cenaDeo=bezAmp[6];
					String[] cena=cenaDeo.split("=");
					String cenaStan=cena[1]; //ovo mi je za cenu stana
					
					
				
					
					
					// Dodavanje novog stana
					Stan noviStan = new Stan(brojStana, adresaStana,gradStana,brojSobaStan,brojKvadrataStan,centralnoGrejanjeStan,cenaStan,"nedostupan");
					stanovi.add(noviStan);
					
					
					PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);
					out.print("HTTP/1.1 200 OK\r\nContent-type: text/html;charset=utf-8\r\n\r\n");
					out.println("<html><head>");
					out.println("<style> table,th,td{ border: 2px solid grey;} </style>"); //siva boja tabele
					out.println("<meta http-equiv=\"Content-type\" value=\"text/html;charset=utf-8\"/></head><body>");
					
					out.println("<h1 style=\"color: green\">HTTP.Pregled stanova</h1>"); //naslov
							
					String tabela = "<table>";
					// resavala sam heder tabele
					tabela +="<tr>"
							+ "<th><b>Broj stana</b></th>"
							+ "<th><b>Adresa</b></th>"
							+ "<th><b>Grad</b></th>"
							+ "<th><b>Broj soba</b></th>"
							+ "<th><b>Broj kvadrata</b></th>"
							+ "<th><b>Centralno grejanje</b></th>"
							+"<th><b>Cena [EUR]</b></th>"
							+"<th></th>"
							+ "</tr>";
					
					
					for (Stan stan : stanovi) {
						tabela += "<tr>";	
						tabela += "<td>" + stan.getBrojStana() + "</td>";
						tabela += "<td>" + stan.getAdresaStana() + "</td>";
						tabela += "<td>" + stan.getGradStana() + "</td>";
						tabela += "<td>" + stan.getBrojSobaStana()+ "</td>";
						tabela += "<td>" + stan.getBrojKvadrataStana()+ "</td>";
						tabela+= "<td>" + stan.getCentralnoGrejanjeStana()+ "</td>";
						tabela += "<td>" + stan.getCenaStana()+ "</td>";
						
						
						tabela+="<td>" +"<a href=\"http://localhost:80/dostupan?rezultat="+stan.getBrojStana()+"\">"+"Dostupan"+"</a></td>";
						tabela += "</tr>";
					}
					tabela+="</table>";
					out.println(tabela);
					
					 out.println("<h2>Pretraga stana po gradu:</h2>");
					 String forma="<form action=\"http://localhost:80/pretrazi\" method=\"get\" >";
					 forma+="<label>Unesite grad:</label>" + 
					 		"  <input type=\"text\" name=\"inputPretrage\">"
							 +"<br><style>p {" + 
							 "  align: right;" + 
							 "}</style><input type=\"submit\" align=\"center\">";
					 forma+="</form>";
					 out.println(forma);
					 out.println("");
					
					out.println("</body></html>");
					
					
				
					
					out.close();
				}else {
			
				

					System.out.println("Request from " + addr.getHostName() + ": " + resource);

					sendResponse(resource, socket.getOutputStream());
				}
				
				
				// MORAM OVDE VODITI RACUNA JER SAM PAR PUTA SRUSILA PROGRAM KADA SAM OVO ZAB !!
				socket.close();
				socket = null;
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Metoda koja prima zahtev i vraca ciljanu stranicu.
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	private String getResource(InputStream is) throws IOException {
		BufferedReader dis = new BufferedReader(new InputStreamReader(is));
		String s = dis.readLine();

		// fail-safe
		if (s == null)
			return null;

		String[] tokens = s.split(" ");

		// prva linija HTTP zahteva: METOD /resurs HTTP/verzija
		// obradjujemo samo GET metodu
		String method = tokens[0];
		if (!method.equals("GET"))
			return null;

		// String resursa
		String resource = tokens[1];

		// izbacimo znak '/' sa pocetka
		resource = resource.substring(1);

		// ignorisemo ostatak zaglavlja
		String s1;
		while (!(s1 = dis.readLine()).equals(""))
			System.out.println(s1);

		return resource;
	}

	/**
	 * Metoda koja salje odgovor klijentu.
	 * 
	 * 
	 * @param resource
	 * @param os
	 * @throws IOException
	 */
	private void sendResponse(String resource, OutputStream os) throws IOException {
		PrintStream ps = new PrintStream(os);

		// zamenimo web separator sistemskim separatorom
		resource = resource.replace('/', File.separatorChar);
		File file = new File(resource);

		if (!file.exists()) {
			// ako datoteka ne postoji, vratimo kod za gresku
			String errorCode = "HTTP/1.0 404 File not found\r\n"
					+ "Content-type: text/html; charset=UTF-8\r\n\r\n<b>404 Not found:" + file.getName() + "</b>";

			ps.print(errorCode);

//            ps.flush();
			System.out.println("Could not find resource: " + file);
			return;
		}

		// ispisemo zaglavlje HTTP odgovora
		ps.print("HTTP/1.0 200 OK\r\n\r\n");

		// a, zatim datoteku
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[8192];
		int len;

		while ((len = fis.read(data)) != -1)
			ps.write(data, 0, len);

		ps.flush();
		fis.close();
	}

}
