package dk.dren.dwa;

import dk.dren.dwa.injectors.InjectorBinder;
import dk.dren.dwa.resources.HelloResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.bundles.webjars.WebJarBundle;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class Server extends Application<ServerConfiguration>{

	/**
	 * This is the main entry point for the application, regardless of how it's started.
	 */
	public static void main(String[] args) throws Exception {
        try {
        	new Server().run(args);
        } catch (Throwable t) {
        	System.err.println("Failed while starting the application, giving up");
        	t.printStackTrace(System.err);
        	System.exit(254);
        }
    }

	/**
	 * Just set a human readable name of the application
	 */
	@Override
	public String getName() {
		return "Dropwizard and Angular";
	}


	/**
	 * Early startup bootstrapping, this is where all the bundles and the commands that might be needed
	 * are added, this is called before the command line parser figures out what we're actually starting.
	 */
	@Override
	public void initialize(Bootstrap<ServerConfiguration> bootstrap) {
		// Add commands		
		//bootstrap.addCommand(new SomeCommand());
		
		// We'll need to serve static files from our own classpath, this causes /static/ in the classpath to be exposed as /static/
		bootstrap.addBundle(new AssetsBundle("/static/", "/static/", "index.html", "static"));		
		
		// Webjars are managed artifacts that are simply re-packaged copies of the bower, npm and other client-side packages 
		// This bundle brings in webjars in the mentioned package hierachies 
		bootstrap.addBundle(new WebJarBundle("org.webjars.bower"));
		
		// As Dropwizard is primarily a REST application server, it makes sense to provide a nice user interface for trying out the
		// REST calls and to allow the developer to explore the API documentation, so we set up swagger which lives at /swagger/:
		bootstrap.addBundle(new SwaggerBundle<ServerConfiguration>() {
	        @Override
	        protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(ServerConfiguration configuration) {
	            return configuration.swaggerBundleConfiguration;
	        }
	    });
		
		// We like forms, so we'll bring in support for muti-part form data with with this bundle:
		bootstrap.addBundle(new MultiPartBundle());		
	}

	@Override
	public void run(ServerConfiguration configuration, Environment environment) throws Exception {
		// Register injectors.
		environment.jersey().register(new InjectorBinder(configuration));

		// Register resources
		environment.jersey().register(HelloResource.class);		
	}

}
