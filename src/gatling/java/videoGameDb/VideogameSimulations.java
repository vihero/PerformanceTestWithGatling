package videoGameDb;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
public class VideogameSimulations extends Simulation {

  private HttpProtocolBuilder httpProtocol = http
    .baseUrl("https://videogamedb.uk:443")
    .inferHtmlResources()
    .acceptHeader("application/json")
    .acceptEncodingHeader("gzip, deflate, br")
    .acceptLanguageHeader("en-GB,en;q=0.8")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");

    private List<ScenarioBuilder> scenarios = new ArrayList<>();
  private ScenarioBuilder scn = scenario("Scenario to get all the video games")
    .exec(
      http("Scenario to get all the video games")
        .get("/api/videogame")
    ).pause(10);

    private ScenarioBuilder scn2 = scenario("Scenario to get 1st video game")
            .exec(
                    http("Scenario to get 1st video game")
                            .get("/api/videogame/1")
            ).pause(4);

    private ScenarioBuilder scn3 = scenario("Scenario to get 2nd video game")
            .exec(
                    http("Scenario to get 2nd video game")
                            .get("/api/videogame/2")
            ).pause(5);

    PopulationBuilder pb=scn.injectOpen(atOnceUsers(10)).protocols(httpProtocol);
    PopulationBuilder pb2=scn2.injectOpen(rampUsers(20).during(10)).protocols(httpProtocol);


  {
      List<List<PopulationBuilder>> arrayList=new ArrayList<>();
      arrayList.add(Arrays.asList(pb,pb2));

      scenarios.add(scn);
//      scenarios.add(scn2);
//      scenarios.add(scn3);

   List<PopulationBuilder> pbs=new ArrayList<>();
    for(ScenarioBuilder scenario:scenarios){
   PopulationBuilder pbb=scenario.injectOpen(rampUsers(23).during(12)).protocols(httpProtocol);
   pbs.add(pbb);
    }

    PopulationBuilder chaindedPopulationBuilder=pbs.stream().reduce(PopulationBuilder::andThen).orElseThrow(()->new IllegalStateException("No Pb Found"));

      setUp(chaindedPopulationBuilder);;

  }

}
