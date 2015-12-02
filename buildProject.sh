# --topLevelPackage - maven groupId
# --named - maven artifactId
project-new --named planets --topLevelPackage org.planets --finalName planets --type war
jpa-setup
jpa-new-entity --named Planet
jpa-new-field --named name
jpa-new-field --named galaxy
scaffold-setup --provider AngularJS
scaffold-generate --provider AngularJS --targets org.planets.model.Planet
rest-generate-endpoints-from-entities --targets org.planets.model.Planet
