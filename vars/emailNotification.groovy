// emailNotification.groovy

def sendEmailNotification(String pipelineStatus, String recipientEmail) {
    def subject, body

    if (pipelineStatus == 'success') {
        subject = "Terraform Pipeline Success"
        body = "The Terraform pipeline has successfully completed."
    } else if (pipelineStatus == 'failure') {
        subject = "Terraform Pipeline Failed"
        body = "The Terraform pipeline has failed. Please investigate."
    } else {
        // Handle other pipeline statuses as needed
        subject = "Terraform Pipeline Status: $pipelineStatus"
        body = "The Terraform pipeline is in an unknown status: $pipelineStatus"
    }

    emailext(
        subject: subject,
        body: body,
        recipientProviders: [[$class: 'CulpritsRecipientProvider']],
        to: recipientEmail,
        attachLog: true
    )
}
