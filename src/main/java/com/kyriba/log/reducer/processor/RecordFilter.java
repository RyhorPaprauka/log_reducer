package com.kyriba.log.reducer.processor;

import com.kyriba.log.reducer.data.Record;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Builder
@AllArgsConstructor
public class RecordFilter {

    private String username;
    private LocalDateTime from;
    private LocalDateTime to;
    private Pattern messagePattern;


    public boolean validate(Record record) {
        return Objects.nonNull(record) && validateName(record);
    }

    private boolean validateName(Record record) {
        return (Objects.isNull(username) || record.getUsername().toUpperCase().equals(username.toUpperCase()))
                && validateTime(record);
    }

    private boolean validateTime(Record record) {
        LocalDateTime time = record.getDateTime();
        boolean isAfterStart = time.isEqual(from) || time.isAfter(from);
        boolean isBeforeEnd = time.isEqual(to) || time.isBefore(to);
        return isAfterStart && isBeforeEnd && validateMessage(record);
    }

    private boolean validateMessage(Record record) {
        if (Objects.nonNull(messagePattern)) {
            Matcher matcher = messagePattern.matcher(record.getMessage());
            return matcher.matches();
        } else {
            return true;
        }
    }
}
