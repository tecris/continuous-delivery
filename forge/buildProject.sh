# --topLevelPackage - maven groupId
# --named - maven artifactId
project-new --named planets --topLevelPackage org.planets --finalName planets --type war
jpa-setup --container WILDFLY --provider Hibernate --persistenceUnitName "pl-persistence-unit" --dataSourceName java:/datasources/MySqlDS --dbType MYSQL5_INNODB
jpa-new-entity --named Planet --idStrategy IDENTITY
jpa-new-field --named name
jpa-new-field --named galaxy
scaffold-setup --provider AngularJS
scaffold-generate --provider AngularJS --targets org.planets.model.Planet
rest-generate-endpoints-from-entities --targets org.planets.model.Planet
