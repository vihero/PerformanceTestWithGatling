package videoGameDb;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class TestSimulations extends Simulation {
    Config config = ConfigFactory.load("Simulation1.properties");
    Thread thread=new Thread(TestClass::printHelloWorld);
    {
        thread.start();
    }

    int users=config.getInt("users");

         HttpProtocolBuilder httpProtocol = http
                .baseUrl("https://videogamedb.uk:443")
                .inferHtmlResources()
                .acceptHeader("application/json")
                .acceptEncodingHeader("gzip, deflate, br")
                .acceptLanguageHeader("en-GB,en;q=0.8")
                .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");

         ScenarioBuilder scn = scenario("Scenario to get all the video games")
                .exec(
                        http("Scenario to get all the video games")
                                .get("/api/videogame")
                ).pause(10);

    {
        setUp(scn.injectOpen(atOnceUsers(users))).protocols(httpProtocol);

    }
}
