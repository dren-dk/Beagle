package dk.dren.beagle.injectors;

import dk.dren.beagle.db.BeagleDBI;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import dk.dren.beagle.ServerConfiguration;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InjectorBinder extends AbstractBinder {
	private final ServerConfiguration configuration;
	private final BeagleDBI beagleDBI;

	@Override
	protected void configure() {
		// We can't create the Thing just yet, but we can set up a factory class to use when creating instances
		//bindFactory(UserAgentFactory.class).to(UserAgent.class);

		// We need some singletons, too
		bind(configuration).to(ServerConfiguration.class);
		bind(beagleDBI).to(BeagleDBI.class);
	}

}
