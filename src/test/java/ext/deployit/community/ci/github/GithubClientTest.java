package ext.deployit.community.ci.github;

import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertTrue;

public class GithubClientTest {


    private static String token="d266616b3e12a57151d0a4186f26e5f369f9af32";
    @Test
    public void connect() {
        GithubClient githubClient = new GithubClient("api.github.com",
            token,
            "bmoussaud/sample_configuration",
            "master");
        githubClient.connect();
    }

    @Test
    public void readProperties() {
        GithubClient githubClient = new GithubClient("api.github.com",
            token,
            "bmoussaud/sample_configuration",
            "master");
        githubClient.connect();
        Properties properties = githubClient.readProperties("petportal/config.properties");
        assertTrue(properties.size() == 2);
        assertTrue(properties.get("branch").equals("master"));
    }

    @Test
    public void readPropertiesDev() {
        GithubClient githubClient = new GithubClient("api.github.com",
            token,
            "bmoussaud/sample_configuration",
            "dev");
        githubClient.connect();
        Properties properties = githubClient.readProperties("petportal/config.properties");
        assertTrue(properties.size() == 2);
        assertTrue(properties.get("branch").equals("dev"));
    }

    @Test
    public void readPropertiesProd() {
        GithubClient githubClient = new GithubClient("api.github.com",
            token,
            "bmoussaud/sample_configuration",
            "prod");
        githubClient.connect();
        Properties properties = githubClient.readProperties("petportal/config.properties");
        assertTrue(properties.size() == 3);
        assertTrue(properties.get("branch").equals("production"));
    }

    @Test
    public void readProperties21() {
        GithubClient githubClient = new GithubClient("api.github.com",
            token,
            "bmoussaud/sample_configuration",
            "v2.1");
        githubClient.connect();
        Properties properties = githubClient.readProperties("petportal/config.properties");
        assertTrue(properties.size() == 3);
        assertTrue(properties.get("branch").equals("production"));
        assertTrue(properties.get("version").equals("2.1"));
    }

    @Test
    public void readProperties22() {
        GithubClient githubClient = new GithubClient("api.github.com",
            token,
            "bmoussaud/sample_configuration",
            "v2.2");
        githubClient.connect();
        Properties properties = githubClient.readProperties("petportal/config.properties");
        assertTrue(properties.size() == 3);
        assertTrue(properties.get("branch").equals("production"));
        assertTrue(properties.get("version").equals("2.2"));
    }

    @Test(expected = RuntimeException.class)
    public void readNonExistingProperties() {
        GithubClient githubClient = new GithubClient("api.github.com",
            token,
            "bmoussaud/sample_configuration",
            "master");
        githubClient.connect();
        Properties properties = githubClient.readProperties("src/test/resources/sub/config/XXCCmyconfig.properties");
    }
}
