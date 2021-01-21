package ru.domclick.cockpit.plugin.log.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class LogDto {
    private String level;
    private String businessKey;
    private String message;
    private String activityName;
    private String time;
}
