def call(Map buildParams) {
    
    def dockerImage = docker.build("${buildParams.imageName}:${buildParams.versionTag}")

}
