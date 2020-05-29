package com.kyriba.log.reducer.processor;

import com.kyriba.log.reducer.data.GroupParam;
import com.kyriba.log.reducer.data.Record;
import lombok.AllArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

import static com.kyriba.log.reducer.util.Constants.SPACE;

@AllArgsConstructor(staticName = "of")
public class RecordGrouper {

    private Set<GroupParam> groupParams;

    public String getIdentity(Record record) {
        return groupParams.stream()
                .sorted()
                .map(param -> param.getIdentity(record))
                .collect(Collectors.joining(SPACE));
    }

    public String getIdentityHeader() {
        return groupParams.stream()
                .sorted()
                .map(GroupParam::getValue)
                .collect(Collectors.joining(SPACE));
    }
}
