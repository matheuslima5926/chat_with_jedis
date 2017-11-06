package DAO;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

public class JedisDAO {
	public static Jedis connectJedis(String host){
		try{
			Jedis jedis = new Jedis(host);
			return jedis;
	
		}catch(JedisException error){
			throw error;
		}
		
	}
	

}
