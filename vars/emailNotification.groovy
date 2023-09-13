def sendEmailNotification(String pipelineStatus, String recipientEmail) {
    def subject, body, consoleLog

    if (pipelineStatus == 'success') {
        subject = "Pipeline Success"
        body = "The pipeline has successfully completed."
    } else if (pipelineStatus == 'failure') {
        subject = "Pipeline Failed"
        body = "The pipeline has failed. Please investigate."

        // Capture the console output
        script {
            consoleLog = currentBuild.rawBuild.getLog(1000)
        }

        // Find the third line starting with 'FAILED for resource:'
        def failedLines = []
        consoleLog.each { line ->
            if (line.contains('FAILED for resource:')) {
                failedLines.add(line)
                if (failedLines.size() == 3) {
                    body += "\n\nConsole Log (Third line starting with 'FAILED for resource:'):\n${failedLines[2]}"
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
        attachLog: false // We won't attach the full log here
    )

    // Save the console output to a file
    writeFile file: '/var/lib/jenkins/consolelog.txt', text: consoleLog
}
