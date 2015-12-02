# --topLevelPackage - maven groupId
# --named - maven artifactId
project-new --named planets --topLevelPackage org.planets --finalName planets --type war
scaffold-setup
jpa-setup
jpa-new-entity --named Planet
jpa-new-field --named name
jpa-new-field --named galaxy
#jpa-new-field --named age --type int
scaffold-generate --provider AngularJS --targets org.planets.model.Planet
rest-generate-endpoints-from-entities --targets org.planets.model.Planet
