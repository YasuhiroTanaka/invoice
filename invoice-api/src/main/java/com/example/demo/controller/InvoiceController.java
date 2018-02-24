package com.example.demo.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.InvoiceEntity;
import com.example.demo.exception.ApiException;
import com.example.demo.form.InvoiceRequestForm;
import com.example.demo.response.ApiError;
import com.example.demo.service.InvoiceService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

/**
 * リクエストコントローラ.
 */
@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {
    /** InvoiceService. */
    @Autowired
    private InvoiceService invoiceService;

    /**
     * 請求書新規登録.
     *
     * @return 登録した請求書管理番号.
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public int postInvoice(@RequestBody InvoiceRequestForm  from) {
        return invoiceService.reigsterInvoice(from);
    }

    /**
     * 請求書一覧取得.
     *
     * @return 請求書一覧.
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<InvoiceEntity> getAllInvoice() {
        return invoiceService.getAllInvoice();
    }

    /**
     * 請求書取得.
     *
     * @param id 請求書管理番号.
     * @return 請求書.
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    public InvoiceEntity getInvoice(@PathVariable final String id) {
        InvoiceEntity invoice = null;
        try {
            int invoiceId = Integer.valueOf(id);
            invoice = invoiceService.getInvoice(invoiceId);
            if (invoice == null) {
                throw new ApiException(ApiError.DATA_NOTHING, "[" + id + "] record is nothing.");
            }

        } catch (NumberFormatException e) {
            throw new ApiException(ApiError.PARAMETER_FORMAT, e.getMessage());
        }
        return invoice;
    }

    /**
     * APIエラーハンドリング(400).
     *
     * @param error error.
     * @return error response.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
        ApiException.class,
        HttpMessageNotReadableException.class,
        InvalidFormatException.class,
        JsonParseException.class})
    @ResponseBody
    public List<ApiError> handleError(final Exception error) {
        List<ApiError> errorList;
        if (error instanceof ApiException) {
            errorList = ((ApiException) error).getApiErrorList();
        } else if (error instanceof HttpMessageNotReadableException
                || error instanceof InvalidFormatException || error instanceof JsonParseException) {
            errorList = Arrays.asList(new ApiError(ApiError.PARAMETER_FORMAT, error.getMessage()));
        } else {
            errorList = Arrays.asList(new ApiError(ApiError.ERROR_OTHER, error.getMessage()));
        }
        return errorList;
    }

    /**
     * APIエラーハンドリング(500).
     *
     * @param error error.
     * @return error response.
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ Throwable.class })
    @ResponseBody
    public List<ApiError> handleError(final Throwable error) {
        return Arrays.asList(new ApiError(ApiError.ERROR_OTHER, error.getMessage() + "aaaaaaaaaaaaaaaaaaaaaaaa"));
    }
}
