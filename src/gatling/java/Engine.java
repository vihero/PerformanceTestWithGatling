import io.gatling.app.Gatling;
import io.gatling.core.config.GatlingConfiguration;
import io.gatling.core.config.GatlingPropertiesBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Engine {

  public static void main(String[] args) {
    GatlingPropertiesBuilder props = new GatlingPropertiesBuilder()
      .resourcesDirectory(IDEPathHelper.gradleResourcesDirectory.toString())
      .resultsDirectory(IDEPathHelper.resultsDirectory.toString())
      .binariesDirectory(IDEPathHelper.gradleBinariesDirectory.toString());

    Gatling.fromMap(props.build());
  }
}
