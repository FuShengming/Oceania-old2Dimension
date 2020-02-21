pipeline {
    stages {
        stage('Init') {
            def maven_home = tool 'maven_3.6.3'
            def workspace = pwd()
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