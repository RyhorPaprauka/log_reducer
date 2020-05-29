package com.kyriba.log.reducer.util;

import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;

@UtilityClass
public class Constants {

    public static final DateTimeFormatter MONTH_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM");
    public static final DateTimeFormatter HOUR_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
    public static final DateTimeFormatter DAY_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final String LOG_PATTERN =
            "(?<date>(19|20)\\d\\d-(0[1-9]|1[012])-([012]\\d|3[01])T([01]\\d|2[0-3]):([0-5]\\d):([0-5]\\d))\\s" +
                    "(?<name>[a-zA-Z]*):\\s(?<message>.*)";
    public static final String GROUP_NAME = "name";
    public static final String GROUP_DATE = "date";
    public static final String GROUP_MESSAGE = "message";
    public static final String SPACE = " ";
    public static final String COMMA = ",";
    public static final String COUNT_HEADER = "Count of records";
}
