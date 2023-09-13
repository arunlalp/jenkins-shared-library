import hudson.FilePath

def sendEmailNotification(String pipelineStatus, String recipientEmail) {
    def subject, body

    if (pipelineStatus == 'success') {
        subject = "Pipeline Success"
        body = "The pipeline has successfully completed."
    } else if (pipelineStatus == 'failure') {
        subject = "Pipeline Failed"
        body = "The pipeline has failed. Please investigate."
    } else {
        subject = "Pipeline Status: $pipelineStatus"
        body = "The pipeline is in an unknown status: $pipelineStatus"
    }

    // Search for the build.log file in the Jenkins workspace or project directory
    def workspace = currentBuild.rawBuild.workspace
    def buildLogFilePath = findBuildLog(workspace)

    // Attach the extracted line to the email body if the build.log file was found
    if (buildLogFilePath) {
        def failedLine = extractFailedLine(buildLogFilePath)
        if (failedLine) {
            body += "\n\nFailed Line from build.log:\n$failedLine"
        }
    }

    emailext(
        subject: subject,
        body: body,
        recipientProviders: [[$class: 'DevelopersRecipientProvider']],
        to: recipientEmail,
        attachLog: true
    )
}

def findBuildLog(workspace) {
    def logFilePath = null
    try {
        def workspaceFilePath = new FilePath(workspace)
        workspaceFilePath.list().each { filePath ->
            if (filePath.getName() == 'build.log') {
                logFilePath = filePath.getRemote()
                return false // Stop searching once the file is found
            }
        }
    } catch (Exception e) {
        println("Error searching for build.log: ${e.message}")
    }
    return logFilePath
}

def extractFailedLine(String filePath) {
    def failedLine = null
    try {
        new File(filePath).eachLine { line ->
            if (line.startsWith('FAILED for resource:')) {
                failedLine = line
                return false // Stop reading after finding the line
            }
        }
    } catch (Exception e) {
        println("Error reading build.log: ${e.message}")
    }
    return failedLine
}
