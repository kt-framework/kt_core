package jp.kt.tool;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 文字データを変換、管理するクラスです。
 */
public class StringUtil {
	/** 半角の濁点 */
	private static final char HAN_MARK1 = 'ﾞ';

	/** 半角の半濁点 */
	private static final char HAN_MARK2 = 'ﾟ';

	/** 半角英数 */
	private static String[] HAN_EISU = new String[] { "A", "B", "C", "D", "E",
			"F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
			"S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e",
			"f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
			"s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4",
			"5", "6", "7", "8", "9" };

	/** 半角数字 */
	private static String[] NUMBER = new String[] { "0", "1", "2", "3", "4",
			"5", "6", "7", "8", "9" };

	/** 全角から半角への変換Map（カタカナ濁音、半濁音） */
	private static final Map<Character, String> ZEN_TO_HAN_MAP1;
	/** 全角から半角への変換Map（その他の文字） */
	private static final Map<Character, Character> ZEN_TO_HAN_MAP2;

	/** 半角から全角への変換Map（カタカナ濁音、半濁音） */
	private static final Map<String, Character> HAN_TO_ZEN_MAP1;
	/** 半角から全角への変換Map（その他の文字） */
	private static final Map<Character, Character> HAN_TO_ZEN_MAP2;

	/** ひらがなからカタカナへの変換Map */
	private static final Map<Character, Character> HIRA_TO_KANA_MAP;

	/**
	 * 全角から半角、半角から全角への変換Mapを作成<br>
	 * ひらがなからカタカナ、カタカナからひらがなへの変換Mapも作成
	 */
	static {
		/*
		 * 全角から半角への変換Map
		 */
		// 濁音、半濁音文字
		ZEN_TO_HAN_MAP1 = new HashMap<Character, String>();
		ZEN_TO_HAN_MAP1.put('ガ', "ｶﾞ");
		ZEN_TO_HAN_MAP1.put('ギ', "ｷﾞ");
		ZEN_TO_HAN_MAP1.put('グ', "ｸﾞ");
		ZEN_TO_HAN_MAP1.put('ゲ', "ｹﾞ");
		ZEN_TO_HAN_MAP1.put('ゴ', "ｺﾞ");
		ZEN_TO_HAN_MAP1.put('ザ', "ｻﾞ");
		ZEN_TO_HAN_MAP1.put('ジ', "ｼﾞ");
		ZEN_TO_HAN_MAP1.put('ズ', "ｽﾞ");
		ZEN_TO_HAN_MAP1.put('ゼ', "ｾﾞ");
		ZEN_TO_HAN_MAP1.put('ゾ', "ｿﾞ");
		ZEN_TO_HAN_MAP1.put('ダ', "ﾀﾞ");
		ZEN_TO_HAN_MAP1.put('ヂ', "ﾁﾞ");
		ZEN_TO_HAN_MAP1.put('ヅ', "ﾂﾞ");
		ZEN_TO_HAN_MAP1.put('デ', "ﾃﾞ");
		ZEN_TO_HAN_MAP1.put('ド', "ﾄﾞ");
		ZEN_TO_HAN_MAP1.put('バ', "ﾊﾞ");
		ZEN_TO_HAN_MAP1.put('ビ', "ﾋﾞ");
		ZEN_TO_HAN_MAP1.put('ブ', "ﾌﾞ");
		ZEN_TO_HAN_MAP1.put('ベ', "ﾍﾞ");
		ZEN_TO_HAN_MAP1.put('ボ', "ﾎﾞ");
		ZEN_TO_HAN_MAP1.put('パ', "ﾊﾟ");
		ZEN_TO_HAN_MAP1.put('ピ', "ﾋﾟ");
		ZEN_TO_HAN_MAP1.put('プ', "ﾌﾟ");
		ZEN_TO_HAN_MAP1.put('ペ', "ﾍﾟ");
		ZEN_TO_HAN_MAP1.put('ポ', "ﾎﾟ");
		ZEN_TO_HAN_MAP1.put('ヴ', "ｳﾞ");
		// その他の文字
		ZEN_TO_HAN_MAP2 = new HashMap<Character, Character>();
		ZEN_TO_HAN_MAP2.put('ア', 'ｱ');
		ZEN_TO_HAN_MAP2.put('イ', 'ｲ');
		ZEN_TO_HAN_MAP2.put('ウ', 'ｳ');
		ZEN_TO_HAN_MAP2.put('エ', 'ｴ');
		ZEN_TO_HAN_MAP2.put('オ', 'ｵ');
		ZEN_TO_HAN_MAP2.put('カ', 'ｶ');
		ZEN_TO_HAN_MAP2.put('キ', 'ｷ');
		ZEN_TO_HAN_MAP2.put('ク', 'ｸ');
		ZEN_TO_HAN_MAP2.put('ケ', 'ｹ');
		ZEN_TO_HAN_MAP2.put('コ', 'ｺ');
		ZEN_TO_HAN_MAP2.put('サ', 'ｻ');
		ZEN_TO_HAN_MAP2.put('シ', 'ｼ');
		ZEN_TO_HAN_MAP2.put('ス', 'ｽ');
		ZEN_TO_HAN_MAP2.put('セ', 'ｾ');
		ZEN_TO_HAN_MAP2.put('ソ', 'ｿ');
		ZEN_TO_HAN_MAP2.put('タ', 'ﾀ');
		ZEN_TO_HAN_MAP2.put('チ', 'ﾁ');
		ZEN_TO_HAN_MAP2.put('ツ', 'ﾂ');
		ZEN_TO_HAN_MAP2.put('テ', 'ﾃ');
		ZEN_TO_HAN_MAP2.put('ト', 'ﾄ');
		ZEN_TO_HAN_MAP2.put('ナ', 'ﾅ');
		ZEN_TO_HAN_MAP2.put('ニ', 'ﾆ');
		ZEN_TO_HAN_MAP2.put('ヌ', 'ﾇ');
		ZEN_TO_HAN_MAP2.put('ネ', 'ﾈ');
		ZEN_TO_HAN_MAP2.put('ノ', 'ﾉ');
		ZEN_TO_HAN_MAP2.put('ハ', 'ﾊ');
		ZEN_TO_HAN_MAP2.put('ヒ', 'ﾋ');
		ZEN_TO_HAN_MAP2.put('フ', 'ﾌ');
		ZEN_TO_HAN_MAP2.put('ヘ', 'ﾍ');
		ZEN_TO_HAN_MAP2.put('ホ', 'ﾎ');
		ZEN_TO_HAN_MAP2.put('マ', 'ﾏ');
		ZEN_TO_HAN_MAP2.put('ミ', 'ﾐ');
		ZEN_TO_HAN_MAP2.put('ム', 'ﾑ');
		ZEN_TO_HAN_MAP2.put('メ', 'ﾒ');
		ZEN_TO_HAN_MAP2.put('モ', 'ﾓ');
		ZEN_TO_HAN_MAP2.put('ヤ', 'ﾔ');
		ZEN_TO_HAN_MAP2.put('ユ', 'ﾕ');
		ZEN_TO_HAN_MAP2.put('ヨ', 'ﾖ');
		ZEN_TO_HAN_MAP2.put('ラ', 'ﾗ');
		ZEN_TO_HAN_MAP2.put('リ', 'ﾘ');
		ZEN_TO_HAN_MAP2.put('ル', 'ﾙ');
		ZEN_TO_HAN_MAP2.put('レ', 'ﾚ');
		ZEN_TO_HAN_MAP2.put('ロ', 'ﾛ');
		ZEN_TO_HAN_MAP2.put('ワ', 'ﾜ');
		ZEN_TO_HAN_MAP2.put('ヲ', 'ｦ');
		ZEN_TO_HAN_MAP2.put('ン', 'ﾝ');
		ZEN_TO_HAN_MAP2.put('ヱ', 'ｴ');
		ZEN_TO_HAN_MAP2.put('ァ', 'ｧ');
		ZEN_TO_HAN_MAP2.put('ィ', 'ｨ');
		ZEN_TO_HAN_MAP2.put('ゥ', 'ｩ');
		ZEN_TO_HAN_MAP2.put('ェ', 'ｪ');
		ZEN_TO_HAN_MAP2.put('ォ', 'ｫ');
		ZEN_TO_HAN_MAP2.put('ッ', 'ｯ');
		ZEN_TO_HAN_MAP2.put('ャ', 'ｬ');
		ZEN_TO_HAN_MAP2.put('ュ', 'ｭ');
		ZEN_TO_HAN_MAP2.put('ョ', 'ｮ');
		ZEN_TO_HAN_MAP2.put('ー', 'ｰ');
		ZEN_TO_HAN_MAP2.put('Ａ', 'A');
		ZEN_TO_HAN_MAP2.put('Ｂ', 'B');
		ZEN_TO_HAN_MAP2.put('Ｃ', 'C');
		ZEN_TO_HAN_MAP2.put('Ｄ', 'D');
		ZEN_TO_HAN_MAP2.put('Ｅ', 'E');
		ZEN_TO_HAN_MAP2.put('Ｆ', 'F');
		ZEN_TO_HAN_MAP2.put('Ｇ', 'G');
		ZEN_TO_HAN_MAP2.put('Ｈ', 'H');
		ZEN_TO_HAN_MAP2.put('Ｉ', 'I');
		ZEN_TO_HAN_MAP2.put('Ｊ', 'J');
		ZEN_TO_HAN_MAP2.put('Ｋ', 'K');
		ZEN_TO_HAN_MAP2.put('Ｌ', 'L');
		ZEN_TO_HAN_MAP2.put('Ｍ', 'M');
		ZEN_TO_HAN_MAP2.put('Ｎ', 'N');
		ZEN_TO_HAN_MAP2.put('Ｏ', 'O');
		ZEN_TO_HAN_MAP2.put('Ｐ', 'P');
		ZEN_TO_HAN_MAP2.put('Ｑ', 'Q');
		ZEN_TO_HAN_MAP2.put('Ｒ', 'R');
		ZEN_TO_HAN_MAP2.put('Ｓ', 'S');
		ZEN_TO_HAN_MAP2.put('Ｔ', 'T');
		ZEN_TO_HAN_MAP2.put('Ｕ', 'U');
		ZEN_TO_HAN_MAP2.put('Ｖ', 'V');
		ZEN_TO_HAN_MAP2.put('Ｗ', 'W');
		ZEN_TO_HAN_MAP2.put('Ｘ', 'X');
		ZEN_TO_HAN_MAP2.put('Ｙ', 'Y');
		ZEN_TO_HAN_MAP2.put('Ｚ', 'Z');
		ZEN_TO_HAN_MAP2.put('ａ', 'a');
		ZEN_TO_HAN_MAP2.put('ｂ', 'b');
		ZEN_TO_HAN_MAP2.put('ｃ', 'c');
		ZEN_TO_HAN_MAP2.put('ｄ', 'd');
		ZEN_TO_HAN_MAP2.put('ｅ', 'e');
		ZEN_TO_HAN_MAP2.put('ｆ', 'f');
		ZEN_TO_HAN_MAP2.put('ｇ', 'g');
		ZEN_TO_HAN_MAP2.put('ｈ', 'h');
		ZEN_TO_HAN_MAP2.put('ｉ', 'i');
		ZEN_TO_HAN_MAP2.put('ｊ', 'j');
		ZEN_TO_HAN_MAP2.put('ｋ', 'k');
		ZEN_TO_HAN_MAP2.put('ｌ', 'l');
		ZEN_TO_HAN_MAP2.put('ｍ', 'm');
		ZEN_TO_HAN_MAP2.put('ｎ', 'n');
		ZEN_TO_HAN_MAP2.put('ｏ', 'o');
		ZEN_TO_HAN_MAP2.put('ｐ', 'p');
		ZEN_TO_HAN_MAP2.put('ｑ', 'q');
		ZEN_TO_HAN_MAP2.put('ｒ', 'r');
		ZEN_TO_HAN_MAP2.put('ｓ', 's');
		ZEN_TO_HAN_MAP2.put('ｔ', 't');
		ZEN_TO_HAN_MAP2.put('ｕ', 'u');
		ZEN_TO_HAN_MAP2.put('ｖ', 'v');
		ZEN_TO_HAN_MAP2.put('ｗ', 'w');
		ZEN_TO_HAN_MAP2.put('ｘ', 'x');
		ZEN_TO_HAN_MAP2.put('ｙ', 'y');
		ZEN_TO_HAN_MAP2.put('ｚ', 'z');
		ZEN_TO_HAN_MAP2.put('０', '0');
		ZEN_TO_HAN_MAP2.put('１', '1');
		ZEN_TO_HAN_MAP2.put('２', '2');
		ZEN_TO_HAN_MAP2.put('３', '3');
		ZEN_TO_HAN_MAP2.put('４', '4');
		ZEN_TO_HAN_MAP2.put('５', '5');
		ZEN_TO_HAN_MAP2.put('６', '6');
		ZEN_TO_HAN_MAP2.put('７', '7');
		ZEN_TO_HAN_MAP2.put('８', '8');
		ZEN_TO_HAN_MAP2.put('９', '9');
		ZEN_TO_HAN_MAP2.put('／', '/');
		ZEN_TO_HAN_MAP2.put('；', ';');
		ZEN_TO_HAN_MAP2.put('：', ':');
		ZEN_TO_HAN_MAP2.put('（', '(');
		ZEN_TO_HAN_MAP2.put('）', ')');
		ZEN_TO_HAN_MAP2.put('＜', '<');
		ZEN_TO_HAN_MAP2.put('＞', '>');
		ZEN_TO_HAN_MAP2.put('｛', '{');
		ZEN_TO_HAN_MAP2.put('｝', '}');
		ZEN_TO_HAN_MAP2.put('「', '｢');
		ZEN_TO_HAN_MAP2.put('」', '｣');
		ZEN_TO_HAN_MAP2.put('［', '[');
		ZEN_TO_HAN_MAP2.put('］', ']');
		ZEN_TO_HAN_MAP2.put('、', '､');
		ZEN_TO_HAN_MAP2.put('。', '｡');
		ZEN_TO_HAN_MAP2.put('，', ',');
		ZEN_TO_HAN_MAP2.put('．', '.');
		ZEN_TO_HAN_MAP2.put('＃', '#');
		ZEN_TO_HAN_MAP2.put('＄', '$');
		ZEN_TO_HAN_MAP2.put('％', '%');
		ZEN_TO_HAN_MAP2.put('＆', '&');
		ZEN_TO_HAN_MAP2.put('＝', '=');
		ZEN_TO_HAN_MAP2.put('＋', '+');
		ZEN_TO_HAN_MAP2.put('－', '-');
		ZEN_TO_HAN_MAP2.put('―', '-');
		ZEN_TO_HAN_MAP2.put('＠', '@');
		ZEN_TO_HAN_MAP2.put('￥', '\\');
		ZEN_TO_HAN_MAP2.put('＊', '*');
		ZEN_TO_HAN_MAP2.put('｜', '|');
		ZEN_TO_HAN_MAP2.put('＾', '^');
		ZEN_TO_HAN_MAP2.put('’', '\'');
		ZEN_TO_HAN_MAP2.put('‘', '\'');
		ZEN_TO_HAN_MAP2.put('”', '"');
		ZEN_TO_HAN_MAP2.put('“', '"');
		ZEN_TO_HAN_MAP2.put('？', '?');
		ZEN_TO_HAN_MAP2.put('！', '!');
		ZEN_TO_HAN_MAP2.put('・', '･');
		ZEN_TO_HAN_MAP2.put('　', ' ');
		ZEN_TO_HAN_MAP2.put('゛', 'ﾞ');
		ZEN_TO_HAN_MAP2.put('゜', 'ﾟ');

		/*
		 * 全角から半角への変換Map
		 */
		// 濁音、半濁音文字
		HAN_TO_ZEN_MAP1 = new HashMap<String, Character>();
		HAN_TO_ZEN_MAP1.put("ｶﾞ", 'ガ');
		HAN_TO_ZEN_MAP1.put("ｷﾞ", 'ギ');
		HAN_TO_ZEN_MAP1.put("ｸﾞ", 'グ');
		HAN_TO_ZEN_MAP1.put("ｹﾞ", 'ゲ');
		HAN_TO_ZEN_MAP1.put("ｺﾞ", 'ゴ');
		HAN_TO_ZEN_MAP1.put("ｻﾞ", 'ザ');
		HAN_TO_ZEN_MAP1.put("ｼﾞ", 'ジ');
		HAN_TO_ZEN_MAP1.put("ｽﾞ", 'ズ');
		HAN_TO_ZEN_MAP1.put("ｾﾞ", 'ゼ');
		HAN_TO_ZEN_MAP1.put("ｿﾞ", 'ゾ');
		HAN_TO_ZEN_MAP1.put("ﾀﾞ", 'ダ');
		HAN_TO_ZEN_MAP1.put("ﾁﾞ", 'ヂ');
		HAN_TO_ZEN_MAP1.put("ﾂﾞ", 'ヅ');
		HAN_TO_ZEN_MAP1.put("ﾃﾞ", 'デ');
		HAN_TO_ZEN_MAP1.put("ﾄﾞ", 'ド');
		HAN_TO_ZEN_MAP1.put("ﾊﾞ", 'バ');
		HAN_TO_ZEN_MAP1.put("ﾋﾞ", 'ビ');
		HAN_TO_ZEN_MAP1.put("ﾌﾞ", 'ブ');
		HAN_TO_ZEN_MAP1.put("ﾍﾞ", 'ベ');
		HAN_TO_ZEN_MAP1.put("ﾎﾞ", 'ボ');
		HAN_TO_ZEN_MAP1.put("ﾊﾟ", 'パ');
		HAN_TO_ZEN_MAP1.put("ﾋﾟ", 'ピ');
		HAN_TO_ZEN_MAP1.put("ﾌﾟ", 'プ');
		HAN_TO_ZEN_MAP1.put("ﾍﾟ", 'ペ');
		HAN_TO_ZEN_MAP1.put("ﾎﾟ", 'ポ');
		HAN_TO_ZEN_MAP1.put("ｳﾞ", 'ヴ');
		// その他の文字
		HAN_TO_ZEN_MAP2 = new HashMap<Character, Character>();
		HAN_TO_ZEN_MAP2.put('ｱ', 'ア');
		HAN_TO_ZEN_MAP2.put('ｲ', 'イ');
		HAN_TO_ZEN_MAP2.put('ｳ', 'ウ');
		HAN_TO_ZEN_MAP2.put('ｴ', 'エ');
		HAN_TO_ZEN_MAP2.put('ｵ', 'オ');
		HAN_TO_ZEN_MAP2.put('ｶ', 'カ');
		HAN_TO_ZEN_MAP2.put('ｷ', 'キ');
		HAN_TO_ZEN_MAP2.put('ｸ', 'ク');
		HAN_TO_ZEN_MAP2.put('ｹ', 'ケ');
		HAN_TO_ZEN_MAP2.put('ｺ', 'コ');
		HAN_TO_ZEN_MAP2.put('ｻ', 'サ');
		HAN_TO_ZEN_MAP2.put('ｼ', 'シ');
		HAN_TO_ZEN_MAP2.put('ｽ', 'ス');
		HAN_TO_ZEN_MAP2.put('ｾ', 'セ');
		HAN_TO_ZEN_MAP2.put('ｿ', 'ソ');
		HAN_TO_ZEN_MAP2.put('ﾀ', 'タ');
		HAN_TO_ZEN_MAP2.put('ﾁ', 'チ');
		HAN_TO_ZEN_MAP2.put('ﾂ', 'ツ');
		HAN_TO_ZEN_MAP2.put('ﾃ', 'テ');
		HAN_TO_ZEN_MAP2.put('ﾄ', 'ト');
		HAN_TO_ZEN_MAP2.put('ﾅ', 'ナ');
		HAN_TO_ZEN_MAP2.put('ﾆ', 'ニ');
		HAN_TO_ZEN_MAP2.put('ﾇ', 'ヌ');
		HAN_TO_ZEN_MAP2.put('ﾈ', 'ネ');
		HAN_TO_ZEN_MAP2.put('ﾉ', 'ノ');
		HAN_TO_ZEN_MAP2.put('ﾊ', 'ハ');
		HAN_TO_ZEN_MAP2.put('ﾋ', 'ヒ');
		HAN_TO_ZEN_MAP2.put('ﾌ', 'フ');
		HAN_TO_ZEN_MAP2.put('ﾍ', 'ヘ');
		HAN_TO_ZEN_MAP2.put('ﾎ', 'ホ');
		HAN_TO_ZEN_MAP2.put('ﾏ', 'マ');
		HAN_TO_ZEN_MAP2.put('ﾐ', 'ミ');
		HAN_TO_ZEN_MAP2.put('ﾑ', 'ム');
		HAN_TO_ZEN_MAP2.put('ﾒ', 'メ');
		HAN_TO_ZEN_MAP2.put('ﾓ', 'モ');
		HAN_TO_ZEN_MAP2.put('ﾔ', 'ヤ');
		HAN_TO_ZEN_MAP2.put('ﾕ', 'ユ');
		HAN_TO_ZEN_MAP2.put('ﾖ', 'ヨ');
		HAN_TO_ZEN_MAP2.put('ﾗ', 'ラ');
		HAN_TO_ZEN_MAP2.put('ﾘ', 'リ');
		HAN_TO_ZEN_MAP2.put('ﾙ', 'ル');
		HAN_TO_ZEN_MAP2.put('ﾚ', 'レ');
		HAN_TO_ZEN_MAP2.put('ﾛ', 'ロ');
		HAN_TO_ZEN_MAP2.put('ﾜ', 'ワ');
		HAN_TO_ZEN_MAP2.put('ｦ', 'ヲ');
		HAN_TO_ZEN_MAP2.put('ﾝ', 'ン');
		HAN_TO_ZEN_MAP2.put('ｴ', 'ヱ');
		HAN_TO_ZEN_MAP2.put('ｧ', 'ァ');
		HAN_TO_ZEN_MAP2.put('ｨ', 'ィ');
		HAN_TO_ZEN_MAP2.put('ｩ', 'ゥ');
		HAN_TO_ZEN_MAP2.put('ｪ', 'ェ');
		HAN_TO_ZEN_MAP2.put('ｫ', 'ォ');
		HAN_TO_ZEN_MAP2.put('ｯ', 'ッ');
		HAN_TO_ZEN_MAP2.put('ｬ', 'ャ');
		HAN_TO_ZEN_MAP2.put('ｭ', 'ュ');
		HAN_TO_ZEN_MAP2.put('ｮ', 'ョ');
		HAN_TO_ZEN_MAP2.put('ｰ', 'ー');
		HAN_TO_ZEN_MAP2.put('A', 'Ａ');
		HAN_TO_ZEN_MAP2.put('B', 'Ｂ');
		HAN_TO_ZEN_MAP2.put('C', 'Ｃ');
		HAN_TO_ZEN_MAP2.put('D', 'Ｄ');
		HAN_TO_ZEN_MAP2.put('E', 'Ｅ');
		HAN_TO_ZEN_MAP2.put('F', 'Ｆ');
		HAN_TO_ZEN_MAP2.put('G', 'Ｇ');
		HAN_TO_ZEN_MAP2.put('H', 'Ｈ');
		HAN_TO_ZEN_MAP2.put('I', 'Ｉ');
		HAN_TO_ZEN_MAP2.put('J', 'Ｊ');
		HAN_TO_ZEN_MAP2.put('K', 'Ｋ');
		HAN_TO_ZEN_MAP2.put('L', 'Ｌ');
		HAN_TO_ZEN_MAP2.put('M', 'Ｍ');
		HAN_TO_ZEN_MAP2.put('N', 'Ｎ');
		HAN_TO_ZEN_MAP2.put('O', 'Ｏ');
		HAN_TO_ZEN_MAP2.put('P', 'Ｐ');
		HAN_TO_ZEN_MAP2.put('Q', 'Ｑ');
		HAN_TO_ZEN_MAP2.put('R', 'Ｒ');
		HAN_TO_ZEN_MAP2.put('S', 'Ｓ');
		HAN_TO_ZEN_MAP2.put('T', 'Ｔ');
		HAN_TO_ZEN_MAP2.put('U', 'Ｕ');
		HAN_TO_ZEN_MAP2.put('V', 'Ｖ');
		HAN_TO_ZEN_MAP2.put('W', 'Ｗ');
		HAN_TO_ZEN_MAP2.put('X', 'Ｘ');
		HAN_TO_ZEN_MAP2.put('Y', 'Ｙ');
		HAN_TO_ZEN_MAP2.put('Z', 'Ｚ');
		HAN_TO_ZEN_MAP2.put('a', 'ａ');
		HAN_TO_ZEN_MAP2.put('b', 'ｂ');
		HAN_TO_ZEN_MAP2.put('c', 'ｃ');
		HAN_TO_ZEN_MAP2.put('d', 'ｄ');
		HAN_TO_ZEN_MAP2.put('e', 'ｅ');
		HAN_TO_ZEN_MAP2.put('f', 'ｆ');
		HAN_TO_ZEN_MAP2.put('g', 'ｇ');
		HAN_TO_ZEN_MAP2.put('h', 'ｈ');
		HAN_TO_ZEN_MAP2.put('i', 'ｉ');
		HAN_TO_ZEN_MAP2.put('j', 'ｊ');
		HAN_TO_ZEN_MAP2.put('k', 'ｋ');
		HAN_TO_ZEN_MAP2.put('l', 'ｌ');
		HAN_TO_ZEN_MAP2.put('m', 'ｍ');
		HAN_TO_ZEN_MAP2.put('n', 'ｎ');
		HAN_TO_ZEN_MAP2.put('o', 'ｏ');
		HAN_TO_ZEN_MAP2.put('p', 'ｐ');
		HAN_TO_ZEN_MAP2.put('q', 'ｑ');
		HAN_TO_ZEN_MAP2.put('r', 'ｒ');
		HAN_TO_ZEN_MAP2.put('s', 'ｓ');
		HAN_TO_ZEN_MAP2.put('t', 'ｔ');
		HAN_TO_ZEN_MAP2.put('u', 'ｕ');
		HAN_TO_ZEN_MAP2.put('v', 'ｖ');
		HAN_TO_ZEN_MAP2.put('w', 'ｗ');
		HAN_TO_ZEN_MAP2.put('x', 'ｘ');
		HAN_TO_ZEN_MAP2.put('y', 'ｙ');
		HAN_TO_ZEN_MAP2.put('z', 'ｚ');
		HAN_TO_ZEN_MAP2.put('0', '０');
		HAN_TO_ZEN_MAP2.put('1', '１');
		HAN_TO_ZEN_MAP2.put('2', '２');
		HAN_TO_ZEN_MAP2.put('3', '３');
		HAN_TO_ZEN_MAP2.put('4', '４');
		HAN_TO_ZEN_MAP2.put('5', '５');
		HAN_TO_ZEN_MAP2.put('6', '６');
		HAN_TO_ZEN_MAP2.put('7', '７');
		HAN_TO_ZEN_MAP2.put('8', '８');
		HAN_TO_ZEN_MAP2.put('9', '９');
		HAN_TO_ZEN_MAP2.put('/', '／');
		HAN_TO_ZEN_MAP2.put(';', '；');
		HAN_TO_ZEN_MAP2.put(':', '：');
		HAN_TO_ZEN_MAP2.put('(', '（');
		HAN_TO_ZEN_MAP2.put(')', '）');
		HAN_TO_ZEN_MAP2.put('<', '＜');
		HAN_TO_ZEN_MAP2.put('>', '＞');
		HAN_TO_ZEN_MAP2.put('{', '｛');
		HAN_TO_ZEN_MAP2.put('}', '｝');
		HAN_TO_ZEN_MAP2.put('｢', '「');
		HAN_TO_ZEN_MAP2.put('｣', '」');
		HAN_TO_ZEN_MAP2.put('[', '［');
		HAN_TO_ZEN_MAP2.put(']', '］');
		HAN_TO_ZEN_MAP2.put('､', '、');
		HAN_TO_ZEN_MAP2.put('｡', '。');
		HAN_TO_ZEN_MAP2.put(',', '，');
		HAN_TO_ZEN_MAP2.put('.', '．');
		HAN_TO_ZEN_MAP2.put('#', '＃');
		HAN_TO_ZEN_MAP2.put('$', '＄');
		HAN_TO_ZEN_MAP2.put('%', '％');
		HAN_TO_ZEN_MAP2.put('&', '＆');
		HAN_TO_ZEN_MAP2.put('=', '＝');
		HAN_TO_ZEN_MAP2.put('+', '＋');
		HAN_TO_ZEN_MAP2.put('-', '－');
		HAN_TO_ZEN_MAP2.put('@', '＠');
		HAN_TO_ZEN_MAP2.put('\\', '￥');
		HAN_TO_ZEN_MAP2.put('*', '＊');
		HAN_TO_ZEN_MAP2.put('|', '｜');
		HAN_TO_ZEN_MAP2.put('^', '＾');
		HAN_TO_ZEN_MAP2.put('\'', '’');
		HAN_TO_ZEN_MAP2.put('"', '”');
		HAN_TO_ZEN_MAP2.put('?', '？');
		HAN_TO_ZEN_MAP2.put('!', '！');
		HAN_TO_ZEN_MAP2.put('･', '・');
		HAN_TO_ZEN_MAP2.put(' ', '　');
		HAN_TO_ZEN_MAP2.put('ﾞ', '゛');
		HAN_TO_ZEN_MAP2.put('ﾟ', '゜');

		/*
		 * ひらがなからカタカナへの変換Map
		 */
		HIRA_TO_KANA_MAP = new HashMap<Character, Character>();
		HIRA_TO_KANA_MAP.put('あ', 'ア');
		HIRA_TO_KANA_MAP.put('い', 'イ');
		HIRA_TO_KANA_MAP.put('う', 'ウ');
		HIRA_TO_KANA_MAP.put('え', 'エ');
		HIRA_TO_KANA_MAP.put('お', 'オ');
		HIRA_TO_KANA_MAP.put('か', 'カ');
		HIRA_TO_KANA_MAP.put('き', 'キ');
		HIRA_TO_KANA_MAP.put('く', 'ク');
		HIRA_TO_KANA_MAP.put('け', 'ケ');
		HIRA_TO_KANA_MAP.put('こ', 'コ');
		HIRA_TO_KANA_MAP.put('さ', 'サ');
		HIRA_TO_KANA_MAP.put('し', 'シ');
		HIRA_TO_KANA_MAP.put('す', 'ス');
		HIRA_TO_KANA_MAP.put('せ', 'セ');
		HIRA_TO_KANA_MAP.put('そ', 'ソ');
		HIRA_TO_KANA_MAP.put('た', 'タ');
		HIRA_TO_KANA_MAP.put('ち', 'チ');
		HIRA_TO_KANA_MAP.put('つ', 'ツ');
		HIRA_TO_KANA_MAP.put('て', 'テ');
		HIRA_TO_KANA_MAP.put('と', 'ト');
		HIRA_TO_KANA_MAP.put('な', 'ナ');
		HIRA_TO_KANA_MAP.put('に', 'ニ');
		HIRA_TO_KANA_MAP.put('ぬ', 'ヌ');
		HIRA_TO_KANA_MAP.put('ね', 'ネ');
		HIRA_TO_KANA_MAP.put('の', 'ノ');
		HIRA_TO_KANA_MAP.put('は', 'ハ');
		HIRA_TO_KANA_MAP.put('ひ', 'ヒ');
		HIRA_TO_KANA_MAP.put('ふ', 'フ');
		HIRA_TO_KANA_MAP.put('へ', 'ヘ');
		HIRA_TO_KANA_MAP.put('ほ', 'ホ');
		HIRA_TO_KANA_MAP.put('ま', 'マ');
		HIRA_TO_KANA_MAP.put('み', 'ミ');
		HIRA_TO_KANA_MAP.put('む', 'ム');
		HIRA_TO_KANA_MAP.put('め', 'メ');
		HIRA_TO_KANA_MAP.put('も', 'モ');
		HIRA_TO_KANA_MAP.put('や', 'ヤ');
		HIRA_TO_KANA_MAP.put('ゆ', 'ユ');
		HIRA_TO_KANA_MAP.put('よ', 'ヨ');
		HIRA_TO_KANA_MAP.put('ら', 'ラ');
		HIRA_TO_KANA_MAP.put('り', 'リ');
		HIRA_TO_KANA_MAP.put('る', 'ル');
		HIRA_TO_KANA_MAP.put('れ', 'レ');
		HIRA_TO_KANA_MAP.put('ろ', 'ロ');
		HIRA_TO_KANA_MAP.put('わ', 'ワ');
		HIRA_TO_KANA_MAP.put('を', 'ヲ');
		HIRA_TO_KANA_MAP.put('ん', 'ン');
		HIRA_TO_KANA_MAP.put('が', 'ガ');
		HIRA_TO_KANA_MAP.put('ぎ', 'ギ');
		HIRA_TO_KANA_MAP.put('ぐ', 'グ');
		HIRA_TO_KANA_MAP.put('げ', 'ゲ');
		HIRA_TO_KANA_MAP.put('ご', 'ゴ');
		HIRA_TO_KANA_MAP.put('ざ', 'ザ');
		HIRA_TO_KANA_MAP.put('じ', 'ジ');
		HIRA_TO_KANA_MAP.put('ず', 'ズ');
		HIRA_TO_KANA_MAP.put('ぜ', 'ゼ');
		HIRA_TO_KANA_MAP.put('ぞ', 'ゾ');
		HIRA_TO_KANA_MAP.put('だ', 'ダ');
		HIRA_TO_KANA_MAP.put('ぢ', 'ヂ');
		HIRA_TO_KANA_MAP.put('づ', 'ヅ');
		HIRA_TO_KANA_MAP.put('で', 'デ');
		HIRA_TO_KANA_MAP.put('ど', 'ド');
		HIRA_TO_KANA_MAP.put('ば', 'バ');
		HIRA_TO_KANA_MAP.put('び', 'ビ');
		HIRA_TO_KANA_MAP.put('ぶ', 'ブ');
		HIRA_TO_KANA_MAP.put('べ', 'ベ');
		HIRA_TO_KANA_MAP.put('ぼ', 'ボ');
		HIRA_TO_KANA_MAP.put('ぱ', 'パ');
		HIRA_TO_KANA_MAP.put('ぴ', 'ピ');
		HIRA_TO_KANA_MAP.put('ぷ', 'プ');
		HIRA_TO_KANA_MAP.put('ぺ', 'ペ');
		HIRA_TO_KANA_MAP.put('ぽ', 'ポ');
		HIRA_TO_KANA_MAP.put('ぁ', 'ァ');
		HIRA_TO_KANA_MAP.put('ぃ', 'ィ');
		HIRA_TO_KANA_MAP.put('ぅ', 'ゥ');
		HIRA_TO_KANA_MAP.put('ぇ', 'ェ');
		HIRA_TO_KANA_MAP.put('ぉ', 'ォ');
		HIRA_TO_KANA_MAP.put('っ', 'ッ');
		HIRA_TO_KANA_MAP.put('ゃ', 'ャ');
		HIRA_TO_KANA_MAP.put('ゅ', 'ュ');
		HIRA_TO_KANA_MAP.put('ょ', 'ョ');
	}

	/**
	 * インスタンス化されないようにするための内部コンストラクタ.
	 */
	private StringUtil() {
	}

	/**
	 * 文字列置換.
	 * <p>
	 * 含まれるキーワードを全置換する.
	 * </p>
	 * 
	 * @param text
	 *            変換対象文字列
	 * @param before
	 *            置換対象キーワード
	 * @param after
	 *            置換する文字列
	 * @return 置換した文字列を返す.
	 */
	public static String replaceAll(String text, String before, String after) {
		if (text == null || text.indexOf(before) < 0) {
			// 元のStringがnull、もしくはキーワードが存在しない場合はそのまま返す
			return text;
		}
		StringBuilder sb = new StringBuilder(text);
		int index = 0;
		while (true) {
			// キーワードを探す
			index = sb.indexOf(before, index);
			if (index < 0) {
				// キーワードが無ければおしまい
				break;
			}
			// 置換実行
			sb.replace(index, index + before.length(), after);
			index = index + after.length();
		}
		return sb.toString();
	}

	/**
	 * 文字列置換.
	 * <p>
	 * 前から検索して初めに見つかったキーワードのみを置換する.
	 * </p>
	 * 
	 * @param text
	 *            変換対象文字列
	 * @param before
	 *            置換対象キーワード
	 * @param after
	 *            置換する文字列
	 * @return 置換した文字列を返す.
	 */
	public static String replaceOne(String text, String before, String after) {
		StringBuilder sb = new StringBuilder(text);
		int index = 0;
		// キーワードを探す
		index = sb.indexOf(before, index);
		if (index >= 0) {
			// キーワードがあれば置換実行
			sb.replace(index, index + before.length(), after);
		}
		return sb.toString();
	}

	/**
	 * 改行コードを除去する.
	 * 
	 * @param text
	 * @return 改行コードを除去した文字列
	 */
	public static String removeLine(String text) {
		String newStr = text;
		newStr = replaceAll(newStr, "\r\n", "");
		newStr = replaceAll(newStr, "\n", "");
		newStr = replaceAll(newStr, "\r", "");
		return newStr;
	}

	/**
	 * 全角文字を半角文字へ変換する.
	 * 
	 * @param text
	 *            元の文字列
	 * @return 変換された文字列
	 */
	public static String zenToHan(String text) {
		if (text == null) {
			return null;
		}
		StringBuilder convText = new StringBuilder();
		char[] cArray = text.toCharArray();
		for (int i = 0; i < cArray.length; i++) {
			// 1文字ずつ取得
			Object c;
			if (ZEN_TO_HAN_MAP1.containsKey(cArray[i])) {
				// 濁音と半濁音を変換
				c = ZEN_TO_HAN_MAP1.get(cArray[i]);
			} else if (ZEN_TO_HAN_MAP2.containsKey(cArray[i])) {
				// その他の文字を変換
				c = ZEN_TO_HAN_MAP2.get(cArray[i]);
			} else {
				// 無変換
				c = cArray[i];
			}
			convText.append(c);
		}
		return convText.toString();
	}

	/**
	 * 半角文字を全角文字へ変換する.
	 * 
	 * @param text
	 *            元の文字列
	 * @return 変換された文字列
	 */
	public static String hanToZen(String text) {
		if (text == null) {
			return null;
		}
		StringBuilder convText = new StringBuilder();
		char[] cArray = text.toCharArray();
		for (int i = 0; i < cArray.length; i++) {
			// 1文字ずつ取得
			Object c = null;
			if (i + 1 < cArray.length
					&& (cArray[i + 1] == HAN_MARK1 || cArray[i + 1] == HAN_MARK2)) {
				// 次の文字が濁点もしくは半濁点
				c = HAN_TO_ZEN_MAP1.get(String.valueOf(new char[] { cArray[i],
						cArray[i + 1] }));
			}
			if (c != null) {
				// 濁点もしくは半濁点で変換されたので、index番号を一つ進める
				i++;
			} else {
				// その他の文字での変換を行う
				if (HAN_TO_ZEN_MAP2.containsKey(cArray[i])) {
					// その他の文字を変換
					c = HAN_TO_ZEN_MAP2.get(cArray[i]);
				} else {
					// 無変換
					c = cArray[i];
				}
			}
			convText.append(c);
		}
		return convText.toString();
	}

	/**
	 * 全角ひらがなを全角カタカナへ変換する.
	 * 
	 * @param text
	 *            元の文字列
	 * @return 変換された文字列
	 */
	public static String hiraToKana(String text) {
		StringBuilder convText = new StringBuilder();
		char[] cArray = text.toCharArray();
		for (int i = 0; i < cArray.length; i++) {
			// 1文字ずつ取得
			Object c;
			if (HIRA_TO_KANA_MAP.containsKey(cArray[i])) {
				// 変換
				c = HIRA_TO_KANA_MAP.get(cArray[i]);
			} else {
				c = cArray[i];
			}
			convText.append(c);
		}
		return convText.toString();
	}

	/**
	 * 文字列を指定バイト数でカットする.
	 * <p>
	 * 全角文字にも対応.<br>
	 * バイト数算出のための文字コードは固定でShift_JISを使用しているため、<br>
	 * 半角英数などは1バイト、ひらがなや漢字などは2バイトで算出される.
	 * </p>
	 * 
	 * @param text
	 *            カット前の文字列
	 * @param maxByte
	 *            最大バイト数
	 * @return カット後の文字列
	 */
	public static String cutString(String text, int maxByte) {
		return cutString(text, maxByte, null);
	}

	/**
	 * 文字列を指定バイト数でカットする.
	 * <p>
	 * カットされた場合の末尾文字列指定版.<br>
	 * <br>
	 * バイト数算出のための文字コードは固定でShift_JISを使用しているため、<br>
	 * 半角英数などは1バイト、ひらがなや漢字などは2バイトで算出される.
	 * </p>
	 * 
	 * @param text
	 *            カット前の文字列
	 * @param maxByte
	 *            最大バイト数
	 * @param postfix
	 *            カットされた場合に末尾に付加する文字列
	 * @return カット後の文字列
	 */
	public static String cutString(String text, int maxByte, String postfix) {
		StringBuilder convText = new StringBuilder();
		// 1文字ずつ連結していく
		char[] cArray = text.toCharArray();
		int bytes = 0;
		for (int i = 0; i < cArray.length; i++) {
			int byteCount = 1;
			try {
				byteCount = Character.toString(cArray[i]).getBytes("Shift_JIS").length;
			} catch (UnsupportedEncodingException e) {
				// Shift_JIS固定なのでExceptionになることはあり得ないため何もしない
			}
			bytes = bytes + byteCount;
			if (bytes > maxByte) {
				// 指定バイト数を超えたら、末尾文字列を付加して終了
				convText.append(postfix == null ? "" : postfix);
				break;
			}
			convText.append(cArray[i]);
		}
		return convText.toString();
	}

	/**
	 * 改行区切りの文字列をString配列に分割する.
	 * 
	 * @param text
	 *            元の文字列
	 * @return 区切られたString配列
	 */
	public static String[] separateByLine(String text) {
		if (text == null) {
			return null;
		}
		// 区切り文字
		String separator = "\t";
		// 元々タブが入っていれば全て消す
		text = StringUtil.replaceAll(text, separator, "");
		// 改行コードをタブ区切りにする
		text = StringUtil.replaceAll(text, "\r\n", separator);
		text = StringUtil.replaceAll(text, "\r", separator);
		text = StringUtil.replaceAll(text, "\n", separator);
		// タブ区切りを分割する
		StringTokenizer st = new StringTokenizer(text, separator);
		String[] strArray = new String[st.countTokens()];
		for (int i = 0; i < strArray.length; i++) {
			strArray[i] = st.nextToken();
		}
		return strArray;
	}

	/**
	 * 半角英数で構成されたランダムな文字列を生成.
	 * 
	 * @param size
	 *            文字長
	 * @return ランダム文字列
	 */
	public static String createRandomText(int size) {
		StringBuilder text = new StringBuilder();
		for (int i = 0; i < size; i++) {
			int index = (int) (Math.random() * HAN_EISU.length);
			text.append(HAN_EISU[index]);
		}
		return text.toString();
	}

	/**
	 * 半角数字で構成されたランダムな文字列を生成.
	 * 
	 * @param size
	 *            文字長
	 * @return ランダム文字列
	 */
	public static String createRandomNumber(int size) {
		StringBuilder text = new StringBuilder();
		for (int i = 0; i < size; i++) {
			int index = (int) (Math.random() * NUMBER.length);
			text.append(NUMBER[index]);
		}
		return text.toString();
	}

	/**
	 * 文字列の分割.
	 * 
	 * @param text
	 *            元のテキスト
	 * @param separator
	 *            区切り文字列
	 * @return 分割されたString配列
	 */
	public static String[] split(String text, String separator) {
		StringTokenizer st = new StringTokenizer(text, separator);
		String[] result = new String[st.countTokens()];
		for (int i = 0; i < result.length; i++) {
			result[i] = st.nextToken();
		}
		return result;
	}

	/**
	 * 文字列の連結.
	 * 
	 * @param array
	 *            連結対象の配列
	 * @param separator
	 *            区切り文字列
	 * @return 連結された文字列
	 */
	public static String join(Object[] array, String separator) {
		if (array == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			if (i > 0) {
				sb.append(separator);
			}
			sb.append(array[i]);
		}
		return sb.toString();
	}

	/**
	 * 文字列の連結.
	 * 
	 * @param iterable
	 *            連結対象のリスト
	 * @param separator
	 *            区切り文字列
	 * @return 連結された文字列
	 */
	public static String join(Iterable<?> iterable, String separator) {
		if (iterable == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		Iterator<?> iterator = iterable.iterator();
		for (int i = 0; iterator.hasNext(); i++) {
			if (i > 0) {
				sb.append(separator);
			}
			sb.append(iterator.next());
		}
		return sb.toString();
	}

	/**
	 * サロゲート文字を除去する.
	 * 
	 * @param text
	 *            元のテキスト
	 * @return サロゲート文字が除去されたテキスト
	 */
	public static String removeSurrogate(String text) {
		StringBuilder resultText = new StringBuilder();
		for (char c : text.toCharArray()) {
			if (!Character.isHighSurrogate(c) && !Character.isLowSurrogate(c)) {
				// サロゲートでなければ連結
				resultText.append(c);
			}
		}
		return resultText.toString();
	}
}
