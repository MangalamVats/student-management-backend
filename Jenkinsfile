pipeline {
    agent any

    environment {
        // Change these to match your setup
        IMAGE_NAME = "student-management-backend"
        IMAGE_TAG  = "latest"
        CONTAINER_NAME = "student-management-backend"
        APP_PORT = "8081"   // host port to expose the app on
    }

    tools {
        maven 'Maven3'   // must match the name configured in Manage Jenkins > Tools
        jdk 'JDK17'      // must match the name configured in Manage Jenkins > Tools
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
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Docker Build') {
            steps {
                sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
            }
        }

        stage('Docker Run') {
            steps {
                // Stop and remove old container if it exists, then run the new image
                sh """
                    docker stop ${CONTAINER_NAME} || true
                    docker rm ${CONTAINER_NAME} || true
                    docker run -d --name ${CONTAINER_NAME} -p ${APP_PORT}:${APP_PORT} ${IMAGE_NAME}:${IMAGE_TAG}
                """
            }
        }

        // Uncomment this stage if you want to push to Docker Hub / a registry
        /*
        stage('Docker Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh """
                        echo \$DOCKER_PASS | docker login -u \$DOCKER_USER --password-stdin
                        docker tag ${IMAGE_NAME}:${IMAGE_TAG} \$DOCKER_USER/${IMAGE_NAME}:${IMAGE_TAG}
                        docker push \$DOCKER_USER/${IMAGE_NAME}:${IMAGE_TAG}
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
