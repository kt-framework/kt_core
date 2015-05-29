package jp.kt.holiday;

import java.util.Calendar;
import java.util.Date;

import jp.kt.tool.DateUtil;
import jp.kt.tool.Validator;

/**
 * 国民の祝日.
 * 
 * @author tatsuya.kumon
 */
class NationalHoliday {
	/**
	 * インスタンス化できないようにするための内部コンストラクタ.
	 */
	private NationalHoliday() {
	}

	/** 祝日法施行 */
	private static final Date IMPLEMENT_THE_LAW_OF_HOLIDAY = DateUtil.getDate(
			1948, 7, 20, 0, 0, 0);
	/** 明仁親王の結婚の儀 */
	private static final Date AKIHITO_MARRIAGE = DateUtil.getDate(1959, 4, 10,
			0, 0, 0);
	/** 昭和天皇大喪の礼 */
	private static final Date SHOWA_TAISO = DateUtil.getDate(1989, 2, 24, 0, 0,
			0);
	/** 徳仁親王の結婚の儀 */
	private static final Date NORIHITO_MARRIAGE = DateUtil.getDate(1993, 6, 9,
			0, 0, 0);
	/** 即位礼正殿の儀 */
	private static final Date SOKUIREISEIDEN = DateUtil.getDate(1990, 11, 12,
			0, 0, 0);
	/** 振替休日施行 */
	private static final Date IMPLEMENT_HOLIDAY = DateUtil.getDate(1973, 4, 12,
			0, 0, 0);

	public static String getHolidayName(Date date) {
		String holidayName = null;
		// 基本の祝日名称を取得
		String holidayNameBasic = getHolidayNameBasic(date);
		if (Validator.isEmpty(holidayNameBasic)) {
			// 基本の祝日ではなかった場合、振替休日判定を行う
			if (DateUtil.getDayOfWeek(date) == Calendar.MONDAY) {
				// 月曜以外は振替休日判定不要
				// 5/6(火,水)の判定は判定済
				// 5/6(月)はここで判定する
				if (date.after(IMPLEMENT_HOLIDAY)
						|| isSameDate(date, IMPLEMENT_HOLIDAY)) {
					// 前日が祝日か判定
					Date yesterday = DateUtil.addDay(date, -1);
					String temp = getHolidayName(yesterday);
					if (!Validator.isEmpty(temp)) {
						// 前日が祝日なら振替休日であると判定
						holidayName = "振替休日";
					}
				}
			}
		} else {
			holidayName = holidayNameBasic;
		}
		return holidayName;
	}

	private static String getHolidayNameBasic(Date date) {
		// 祝日法施工前は空で返す
		if (date.before(IMPLEMENT_THE_LAW_OF_HOLIDAY)) {
			return null;
		}

		// Dateから年月日をそれぞれintに変換
		int[] datetimeArray = DateUtil.getDatetimeArray(date);
		int year = datetimeArray[0];
		int month = datetimeArray[1];
		int day = datetimeArray[2];
		// 何週目
		int numberOfWeek = ((day - 1) / 7) + 1;
		// 曜日
		int dayOfWeek = DateUtil.getDayOfWeek(date);

		String result = null;
		switch (month) {
		// 1月
		case 1:
			if (day == 1) {
				result = "元日";
			} else {
				if (year >= 2000) {
					if (numberOfWeek == 2 && dayOfWeek == Calendar.MONDAY) {
						result = "成人の日";
					}
				} else {
					if (day == 15) {
						result = "成人の日";
					}
				}
			}
			break;
		// 2月
		case 2:
			if (day == 11) {
				if (year >= 1967) {
					result = "建国記念の日";
				}
			} else {
				if (isSameDate(date, SHOWA_TAISO)) {
					result = "昭和天皇の大喪の礼";
				}
			}
			break;
		// 3月
		case 3:
			if (day == getDayOfSpringEquinox(year)) {
				result = "春分の日";
			}
			break;
		// 4月
		case 4:
			if (day == 29) {
				if (year >= 2007) {
					result = "昭和の日";
				} else {
					if (year >= 1989) {
						result = "みどりの日";
					} else {
						result = "天皇誕生日";
					}
				}
			} else {
				if (isSameDate(date, AKIHITO_MARRIAGE)) {
					result = "皇太子明仁親王の結婚の儀";
				}
			}
			break;
		// 5月
		case 5:
			switch (day) {
			case 3:
				result = "憲法記念日";
				break;
			case 4:
				if (year >= 2007) {
					result = "みどりの日";
				} else {
					if (year >= 1986) {
						if (dayOfWeek > Calendar.MONDAY) {
							// 5/4が日曜日は『只の日曜』､月曜日は『憲法記念日の振替休日』(～2006年)
							result = "国民の休日";
						}
					}
				}
				break;
			case 5:
				result = "こどもの日";
				break;
			case 6:
				if (year >= 2007) {
					if (dayOfWeek == Calendar.TUESDAY
							|| dayOfWeek == Calendar.WEDNESDAY) {
						// [5/3,5/4が日曜]ケースのみ、ここで判定
						result = "振替休日";
					}
				}
				break;
			}
			break;
		// 6月
		case 6:
			if (isSameDate(date, NORIHITO_MARRIAGE)) {
				result = "皇太子徳仁親王の結婚の儀";
			}
			break;
		// 7月
		case 7:
			if (year >= 2003) {
				if (numberOfWeek == 3 && dayOfWeek == Calendar.MONDAY) {
					result = "海の日";
				}
			} else {
				if (year >= 1996) {
					if (day == 20) {
						result = "海の日";
					}
				}
			}
			break;
		// 8月
		case 9:
			// 第3月曜日(15～21)と秋分日(22～24)が重なる事はない
			int autumnEquinox = getDayOfAutumnEquinox(year);
			if (day == autumnEquinox) {
				result = "秋分の日";
			} else {
				if (year >= 2003) {
					if (numberOfWeek == 3 && dayOfWeek == Calendar.MONDAY) {
						result = "敬老の日";
					} else {
						if (dayOfWeek == Calendar.TUESDAY) {
							if (day == (autumnEquinox - 1)) {
								result = "国民の休日";
							}
						}
					}
				} else {
					if (year >= 1966) {
						if (day == 15) {
							result = "敬老の日";
						}
					}
				}
			}
			break;
		// 10月
		case 10:
			if (year >= 2000) {
				if (numberOfWeek == 2 && dayOfWeek == Calendar.MONDAY) {
					result = "体育の日";
				}
			} else {
				if (year >= 1966) {
					if (day == 10) {
						result = "体育の日";
					}
				}
			}
			break;
		// 11月
		case 11:
			if (day == 3) {
				result = "文化の日";
			} else {
				if (day == 23) {
					result = "勤労感謝の日";
				} else {
					if (isSameDate(date, SOKUIREISEIDEN)) {
						result = "即位礼正殿の儀";
					}
				}
			}
			break;
		// 12月
		case 12:
			if (day == 23) {
				if (year >= 1989) {
					result = "天皇誕生日";
				}
			}
			break;
		}
		return result;
	}

	/**
	 * 同一日判定.
	 * 
	 * @param date1
	 *            日付1
	 * @param date2
	 *            日付2
	 * @return 同一日ならtrue
	 */
	private static boolean isSameDate(Date date1, Date date2) {
		int[] array1 = DateUtil.getDatetimeArray(date1);
		int[] array2 = DateUtil.getDatetimeArray(date2);
		if (array1[0] == array2[0] && array1[1] == array2[1]
				&& array1[2] == array2[2]) {
			return true;
		}
		return false;
	}

	/**
	 * 指定した年の春分の日を取得する.
	 * <p>
	 * 『海上保安庁水路部 暦計算研究会編 新こよみ便利帳』を元に算出しています.
	 * </p>
	 * 
	 * @param year
	 *            年
	 * @return 春分の日の日付
	 */
	private static int getDayOfSpringEquinox(int year) {
		int springEquinox;
		if (year <= 1947) {
			// 祝日法施行前
			springEquinox = 0;
		} else {
			if (year <= 1979) {
				springEquinox = (int) (20.8357 + (0.242194 * (year - 1980)) - (int) ((year - 1983) / 4));
			} else {
				if (year <= 2099) {
					springEquinox = (int) (20.8431 + (0.242194 * (year - 1980)) - (int) ((year - 1980) / 4));
				} else {
					if (year <= 2150) {
						springEquinox = (int) (21.851 + (0.242194 * (year - 1980)) - (int) ((year - 1980) / 4));
					} else {
						// 2151年以降は略算式が無いので不明
						springEquinox = 0;
					}
				}
			}
		}
		return springEquinox;
	}

	/**
	 * 指定した年の秋分の日を取得する.
	 * <p>
	 * 『海上保安庁水路部 暦計算研究会編 新こよみ便利帳』を元に算出しています.
	 * </p>
	 * 
	 * @param year
	 *            年
	 * @return 秋分の日の日付
	 */
	private static int getDayOfAutumnEquinox(int year) {
		int autumnEquinox;
		if (year <= 1947) {
			// 祝日法施行前
			autumnEquinox = 0;
		} else {
			if (year <= 1979) {
				autumnEquinox = (int) (23.2588 + (0.242194 * (year - 1980)) - (int) ((year - 1983) / 4));
			} else {
				if (year <= 2099) {
					autumnEquinox = (int) (23.2488 + (0.242194 * (year - 1980)) - (int) ((year - 1980) / 4));
				} else {
					if (year <= 2150) {
						autumnEquinox = (int) (24.2488 + (0.242194 * (year - 1980)) - (int) ((year - 1980) / 4));
					} else {
						// 2151年以降は略算式が無いので不明
						autumnEquinox = 0;
					}
				}
			}
		}
		return autumnEquinox;
	}
}
