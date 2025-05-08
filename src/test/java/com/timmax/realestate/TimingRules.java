package com.timmax.realestate;

import org.junit.rules.ExternalResource;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class TimingRules {
    private static final Logger log = LoggerFactory.getLogger("result");

    private static final String CLASS_NAME_COLUMN_NAME = "Test";
    private static final String DURATION_COLUMN_NAME = "Duration, ms";
    private static final int CLASS_NAME_MAX_LENGTH = 95;
    private static final int DURATION_MAX_LENGTH = 13;

    private static final StringBuilder results = new StringBuilder();

    // http://stackoverflow.com/questions/14892125/what-is-the-best-practice-to-determine-the-execution-time-of-the-bussiness-relev
    public static final Stopwatch STOPWATCH = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            String result = String.format(
                    "%-" + CLASS_NAME_MAX_LENGTH + "s|%" + DURATION_MAX_LENGTH + "d",
                    description.getDisplayName(),
                    TimeUnit.NANOSECONDS.toMillis(nanos)
            );
            results.append(result).append('\n');
            log.info(result + " ms\n");
        }
    };

    //    https://dzone.com/articles/applying-new-jdk-11-string-methods
    private static final String DELIM = "-".repeat(CLASS_NAME_MAX_LENGTH) + "+" + "-".repeat(DURATION_MAX_LENGTH);

    public static final ExternalResource SUMMARY = new ExternalResource() {
        @Override
        protected void before() {
            results.setLength(0);
        }

        @Override
        protected void after() {
            log.info("\n" +
                    DELIM + "\n" +
                    CLASS_NAME_COLUMN_NAME +
                    " ".repeat(CLASS_NAME_MAX_LENGTH - CLASS_NAME_COLUMN_NAME.length()) +
                    "|" +
                    DURATION_COLUMN_NAME +
                    "\n" +
                    DELIM + "\n" +
                    results +
                    DELIM + "\n"
            );
        }
    };
}
