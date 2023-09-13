def sendEmailNotification(String pipelineStatus, String recipientEmail, String PROJECT_NAME, String BUILD_NUMBER) {
    def subject, body

    if (pipelineStatus == 'success') {
        subject = "Pipeline Success: ${PROJECT_NAME} - ${BUILD_NUMBER}"
        body = "The pipeline has successfully completed."
    } else if (pipelineStatus == 'failure') {
        subject = "Pipeline Failed: ${PROJECT_NAME} - ${BUILD_NUMBER}"
        body = "The pipeline has failed. Please investigate."
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
