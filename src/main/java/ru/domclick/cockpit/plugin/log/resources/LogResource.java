package ru.domclick.cockpit.plugin.log.resources;

import lombok.val;
import org.camunda.bpm.cockpit.plugin.resource.AbstractCockpitPluginResource;
import ru.domclick.cockpit.plugin.log.dto.LogDto;
import ru.domclick.cockpit.plugin.log.mapper.LogMapper;
import ru.domclick.cockpit.plugin.log.mapper.util.SearchHitWrapper;
import ru.domclick.cockpit.plugin.log.query.LogSearchQuery;

import javax.ws.rs.GET;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogResource extends AbstractCockpitPluginResource {

    private String procDefKey;
    private String businessKey;
    private String activityId;

    public LogResource(String pluginName, String procDefKey, String businessKey, String activityId) {
        super(pluginName);
        this.procDefKey = procDefKey;
        this.businessKey = businessKey;
        this.activityId = activityId;
    }

    @GET
    public List<LogDto> getAllLogs() {
        try (val logSearchQuery = LogSearchQuery.builder()
                .procDefKey(procDefKey)
                .activityId(activityId)
                .businessKey(businessKey)
                .build()) {
            val hits = logSearchQuery.search();
            return Stream.of(hits.getHits())
                    .map(hit -> LogMapper.MAPPER.toLogDto(new SearchHitWrapper(hit)))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
