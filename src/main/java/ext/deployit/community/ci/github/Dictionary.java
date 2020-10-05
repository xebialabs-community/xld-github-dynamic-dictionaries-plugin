package ext.deployit.community.ci.github;

import com.xebialabs.deployit.plugin.api.flow.Step;
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

    @Property(description = "OAuth2 token authentication", password = true)
    public String token;

    @Property(description = "The GitHub repository")
    public String repository;

    @Property(description = "The branch",defaultValue = "master")
    public String branch;

    @Property(description = "The path to the property file",defaultValue = "master")
    public String path;

    @ControlTask(label = "Check connection")
    public List<Step> checkConnection() {
        return null;
    }

    @Override
    public Map<String, String> loadData() {
        return super.loadData();
    }
}
