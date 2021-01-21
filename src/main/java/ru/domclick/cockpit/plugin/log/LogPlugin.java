package ru.domclick.cockpit.plugin.log;

import lombok.val;
import org.camunda.bpm.cockpit.plugin.spi.impl.AbstractCockpitPlugin;
import ru.domclick.cockpit.plugin.log.resources.LogPluginRootResource;

import java.util.HashSet;
import java.util.Set;

public class LogPlugin extends AbstractCockpitPlugin {
    public static final String ID = "log-plugin";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public Set<Class<?>> getResourceClasses() {
        val classes = new HashSet<Class<?>>();

        classes.add(LogPluginRootResource.class);

        return classes;
    }
}
