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

        stage('Generate Allure Report') {
            steps {
                bat '''
                if exist target\\allure-report rmdir /s /q target\\allure-report
                allure generate target\\allure-results -o target\\allure-report --clean
                '''
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/allure-report/**', allowEmptyArchive: true
        }
    }
}
