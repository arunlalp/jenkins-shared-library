def call(Map successConfig = [:], Map failureConfig = [:]) {
    def defaultSubject = "Terraform Pipeline Notification"
    def defaultTo = 'arunsample555@gmail.com'
    def defaultAttachLog = false

    // Success email configuration
    def successSubject = successConfig.subject ?: defaultSubject
    def successBody = successConfig.body ?: "Terraform pipeline has successfully completed."
    def successTo = successConfig.to ?: defaultTo
    def successAttachLog = successConfig.attachLog ?: defaultAttachLog

    // Failure email configuration
    def failureSubject = failureConfig.subject ?: defaultSubject
    def failureBody = failureConfig.body ?: "Terraform pipeline has failed. Please investigate."
    def failureTo = failureConfig.to ?: defaultTo
    def failureAttachLog = failureConfig.attachLog ?: defaultAttachLog

    post {
        success {
            emailext(
                subject: successSubject,
                body: successBody,
                recipientProviders: [[$class: 'DevelopersRecipientProvider']],
                to: successTo,
                attachLog: successAttachLog,
            )
        }
        failure {
            emailext(
                subject: failureSubject,
                body: failureBody,
                recipientProviders: [[$class: 'DevelopersRecipientProvider']],
                to: failureTo,
                attachLog: failureAttachLog,
            )
        }
    }
}
