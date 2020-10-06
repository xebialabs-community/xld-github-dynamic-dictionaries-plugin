package ext.deployit.community.ci.github;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xebialabs.deployit.plugin.api.flow.ExecutionContext;
import com.xebialabs.deployit.plugin.api.flow.Step;
import com.xebialabs.deployit.plugin.api.flow.StepExitCode;
import com.xebialabs.deployit.plugin.api.udm.ControlTask;
import com.xebialabs.deployit.plugin.api.udm.Metadata;
import com.xebialabs.deployit.plugin.api.udm.Property;
import com.xebialabs.deployit.plugin.api.udm.TypeIcon;
import com.xebialabs.deployit.plugin.api.udm.base.BaseConfigurationItem;
import ext.deployit.community.ci.dictionary.BaseDynamicDictionary;

import java.util.List;
import java.util.Map;

@Metadata(root = Metadata.ConfigurationItemRoot.ENVIRONMENTS, description = "a Github Dictionary ")
@TypeIcon(value = "icons/types/github-logo.svg")
public class Dictionary extends BaseDynamicDictionary {

    @Property(description = "Address of GitHub", defaultValue = "api.github.com")
    public String api;

    @Property(description = "OAuth2 token authentication, can be empty for access a public repository", password = true, required = false)
    public String token;

    @Property(description = "The GitHub repository owner/repo")
    public String repository;

    @Property(description = "The branch or tag in the repository", defaultValue = "master")
    public String branch;

    @Property(description = "The path to the property file")
    public String path;

    @ControlTask(label = "Check connection")
    public List<Step> checkConnection() {
        Step step = new Step() {

            @Override
            public int getOrder() {
                return 0;
            }

            @Override
            public String getDescription() {
                return "Check GitHub connection on " + getName();
            }

            @Override
            public StepExitCode execute(ExecutionContext executionContext) throws Exception {
                GithubClient githubClient = new GithubClient(api, token, repository, branch);
                githubClient.connect();
                executionContext.logOutput("connected ");
                ImmutableMap<String, String> map = Maps.fromProperties(githubClient.readProperties(path));
                executionContext.logOutput("content is " );
                executionContext.logOutput(map.toString());
                return StepExitCode.SUCCESS;
            }
        };
        return Lists.newArrayList(step);
    }

    @Override
    public Map<String, String> loadData() {
        GithubClient githubClient = new GithubClient(api, token, repository, branch);
        githubClient.connect();
        return Maps.fromProperties(githubClient.readProperties(path));
    }
}
