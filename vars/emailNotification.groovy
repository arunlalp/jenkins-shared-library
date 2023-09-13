def sendEmailNotification(log, pipelineStatus, recipientEmail) {
    def subject, body

    if (pipelineStatus == 'success') {
        subject = "Pipeline Success"
        body = "The pipeline has successfully completed."
    } else if (pipelineStatus == 'failure') {
        subject = "Pipeline Failed"
        body = "The pipeline has failed. Please investigate.\n\n"
        
        // Check if the log contains "Failed checks: 2"
        if (log.contains("Failed checks: 2")) {
            // Split the log by "Check:" to extract individual checks
            def checks = log.split("Check:")
            
            // Loop through the checks to find failed checks
            checks.each { check ->
                if (check.contains("FAILED for resource")) {
                    // Extract the failed resource and guide information
                    def failedResource = check.substringAfter("FAILED for resource:").trim()
                    def guide = check.substringAfter("Guide:").trim()
                    
                    // Add the failed resource and guide information to the email body
                    body += "FAILED for resource: $failedResource\nGuide: $guide\n\n"
                }
            }
        }
    } else {
        subject = "Pipeline Status: $pipelineStatus"
        body = "The pipeline is in an unknown status: $pipelineStatus"
    }

    emailext(
        subject: subject,
        body: body,
        recipientProviders: [[$class: 'DevelopersRecipientProvider']],
        to: recipientEmail,
        attachLog: true
    )
}
