package ext.deployit.community.ci.github;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GithubClientTest {

    private GithubClient githubClient;

    @Before
    public void setup( ) {
        githubClient = new GithubClient("api.github.com",
                null,
                "xebialabs-community/xld-github-dynamic-dictionaries-plugin",
                "master",
                "src/main/resources/synthetic.xml");
    }

    @Test
    public void connect() {
        githubClient.connect();
    }

    @Test
    public void readFile() {
        githubClient.connect();
        githubClient.readFile();
    }
}