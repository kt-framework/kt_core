package jp.kt.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * {@link Date}オブジェクトを操作するクラス.
 *
 * @author tatsuya.kumon
 */
public class DateUtil {
	/**
	 * インスタンス化されないための内部コンストラクタ.
	 */
	private DateUtil() {
	}

	/**
	 * 年月日から{@link Date}オブジェクトを生成.<br>
	 * 時刻は0時0分0秒でセットする.
	 *
	 * @param y
	 *            年
	 * @param m
	 *            月
	 * @param d
	 *            日
	 * @param h
	 *            時
	 * @param mi
	 *            分
	 * @param s
	 *            秒
	 * @return {@link Date} オブジェクト
	 */
	public static Date getDate(String y, String m, String d, String h,
			String mi, String s) {
		return getDate(Integer.parseInt(y), Integer.parseInt(m),
				Integer.parseInt(d), Integer.parseInt(h), Integer.parseInt(mi),
				Integer.parseInt(s));
	}

	/**
	 * 年月日から{@link Date}オブジェクトを生成.<br>
	 * 時刻は0時0分0秒でセットする.
	 *
	 * @param y
	 *            年
	 * @param m
	 *            月
	 * @param d
	 *            日
	 * @param h
	 *            時
	 * @param mi
	 *            分
	 * @param s
	 *            秒
	 * @return {@link Date} オブジェクト
	 */
	public static Date getDate(int y, int m, int d, int h, int mi, int s) {
		Calendar cal = Calendar.getInstance();
		cal.set(y, m - 1, d, h, mi, s);
		cal.set(Calendar.MILLISECOND, 0);
		// 厳密モードする
		cal.setLenient(false);
		return cal.getTime();
	}

	/**
	 * 日時文字列から{@link Date}オブジェクトを生成.
	 * <p>
	 * デフォルトロケール（日本）のフォーマットの文字列を変換する.
	 * </p>
	 *
	 * @param datetimeText
	 *            日時を表す文字列
	 * @param pattern
	 *            datetimeTextの形式.<br>
	 *            {@link java.text.SimpleDateFormat} にて定義されている形式で指定すること.
	 * @return {@link Date} オブジェクト
	 * @throws ParseException
	 *             文字列が日付解析できない場合
	 *
	 */
	public static Date getDate(String datetimeText, String pattern)
			throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		format.setLenient(false);
		return format.parse(datetimeText);
	}

	/**
	 * 日時文字列から{@link Date}オブジェクトを生成.
	 * <p>
	 * {@link Locale#US} フォーマットの文字列を変換する.
	 * </p>
	 *
	 * @param datetimeText
	 *            日時を表す文字列
	 * @param pattern
	 *            datetimeTextの形式.<br>
	 *            {@link java.text.SimpleDateFormat} にて定義されている形式で指定すること.
	 * @return {@link Date} オブジェクト
	 * @throws ParseException
	 *             文字列が日付解析できない場合
	 */
	public static Date getDateUsFormat(String datetimeText, String pattern)
			throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.US);
		return format.parse(datetimeText);
	}

	/**
	 * {@link Date}型の日時を年・月・日・時・分・秒・ミリ秒に分けてint配列で返す.
	 *
	 * @param date
	 *            {@link Date} オブジェクト
	 * @return サイズが7のint配列.ただし引数がnullの場合はnullを返す.
	 */
	public static int[] getDatetimeArray(Date date) {
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int[] datetimes = new int[] { cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH),
				cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),
				cal.get(Calendar.SECOND), cal.get(Calendar.MILLISECOND) };
		return datetimes;
	}

	/**
	 * 指定日数を加算する.<br>
	 * マイナス指定も可.
	 *
	 * @param date
	 *            元のDate
	 * @param day
	 *            加算する日数
	 * @return 加算された{@link Date} オブジェクト
	 */
	public static Date addDay(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}

	/**
	 * 指定月数を加算する.<br>
	 * マイナス指定も可.
	 *
	 * @param date
	 *            元の{@link Date}
	 * @param month
	 *            加算する月数
	 * @return 加算された{@link Date}オブジェクト
	 */
	public static Date addMonth(Date date, int month) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, month);
		return cal.getTime();
	}

	/**
	 * 指定の時間加算する.<br>
	 * マイナス指定も可.<br>
	 * 変更しない項目は0を指定する.
	 *
	 * @param date
	 *            元の{@link Date}
	 * @param hour
	 *            加算する時間数
	 * @param minute
	 *            加算する分数
	 * @param second
	 *            加算する秒数
	 * @param millisec
	 *            加算するミリ秒数
	 * @return 加算されたDateオブジェクト
	 */
	public static Date addTime(Date date, int hour, int minute, int second,
			int millisec) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, hour);
		cal.add(Calendar.MINUTE, minute);
		cal.add(Calendar.SECOND, second);
		cal.add(Calendar.MILLISECOND, millisec);
		return cal.getTime();
	}

	/**
	 * 日時の大小判定.
	 * <p>
	 * 第一引数＜第二引数の場合、true.<br>
	 * 第一引数＝第二引数の場合、true.<br>
	 * 第一引数＞第二引数の場合、false.
	 * </p>
	 *
	 * @param date1
	 *            比較対象の{@link Date}
	 * @param date2
	 *            比較対象の{@link Date}
	 * @return 判定結果
	 */
	public static boolean after(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return cal2.compareTo(cal1) >= 0;
	}

	/**
	 * 日付は変えず、時刻を0時0分0秒にする.
	 *
	 * @param date
	 *            対象の{@link Date}
	 * @return 時刻を0時0分0秒にした{@link Date}オブジェクト
	 */
	public static Date setZeroTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 曜日を取得します.
	 * <p>
	 * 結果は、{@link Calendar}クラスで定義されている値を返します.<br>
	 * 日曜日：{@link Calendar#SUNDAY}<br>
	 * 月曜日：{@link Calendar#MONDAY}<br>
	 * 火曜日：{@link Calendar#TUESDAY}<br>
	 * 水曜日：{@link Calendar#WEDNESDAY}<br>
	 * 木曜日：{@link Calendar#THURSDAY}<br>
	 * 金曜日：{@link Calendar#FRIDAY}<br>
	 * 土曜日：{@link Calendar#SATURDAY}
	 * </p>
	 *
	 * @param date
	 *            対象の {@link Date}
	 * @return 曜日
	 */
	public static int getDayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 2つの Date の日数の差を取得する.
	 * <p>
	 * 第二引数の方が大きい場合は正の値を、第二引数のほうが小さい場合は負の値を返します.<br>
	 * また、同一日の場合は0を返します.<br>
	 * 算出の際、Dateオブジェクトの時刻部分は無視されます.
	 * </p>
	 *
	 * @param date1
	 *            比較対象Date
	 * @param date2
	 *            比較対象Date
	 * @return 差分の日数
	 */
	public static int diffDay(Date date1, Date date2) {
		// 時刻を0時にする
		date1 = setZeroTime(date1);
		date2 = setZeroTime(date2);
		// 差を算出
		long diffTime = date2.getTime() - date1.getTime();
		long diffDay = diffTime / (1000 * 60 * 60 * 24);
		return (int) diffDay;
	}
}
