def sendEmailNotification(String pipelineStatus, String recipientEmail) {
    def subject, body

    if (pipelineStatus == 'SUCCESS') {
        subject = "Pipeline Success"
        body = "The pipeline has successfully completed."
    } else if (pipelineStatus == 'FAILURE') {
        // Extract the failure reason from the pipeline log
        def buildLog = currentBuild.rawBuild.getLog(1000)
        def failureReason = extractFailureReason(buildLog)

        subject = "Pipeline Failed"
        body = "The pipeline has failed with the following reason:\n\n$failureReason\n\nPlease investigate."
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

def extractFailureReason(log) {
    def failureReason = ""
    def logLines = log.readLines()

    // Search for "Failed checks: 0" in the log
    if (logLines.find { it.contains("Failed checks: 0") }) {
        failureReason = "No check failures detected."
    } else {
        // Search for "FAILED for resource:" and "Guide:" in the log
        def resourceFailed = logLines.find { it.contains("FAILED for resource:") }
        def guide = logLines.find { it.contains("Guide:") }

        if (resourceFailed && guide) {
            failureReason = "Resource Failed: $resourceFailed\nGuide: $guide"
        } else {
            failureReason = "Unknown failure reason. Please check the pipeline log."
        }
    }

    return failureReason
}
