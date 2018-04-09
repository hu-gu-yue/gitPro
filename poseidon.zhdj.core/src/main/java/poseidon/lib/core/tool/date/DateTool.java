package poseidon.lib.core.tool.date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateTool {

	private final static Logger log = LoggerFactory.getLogger(DateTool.class);

	public static String getDateHourMinute(Date date)
	{
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy年MM月dd HH:mm");
		return dateformat.format(date);
	}
	
	public static String getYearMonDay(Date date)
	{
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		return dateformat.format(date);
	}
	
	
	public static String getYear(){
		String year = "2015";
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy");
		year = dateformat.format(new Date());
		return year;
	}
	
	public static String getMonth(){
		String month = "01";
		SimpleDateFormat dateformat = new SimpleDateFormat("MM");
		month = dateformat.format(new Date());
		return month;
	}
	
	public static int getCurrentDay() {
		int day = 20141016;
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
		day = Integer.parseInt(dateformat.format(new Date()));
		return day;
	}
	
	public static Date now(){
		return new Date();
	}
	
	public static String longToChineseString(long longTime) {
		try {
			SimpleDateFormat ft = new SimpleDateFormat("yyyy年MM月dd日 HH点mm分ss秒");
			return ft.format(longTime);
		} catch (Exception e) {
			log.info("DateHelper error", e);
			return "2014年12月17日 10点57分0秒";
		}

	}

	public static String longToString(long longTime) {
		try {
			SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd HH:mm:ss E");
			return ft.format(longTime);
		} catch (Exception e) {
			log.info("DateHelper error", e);
			return "20080808";
		}
	}

	public static String longToDateFullString(long longTime) {
		try {
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return ft.format(longTime);
		} catch (Exception e) {
			log.info("DateHelper error", e);
			return "20080808";
		}
	}
	
	public static String DateToString(Date date) {
		try {
			SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
			return ft.format(date);
		} catch (Exception e) {
			log.info("DateHelper error", e);
			return "20080808";
		}
	}
	
	public static String DateToPhotoDateString(Date date)
	{
		try {
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
			return ft.format(date);
		} catch (Exception e) {
			log.info("DateHelper error", e);
			return "2008-08-08";
		}
	}

	public static String DateToFileName(Date date){
		try {
			SimpleDateFormat ft = new SimpleDateFormat("yyMMddHHmmssS");
			return ft.format(date);
		} catch (Exception e) {
			log.info("DateHelper error", e);
			return "20080808";
		}
	}
	
	public static Date StrToDate(String str) {
	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   Date date = null;
	   try {
		   date = format.parse(str);
	   } catch (ParseException e) {
		   log.error("字符串转换为日期失败", e );
	   }
	   return date;
	}
	
	
	public static String getLastYearMonth(){
		 Calendar c = Calendar.getInstance();
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		 c.add(Calendar.MONTH, -1);
		 return sdf.format(c.getTime());
	}
	
	public static String getNowYearMonth(){
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		 return sdf.format(new Date());
	}

	public static Date subDay(Date dt, int num){
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		//rightNow.add(Calendar.YEAR,-1);//日期减1年
		//rightNow.add(Calendar.MONTH,3);//日期加3个月
		rightNow.add(Calendar.DAY_OF_YEAR,num);//日期加10天
		return rightNow.getTime();
	}

	public static Date subMonth(Date dt, int num){
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		//rightNow.add(Calendar.YEAR,-1);//日期加年
		rightNow.add(Calendar.MONTH, num);//日期加月
		//rightNow.add(Calendar.DAY_OF_YEAR,num);//日期加天
		return rightNow.getTime();
	}
	
	public static String dateToStr(Date date){
		if(date == null){
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}


	public static long getNowTimeInMillis() {
		return new Date().getTime();
	}


	public static long getTimeInMillis(Date date, String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(sdf.format(date)).getTime();
	}


	public static Date addDay(Date date, int day) {
		if(date == null){
			return null;
		}
		
		Calendar c = GregorianCalendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_YEAR, day);
		return c.getTime();
	}
	
	public static String dateToStr(Date date, String format) {
		try {
			SimpleDateFormat ft = new SimpleDateFormat(format);
			return ft.format(date);
		} catch (Exception e) {
			log.info("DateHelper error", e);
			return "2008-08-08";
		}
	}
	
	public static Date strToDate(String str, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			log.error("字符串转换为日期失败", e );
		}
		return date;
	}

	/**
	 * 显示时间，如果与当前时间差别小于一天，则自动用**秒(分，小时)前，如果大于一天则用format规定的格式显示
	 *
	 *  20161230
	 * @param ctime  时间
	 * @param format 格式 格式描述:例如:yyyy-MM-dd yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String showTime(Date ctime, String format) {
		String r = "";
		if(ctime==null)
			return r;
		if(format==null)
			format="yyyy-MM-dd HH:mm:ss";

		long nowtimelong = System.currentTimeMillis();

		long ctimelong = ctime.getTime();
		long result = Math.abs(nowtimelong - ctimelong);

		if(result < 60000){// 一分钟内
			long seconds = result / 1000;
			if(seconds == 0){
				r = "刚刚";
			} else {
				r = seconds + "秒前";
			}
		}else if (result >= 60000 && result < 3600000){// 一小时内
			long seconds = result / 60000;
			r = seconds + "分钟前";
		}else if (result >= 3600000 && result < 86400000){// 一天内
			long seconds = result / 3600000;
			r = seconds + "小时前";
		}else if (result >= 86400000 && result < 172800000){// 两天内
			r = "昨天";
		}else if (result >= 172800000 && result < 259200000){// 三天内
			r = "前天";
		}
//		else if (result >= 259200000 && result < 345600000){// 四天内
//			long seconds = result / 86400000;
//			r = seconds + "天前";
//		}
		else {// 日期格式
			SimpleDateFormat df = new SimpleDateFormat(format);
			r = df.format(ctime).toString();
		}
		return r;
	}

	/**
	 * 判断时间是否在10-24点时间段内
	 * @return
	 */
	public static boolean isBelong(){
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
		Date now =null;
		Date beginTime = null;
		Date endTime = null;
		try {
			now = df.parse(df.format(new Date()));
			beginTime = df.parse("15:00");
			endTime = df.parse("23:59");
		} catch (Exception e) {
			e.printStackTrace();
		}

		Boolean flag = belongCalendar(now, beginTime, endTime);
		return flag;
	}


	/**
	 * 判断时间是否超过某时间
	 * @param nowTime
	 * @param targetTime
	 * @return
	 */
	public static boolean belongCalendar(Date nowTime, Date targetTime) {
		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);

		Calendar target = Calendar.getInstance();
		target.setTime(targetTime);


		if (date.after(target)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断时间是否在时间段内
	 * @param nowTime
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);

		Calendar begin = Calendar.getInstance();
		begin.setTime(beginTime);

		Calendar end = Calendar.getInstance();
		end.setTime(endTime);

		if (date.after(begin) && date.before(end)) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		System.out.println(DateTool.showTime(new Date(),"yyyy-MM-dd HH:mm:ss"));
	}

}
