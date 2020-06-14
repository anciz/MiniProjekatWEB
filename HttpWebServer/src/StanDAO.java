

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class StanDAO {

	private Map<String, Stan> stanovi = new HashMap<>();
	
	public StanDAO() {
		
		Stan stan1 = new Stan("012-55","Jovina 15","Novi Sad", "2","31","DA","100","nedostupan");
		stanovi.put(stan1.getBrojStana(),stan1);
		
		Stan stan2 = new Stan("013-45","Jovina 11","Novi Sad", "2","41","DA","90","dostupan");
		stanovi.put(stan2.getBrojStana(),stan2);
	}
	
	public Stan addStan(Stan stan) {
		if(!stanovi.containsKey(stan.getBrojStana())) {
			stanovi.put(stan.getBrojStana(), stan);
			return stan;
		}
		
		return null;
	}
	
	public Collection<Stan> findAll(){
		return stanovi.values();
	}
}
