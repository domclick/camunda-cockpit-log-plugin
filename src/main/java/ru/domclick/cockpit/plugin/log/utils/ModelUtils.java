package ru.domclick.cockpit.plugin.log.utils;

import lombok.experimental.UtilityClass;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.model.bpmn.impl.instance.CallActivityImpl;
import org.camunda.bpm.model.bpmn.impl.instance.ProcessImpl;
import org.camunda.bpm.model.bpmn.impl.instance.ServiceTaskImpl;
import org.camunda.bpm.model.bpmn.impl.instance.SubProcessImpl;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ModelUtils {
    public String getProcessDefinitionId(ProcessEngine processEngine, String processDefinitionKey) {
        return processEngine
                .getRepositoryService()
                .createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey)
                .latestVersion()
                .singleResult()
                .getId();
    }

    public List<String> getActivityIds(ProcessEngine processEngine, String processDefinitionId) {
        return processEngine
                .getRepositoryService()
                .getBpmnModelInstance(processDefinitionId)
                .getDocumentElement()
                .getDomElement()
                .getChildElements()
                .stream()
                .map(element -> getActivityIds(processEngine, element.getModelElementInstance()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<String> getActivityIds(ProcessEngine processEngine, ProcessImpl process) {
        return process
                .getFlowElements()
                .stream()
                .map(element -> getActivityIds(processEngine, element))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public List<String> getActivityIds(ProcessEngine processEngine, ModelElementInstance modelElementInstance) {
        if (modelElementInstance instanceof ProcessImpl) {
            return getActivityIds(processEngine, (ProcessImpl) modelElementInstance);
        } else if (modelElementInstance instanceof CallActivityImpl) {
            return getActivityIds(processEngine, (CallActivityImpl) modelElementInstance);
        } else if (modelElementInstance instanceof ServiceTaskImpl) {
            return getActivityIds((ServiceTaskImpl) modelElementInstance);
        } else if (modelElementInstance instanceof SubProcessImpl) {
            return getActivityIds(processEngine, (SubProcessImpl) modelElementInstance);
        } else {
            return getActivityIds();
        }
    }

    private List<String> getActivityIds(ServiceTaskImpl serviceTask) {
        return Collections.singletonList(serviceTask.getId());
    }

    private List<String> getActivityIds(ProcessEngine processEngine, CallActivityImpl callActivity) {
        return getActivityIds(processEngine, getProcessDefinitionId(processEngine, callActivity.getCalledElement()));
    }

    private List<String> getActivityIds(ProcessEngine processEngine, SubProcessImpl subProcess) {
        return subProcess
                .getFlowElements()
                .stream()
                .map(element -> getActivityIds(processEngine, element))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<String> getActivityIds() {
        return Collections.EMPTY_LIST;
    }

    public List<String> getProcessDefinitionKeys(ProcessEngine processEngine, String processDefinitionId) {
        return processEngine
                .getRepositoryService()
                .getBpmnModelInstance(processDefinitionId)
                .getDocumentElement()
                .getDomElement()
                .getChildElements()
                .stream()
                .map(element -> getProcessDefinitionKeys(processEngine, element.getModelElementInstance()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public List<String> getProcessDefinitionKeys(ProcessEngine processEngine, ModelElementInstance modelElementInstance) {
        if (modelElementInstance instanceof ProcessImpl) {
            return getProcessDefinitionKeys(processEngine, (ProcessImpl) modelElementInstance);
        } else if (modelElementInstance instanceof CallActivityImpl) {
            return getProcessDefinitionKeys(processEngine, (CallActivityImpl) modelElementInstance);
        } else if (modelElementInstance instanceof SubProcessImpl) {
            return getProcessDefinitionKeys(processEngine, (SubProcessImpl) modelElementInstance);
        } else {
            return getProcessDefinitionKeys();
        }
    }

    private List<String> getProcessDefinitionKeys(ProcessEngine processEngine, ProcessImpl process) {
        return process
                .getFlowElements()
                .stream()
                .map(element -> getProcessDefinitionKeys(processEngine, element))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<String> getProcessDefinitionKeys(ProcessEngine processEngine, SubProcessImpl subProcess) {
        return subProcess
                .getFlowElements()
                .stream()
                .map(element -> getProcessDefinitionKeys(processEngine, element))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<String> getProcessDefinitionKeys(ProcessEngine processEngine, CallActivityImpl callActivity) {
        List<String> procDefKeys = Arrays.asList(callActivity.getCalledElement());
        procDefKeys.addAll(getProcessDefinitionKeys(processEngine, getProcessDefinitionId(processEngine, callActivity.getCalledElement())));
        return procDefKeys;
    }

    private List<String> getProcessDefinitionKeys() {
        return Collections.EMPTY_LIST;
    }
}
