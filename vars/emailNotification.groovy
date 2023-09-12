def call(Map<String, String> params) {
    def subject = params.subject
    def body = params.body
    def to = params.to
    def attachLog = params.attachLog ?: false

    emailNotification(subject, body, to, attachLog)
}    

def emailNotification(subject, body, to, attachLog) {
    emailtext(
        subject: subject,
        body: body,
        to: to
    )
}
   
    
