package com.kretsev.calorietracker.service.impl;

import com.kretsev.calorietracker.service.LoggingService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.slf4j.spi.LoggingEventBuilder;
import org.springframework.stereotype.Service;

/**
 * Implementation of the LoggingService for logging messages at various severity levels.
 */
@Slf4j
@Service
public class LoggingServiceImpl implements LoggingService {
    /**
     * Logs a debug-level message.
     *
     * @param message the message template with placeholders
     * @param args the arguments to substitute into the message placeholders
     */
    @Override
    public void logDebug(String message, Object... args) {
        logMessage(Level.DEBUG, message, args);
    }

    /**
     * Logs an info-level message.
     *
     * @param message the message template with placeholders
     * @param args the arguments to substitute into the message placeholders
     */
    @Override
    public void logInfo(String message, Object... args) {
        logMessage(Level.INFO, message, args);
    }

    /**
     * Logs a warning-level message.
     *
     * @param message the message template with placeholders
     * @param args the arguments to substitute into the message placeholders
     */
    @Override
    public void logWarn(String message, Object... args) {
        logMessage(Level.WARN, message, args);
    }

    /**
     * Logs an error-level message.
     *
     * @param message the message template with placeholders
     * @param args the arguments to substitute into the message placeholders
     */
    @Override
    public void logError(String message, Object... args) {
        logMessage(Level.ERROR, message, args);
    }

    /**
     * Logs a message at the specified severity level.
     *
     * @param level the severity level of the message
     * @param message the message template with placeholders
     * @param args the arguments to substitute into the message placeholders
     */
    private void logMessage(Level level, String message, Object... args) {
        LoggingEventBuilder logBuilder;

        switch (level) {
            case DEBUG -> logBuilder = log.atDebug();
            case ERROR -> logBuilder = log.atError();
            case WARN -> logBuilder = log.atWarn();
            case INFO -> logBuilder = log.atInfo();
            default -> throw new IllegalArgumentException("Unsupported log level: " + level);
        }

        logBuilder = logBuilder.setMessage(message);

        for (Object arg : args) {
            logBuilder = logBuilder.addArgument(() -> arg);
        }

        logBuilder.log();
    }
}
