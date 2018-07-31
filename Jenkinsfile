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
              sh 'TAG=dev docker-compose up -d flyway-migrate'
          }
       }
       stage('Run tests') {
          steps {
              sh 'mvn clean verify -Dmaven.test.failure.ignore=false -Dtest.port=8070'
          }
       }
    }
}

