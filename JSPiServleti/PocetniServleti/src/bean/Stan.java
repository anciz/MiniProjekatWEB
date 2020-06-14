package bean;

public class Stan {
	private String brojStana;
	private String adresaStana;
	private String gradStana;
	private String brojSobaStana;
	private String brojKvadrataStana;
	private String centralnoGrejanjeStana;
	private String cenaStana;
	private String dostupan;
	
	
	
	public Stan(String brojStana, String adresaStana, String gradStana, String brojSobaStana, String brojKvadrataStana,
			String centralnoGrejanjeStana, String cenaStana, String dostupan) {
		super();
		this.brojStana = brojStana;
		this.adresaStana = adresaStana;
		this.gradStana = gradStana;
		this.brojSobaStana = brojSobaStana;
		this.brojKvadrataStana = brojKvadrataStana;
		this.centralnoGrejanjeStana = centralnoGrejanjeStana;
		this.cenaStana = cenaStana;
		this.dostupan = dostupan;
	}

	public String getDostupan() {
		return dostupan;
	}
	
	public void setDostupan(String dostupanStan) {
		this.dostupan = dostupanStan;
	}
	
	public String getBrojStana() {
		return brojStana;
	}
	public void setBrojStana(String brojStana) {
		this.brojStana = brojStana;
	}
	public String getAdresaStana() {
		return adresaStana;
	}
	public void setAdresaStana(String adresaStana) {
		this.adresaStana = adresaStana;
	}
	public String getGradStana() {
		return gradStana;
	}
	public void setGradStana(String gradStana) {
		this.gradStana = gradStana;
	}
	public String getBrojSobaStana() {
		return brojSobaStana;
	}
	public void setBrojSobaStana(String brojSobaStana) {
		this.brojSobaStana = brojSobaStana;
	}
	public String getBrojKvadrataStana() {
		return brojKvadrataStana;
	}
	public void setBrojKvadrataStana(String brojKvadrataStana) {
		this.brojKvadrataStana = brojKvadrataStana;
	}
	public String getCentralnoGrejanjeStana() {
		return centralnoGrejanjeStana;
	}
	public void setCentralnoGrejanjeStana(String centralnoGrejanjeStana) {
		this.centralnoGrejanjeStana = centralnoGrejanjeStana;
	}
	public String getCenaStana() {
		return cenaStana;
	}
	public void setCenaStana(String cenaStana) {
		this.cenaStana = cenaStana;
	}
	
}
