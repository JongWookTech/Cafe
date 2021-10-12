package dao;

import java.util.HashMap;

import dto.ProductDTO;
import dto.UserDTO;

public class Session {
	private static HashMap<String, UserDTO> datas = new HashMap<>();
	
	public static void put(String key, UserDTO udto) {
		datas.put("session", udto);
	}
	
	public static UserDTO get(String key) {
		return datas.get("session");
	}
	
}

	


	