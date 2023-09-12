def call(Map config) {
    def to = config.to ?: error("'to' parameter is required for sending the email.")
    def status = config.status ?: "Notification"
    def successSubject = config.successSubject ?: "${status} Success"
    def failureSubject = config.failureSubject ?: "${status} Failure"
    def body = config.body ?: "${status} notification message."
    def attachLog = config.attachLog ?: false

    def subject = currentBuild.resultIsBetterOrEqualTo("SUCCESS") ? successSubject : failureSubject

    emailext(
        subject: subject,
        body: body,
        recipientProviders: [[$class: 'DevelopersRecipientProvider']],
        to: to,
        attachLog: attachLog,
    )
}
