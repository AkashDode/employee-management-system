pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/AkashDode/employee-management-system.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh '''
                    mvn sonar:sonar \
                    -Dsonar.projectKey=employee-management-system
                    '''
                }
            }
        }

        stage('Archive WAR') {
            steps {
                archiveArtifacts artifacts: 'target/*.war'
            }
        }

        stage('Upload WAR to S3') {
            steps {
                withCredentials([[
                    $class: 'AmazonWebServicesCredentialsBinding',
                    credentialsId: 'aws-s3'
                ]]) {

                    sh '''
                    export AWS_DEFAULT_REGION=us-west-1

                    echo "Verifying AWS Identity..."
                    aws sts get-caller-identity

                    echo "Uploading WAR to S3..."
                    aws s3 cp \
                    target/employee-management-system.war \
                    s3://employee-management-artifacts-akash
                    '''
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline executed successfully!'
        }

        failure {
            echo 'Pipeline execution failed!'
        }

        always {
            echo 'Pipeline execution completed.'
        }
    }
}