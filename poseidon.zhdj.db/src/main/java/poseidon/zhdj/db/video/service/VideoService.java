package poseidon.zhdj.db.video.service;

import poseidon.zhdj.db.video.model.VideoModel;

import java.util.Map;

public interface VideoService {

	int insert(VideoModel record);

	VideoModel selectByPrimaryKey(Long vid);

	Map<String, Object> getHzbVideoContent(Long vid, String uid);

	Integer getZyType(String zyType);

	Map<String, Object> getVideoCommonData(Long vid, String uid);
}
