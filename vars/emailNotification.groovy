def call(Map params) {
    def subject = params.subject
    def body = params.body
    def to = params.to
    def attachLog = params.attachLog ?: false

    def email = emailext(
        subject: subject,
        body: body,
        to: to,
    )
}
