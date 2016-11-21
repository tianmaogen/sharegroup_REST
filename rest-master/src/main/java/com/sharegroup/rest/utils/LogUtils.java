package com.sharegroup.rest.utils;

import com.sharegroup.rest.exception.CustomException;
import org.cubilose.common.exception.GeneralException;
import org.cubilose.common.log.ILog;
import org.cubilose.common.log.LogAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jove
 */
public class LogUtils {
    //
    private static final ILog logger = new LogAdapter(LogUtils.class);


    /**
     * дDEBUG�������־
     *
     * @param message
     * @param args
     */
    public static void WriteDebug(String message, Object... args) {

            StringBuilder builder = new StringBuilder();
            builder.append(message);
            for (Object object : args) {
                if (null != object) {
                    builder.append("\n").append(object.getClass()).append("|").append(object.toString());
                }
            }
            logger.debug(builder.toString());

    }

    /**
     * дINFO�������־
     *
     * @param message
     * @param args
     */
    public static void WriteInfo(String message, Object... args) {
            StringBuilder builder = new StringBuilder();
            builder.append(message);
            for (Object object : args) {
                if (null != object) {
                    builder.append("\n").append(object.getClass()).append("|").append(object.toString());
                }
            }
            logger.info(builder.toString());
    }


    /**
     * дϵͳ������쳣��־
     *
     * @param message
     * @param ex
     * @param args
     */
    public static void WriteError(String message, Exception ex, Object... args) {
        StringBuilder builder = new StringBuilder();
        if(null != ex) {
            builder.append(ex.getMessage());
        }
        for (Object object : args) {
            if (null != object) {
                builder.append("\n").append(object.getClass()).append("|").append(object.toString());
            }
        }
        String msg = builder.toString();
        logger.error(new GeneralException(msg));
    }

    /**
     * дҵ�񼶱�ʧ�ܵ��쳣��־
     *
     * @param ex
     * @param args
     */
    public static void WriteFatal(CustomException ex, Object... args) {
        StringBuilder builder = new StringBuilder();
        if(null != ex) {
            builder.append(ex.getMessage());
        }
        for (Object object : args) {
            if (null != object) {
                builder.append("\n").append(object.getClass()).append("|").append(object.toString());
            }
        }
        String msg = builder.toString();
        logger.fatal(ex,String.format(msg, args));

    }
}
