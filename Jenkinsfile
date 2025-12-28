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

        stage('Run Tests') {
            steps {
                bat 'mvn clean test -Dallure.results.directory=target\\allure-results'
            }
        }

        stage('Publish Allure Report') {
            steps {
                allure([
                    includeProperties: false,
                    jdk: '',
                    results: [[path: 'target/allure-results']]
                ])
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/allure-results/**', allowEmptyArchive: true
        }
    }
}
