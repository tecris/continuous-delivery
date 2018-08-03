pipeline {
    agent {
        docker {
            image 'trivialis/maven:3'
            args '-v /root/.m2:/root/.m2'
        }
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Start services') {
           steps {
               sh 'TAG=dev docker-compose up -d ackris-db ackris-web'
           }
       }
       stage('Apply db changes') {
          steps {
              sh 'TAG=dev docker-compose -f docker-compose.yaml -f docker-compose-jenkins.yaml -f docker-compose-cd.yaml up flyway-migrate'
          }
       }
       stage('Run tests') {
          steps {
              sh 'docker-compose -f docker-compose.yaml -f docker-compose-jenkins.yaml -f docker-compose-cd.yaml up maven-test'
          }
       }
       stage('Stop & destroy services') {
          steps {
              sh 'TAG=dev docker-compose down'
          }
      }
    }
    post {
     always {
         junit 'target/failsafe-reports/**/*.xml'
     }
   }
}
