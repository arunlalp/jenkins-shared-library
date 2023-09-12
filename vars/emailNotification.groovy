def call(Map config) {
    def subject = config.subject ?:
    def body = config.body ?: "Terraform pipeline notification message."
    def to = config.to ?: 'arunsample555@gmail.com'
    def attachLog = config.attachLog ?: false

    emailext(
        subject: subject,
        body: body,
        recipientProviders: [[$class: 'DevelopersRecipientProvider']],
        to: to,
        attachLog: attachLog,
    )
}
