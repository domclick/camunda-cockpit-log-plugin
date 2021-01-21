package ru.domclick.cockpit.plugin.log;

import org.camunda.bpm.cockpit.Cockpit;
import org.camunda.bpm.cockpit.plugin.spi.CockpitPlugin;
import org.camunda.bpm.cockpit.plugin.test.AbstractCockpitPluginTest;
import org.junit.Assert;
import org.junit.Test;

public class LogPluginTest extends AbstractCockpitPluginTest {

    @Test
    public void testPluginDiscovery() {
        CockpitPlugin thisPlugin = Cockpit.getRuntimeDelegate().getAppPluginRegistry().getPlugin(LogPlugin.ID);
        Assert.assertNotNull(thisPlugin);
    }
}
