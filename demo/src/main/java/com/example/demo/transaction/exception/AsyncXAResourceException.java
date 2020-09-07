package com.example.demo.transaction.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsyncXAResourceException extends Throwable {
    private static Logger logger = LoggerFactory.getLogger(AsyncXAResourceException.class);

    public AsyncXAResourceException(String msg) {
        logger.error(msg);
    }
}
