package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.demo.DebugLog;
import com.example.demo.RequestPath;
import com.example.demo.entities.InvoiceEntity;
import com.example.demo.exception.ApiException;
import com.example.demo.exception.ApiException.ErrorCode;
import com.example.demo.form.InvoiceRequestForm;
import com.example.demo.service.InvoiceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

/**
 * リクエストコントローラ.
 */
@Controller
@RequestMapping(RequestPath.API + "/invoice")
public class InvoiceController {
    /** CLASS. */
    private static final String CLASS = InvoiceController.class.getSimpleName();

    /** InvoiceService. */
    @Autowired
    private InvoiceService mInvoiceService;

    /**
     * 請求書新規登録.
     *
     * @return 登録した請求書管理番号.
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public int postInvoice(@RequestBody String body) {
        DebugLog.enter(CLASS + ".postInvoice");
        int invoiceNumber = -1;
        try {
            ObjectMapper mapper = new ObjectMapper();
            invoiceNumber = mInvoiceService.reigsterInvoice(mapper.readValue(body, InvoiceRequestForm.class));
        } catch (InvalidFormatException e) {
            // フォーマット不正.
            throw new ApiException(ErrorCode.PARAMETER_FORMAT, e.getMessage());
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            // その他エラー.
            throw new ApiException(ErrorCode.ERROR_OTHER, e.getMessage());
        }
        DebugLog.exit(CLASS + ".postInvoice : " + invoiceNumber);
        return invoiceNumber;
    }

    /**
     * 請求書一覧取得.
     *
     * @return 請求書一覧.
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<InvoiceEntity> getAllInvoice() {
        DebugLog.enter(CLASS + ".getAllInvoice");

        List<InvoiceEntity> list = mInvoiceService.getAllInvoice();
        DebugLog.exit(CLASS + ".getAllInvoice");
        return list;
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
        DebugLog.enter(CLASS + ".getInvoice:" + id);
        InvoiceEntity invoice = null;
        try {
            int invoiceId = Integer.valueOf(id);
            invoice = mInvoiceService.getInvoice(invoiceId);
            if (invoice == null) {
                throw new ApiException(ErrorCode.DATA_NOTHING, "[" + id + "] record is nothing.");
            }

        } catch (NumberFormatException e) {
            throw new ApiException(ErrorCode.PARAMETER_FORMAT, e.getMessage());
        }
        DebugLog.exit(CLASS + ".getInvoice");
        return invoice;
    }

    /**
     * APIエラーハンドリング(400).
     *
     * @param error error.
     * @return error response.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ ApiException.class })
    @ResponseBody
    public List<Map<String, Object>> handleError(final ApiException error) {
        DebugLog.enter(CLASS + ".handleApiError 400");
        List<Map<String, Object>> errorResponse = error.createErrorResponse();
        DebugLog.exit(CLASS + ".handleApiError:" + errorResponse);
        return errorResponse;
    }

    /**
     * APIエラーハンドリング(500).
     *
     * @param error error.
     * @return error response.
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ Exception.class })
    @ResponseBody
    public List<Map<String, Object>> handleError(final Exception error) {
        DebugLog.enter(CLASS + ".handleApiError 500");
        List<Map<String, Object>> errorResponse
            = new ApiException(ErrorCode.ERROR_OTHER, error.getMessage()).createErrorResponse();
        DebugLog.exit(CLASS + ".handleApiError:" + errorResponse);
        return errorResponse;
    }
}
