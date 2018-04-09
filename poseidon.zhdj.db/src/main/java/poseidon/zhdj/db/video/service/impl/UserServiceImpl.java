package poseidon.zhdj.db.video.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poseidon.zhdj.db.video.dao.UserModelMapper;
import poseidon.zhdj.db.video.dao.VideoModelMapper;
import poseidon.zhdj.db.video.model.UserModel;
import poseidon.zhdj.db.video.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	UserModelMapper userModelMapper;
	@Autowired
	VideoModelMapper videoModelMapper;

	/**
	 * 用户基本资料
	 * hulufeng 20170509
	 */
	@Override
	public UserModel selectByPrimaryKey(String uid) {
		return userModelMapper.selectByPrimaryKey(uid);
	}

	/**
	 * 更新用户基本资料
	 * hulufeng 20170509
	 */
	@Override
	public int updateByPrimaryKeySelective(UserModel record){
		return userModelMapper.updateByPrimaryKeySelective(record);
	}


	@Override
	public int saveUser(UserModel userModel) {
		UserModel um = userModelMapper.selectByPrimaryKey(userModel.getUid());
		if (um == null){
			return userModelMapper.insertSelective(userModel);
		}else {
			return userModelMapper.updateByPrimaryKeySelective(userModel);
		}
	}

}
