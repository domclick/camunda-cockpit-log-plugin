package ru.domclick.cockpit.plugin.log.resources;

import lombok.val;
import org.camunda.bpm.cockpit.plugin.resource.AbstractCockpitPluginResource;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.springframework.util.StringUtils;
import ru.domclick.cockpit.plugin.log.dto.LogDto;
import ru.domclick.cockpit.plugin.log.mapper.LogMapper;
import ru.domclick.cockpit.plugin.log.mapper.util.SearchHitWrapper;
import ru.domclick.cockpit.plugin.log.query.LogSearchQuery;
import ru.domclick.cockpit.plugin.log.utils.ModelUtils;

import javax.ws.rs.GET;
import java.io.IOException;
import java.util.ArrayList;
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
        val procDefKeys = new ArrayList<String>();
        val activityIds = new ArrayList<String>();
        if (StringUtils.isEmpty(activityId)) {
            procDefKeys.addAll(
                    ModelUtils.getProcessDefinitionKeys(
                            getProcessEngine(),
                            ModelUtils.getProcessDefinitionId(getProcessEngine(), procDefKey)
                    )
            );
            procDefKeys.add(procDefKey);
            activityIds.addAll(
                    ModelUtils.getActivityIds(
                            getProcessEngine(),
                            ModelUtils.getProcessDefinitionId(getProcessEngine(), procDefKey)
                    )
            );
        } else {
            procDefKeys.addAll(
                    ModelUtils.getProcessDefinitionKeys(
                            getProcessEngine(),
                            (ModelElementInstance) getProcessEngine()
                                    .getRepositoryService()
                                    .getBpmnModelInstance(ModelUtils.getProcessDefinitionId(getProcessEngine(), procDefKey))
                                    .getModelElementById(activityId)
                    )
            );
            activityIds.addAll(
                    ModelUtils.getActivityIds(
                            getProcessEngine(),
                            (ModelElementInstance) getProcessEngine()
                                    .getRepositoryService()
                                    .getBpmnModelInstance(ModelUtils.getProcessDefinitionId(getProcessEngine(), procDefKey))
                                    .getModelElementById(activityId)
                    )
            );
        }

        return getAllLogsByParams(procDefKeys, activityIds, businessKey);
    }

    private List<LogDto> getAllLogsByParams(List<String> procDefKeys,List<String> activityIds, String businessKey) {
        try (val logSearchQuery = LogSearchQuery.builder()
                .procDefKeys(procDefKeys)
                .activityIds(activityIds)
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
