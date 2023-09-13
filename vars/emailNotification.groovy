def sendEmailNotification(String logText, String recipientEmail) {
    def subject, body

    if (logText.contains("Failed checks: 2")) {
        subject = "Pipeline Failed"
        body = "The pipeline has failed. Details:\n\n"
        
        // Split the log into lines
        def lines = logText.split('\n')
        
        // Iterate through the lines to find the failed checks and their guides
        for (line in lines) {
            if (line.contains("FAILED for resource")) {
                body += "\nFailed Check: ${line.trim()}"
                def guideIndex = lines.indexOf(line) + 1
                if (guideIndex < lines.size() && lines[guideIndex].startsWith("Guide:")) {
                    body += "\n${lines[guideIndex].trim()}"
                }
            }
        }
    } else {
        subject = "Pipeline Success"
        body = "The pipeline has successfully completed."
    }

    emailext(
        subject: subject,
        body: body,
        recipientProviders: [[$class: 'DevelopersRecipientProvider']],
        to: recipientEmail,
        attachLog: true
    )
}
