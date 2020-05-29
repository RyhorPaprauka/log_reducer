package com.kyriba.log.reducer.processor;

import com.kyriba.log.reducer.config.Configuration;
import com.kyriba.log.reducer.data.Record;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.kyriba.log.reducer.util.Constants.GROUP_DATE;
import static com.kyriba.log.reducer.util.Constants.GROUP_MESSAGE;
import static com.kyriba.log.reducer.util.Constants.GROUP_NAME;
import static com.kyriba.log.reducer.util.Constants.LOG_PATTERN;

public class FileProcessor {

    private final Configuration config;
    private RecordFilter filter;
    private RecordGrouper grouper;
    private FileWriter writer;

    public FileProcessor(Configuration config) {
        this.config = config;
        this.filter = config.getFilter();
        this.grouper = config.getGrouper();
        this.writer = config.getWriter();
    }

    public void process() throws Throwable {
        List<Record> filteredLogs = filterLogs(getPaths());
        Map<String, Long> groupedLogs = groupLogs(filteredLogs);

        ReportMaker reportMaker = ReportMaker.of(grouper.getIdentityHeader());
        writer.write(reportMaker.makeReport(filteredLogs, groupedLogs));
    }

    private Set<Path> getPaths() throws IOException {
        return Files.walk(config.getInputPath())
                .filter(Files::isRegularFile)
                .filter(Files::isReadable)
                .collect(Collectors.toSet());
    }

    private List<Record> filterLogs(Set<Path> paths) throws Throwable {
        ForkJoinPool threadPool = new ForkJoinPool(config.getThreadCount());
        try {
            return threadPool.submit(() ->
                    paths.parallelStream()
                            .flatMap(path -> {
                                try {
                                    return Files.lines(path);
                                } catch (IOException e) {
                                    throw new UncheckedIOException(e);
                                }
                            })
                            .map(this::lineToRecord)
                            .filter(filter::validate)
                            .collect(Collectors.toList())).get();
        } catch (UncheckedIOException | ExecutionException ex) {
            throw ex.getCause();
        }
    }

    private Record lineToRecord(String line) {
        Matcher matcher = Pattern.compile(LOG_PATTERN).matcher(line);
        return matcher.matches() ?
                Record.builder()
                        .dateTime(LocalDateTime.parse(matcher.group(GROUP_DATE)))
                        .username(matcher.group(GROUP_NAME))
                        .message(matcher.group(GROUP_MESSAGE))
                        .build()
                : null;
    }

    private Map<String, Long> groupLogs(List<Record> logs) {
        return logs.stream()
                .map(grouper::getIdentity)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }
}
