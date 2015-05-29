package jp.kt.tool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 集合の中からランダムにデータを抽出するためのツールクラス.
 *
 * @author tatsuya.kumon
 */
public class RandomSelector<E> {
	/** 抽出対象の集合 */
	private Collection<E> list;

	/**
	 * コンストラクタ.
	 */
	public RandomSelector() {
		this.list = new ArrayList<E>();
	}

	/**
	 * 集合にオブジェクトを追加する.
	 *
	 * @param obj
	 *            オブジェクト
	 */
	public void add(E obj) {
		this.list.add(obj);
	}

	/**
	 * 集合にリストを追加する.
	 *
	 * @param list
	 *            リスト
	 */
	public void addList(Collection<E> list) {
		this.list.addAll(list);
	}

	/**
	 * リストの中から1個抽出する.<br>
	 * 集合が0件の場合はnullを返す.
	 *
	 * @return 抽出したオブジェクト
	 */
	public E select() {
		// 集合が0件の場合はnullを返す
		if (this.list.size() == 0) {
			return null;
		}
		// 対象オブジェクトを返す
		return select(1).get(0);
	}

	/**
	 * リストの中から複数個抽出する.
	 * <p>
	 * 集合が0件の場合はnullを返す.
	 * </p>
	 *
	 * @param num
	 *            抽出したい個数
	 * @return 抽出したオブジェクトリスト
	 */
	public List<E> select(int num) {
		// 集合が0件の場合はnullを返す
		if (this.list.size() == 0) {
			return null;
		}
		// 抽出済みリストを生成する
		// ただし、抽出個数よりも元リストサイズの方が小さい場合は、元リストのサイズとなる
		List<E> selectedList = new ArrayList<E>();
		// 抽出対象のリストサイズ
		int orgListSize = this.list.size();
		// 抽出済みフラグの配列
		boolean[] isSelectedFlags = new boolean[orgListSize];
		while (true) {
			// インデックス番号をランダム抽出
			int seletedIndex = (int) (Math.random() * orgListSize);
			if (isSelectedFlags[seletedIndex]) {
				// 抽出済みのためスキップ
				continue;
			}
			// 抽出済みフラグを立てる
			isSelectedFlags[seletedIndex] = true;
			// 抽出済みリストに追加
			Iterator<E> it = this.list.iterator();
			for (int i = 0; true; i++) {
				E obj = it.next();
				if (i == seletedIndex) {
					selectedList.add(obj);
					break;
				}
			}
			// 抽出済みリストが抽出個数になった、もしくは元のリストのサイズになったらbreak
			if (selectedList.size() == num
					|| selectedList.size() == orgListSize) {
				break;
			}
		}
		return selectedList;
	}

	/**
	 * 選択肢リストのサイズ.
	 *
	 * @return 選択肢リストのサイズ
	 */
	public int size() {
		return this.list.size();
	}
}
