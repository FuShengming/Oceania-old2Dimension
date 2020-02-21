pipeline {
    agent any
    def maven_home
    def workspace
    stages {
        stage('Init') {
            maven_home = tool 'maven_3.6.3'
            workspace = pwd()
        }
        stage('Build') {
            steps {
                echo 'Building..'
                sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package"
            }
        }
        stage('Test') {
            steps {
                echo 'TODO:Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'TODO:Deploying....'
            }
        }
    }
}