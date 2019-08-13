node {
   def mvnHome
   stage('Preparation') { // for display purposes
      git 'https://github.com/ryoshino2/ccs-mark-consumer-001.git'
   }
   stage('Test') {
            sh 'mvn test'
      }
    stage('Build'){
            sh 'mvn package'
    }
}