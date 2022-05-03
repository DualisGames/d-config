package games.dualis.dconfig;

import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.spongepowered.plugin.builtin.jvm.Plugin;

@Plugin("d-config")
public class DConfigPlugin implements DConfig {

    public DConfigPlugin() {

    }

    @Override
    public FluentJdbc fluentJdbc() {
        return null;
    }

}
