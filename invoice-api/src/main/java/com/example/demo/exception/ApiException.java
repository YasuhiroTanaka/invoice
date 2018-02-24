package com.example.demo.exception;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.response.ApiError;

/**
 * APIエラー例外.
 */
public class ApiException extends RuntimeException {

    /** エラーリスト. */
    private List<ApiError> errorList = new ArrayList<>();

    /**
     * コンストラクタ.
     *
     * @param errorCode エラーコード.
     * @param message エラーメッセージ.
     */
    public ApiException(final int errorCode, final String message) {
        addError(new ApiError(errorCode, message));
    }

    /**
     * コンストラクタ.
     * @param errorList エラーリスト.
     */
    public ApiException(final List<ApiError> errorList) {
        addError(errorList);
    }

    /**
     * エラー追加.
     * @param error error.
     */
    public void addError(final ApiError error) {
        errorList.add(error);
    }

    /**
     * エラー追加.
     * @param errorList エラーリスト.
     */
    public void addError(final List<ApiError> errorList) {
        this.errorList.addAll(errorList);
    }

    /**
     * エラーリスト取得.
     *
     * @return エラーリスト.
     */
    public List<ApiError> getApiErrorList() {
        return errorList;
    }
}
