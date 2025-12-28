pipeline {
    agent any

    tools {
        jdk 'JDK11'
        maven 'Maven3'
        allure 'Allure'
    }

    environment {
        CI = 'true'
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/mchourasia88/MyPlaywrightProject.git'
            }
        }

        stage('Install Playwright Browsers') {
            steps {
                sh 'npx playwright install --with-deps'
            }
        }

        stage('Run Automation Tests') {
            steps {
                sh '''
                    mvn clean test -Dallure.results.directory=target/allure-results
                '''
            }
        }
    }

    post {
        always {
            allure(
                includeProperties: false,
                results: [[path: 'target/allure-results']]
            )
        }

        failure {
            echo '❌ Tests failed. Please check Allure report.'
        }

        success {
            echo '✅ Tests executed successfully.'
        }
    }
}
