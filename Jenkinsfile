node {
    def mvnHome
    stage('Preparation') { // for display purposes
        echo 'Getting code...'
        // Get some code from a GitHub repository
        git branch: 'master', credentialsId: 'git_account', url: 'http://212.129.149.40/171250640_old2dimension/oceania.git'
        // Get the Maven tool.
        // ** NOTE: This 'M3' Maven tool must be configured
        // **       in the global configuration.           
        mvnHome = tool 'maven_3.3.9'
    }
    stage('Unit Test') {
        echo 'Testing...'
        sh "mvn org.jacoco:jacoco-maven-plugin:prepare-agent -f pom.xml clean test -Dautoconfig.skip=true -Dmaven.test.skip=false -Dmaven.test.failure.ignore=true"
        junit '**/target/surefire-reports/*.xml'
        jacoco()
    }
    stage('Static Analysis') {
        echo 'Analyzing with Sonarqube...'
        withEnv(["MVN_HOME=$mvnHome"]) {
            sh '"$MVN_HOME/bin/mvn" sonar:sonar -Dsonar.host.url=http://212.64.93.130:9999'
        }
    }
    stage('Build') {
        echo 'Building...'
        // Run the maven build
        withEnv(["MVN_HOME=$mvnHome"]) {
            if (isUnix()) {
                sh '"$MVN_HOME/bin/mvn" -Dmaven.test.failure.ignore -Dmaven.test.skip=true clean package'
            } else {
                bat(/"%MVN_HOME%\bin\mvn" -Dmaven.test.failure.ignore clean package/)
            }
        }
    }
    stage('Deployment') {
        echo 'Deploying...'
        withEnv(["JENKINS_NODE_COOKIE=dontkillme"]) {
        sh label: '', script: '''
        pid=$(ps aux|grep OCEANIA|grep -v grep|awk \'{print $2}\')
        if [ ! -n "$pid" ]; then  
            echo "OCEANIA is not running..."
        else  
	        kill -9 $pid
            echo "Killed OCEANIA..."
        fi  
        
        cd /var/jenkins_home/workspace/Oceania/target
        nohup java -jar OCEANIA-0.0.1-SNAPSHOT.jar >temp.text & 
        echo "OCEANIA running..."
        '''
        }
    }
    stage('Notification') {
        emailext body: '$DEFAULT_CONTENT', subject: '$DEFAULT_SUBJECT', to: '$DEFAULT_RECIPIENTS'
    }
}
