deploy:
	rm xl-deploy-9.7.5-server/plugins/xld-github-dynamic-dictionaries-plugin-1.1.0*.xldp
	./gradlew clean build
	cp build/distributions/xld-github-dynamic-dictionaries-plugin-1.1.0-*.xldp xl-deploy-9.7.5-server/plugins/


