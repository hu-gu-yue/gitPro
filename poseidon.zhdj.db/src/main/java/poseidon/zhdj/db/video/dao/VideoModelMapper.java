package poseidon.zhdj.db.video.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import poseidon.zhdj.db.video.model.VideoModel;


@Repository
public interface VideoModelMapper {
    int deleteByPrimaryKey(Long vid);

    int insert(VideoModel record);

    int insertSelective(VideoModel record);

    VideoModel selectByPrimaryKey(Long vid);

    VideoModel selectByActZyId(@Param("act") Integer act, @Param("zyId") Long zyId);

    int deleteByActZyId(@Param("act") Integer act, @Param("zyId") Long zyId);

    int updateByPrimaryKeySelective(VideoModel record);
	
    int updateByPrimaryKey(VideoModel record);

    VideoModel getVideoInfo(@Param("uid") String uid, @Param("vid") Long vid);

}