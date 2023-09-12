def call(Map config) {
    def successSubject = config.successSubject ?: "Terraform Pipeline Success"
    def successBody = config.successBody ?: "The Terraform pipeline has successfully completed."
    def failureSubject = config.failureSubject ?: "Terraform Pipeline Failed"
    def failureBody = config.failureBody ?: "The Terraform pipeline has failed. Please investigate."
    def to = config.to ?: 'arunsample555@gmail.com'
    def attachLog = config.attachLog ?: false

    post {
        success {
            emailext(
                subject: successSubject,
                body: successBody,
                recipientProviders: [[$class: 'DevelopersRecipientProvider']],
                to: to,
                attachLog: attachLog,
            )
        }
        failure {
            emailext(
                subject: failureSubject,
                body: failureBody,
                recipientProviders: [[$class: 'DevelopersRecipientProvider']],
                to: to,
                attachLog: attachLog,
            )
        }
    }
}

