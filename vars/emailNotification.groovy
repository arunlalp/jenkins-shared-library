def call(Map successConfig = [:], Map failureConfig = [:]) {
    def successSubject = successConfig.subject ?: "Terraform Pipeline Success"
    def successBody = successConfig.body ?: "The Terraform pipeline has successfully completed."
    def successTo = successConfig.to ?: 'arunsample555@gmail.com'
    def successAttachLog = successConfig.attachLog ?: false

    def failureSubject = failureConfig.subject ?: "Terraform Pipeline Failed"
    def failureBody = failureConfig.body ?: "The Terraform pipeline has failed. Please investigate."
    def failureTo = failureConfig.to ?: 'arunsample555@gmail.com'
    def failureAttachLog = failureConfig.attachLog ?: false

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
