package DAO;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import entity.User;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

public class UserDAO {
	private static Jedis dao = JedisDAO.connectJedis("localhost");
	public static boolean saveUser(User user){
		try{
			if(user.getName().isEmpty() || user.getName().isEmpty()){
				return false;
			}
			user.setName(user.getName().trim().toLowerCase().toString());
			user.setNickname(user.getNickname().trim().toLowerCase().toString());
			if(user.getRegisterDate() == null){
				user.setRegisterDate(LocalDate.now());
			}
			 dao.hset("user", "nickname", user.getNickname().toString());
			 dao.hset("user", "name", user.getName().toString());
			 dao.hset("user", "register_date", user.getRegisterDate().toString());
			 return true;
			
		}catch(JedisException exception){
			throw exception;
		}
	}
	public static User findUserByNick(User user){
		
		return null;
	}
	
	public static String getAllUsers(){
		return dao.get("user");
	}
}
