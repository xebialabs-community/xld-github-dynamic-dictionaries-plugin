package ext.deployit.community.ci.github;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryContents;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.ContentsService;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.io.IOException;
import java.util.List;

public class GithubClient {


    private final String api;

    private final String token;

    private final String repository;

    private final String branch;

    private final String path;

    private GitHubClient client;

    public GithubClient(String api, String token, String repository, String branch, String path) {
        this.api = api;
        this.token = token;
        this.repository = repository;
        this.branch = branch;
        this.path = path;
    }

    public void connect() {
        client = new GitHubClient(this.api);
        client.setOAuth2Token(this.token);
    }

    public void readFile() {

        try {
            RepositoryService repositoryService = new RepositoryService(this.client);

            Repository repository = repositoryService.getRepository(getRepositoryOwner(), getRepositoryName());

            ContentsService contentsService = new ContentsService(client);
            List<RepositoryContents> contents = contentsService.getContents(repository, path);
            for (RepositoryContents content : contents) {
                System.out.println("name = " + content.getName());
                System.out.println("content = " + content.getContent());
                System.out.println("encoding = " + content.getEncoding());
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
