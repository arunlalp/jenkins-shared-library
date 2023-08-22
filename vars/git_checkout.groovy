def call(Map parameters) {
    def branch = parameters.branch ?: 'main'
    def repoUrl = parameters.repoUrl ?: ''
    
    checkout([$class: 'GitSCM', 
              branches: [[name: "*/${branch}"]], 
              doGenerateSubmoduleConfigurations: false, 
              extensions: [], 
              userRemoteConfigs: [[url: repoUrl]]])
}
