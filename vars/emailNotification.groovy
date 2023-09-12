def call(Map params) {
    def subject = params.subject
    def body = params.body
    def to = params.to
    def attachLog = params.attachLog ?: false

    def emailext(
        subject: $subject,
        body: $body,
        recipientProviders: [[$class: 'DevelopersRecipientProvider']],
        to: $to,
        attachLog: $attachLog,
    )
}
