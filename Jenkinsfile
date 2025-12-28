pipeline {
    agent any

    tools {
        jdk 'jdk-17.0.12'
        maven 'Maven'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                bat 'mvn clean test -Dallure.results.directory=target\\allure-results'
            }
        }

        stage('Publish Allure Report') {
            steps {
                allureReport(
                    name: 'Allure Report',
                    path: 'target/allure-results'
                )
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/allure-results/**', allowEmptyArchive: true
        }
    }
}
