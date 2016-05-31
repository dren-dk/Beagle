package dk.dren.beagle;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


/**
 * Add fields for the configuration in this class,
 * Dropwizard will take care of reading the server,yaml file.
 * Don't bother creating getters and setters, @Data from Lombok creates getters and setters automagically.
 */
@Data
public class ServerConfiguration extends Configuration {

	@JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;

    @JsonProperty("database")
    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();
}