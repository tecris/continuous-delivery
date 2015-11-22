# --topLevelPackage - maven groupId
# --named - maven artifactId
project-new --named planets --topLevelPackage org.planets --finalName planets --type war
scaffold-setup
jpa-setup
jpa-new-entity --named Planet
jpa-new-field --named Name
jpa-new-field --named Galaxy
#jpa-new-field --named age --type int
scaffold-generate --provider AngularJS --targets org.planets.model.Planet
