package com.kyriba.log.reducer.config;

import com.kyriba.log.reducer.data.GroupParam;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.kyriba.log.reducer.util.Constants.COMMA;
import static com.kyriba.log.reducer.util.Constants.HOUR_FORMAT;

@Getter
@NoArgsConstructor
public class ConsoleMenu {

    private Configuration config;

    public void startMenu() {
        config = new Configuration();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please, enter username for filtering:");
        String filterName = scanner.nextLine().trim();
        config.setFilterName(filterName.isEmpty()
                ? null
                : filterName);

        System.out.println("Please, enter start date for filtering (format: yyyy-MM-dd HH):");
        String filterFrom = scanner.nextLine().trim();
        config.setFilterFrom(filterFrom.isEmpty()
                ? LocalDateTime.MIN
                : LocalDateTime.parse(filterFrom, HOUR_FORMAT));

        System.out.println("Please, enter end date for filtering (format: yyyy-MM-dd HH):");
        String filterTo = scanner.nextLine().trim();
        config.setFilterTo(filterTo.isEmpty()
                ? LocalDateTime.MAX
                : LocalDateTime.parse(scanner.nextLine(), HOUR_FORMAT));

        System.out.println("Please, enter message pattern for filtering:");
        String pattern = scanner.nextLine().trim();
        config.setFilterMessagePattern(pattern.isEmpty()
                ? null
                : Pattern.compile(pattern));

        System.out.println("Please, enter comma-separated parameters for grouping (username, hour, day, month):");
        config.getGroupParams().addAll(convertToGroupParams(scanner.nextLine()));

        System.out.println("Please, enter path of logs directory:");
        String inputPath = scanner.nextLine().trim();
        if (!inputPath.isEmpty()) {
            config.setInputPath(Paths.get(inputPath));
        } else {
            throw new RuntimeException("path of directory must not be empty");
        }

        System.out.println("Please, enter output path of result:");
        String outputPath = scanner.nextLine().trim();
        if (!outputPath.isEmpty()) {
            config.setOutputPath(Paths.get(outputPath));
        } else {
            throw new RuntimeException("output path must not be empty");
        }

        System.out.println("Please, enter thread count for processing:");
        config.setThreadCount(scanner.nextInt());

        System.out.println("Processing starts");
    }

    private Set<GroupParam> convertToGroupParams(String params) {
        try {
            return Stream.of(params.split(COMMA))
                    .map(String::trim)
                    .map(param -> GroupParam.valueOf(param.toUpperCase()))
                    .collect(Collectors.toSet());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("At least one parameter must be entered");
        }
    }
}
