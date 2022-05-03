package games.dualis.dconfig;

import com.google.inject.ImplementedBy;
import org.codejargon.fluentjdbc.api.FluentJdbc;

@ImplementedBy(DConfigPlugin.class)
public interface DConfig {

    /**
     * Returns the {@link FluentJdbc} instance used by our plugins.
     * <p>
     *     This instance is always used to interact with the database, allowing
     *     multiple plugins to easily work on a single database without having
     *     to configure each plugin.
     * </p>
     * @return the {@link FluentJdbc} instance used by our plugins.
     */
    FluentJdbc fluentJdbc();

}
