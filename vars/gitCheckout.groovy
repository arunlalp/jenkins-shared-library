def call(Map config = [:]) {
    def gitUrl = config.gitUrl ?: 'https://github.com/arunlalp/java_code.git'
    def branch = config.branch ?: 'main'

    checkout([
        $class: 'GitSCM',
        branches: [[name: "refs/heads/$branch"]],
        userRemoteConfigs: [[url: gitUrl]]
    ])
}