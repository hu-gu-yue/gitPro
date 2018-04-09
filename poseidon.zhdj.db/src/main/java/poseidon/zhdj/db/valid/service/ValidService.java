package poseidon.zhdj.db.valid.service;


public interface ValidService {
	Boolean isExists(String userName);
	public  String put(String userName );
	public  String putForEmail(String userName );
	public  String get(String userName );
	public  void delete(String userName );
}
