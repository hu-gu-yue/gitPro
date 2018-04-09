package poseidon.zhdj.db.video.service;

import poseidon.zhdj.db.video.model.UserModel;

public interface UserService {
	UserModel selectByPrimaryKey(String uid);

	int updateByPrimaryKeySelective(UserModel record);

    int saveUser(UserModel userModel);
}
