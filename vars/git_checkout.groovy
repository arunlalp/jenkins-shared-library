def call() {
    checkout scm: [$class: 'GitSCM', branches: [[name: 'main']], userRemoteConfigs: [[url: 'https://github.com/arunlalp/java_code.git']]]
}
