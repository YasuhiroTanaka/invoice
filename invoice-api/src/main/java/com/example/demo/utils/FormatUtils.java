package com.example.demo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatUtils {
    /** Date型Stringフォーマット. */
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    /** DateTime型Stringフォーマット. */
    private static final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    static {
        DATE_FORMAT.setLenient(false);
        DATETIME_FORMAT.setLenient(false);
    }

    /**
     * 日付文字列パース.
     *
     * @param dateString 日付文字列.
     * @return Date型.
     * @throws ParseException 失敗.
     */
    public static Date parseDateString(final String dateString) throws ParseException {
        return DATE_FORMAT.parse(dateString);
    }

    /**
     * 日時文字列パース.
     *
     * @param dateTimeString 日時文字列.
     * @return Date型.
     * @throws ParseException 失敗.
     */
    public static Date parseDateTimeString(final String dateTimeString) throws ParseException {
        return DATETIME_FORMAT.parse(dateTimeString);
    }

    /**
     * 文字列チェック.
     *
     * @param string 文字列.
     * @return true 問題なし false 左記以外.
     */
    public static boolean isFineString(final String string) {
        return string != null && !string.isEmpty();
    }

    /**
     * Date型文字列チェック.
     *
     * @param string 文字列.
     * @return true 問題なし false 左記以外.
     */
    public static boolean isFineDateString(final String string) {
        try {
            return parseDateString(string) != null;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * DateTime型文字列チェック.
     *
     * @param string 文字列.
     * @return true 問題なし false 左記以外.
     */
    public static boolean isFineDateTimeString(final String string) {
        try {
            return parseDateTimeString(string) != null;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * コンストラクタ.
     */
    private FormatUtils() {
    }
}
