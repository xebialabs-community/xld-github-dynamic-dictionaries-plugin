deploy:
	rm xl-deploy-9.7.5-server/plugins/xld-github-dynamic-dictionaries-plugin-1.1.0*.xldp
	./gradlew clean build -PGithubClientTestToken=${GithubClientTestToken}
	cp build/distributions/xld-github-dynamic-dictionaries-plugin-1.1.0-*.xldp xl-deploy-9.7.5-server/plugins/


gen:
	xl --config config.yaml generate xl-deploy -p Configuration -f xebialabs/configuration.yaml
	xl --config config.yaml generate xl-deploy -p Applications -f xebialabs/applications.yaml
	xl --config config.yaml generate xl-deploy -p Infrastructure -f xebialabs/infrastructure.yaml
	xl --config config.yaml generate xl-deploy -s -p Environments -f xebialabs/environments.yaml
