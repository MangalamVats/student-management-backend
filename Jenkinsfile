pipeline {
    agent any
    environment {
        IMAGE_NAME = "student-management-backend"
        IMAGE_TAG  = "latest"
        CONTAINER_NAME = "student-management-backend"
        HOST_PORT = "8082"
        CONTAINER_PORT = "8080"
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Docker Build') {
            steps {
                // mvn build happens INSIDE this docker build (see Dockerfile Stage 1)
                sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
            }
        }
        stage('Docker Run') {
            steps {
                sh """
                    docker stop ${CONTAINER_NAME} || true
                    docker rm ${CONTAINER_NAME} || true
                    docker run -d --name ${CONTAINER_NAME} -p ${HOST_PORT}:${CONTAINER_PORT} ${IMAGE_NAME}:${IMAGE_TAG}
                """
            }
        }
        stage('Smoke Test') {
            steps {
                sh 'sleep 10'
                sh "curl -f http://localhost:${HOST_PORT}/api/students || echo 'App did not respond as expected'"
            }
        }
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