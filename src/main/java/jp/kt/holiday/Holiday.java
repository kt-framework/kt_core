package jp.kt.holiday;

import java.io.Serializable;
import java.util.Date;

/**
 * 祝日データクラス.
 *
 * @author tatsuya.kumon
 */
public class Holiday implements Serializable, Comparable<Holiday> {
	private static final long serialVersionUID = 1L;

	private Date date;

	private String name;

	private boolean original;

	/**
	 * コンストラクタ.
	 *
	 * @param date
	 *            Date
	 * @param name
	 *            祝日名称
	 * @param original
	 *            独自休日の場合はtrueを指定.
	 */
	Holiday(Date date, String name, boolean original) {
		this.date = date;
		this.name = name;
		this.original = original;
	}

	/**
	 * 祝日のDateを取得
	 *
	 * @return 祝日のDate
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * 祝日名称を取得.
	 *
	 * @return 祝日名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 独自祝日か国民の祝日かを返す.
	 *
	 * @return 独自休日の場合はtrue、国民の祝日の場合はfalse.
	 */
	public boolean isOriginal() {
		return original;
	}

	/**
	 * 大小比較.
	 */
	public int compareTo(Holiday o) {
		return this.date.compareTo(o.getDate());
	}
}
