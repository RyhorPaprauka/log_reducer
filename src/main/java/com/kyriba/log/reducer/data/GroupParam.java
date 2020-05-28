package com.kyriba.log.reducer.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.kyriba.log.reducer.util.Constants.DAY_FORMAT;
import static com.kyriba.log.reducer.util.Constants.HOUR_FORMAT;
import static com.kyriba.log.reducer.util.Constants.MONTH_FORMAT;

@Getter
@AllArgsConstructor
public enum GroupParam implements Identifier {

    USERNAME("Username", false) {
        @Override
        public String getIdentity(Record record) {
            return record.getUsername();
        }
    },
    HOUR("Hour", true) {
        @Override
        public String getIdentity(Record record) {
            return record.getDateTime().format(HOUR_FORMAT);
        }
    },
    DAY("Day", true) {
        @Override
        public String getIdentity(Record record) {
            return record.getDateTime().format(DAY_FORMAT);
        }
    },
    MONTH("Month", true) {
        @Override
        public String getIdentity(Record record) {
            return record.getDateTime().format(MONTH_FORMAT);
        }
    };

    private String value;
    private boolean isDate;
}
