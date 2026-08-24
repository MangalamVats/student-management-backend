pipeline {
    agent any

    environment {
        // Change these to match your setup
        IMAGE_NAME = "student-management-backend"
        IMAGE_TAG  = "latest"
        CONTAINER_NAME = "student-management-backend"
        HOST_PORT = "8082"       // port on your machine you'll browse to (8080 is taken by Jenkins)
        CONTAINER_PORT = "8081"  // port the app listens on INSIDE the container (matches server.port)
    }

    tools {
        maven 'Maven3'   // must match the name configured in Manage Jenkins > Tools
        jdk 'JDK21'      // must match the name configured in Manage Jenkins > Tools
    }

    stages {

        stage('Checkout') {
            steps {
                // Pulls code from the repo configured in the Jenkins job (Pipeline script from SCM)
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
            post {
                always {
                    junit testResults: '**/target/surefire-reports/*.xml', allowEmptyResults: true
                }
            }
        }

        stage('Docker Build') {
            steps {
                bat "docker build -t %IMAGE_NAME%:%IMAGE_TAG% ."
            }
        }

        stage('Docker Run') {
            steps {
                // Stop and remove old container if it exists, then run the new image
                bat """
                    docker stop %CONTAINER_NAME% || exit 0
                    docker rm %CONTAINER_NAME% || exit 0
                    docker run -d --name %CONTAINER_NAME% -p %HOST_PORT%:%CONTAINER_PORT% %IMAGE_NAME%:%IMAGE_TAG%
                """
            }
        }

        // Uncomment this stage if you want to push to Docker Hub / a registry
        /*
        stage('Docker Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    bat """
                        echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin
                        docker tag %IMAGE_NAME%:%IMAGE_TAG% %DOCKER_USER%/%IMAGE_NAME%:%IMAGE_TAG%
                        docker push %DOCKER_USER%/%IMAGE_NAME%:%IMAGE_TAG%
                    """
                }
            }
        }
        */
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed. Check the logs above.'
        }
    }
}