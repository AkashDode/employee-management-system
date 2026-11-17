pipeline {

    agent any

    tools {

        maven 'Maven3'
    }

    stages {

        stage('Checkout') {

            steps {

                git branch: 'main',
                url: 'https://github.com/YOUR_USERNAME/employee-management-system.git'
            }
        }

        stage('Build') {

            steps {

                sh 'mvn clean compile'
            }
        }

        stage('Test') {

            steps {

                sh 'mvn test'
            }
        }

        stage('Package') {

            steps {

                sh 'mvn clean package'
            }
        }

        stage('SonarQube') {

            steps {

                withSonarQubeEnv('SonarQube') {

                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Upload WAR to S3') {

            steps {

                sh '''
                aws s3 cp \
                target/employee-management-system.war \
                s3://employee-artifacts-bucket/
                '''
            }
        }
    }
}