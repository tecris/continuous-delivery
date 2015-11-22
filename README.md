# Planets

## Project generated using forge.
1. Install forge
2. Install AngularJS plugin
 1. start forge shell and execute `addon-install-from-git --url https://github.com/forge/angularjs-addon.git`
3. Build project: 
 1. `runForgeScript.sh buildProject.sh`
4. Deploy to wildfly
 1. `mvn clean wildfly:deploy -Dwildfly.username=admin -Dwildfly.password=1admin!`
