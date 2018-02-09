package com.example.demo.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * APIエラー例外.
 */
public class ApiException extends RuntimeException {
    /** エラーコード. */
    public enum ErrorCode {
        /** エラーコード(必須パラメータなし). */
        REQIRE_PARAMETER(1000),
        /** エラーコード(フォーマット不正). */
        PARAMETER_FORMAT(1001),
        /** エラーコード(登録データなし). */
        DATA_NOTHING(1002),
        /** エラーコード(その他エラー). */
        ERROR_OTHER(1003);

        /** Value. */
        private int mValue;

        /**
         * コンストラクタ.
         *
         * @param value
         *            Value.
         */
        ErrorCode(final int value) {
            mValue = value;
        }
    }

    /** エラーリスト. */
    private List<ApiError> mErrorList = new ArrayList<>();

    /**
     * コンストラクタ.
     *
     * @param errorCode
     *            エラーコード.
     * @param message
     *            エラーメッセージ.
     */
    public ApiException(final ErrorCode errorCode, final String message) {
        super("api error");
        addError(errorCode, message);
    }

    /**
     * コンストラクタ.
     */
    public ApiException() {
        super("api error");
    }

    /**
     * エラー追加.
     *
     * @param errorCode
     *            エラーコード.
     * @param message
     *            エラーメッセージ.
     */
    public void addError(final ErrorCode errorCode, final String message) {
        mErrorList.add(new ApiError(errorCode, message));
    }

    /**
     * 空判定
     *
     * @return true 空 false 左記以外.
     */
    public boolean isEmpty() {
        return mErrorList.isEmpty();
    }

    /**
     * エラーレスポンス生成.
     *
     * @return エラーレスポンス.
     */
    public List<Map<String, Object>> createErrorResponse() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ApiError error : mErrorList) {
            list.add(error.createErrorResponce());
        }
        return list;
    }

    /** エラーオブジェクト. */
    private class ApiError {
        /** エラーコード. */
        private ErrorCode mErrorCode;
        /** エラーメッセージ. */
        private String mMesssage;

        /**
         * コンストラクタ.
         *
         * @param errorCode
         *            エラーコード
         * @param message
         *            エラーメッセージ
         */
        private ApiError(final ErrorCode errorCode, final String message) {
            mErrorCode = errorCode;
            mMesssage = message;
        }

        /**
         * エラーレスポンス生成.
         *
         * @return エラーレスポンス.
         */
        private Map<String, Object> createErrorResponce() {
            Map<String, Object> error = new HashMap<String, Object>();
            error.put("error_code", mErrorCode.mValue);
            error.put("error_message", mMesssage);
            return error;
        }
    }
}
