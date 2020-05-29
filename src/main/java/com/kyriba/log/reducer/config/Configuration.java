package com.kyriba.log.reducer.config;

import com.kyriba.log.reducer.data.GroupParam;
import com.kyriba.log.reducer.processor.FileWriter;
import com.kyriba.log.reducer.processor.RecordFilter;
import com.kyriba.log.reducer.processor.RecordGrouper;
import lombok.Data;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Data
public class Configuration {

    private Set<GroupParam> groupParams = new HashSet<>();
    private String filterName;
    private LocalDateTime filterFrom;
    private LocalDateTime filterTo;
    private Pattern filterMessagePattern;
    private Integer threadCount;
    private Path outputPath;
    private Path inputPath;

    public RecordFilter getFilter() {
        return RecordFilter.builder()
                .username(filterName)
                .from(filterFrom)
                .to(filterTo)
                .messagePattern(filterMessagePattern)
                .build();
    }

    public RecordGrouper getGrouper() {
        return RecordGrouper.of(
                reduceDates(groupParams));
    }

    public FileWriter getWriter() {
        return FileWriter.of(outputPath);
    }

    private Set<GroupParam> reduceDates(Set<GroupParam> groupParams) {
        Set<GroupParam> reduced = groupParams.stream()
                .filter(param -> !param.isDate())
                .collect(Collectors.toSet());

        Optional<GroupParam> smallestDate = groupParams.stream()
                .filter(GroupParam::isDate)
                .reduce((p1, p2) -> p1.ordinal() < p2.ordinal() ? p1 : p2);

        smallestDate.ifPresent(reduced::add);
        return reduced;
    }
}
