package ext.deployit.community.ci.github;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.xebialabs.deployit.plugin.api.udm.Metadata;
import com.xebialabs.deployit.plugin.api.udm.Property;
import com.xebialabs.deployit.plugin.api.udm.base.BaseConfigurationItem;
import com.xebialabs.deployit.plugin.api.udm.lookup.LookupValueProvider;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

import static com.xebialabs.deployit.plugin.api.udm.Metadata.ConfigurationItemRoot.CONFIGURATION;

@Metadata(root = CONFIGURATION, description = "A LookupValueProvider that stores values in GitHub.")
public class RepositoryLookupValueProvider extends BaseConfigurationItem implements LookupValueProvider {

    private static Logger logger = LoggerFactory.getLogger(RepositoryLookupValueProvider.class);

    @Property(description = "Address of GitHub", defaultValue = "api.github.com", category = "Others")
    public String api;

    @Property(description = "OAuth2 token authentication, can be empty for access a public repository but the API rate limit exceeded can be quickly reached", password = true, required = false)
    public String token;

    @Property(description = "The GitHub repository owner/repo")
    public String repository;

    @Property(description = "The branch or tag in the repository", defaultValue = "master")
    public String branch;

    @Property(description = "The path to the property file")
    public String path;

    @Override
    public String lookup(String key, boolean password) throws Exception {
        GithubClient githubClient = new GithubClient(api, token, repository, branch);
        githubClient.connect();
        Properties properties = githubClient.readProperties(path);
        ImmutableMap<String, String> entries = Maps.fromProperties(properties);
        if (entries.containsKey(key)) {
            return entries.get(key);
        }
        logger.debug("'{}' not found in the '{}' repository branch '{}' '{}' ", key, repository, branch, path);
        return null;
    }


}
