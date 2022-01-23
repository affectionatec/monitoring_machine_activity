package uk.ac.cardiff.mma.application.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskVM {
    private final Long id;

    private final String title;

    private final String description;

    private final String deadLine ;

    private final String createDate ;

    private final boolean done ;

    private final Boolean isWarning;
}
