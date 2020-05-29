package com.kyriba.log.reducer.data;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.kyriba.log.reducer.util.Constants.SPACE;

@Builder
@Getter
public class Record {

    private LocalDateTime dateTime;
    private String username;
    private String message;

    @Override
    public String toString() {
        return dateTime + SPACE + username + SPACE + message;
    }
}
