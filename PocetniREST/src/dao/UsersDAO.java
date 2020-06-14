package dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import bean.User;

public class UsersDAO {
	private Map<String, User> users = new HashMap<>();
	
	
	public UsersDAO(){
		
		
		// popunjavam test podatke
		User ana = new User("x","ana","perisic","12-09-1998","zenski","nikad bolje");
		User ivana = new User("y","ivana","perisic","09-05-2001","zenski","dobro sam");
		
		this.users.put(ana.getBrojZdravstvenogOsiguranja(), ana);
		this.users.put(ivana.getBrojZdravstvenogOsiguranja(), ivana);
	}
	
	
	public Collection<User> findAll(){
		return users.values();
	}
	
	
}
