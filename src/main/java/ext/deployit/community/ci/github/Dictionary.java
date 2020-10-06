package ext.deployit.community.ci.github;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xebialabs.deployit.plugin.api.flow.ExecutionContext;
import com.xebialabs.deployit.plugin.api.flow.Step;
import com.xebialabs.deployit.plugin.api.flow.StepExitCode;
import com.xebialabs.deployit.plugin.api.udm.*;
import com.xebialabs.deployit.plugin.api.udm.base.BaseConfigurationItem;
import ext.deployit.community.ci.dictionary.BaseDynamicDictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Metadata(root = Metadata.ConfigurationItemRoot.ENVIRONMENTS, description = "a Github Dictionary ")
@TypeIcon(value = "icons/types/github-logo.svg")
public class Dictionary extends BaseDynamicDictionary {

    private static Logger logger = LoggerFactory.getLogger(Dictionary.class);

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

    @Property(description = "Use the version of the package as the tag to get the data from GitHub", required = false, defaultValue = "false")
    public boolean useDeployedPackageVersion;

    @Property(description = "Version detected as the deployed package", required = false, isTransient = true, hidden = true)
    public String detectedVersion;

    @Property(description = "Prefix used by the tag", required = false, category = "Others", defaultValue = "v")
    public String githubVersionPrefix;

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
                executionContext.logOutput("open connection on the GitHub repository " + repository + " branch" + branch);
                GithubClient githubClient = new GithubClient(api, token, repository, branch);
                githubClient.connect();
                executionContext.logOutput("connected !");
                executionContext.logOutput("read " + path);
                ImmutableMap<String, String> map = Maps.fromProperties(githubClient.readProperties(path));
                executionContext.logOutput("content is ");
                executionContext.logOutput(map.toString());
                executionContext.logOutput("done");
                return StepExitCode.SUCCESS;
            }
        };
        return Lists.newArrayList(step);
    }

    @Override
    public Map<String, String> loadData() {

        long start = System.currentTimeMillis();
        try {
            String currentBranch = branch;
            if (this.useDeployedPackageVersion) {
                currentBranch = this.detectedVersion;
            }
            logger.debug("loadData from the GitHub repository '{}' on the branch {}')", repository, currentBranch);
            GithubClient githubClient = new GithubClient(api, token, repository, currentBranch);
            githubClient.connect();
            logger.debug("read ({})", path);
            return Maps.fromProperties(githubClient.readProperties(path));
        } finally {
            long stop = System.currentTimeMillis();
            long duration = stop - start;
            logger.debug("loaded Data from github took {} ms", new Long(duration));
        }
    }

    @Override
    public IDictionary applyTo(DictionaryContext context) {
        logger.debug("{} Dictionary.applyTo {}", this, context);
        if (context.getApplication() != null)
            logger.debug("context.getApplication().getName() = {} ", context.getApplication().getName());

        if (context.getApplicationVersion() != null) {
            logger.debug("context.getApplicationVersion().getName() = {} ", context.getApplicationVersion().getName());
            this.detectedVersion = githubVersionPrefix + context.getApplicationVersion().getName();
        }

        if (context.getContainer() != null)
            logger.debug("context.getContainer().getName() = {}", context.getContainer().getName());
        if (context.getEnvironment() != null)
            logger.debug("context.getEnvironment().getName() = {}", context.getEnvironment().getName());
        logger.debug("/ {} Dictionary.applyTo {}", this, context);
        return super.applyTo(context);
    }


}
