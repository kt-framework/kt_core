package jp.kt.holiday;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.kt.tool.DateUtil;
import jp.kt.tool.TextFormat;
import jp.kt.tool.Validator;

/**
 * 祝日判定.
 * 
 * @author tatsuya.kumon
 */
public class HolidayUtil {
	/** 独自休日の日付フォーマット */
	private static final String DATE_TEXT_FORMAT = "yyyy/MM/dd";

	/** 独自休日Map */
	private Map<String, String> originalHolidayMap;

	/**
	 * 独自休日を指定しない場合のコンストラクタ.
	 */
	public HolidayUtil() {
	}

	/**
	 * 独自休日を指定する場合のコンストラクタ.
	 * <p>
	 * 引数のMapのキーは日付、値は休日名称.<br>
	 * 日付の形式は「yyyy/MM/dd」であること.<br>
	 * 休日名称は必須です.空にすると休日と判定されませんので注意してください.
	 * </p>
	 * 
	 * @param originalHolidayMap
	 *            独自休日のMap
	 */
	public HolidayUtil(Map<String, String> originalHolidayMap) {
		this.originalHolidayMap = originalHolidayMap;
	}

	/**
	 * 祝日リストを取得.
	 * <p>
	 * 国民の祝日と独自休日が重なった場合は、国民の祝日の情報がセットされます.
	 * </p>
	 * 
	 * @param year
	 *            年
	 * @return 祝日リスト
	 */
	public List<Holiday> getHolidayList(int year) {
		// 指定年の1月1日から翌年の1月1日まで
		Date fromDate = DateUtil.getDate(year, 1, 1, 0, 0, 0);
		Date toDate = DateUtil.getDate(year + 1, 1, 1, 0, 0, 0);
		// 祝日リストを取得
		List<Holiday> holidayList = getHolidayList(fromDate, toDate);
		return holidayList;
	}

	/**
	 * 祝日リストを取得.
	 * <p>
	 * 国民の祝日と独自休日が重なった場合は、国民の祝日の情報がセットされます.
	 * </p>
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 祝日リスト
	 */
	public List<Holiday> getHolidayList(int year, int month) {
		// 指定年月の1日から翌月の1日まで
		Date fromDate = DateUtil.getDate(year, month, 1, 0, 0, 0);
		Date toDate = DateUtil.addMonth(fromDate, 1);
		// 祝日リストを取得
		List<Holiday> holidayList = getHolidayList(fromDate, toDate);
		return holidayList;
	}

	/**
	 * 指定された期間内の祝日リストを返します.
	 * <p>
	 * 国民の祝日と独自休日が重なった場合は、国民の祝日の情報がセットされます.
	 * </p>
	 * 
	 * @param fromDate
	 *            From日付（この日付は含みます）
	 * @param toDate
	 *            To日付（この日付は含みません）
	 * @return {@link Holiday} オブジェクト.<br>
	 *         ただし祝日でない場合はnullを返します.
	 */
	private List<Holiday> getHolidayList(Date fromDate, Date toDate) {
		List<Holiday> holidayList = new ArrayList<Holiday>();
		// fromDateからtoDateの前日まで1日ずつ加算してループ
		for (Date date = fromDate; date.before(toDate); date = DateUtil.addDay(
				date, 1)) {
			Holiday holiday = getHoliday(date);
			if (holiday != null) {
				// nullでなければリストに追加
				holidayList.add(holiday);
			}
		}
		return holidayList;
	}

	/**
	 * 祝日データを取得します.
	 * <p>
	 * 祝日でなければnullを返します.
	 * </p>
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @return {@link Holiday} オブジェクト.祝日でない場合はnullを返します.
	 */
	public Holiday getHoliday(int year, int month, int day) {
		return getHoliday(DateUtil.getDate(year, month, day, 0, 0, 0));
	}

	/**
	 * 祝日データを取得します.
	 * <p>
	 * 祝日でなければnullを返します.
	 * </p>
	 * 
	 * @param date
	 *            対象日付
	 * @return {@link Holiday} オブジェクト.祝日でない場合はnullを返します.
	 */
	public Holiday getHoliday(Date date) {
		Holiday holiday = null;
		// 国民の祝日判定
		String holidayName = NationalHoliday.getHolidayName(date);
		if (!Validator.isEmpty(holidayName)) {
			holiday = new Holiday(date, holidayName, false);
		} else {
			// 独自休日判定
			holidayName = getOriginalHolidayName(date);
			if (!Validator.isEmpty(holidayName)) {
				holiday = new Holiday(date, holidayName, true);
			}
		}
		return holiday;
	}

	/**
	 * 独自休日の名称を取得.
	 * 
	 * @param date
	 *            対象の日付
	 * @return 独自休日名称.<br>
	 *         ただし独自休日でない場合はnullを返します.
	 */
	private String getOriginalHolidayName(Date date) {
		if (originalHolidayMap == null) {
			return null;
		}
		String dateText = TextFormat.formatDate(date, DATE_TEXT_FORMAT);
		return originalHolidayMap.get(dateText);
	}
}
