package jp.kt.mail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import jp.kt.exception.KtException;
import jp.kt.internet.MimeType;
import jp.kt.prop.KtProperties;
import jp.kt.tool.StringUtil;
import jp.kt.tool.Validator;

/**
 * JavaMailを使ってメール送信するクラス.
 *
 * @author tatsuya.kumon
 */
public class MailSender {
	/** テキスト用のContent-Transfer-Encoding */
	private static final String CONTENT_TRANSFER_ENCODING_TEXT = "7bit";

	/** HTML用のContent-Transfer-Encoding */
	private static final String CONTENT_TRANSFER_ENCODING_HTML = "quoted-printable";

	/** 件名 */
	private String subject;

	/** テキスト本文 */
	private String textBody;

	/** HTML本文（インライン画像含む） */
	private HtmlBody htmlBody;

	/** SMTPサーバホスト名 */
	private String smtpHost;

	/** SMTPサーバポート番号 */
	private int smtpPort;

	/** Fromアドレス */
	private String fromAddress;

	/** From名称 */
	private String fromName;

	/** Toアドレスリスト */
	private List<InternetAddress> toAddressList;

	/** Ccアドレスリスト */
	private List<InternetAddress> ccAddressList;

	/** Bccアドレスリスト */
	private List<InternetAddress> bccAddressList;

	/** ReplyToアドレスリスト */
	private List<InternetAddress> replyToList;

	/** 添付ファイル */
	private List<AttachmentFile> attachmentFileList;

	/** エンコード */
	private String encoding = "iso-2022-jp";

	/** 重要度 */
	private Priority priority;

	/** 追加メールヘッダのリスト */
	private List<Header> headerList;

	/** Return-Path */
	private String returnPath;

	/** SMTP認証設定 */
	private Authenticator authenticator;

	/**
	 * プロパティファイルのデフォルト設定を適用するコンストラクタ.
	 */
	public MailSender() {
		KtProperties aProps = KtProperties.getInstance();
		String smtpHost = aProps.getString("kt.core.mailsend.smtpHost");
		int smtpPort = aProps.getInt("kt.core.mailsend.smtpPort");
		String fromAddress = aProps.getString("kt.core.mailsend.fromAddress");
		init(smtpHost, smtpPort, fromAddress);
	}

	/**
	 * コンストラクタ.
	 *
	 * @param smtpHost
	 *            SMTPサーバホスト名
	 * @param smtpPort
	 *            SMTPサーバポート番号
	 * @param fromAddress
	 *            Fromアドレス
	 */
	public MailSender(String smtpHost, int smtpPort, String fromAddress) {
		init(smtpHost, smtpPort, fromAddress);
	}

	/**
	 * コンストラクタから呼ばれる初期設定.
	 *
	 * @param smtpHost
	 *            SMTPサーバホスト名
	 * @param smtpPort
	 *            SMTPサーバポート番号
	 * @param fromAddress
	 *            Fromアドレス
	 */
	private void init(String smtpHost, int smtpPort, String fromAddress) {
		this.smtpHost = smtpHost;
		this.smtpPort = smtpPort;
		this.fromAddress = fromAddress;
		// 添付ファイルリストをインスタンス化
		this.attachmentFileList = new ArrayList<AttachmentFile>();
		// アドレスリストを初期化
		this.clearToCcBccAddress();
		// textBodyは空文字で初期化（nullだと送信処理でエラーになる可能性があるため）
		this.textBody = "";
	}

	/**
	 * Toアドレス、Ccアドレス、Bccアドレスをクリアする.
	 */
	public void clearToCcBccAddress() {
		// 早くガベジコレクションされるよう、一旦nullを入れる
		this.toAddressList = null;
		this.ccAddressList = null;
		this.bccAddressList = null;
		this.replyToList = null;
		// リストをインスタンス化
		this.toAddressList = new ArrayList<InternetAddress>();
		this.ccAddressList = new ArrayList<InternetAddress>();
		this.bccAddressList = new ArrayList<InternetAddress>();
		this.replyToList = new ArrayList<InternetAddress>();
	}

	/**
	 * SMTPサーバをセットする.
	 *
	 * @param smtpHost
	 *            SMTPサーバホスト名
	 * @param smtpPort
	 *            SMTPサーバポート番号
	 */
	public void setSmtpServer(String smtpHost, int smtpPort) {
		this.smtpHost = smtpHost;
		this.smtpPort = smtpPort;
	}

	/**
	 * Fromアドレスをセットする.
	 *
	 * @param fromAddress
	 *            Fromメールアドレス
	 */
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	/**
	 * FromNameをセットする.
	 *
	 * @param fromName
	 *            From名
	 */
	public void setFromName(String fromName) {
		this.fromName = StringUtilForMail.convertMapping(fromName);
	}

	/**
	 * 件名をセットする.
	 *
	 * @param subject
	 *            件名
	 */
	public void setSubject(String subject) {
		this.subject = StringUtilForMail.convertMapping(subject);
	}

	/**
	 * テキスト本文をセットする.
	 *
	 * @param textBody
	 *            テキスト本文
	 */
	public void setBody(String textBody) {
		this.textBody = StringUtilForMail.convertMapping(textBody);
	}

	/**
	 * HTML本文をセットする.
	 *
	 * @param htmlBody
	 *            HTML本文（インライン画像を含む）
	 */
	public void setBody(HtmlBody htmlBody) {
		this.htmlBody = htmlBody;
	}

	/**
	 * Toアドレスを追加する.
	 *
	 * @param address
	 *            追加するメールアドレス
	 * @throws Exception
	 */
	public void addToAddress(String address) throws Exception {
		if (address != null && !address.equals("")) {
			toAddressList.add(new InternetAddress(address));
		}
	}

	/**
	 * Ccアドレスを追加する.
	 *
	 * @param address
	 *            追加するメールアドレス
	 * @throws Exception
	 */
	public void addCcAddress(String address) throws Exception {
		if (address != null && !address.equals("")) {
			ccAddressList.add(new InternetAddress(address));
		}
	}

	/**
	 * Bccアドレスを追加する.
	 *
	 * @param address
	 *            追加するメールアドレス
	 * @throws Exception
	 */
	public void addBccAddress(String address) throws Exception {
		if (address != null && !address.equals("")) {
			bccAddressList.add(new InternetAddress(address));
		}
	}

	/**
	 * ReplyToアドレスを追加する.
	 *
	 * @param address
	 *            追加するメールアドレス
	 * @throws Exception
	 */
	public void addReplyToAddress(String address) throws Exception {
		if (address != null && !address.equals("")) {
			replyToList.add(new InternetAddress(address));
		}
	}

	/**
	 * 添付ファイルの追加.
	 * <p>
	 * 物理ファイル用.
	 * </p>
	 *
	 * @param filePath
	 *            ファイルのパス
	 * @throws IOException
	 */
	public void addAttachmentFile(String filePath) throws IOException {
		attachmentFileList.add(new AttachmentFile(filePath));
	}

	/**
	 * 添付ファイルの追加.
	 * <p>
	 * バイトデータ用.
	 * </p>
	 *
	 * @param fileData
	 *            ファイルのバイトデータ
	 * @param fileName
	 *            ファイル名
	 */
	public void addAttachmentFile(byte[] fileData, String fileName) {
		attachmentFileList.add(new AttachmentFile(fileData, fileName));
	}

	/**
	 * エンコーディングを指定する
	 *
	 * @param encoding
	 *            エンコーディング
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * 重要度を設定する.
	 *
	 * @param priority
	 *            重要度
	 */
	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	/**
	 * メールヘッダーの追加.
	 *
	 * @param name
	 *            ヘッダ名
	 * @param value
	 *            ヘッダ値
	 */
	public void addHeader(String name, String value) {
		if (this.headerList == null) {
			this.headerList = new ArrayList<Header>();
		}
		this.headerList.add(new Header(name, value));
	}

	/**
	 * Return－Pathの値をセットする.
	 *
	 * @param returnPath
	 *            Return-Pathの値
	 */
	public void setReturnPath(String returnPath) {
		this.returnPath = returnPath;
	}

	/**
	 * メール送信実行.
	 *
	 * @throws Exception
	 */
	public void send() throws Exception {
		MimeMessage message = createMessage();
		try {
			Transport.send(message);
		} catch (SendFailedException e) {
			// ログ出力文字列生成
			StringBuffer msg = new StringBuffer();
			msg.append(e.getClass().getName());
			msg.append(":");
			msg.append(StringUtil.removeLine(e.getMessage()));
			Address[] addresses = e.getInvalidAddresses();
			for (int i = 0; i < addresses.length; i++) {
				if (i == 0) {
					msg.append("[");
				} else {
					msg.append(",");
				}
				msg.append(addresses[i].toString());
				if (i == addresses.length - 1) {
					msg.append("]");
				}
			}
			// ApplicationExceptionをthrow
			throw new KtException("B052", msg.toString());
		}
	}

	/**
	 * メール内容を文字列で取得.
	 *
	 * @return メール内容
	 * @throws Exception
	 */
	public String getMailString() throws Exception {
		MimeMessage message = createMessage();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		message.writeTo(bos);
		bos.close();
		return bos.toString(encoding);
	}

	/**
	 * MimeMessage作成.
	 *
	 * @return MimeMessageオブジェクト
	 * @throws Exception
	 */
	private MimeMessage createMessage() throws Exception {
		// Propertiesの作成
		Properties p = new Properties();
		p.put("mail.from", fromAddress);
		p.put("mail.smtp.from", Validator.isEmpty(returnPath) ? fromAddress
				: returnPath);
		p.put("mail.smtp.host", smtpHost);
		p.put("mail.host", smtpHost);
		p.put("mail.smtp.port", String.valueOf(smtpPort));

		// Sessionの作成
		Session session;
		if (this.authenticator == null) {
			// SMTP認証なし
			session = Session.getInstance(p, null);
		} else {
			// SMTP認証あり
			p.put("mail.smtp.auth", "true");
			session = Session.getInstance(p, this.authenticator);
		}
		// MimeMessageの作成
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(fromAddress, fromName, encoding));
		message.setSubject(MimeUtility.encodeText(subject, encoding, "B"));
		message.setSentDate(new Date());
		message.setHeader("X-Mailer", "kt MailSender");

		// メールアドレスセット
		Address[] toAddresses = new Address[toAddressList.size()];
		toAddressList.toArray(toAddresses);
		message.addRecipients(Message.RecipientType.TO, toAddresses);
		Address[] ccAddresses = new Address[ccAddressList.size()];
		ccAddressList.toArray(ccAddresses);
		message.addRecipients(Message.RecipientType.CC, ccAddresses);
		Address[] bccAddresses = new Address[bccAddressList.size()];
		bccAddressList.toArray(bccAddresses);
		message.addRecipients(Message.RecipientType.BCC, bccAddresses);
		Address[] replyToAddresses = new Address[replyToList.size()];
		replyToList.toArray(replyToAddresses);
		message.setReplyTo(replyToAddresses);

		// 本文のセット
		setBody(message);

		// 送信日付を指定
		message.setSentDate(new Date());
		// 重要度を設定
		if (priority != null) {
			Map<String, String> header = priority.getHeader();
			for (String name : header.keySet()) {
				// ヘッダ値をセット
				message.setHeader(name, header.get(name));
			}
		}
		// その他ヘッダーを追加
		if (this.headerList != null) {
			for (Header header : headerList) {
				message.addHeader(header.getName(), header.getValue());
			}
		}
		return message;
	}

	/**
	 * 本文のセット.
	 *
	 * @param message
	 *            メッセージオブジェクト
	 * @throws Exception
	 */
	private void setBody(MimeMessage message) throws Exception {
		/*
		 * HTMLなし（テキストのみ）且つ添付なし
		 */
		if (htmlBody == null && attachmentFileList.size() == 0) {
			message.setText(textBody, encoding);
			message.setHeader("Content-Transfer-Encoding",
					CONTENT_TRANSFER_ENCODING_TEXT);
			return;
		}

		// これ以下はMultipart

		/*
		 * テキスト本文
		 */
		MimeBodyPart textPart = new MimeBodyPart();
		textPart.setText(textBody, encoding);
		textPart.setHeader("Content-Transfer-Encoding",
				CONTENT_TRANSFER_ENCODING_TEXT);

		/*
		 * HTMLなし（テキストのみ）且つ添付あり
		 */
		if (htmlBody == null && attachmentFileList.size() > 0) {
			Multipart mixedPart = new MimeMultipart("mixed");
			mixedPart.addBodyPart(textPart);
			// 添付ファイルを設定
			setAttachementFile(mixedPart, attachmentFileList);
			// マルチパートオブジェクトをメッセージに設定
			message.setContent(mixedPart);
			return;
		}

		// これ以下はHTMLがある

		/*
		 * HTML本文
		 */
		MimeBodyPart htmlPart = new MimeBodyPart();
		htmlPart.setContent(htmlBody.getHtml(), "text/html; charset="
				+ encoding);
		htmlPart.setHeader("Content-Transfer-Encoding",
				CONTENT_TRANSFER_ENCODING_HTML);
		/*
		 * テキスト本文とHTML本文をまとめるalternativeパート
		 */
		Multipart alternativePart = new MimeMultipart("alternative");
		alternativePart.addBodyPart(textPart);
		alternativePart.addBodyPart(htmlPart);

		// 最上層のマルチパートを定義
		Multipart mainPart = null;
		if (htmlBody.getInlineImageList().size() == 0) {
			/*
			 * インライン画像が無い場合（multipart/alternative）
			 */
			// 最上層マルチパートはalternative
			mainPart = alternativePart;
		} else {
			/*
			 * インライン画像がある場合（multipart/related）
			 */
			// relatedを生成
			Multipart relatedPart = new MimeMultipart("related");
			// alternativeを生成
			MimeBodyPart alternativeBodyPart = new MimeBodyPart();
			alternativeBodyPart.setContent(alternativePart);
			relatedPart.addBodyPart(alternativeBodyPart);
			// インライン画像をセット
			setAttachementFile(relatedPart, htmlBody.getInlineImageList());
			// 最上層マルチパートはrelated
			mainPart = relatedPart;
		}

		/*
		 * 添付ファイルがある場合の処理
		 */
		if (attachmentFileList.size() > 0) {
			// 最上層マルチパートの上にさらにmixedを付加する
			Multipart mixedPart = new MimeMultipart("mixed");
			MimeBodyPart mainBodyPart = new MimeBodyPart();
			mainBodyPart.setContent(mainPart);
			mixedPart.addBodyPart(mainBodyPart);
			// 添付ファイルを設定
			setAttachementFile(mixedPart, attachmentFileList);
			// マルチパートオブジェクトをメッセージに設定
			message.setContent(mixedPart);
			// 最上層マルチパートはmixed
			mainPart = mixedPart;
		}

		// MimeMessageにマルチパートをセット
		message.setContent(mainPart);
	}

	/**
	 * 添付ファイルをセットする.
	 *
	 * @param parentPart
	 *            添付ファイルをセットするマルチパート
	 * @throws Exception
	 */
	private void setAttachementFile(Multipart parentPart, List<?> fileList)
			throws Exception {
		for (Object o : fileList) {
			AttachmentFile attachmentFile = (AttachmentFile) o;
			MimeBodyPart imgPart = new MimeBodyPart();
			// MimeTypeをファイル名から取得
			String mimeType = MimeType
					.getMimeType(attachmentFile.getFileName());
			if (Validator.isEmpty(mimeType)) {
				// MimeTypeが取得できなかったら、固定値をセット
				mimeType = "application/octet-stream";
			}
			// ファイルデータをセット
			ByteArrayDataSource ds = new ByteArrayDataSource(
					attachmentFile.getFileData(), mimeType);
			imgPart.setDataHandler(new DataHandler(ds));
			// ファイル名をセット
			imgPart.setFileName(MimeUtility.encodeWord(attachmentFile
					.getFileName()));
			// インライン指定
			imgPart.setDisposition("inline");
			// cidのセット（インライン画像のみ）
			if (attachmentFile instanceof InlineImage) {
				InlineImage inlineImage = (InlineImage) attachmentFile;
				// cidは、<>を付けないと、au端末でインライン表示されない
				imgPart.setContentID("<" + inlineImage.getCid() + ">");
			}
			// Multipartオブジェクトにセット
			parentPart.addBodyPart(imgPart);
		}
	}

	/**
	 * SMTP認証設定.
	 *
	 * @param id
	 *            SMTP認証ID
	 * @param password
	 *            SMTP認証パスワード
	 */
	public void setAuth(final String id, final String password) {
		this.authenticator = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(id, password);
			}
		};
	}
}
