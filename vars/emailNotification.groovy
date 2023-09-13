def sendEmailNotification(String pipelineStatus, String recipientEmail) {
    def subject, body

    if (pipelineStatus == 'success') {
        subject = "Pipeline Success"
        body = "The pipeline has successfully completed."
    } else if (pipelineStatus == 'failure') {
        subject = "Pipeline Failed"
        // Automatically extract failure reason from the Jenkins build log
        body = getFailureReason()
    } else {
        subject = "Pipeline Status: $pipelineStatus"
        body = "The pipeline is in an unknown status: $pipelineStatus"
    }

    emailext(
        subject: subject,
        body: body,
        recipientProviders: [[$class: 'DevelopersRecipientProvider']],
        to: recipientEmail,
        attachLog: true
    )
}

def getFailureReason() {
    def buildLog = currentBuild.rawBuild.getLog(1000)
    // Extract the failure reason from the log
    def failureReason = buildLog.readLines().findAll { line ->
        // Add your logic to find the failure reason in the log
        // Example: return line.contains("FailureKeyword")
    }.join('\n')

    if (failureReason.isEmpty()) {
        return "The reason for failure could not be determined."
    } else {
        return "The pipeline has failed with the following reason:\n${failureReason}"
    }
}

// Example usage:
// sendEmailNotification('failure', 'recipient@example.com')
