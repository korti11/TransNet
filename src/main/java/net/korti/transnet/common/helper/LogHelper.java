package net.korti.transnet.common.helper;

import org.apache.logging.log4j.Logger;

/**
 * Created by Korti on 30.08.2017.
 */
public final class LogHelper {

    private static Logger logger;

    public static void setLogger(Logger logger) {
        LogHelper.logger = logger;
    }

    public static void i(String message) {
        logger.info(message);
    }

    public static void d(String message) {
        logger.debug(message);
    }

    public static void d(String message, Throwable throwable) {
        logger.debug(message, throwable);
    }

    public static void e(String message) {
        logger.error(message);
    }

    public static void e(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    public static void w(String message) {
        logger.warn(message);
    }

    public static void w(String message, Throwable throwable) {
        logger.warn(message, throwable);
    }

}
