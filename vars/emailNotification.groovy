def sendEmailNotification(String pipelineStatus, String recipientEmail) {
    def buildNotification = libraryResource 'notification/notify.tpl'
    def subject, body

    if (pipelineStatus == 'success') {
        subject = "Terraform Pipeline Success"
        body = "${buildNotification}/"
    } else if (pipelineStatus == 'failure') {
        subject = "Terraform Pipeline Failed"
        body = "${buildNotification}"
    } else {
        subject = "Terraform Pipeline Status: $pipelineStatus"
        body = "The Terraform pipeline is in an unknown status: $pipelineStatus"
    }

    emailext(
        subject: subject,
        body: body,
        recipientProviders: [[$class: 'CulpritsRecipientProvider']],
        to: recipientEmail
    )
}
