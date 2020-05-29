package com.kyriba.log.reducer.processor;

import com.kyriba.log.reducer.data.Record;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.kyriba.log.reducer.util.Constants.COUNT_HEADER;
import static java.lang.System.*;

@AllArgsConstructor(staticName = "of")
public class ReportMaker {

    private String groupHeader;

    public List<String> makeReport(List<Record> filteredLogs, Map<String, Long> groupedLogs) {
        List<String> result = new ArrayList<>();
        result.add(lineSeparator());

        result.addAll(filteredLogs.stream()
                .map(Record::toString)
                .collect(Collectors.toList()));
        result.add(lineSeparator());

        result.add(String.format("%20s %20s" + lineSeparator(), groupHeader, COUNT_HEADER));
        result.addAll(groupedLogs.entrySet().stream()
                .map(entry -> String.format("%20s %20s" + lineSeparator(), entry.getKey(), entry.getValue().toString()))
                .collect(Collectors.toList()));

        return result;
    }
}
