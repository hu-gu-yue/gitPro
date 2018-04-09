package poseidon.zhdj.db.video.dao;


import org.springframework.stereotype.Repository;
import poseidon.zhdj.db.video.model.UserModel;

@Repository
public interface UserModelMapper {
    int deleteByPrimaryKey(String uid);

    int insert(UserModel record);

    int insertSelective(UserModel record);

    UserModel selectByPrimaryKey(String uid);

    int updateByPrimaryKeySelective(UserModel record);

    int updateByPrimaryKey(UserModel record);


}