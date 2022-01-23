package uk.ac.cardiff.mma.application;

import org.springframework.core.convert.converter.Converter;
import uk.ac.cardiff.mma.application.enums.TaskListType;

public class StringToTaskListTypeConverter implements Converter<String, TaskListType> {
    @Override
    public TaskListType convert(String source) {
        return TaskListType.valueOf(source.toUpperCase());
    }
}