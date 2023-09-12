def emailNotification(String subject, String body, String recipient) {
    emailext(
        subject: subject,
        body: body,
        recipientProviders: [[$class: 'CulpritsRecipientProvider']],
        to: recipient,
        attachLog: true,
    )
}
