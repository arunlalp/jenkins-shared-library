def call(Map config) {
    if (!config.subject || !config.body || !config.to) {
        error("Required parameters (subject, body, and to) are missing.")
    }

    emailext(
        subject: config.subject,
        body: config.body,
        recipientProviders: [[$class: 'DevelopersRecipientProvider']],
        to: config.to,
        attachLog: config.attachLog ?: false,
    )
}
