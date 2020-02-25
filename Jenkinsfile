pipeline {
  agent any
  stages {
    stage('Preparation') {
      steps {
        echo 'Getting code...'
        git(url: 'http://212.129.149.40/171250640_old2dimension/oceania.git', branch: 'master', credentialsId: 'git_account')
      }
    }

    stage('Unit Test') {
      steps {
        echo 'Testing...'
        sh 'mvn org.jacoco:jacoco-maven-plugin:prepare-agent -f pom.xml clean test -Dautoconfig.skip=true -Dmaven.test.skip=false -Dmaven.test.failure.ignore=true'
        junit '**/target/surefire-reports/*.xml'
        jacoco()
      }
    }

    stage('Static Analysis') {
      steps {
        echo 'Analyzing with Sonarqube...'
        sh 'mvn sonar:sonar -Dsonar.host.url=http://212.64.93.130:9999'
      }
    }

    stage('Build') {
      steps {
        echo 'Building...'
        sh 'mvn -Dmaven.test.failure.ignore -Dmaven.test.skip=true clean package'
      }
    }

    stage('Deployment') {
      environment {
        JENKINS_NODE_COOKIE = 'dontkillme'
        MVN_HOME = 'tool \'maven_3.3.9\''
      }
      steps {
        echo 'Deploying'
        sh '''pid=$(ps aux|grep OCEANIA|grep -v grep|awk \\\'{print $2}\\\')
if [ ! -n "$pid" ]; then  
        echo "OCEANIA is not running..."
else  
        kill -9 $pid
        echo "Killed OCEANIA..."
fi  
        
cd /var/jenkins_home/workspace/Oceania/target
nohup java -jar OCEANIA-0.0.1-SNAPSHOT.jar >temp.text & 
echo "OCEANIA running..."'''
      }
    }

    stage('Notification') {
      steps {
        echo 'Sending mails...'
      }
    }

  }
}