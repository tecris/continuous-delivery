# Planets

## How to generate project.
1. Install forge
2. Install AngularJS plugin
 1. start forge shell and execute `addon-install-from-git --url https://github.com/forge/angularjs-addon.git`
3. Generate project:
 1. `runForgeScript.sh buildProject.sh`
 1. Edit src/main/resources/META-INF/persistence.xml set property hibernate.hbm2ddl.auto to none
5. How to deploy
  1. 'docker-compose up -d'
  1. 'mvn compile flyway:migrate'
  1. `mvn clean wildfly:deploy -Dwildfly.username=admin -Dwildfly.password=1admin!`
5. Continuous delivery
  1. In Jenkins create job with following:
   1. `mvn clean deploy`
   1. `mvn integration-test -Pcontinuous-delivery`
