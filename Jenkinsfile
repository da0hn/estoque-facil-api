pipeline {
  agent any

  environment {
    APP_NAME = 'estoque-facil-api'
    IMAGE = "anunciabem.com.br/${APP_NAME}"
    IMAGE_LATEST = "${env.IMAGE}:latest"
    HOST_PORT = '8200'
    CONTAINER_PORT = '8080'
    JAVA_HOME = '/usr/lib/jvm/amazon-corretto-21.0.2.13.1-linux-x64'
    CONTAINER_VOLUME = ''
  }

  stages {
    stage('Building application') {
      steps {
        sh 'rm -rf ./target'
        sh 'chmod +x ./mvnw'
        sh "./mvnw clean package -Dmaven.compiler.executable=${JAVA_HOME}/bin/javac"
        sh "git rev-parse --short HEAD > commit-id"
      }
    }
    stage('Building Docker Image') {
      environment {
        APP_VERSION = sh script: "./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout -Dmaven.compiler.executable=${JAVA_HOME}/bin/javac", returnStdout: true
        TAG = readFile("commit-id").replace("\n", "").replace("\r", "")
        TAGGED_IMAGE = "${env.IMAGE}:${env.APP_VERSION}-${env.BUILD_NUMBER}"
      }

      steps {
        echo "Criando a imagem: ${env.TAGGED_IMAGE}, latest: ${env.IMAGE_LATEST}"
        sh "docker build -t ${env.TAGGED_IMAGE} ."
        sh "docker tag ${env.TAGGED_IMAGE} ${env.IMAGE_LATEST}"
        sh "docker stop ${APP_NAME} || true"
        sh "docker wait ${APP_NAME} || true"
        sh "docker container prune -f"
        sh "docker run -d --name ${APP_NAME} -p ${HOST_PORT}:${CONTAINER_PORT} " +
            "-e ESTOQUE_FACIL_DB_HOST=${ESTOQUE_FACIL_DB_HOST} " +
            "-e ESTOQUE_FACIL_DB_PORT=${ESTOQUE_FACIL_DB_PORT} " +
            "-e ESTOQUE_FACIL_DB_USER=${ESTOQUE_FACIL_DB_USER} " +
            "-e ESTOQUE_FACIL_DB_PASSWORD=${ESTOQUE_FACIL_DB_PASSWORD} " +
            "-t ${TAGGED_IMAGE}"
      }
    }
  }
}
