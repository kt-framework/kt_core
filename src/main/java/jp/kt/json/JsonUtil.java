package jp.kt.json;

import jp.kt.exception.KtException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * JSON処理のユーティリティクラス.
 *
 * @author tatsuya.kumon
 */
public final class JsonUtil {
	private JsonUtil() {
	}

	/**
	 * JSONテキストを解析する.
	 *
	 * @param jsonText
	 *            JSONテキスト
	 * @return {@link org.json.JSONArray} もしくは {@link org.json.JSONObject}オブジェクト
	 * @throws JSONException
	 */
	public static Object parse(String jsonText) throws JSONException {
		/*
		 * まずJSONArrayオブジェクトを作ってみる
		 */
		JSONArray jsonArray = null;
		try {
			jsonArray = new JSONArray(jsonText);
		} catch (JSONException e) {
		}
		if (jsonArray != null) {
			// JSONExceptionが発生しなければJSONArrayオブジェクトを返す
			return jsonArray;
		}
		/*
		 * 次にJSONObjectオブジェクトを作ってみる
		 */
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(jsonText);
		} catch (JSONException e) {
		}
		if (jsonObject != null) {
			// JSONExceptionが発生しなければJSONArrayオブジェクトを返す
			return jsonObject;
		}
		// ここまで来るのは通常ありえない
		throw new KtException("A061", "JSONテキストの解析に失敗しました");
	}
}
