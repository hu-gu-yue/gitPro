package poseidon.zhdj.db.video.service.impl;

import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poseidon.lib.core.tool.date.DateTool;
import poseidon.lib.core.tool.string.StringTool;
import poseidon.zhdj.db.global.GlobalConstants;
import poseidon.zhdj.db.video.dao.UserModelMapper;
import poseidon.zhdj.db.video.dao.VideoModelMapper;
import poseidon.zhdj.db.video.model.UserModel;
import poseidon.zhdj.db.video.model.VideoModel;
import poseidon.zhdj.db.video.service.VideoService;

import java.util.HashMap;
import java.util.Map;

@Service
public class VideoServiceImpl implements VideoService {
	@Autowired
	VideoModelMapper videoModelMapper;
	@Autowired
	UserModelMapper userModelMapper;

	private final static Logger log = LoggerFactory.getLogger(VideoServiceImpl.class);
	@Override
	public int insert(VideoModel record) {
		VideoModel vm = videoModelMapper.selectByActZyId(record.getAct(), record.getZyId());
		if (vm == null){
			record.setCTime(DateTool.now());
			return videoModelMapper.insertSelective(record);
		}else {
			record.setVid(vm.getVid());
			return videoModelMapper.updateByPrimaryKeySelective(record);
		}
	}

	/**
	 * 资源的基本信息
	 */
	@Override
	public VideoModel selectByPrimaryKey(Long vid) {
		return videoModelMapper.selectByPrimaryKey(vid);
	}


	/**
	 * 和主播直播间详情
	 */
	@Override
	public Map<String, Object> getHzbVideoContent(Long vid, String uid) {
		//公共的详情方法
		Map<String, Object> videoMap = getVideoCommonData(vid,uid);

		VideoModel videoModel =  (VideoModel) videoMap.get("video");
		UserModel userModel;
		userModel = (UserModel) videoMap.get("user");

		Integer gift1 = null ,gift2 = null ,gift3 = null ,gift4 = null ,gift5 = null ,gift6 = null;
		if(uid.equalsIgnoreCase(videoModel.getUid())){//如果是主播

		} else {
			gift1 = userModel.getGift1();
			gift2 = userModel.getGift2();
			gift3 = userModel.getGift3();
			gift4 = userModel.getGift4();
			gift5 = userModel.getGift5();
			gift6 = userModel.getGift6();
		}
		videoMap.put("gift1",gift1);
		videoMap.put("gift2",gift2);
		videoMap.put("gift3",gift3);
		videoMap.put("gift4",gift4);
		videoMap.put("gift5",gift5);
		videoMap.put("gift6",gift6);

		return videoMap;
	}


	@Override
	public Integer getZyType(String zyType) {
		int type = 0;
		for(int i = 0;i < GlobalConstants.VIDEO_TYPES.length; i++){
			if(zyType.equalsIgnoreCase(GlobalConstants.VIDEO_TYPES[i])){
				type = 1;//视频格式
			}
		}
		for(int i = 0;i < GlobalConstants.DOC_TYPES.length; i++){
			if(zyType.equalsIgnoreCase(GlobalConstants.DOC_TYPES[i])){
				type = 2;//文档格式
			}
		}
		for(int i = 0;i < GlobalConstants.IMG_TYPES.length; i++){
			if(zyType.equalsIgnoreCase(GlobalConstants.IMG_TYPES[i])){
				type = 3;//图像格式
			}
		}
		return type;
	}

	/**
	 * 手机端、PC端直播间详情 公共数据方法
	 */
	@Override
	public Map<String, Object> getVideoCommonData(Long vid, String uid){
		Map<String, Object> videoMap = new HashMap<>();
		videoMap.put("qcjUid",uid);
		//当前资源信息
		VideoModel videoModel = videoModelMapper.getVideoInfo(uid,vid);
		videoMap.put("video",videoModel);

		videoModel.setViews(videoModel.getViews() + 1);
		videoModelMapper.updateByPrimaryKeySelective(videoModel);

		//当前资源的用户信息
		UserModel user = userModelMapper.selectByPrimaryKey(videoModel.getUid());
		videoMap.put("videoUser",user);

		//当前登录的用户信息
		UserModel loginUser = userModelMapper.selectByPrimaryKey(uid);
		videoMap.put("user",loginUser);
		if(StringTool.isEmpty(loginUser.getViewVid())){
			loginUser.setViewVid(""+vid);
		} else {
			String[] vids = loginUser.getViewVid().split(",");
			String vidT = "" + vid;
			boolean flag = true;
			for(int i = 0 ; i < vids.length; i++){
				if(vidT.equals(vids[i])){
					flag = false;
				}
			}
			if(flag){
				loginUser.setViewVid(loginUser.getViewVid()+","+vid);
			}
		}
		userModelMapper.updateByPrimaryKeySelective(loginUser);//更新登录用户浏览过的

		//当前资源的类型
		Integer type = getZyType(videoModel.getZyType());
		videoMap.put("type",type);//资源格式：1：视频，2：文档，3：图像

		//某视频直播间互动交流
		PageHelper.startPage(0, 15, true);
		videoMap.put("totalVideoComment", videoMap.get("total"));

		return videoMap;
	}


}
