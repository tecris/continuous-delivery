# Planets

## Project generated using forge.
1. Install forge
2. Install AngularJS plugin
 1. start forge shell and execute `addon-install-from-git --url https://github.com/forge/angularjs-addon.git`
3. Build project: 
 1. `runForgeScript.sh buildProject.sh`
5. Deploy
  1. 'docker-compose up -d'
  1. 'mvn compile flyway:migrate'
  1. `mvn clean wildfly:deploy -Dwildfly.username=admin -Dwildfly.password=1admin!`
5. Continuous delivery
  1. In Jenkins create job with following:
   1. `mvn clean deploy`
   1. `mvn pre-integration-test`
