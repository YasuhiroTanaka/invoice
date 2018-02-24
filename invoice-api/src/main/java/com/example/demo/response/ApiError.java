package com.example.demo.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.ToString;

/** エラー. */
@ToString
public class ApiError {
    /** エラーコード(必須パラメータなし). */
    public static final int REQIRE_PARAMETER = 1000;
    /** エラーコード(フォーマット不正). */
    public static final int PARAMETER_FORMAT = 1001;
    /** エラーコード(登録データなし). */
    public static final int DATA_NOTHING = 1002;
    /** エラーコード(その他エラー). */
    public static final int ERROR_OTHER = 1003;

    /** code. */
    @JsonProperty("error_code")
    public int errorCode;
    /** message. */
    @JsonProperty("error_message")
    public String errorMessage;


    /**
     * コンストラクタ.
     * @param errorCode エラーコード.
     * @param errorMessage エラーメッセージ.
     */
    public ApiError(@JsonProperty("error_code") final int errorCode, @JsonProperty("error_message") final String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
