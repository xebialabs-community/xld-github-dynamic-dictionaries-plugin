package ext.deployit.community.ci.github;

import com.google.gson.internal.$Gson$Preconditions;
import org.apache.commons.net.util.Base64;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryContents;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.RequestException;
import org.eclipse.egit.github.core.service.ContentsService;
import org.eclipse.egit.github.core.service.RepositoryService;

import javax.swing.text.html.HTMLDocument;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Properties;

public class GithubClient {


    private final String api;
	
    private final String token;

    private final String repository;

    private final String branch;

    private GitHubClient client;

    public GithubClient(String api, String token, String repository, String branch) {
        this.api = api;
        this.token = token;
        this.repository = repository;
        this.branch = branch;
    }
	
	public void connect() {
        client = new GitHubClient(this.api);
        client.setOAuth2Token(this.token);
    }
	
	public void connect(String user) {
        client = new GitHubClient(this.api);
        client.setCredentials(user, this.token);
    }

    public Properties readProperties(String path) {

        try {
            RepositoryService repositoryService = new RepositoryService(this.client);

            Repository repository = repositoryService.getRepository(getRepositoryOwner(), getRepositoryName());

            ContentsService contentsService = new ContentsService(client);
            List<RepositoryContents> contents = contentsService.getContents(repository, path, branch);
            if (contents.size() == 0)
                throw new RuntimeException("File " + path + " not found in " + this.repository + " on " + this.branch + " branch");

            if (contents.size() > 2) {
                throw new RuntimeException("Too many files (" + contents.size() + ")  found on  " + path + "" + this.repository + " on " + this.branch + " branch");
            }

            RepositoryContents content = contents.iterator().next();

            StringReader reader = new StringReader(new String(Base64.decodeBase64(content.getContent())));
            Properties properties = new Properties();
            properties.load(reader);
            //System.out.println("properties = " + properties);
            return properties;
        } catch (RequestException re) {
            if (re.getStatus() == 404) {
                throw new RuntimeException("File " + path + " not found in " + this.repository + " on " + this.branch + " branch");
            } else {
                throw new RuntimeException("Error when read the file ob github", re);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error when read the file ob github", e);
        }

    }

    private String getRepositoryName() {
        return this.repository.split("/")[1];
    }

    private String getRepositoryOwner() {
        return this.repository.split("/")[0];
    }
}
