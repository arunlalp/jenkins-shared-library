def sendSuccessEmail(Map config) {
    def subject = config.subject ?: "Terraform Pipeline Success"
    def body = config.body ?: "The Terraform pipeline has successfully completed."
    def to = config.to ?: 'arunsample555@gmail.com'
    def attachLog = config.attachLog ?: true

    emailext(
        subject: subject,
        body: body,
        recipientProviders: [[$class: 'DevelopersRecipientProvider']],
        to: to,
        attachLog: attachLog,
    )
}

def sendFailureEmail(Map config) {
    def subject = config.subject ?: "Terraform Pipeline Failed"
    def body = config.body ?: "The Terraform pipeline has failed. Please investigate."
    def to = config.to ?: 'arunsample555@gmail.com'
    def attachLog = config.attachLog ?: true

    emailext(
        subject: subject,
        body: body,
        recipientProviders: [[$class: 'DevelopersRecipientProvider']],
        to: to,
        attachLog: attachLog,
    )
}
