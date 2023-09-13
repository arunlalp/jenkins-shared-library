def sendEmailNotification(String pipelineStatus, String recipientEmail, String consoleOutput) {
    def subject, body

    if (pipelineStatus == 'success') {
        subject = "Pipeline Success"
        body = "The pipeline has successfully completed."
    } else if (pipelineStatus == 'failure') {
        subject = "Pipeline Failed"
        body = "The pipeline has failed. Please investigate."

        // Extract the line starting with 'FAILED for resource:' from console output
        def failedLine = consoleOutput =~ /FAILED for resource:.+/
        if (failedLine) {
            body += "\n\nFailed Line: ${failedLine[0]}"
        }
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
