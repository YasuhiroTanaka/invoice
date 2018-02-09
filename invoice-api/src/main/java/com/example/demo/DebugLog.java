package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ログ出力クラス.
 */
public class DebugLog {
    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceApplication.class);

    /**
     * Enter Log.
     *
     * @param message Massage.
     */
    public static void enter(final String message) {
        LOGGER.info(message + " <");
    }

    /**
     * Exit Log.
     *
     * @param message Massage.
     */
    public static void exit(final String message) {
        LOGGER.info(message + " >");
    }

    /**
     * Info Log.
     *
     * @param message Massage.
     */
    public static void info(final String message) {
        LOGGER.info(message);
    }
}
