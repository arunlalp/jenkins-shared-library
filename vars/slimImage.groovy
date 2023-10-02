def slimImage(String imageNameAndTag) {
    def command = "docker-slim build ${imageNameAndTag}"
    def process = command.execute()
    def outputStream = new StringBuffer()
    def errorStream = new StringBuffer()
    def exitCode = process.waitForProcessOutput(outputStream, errorStream)
}
