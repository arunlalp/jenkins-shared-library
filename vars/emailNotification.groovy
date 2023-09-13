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

        // Find the line starting with 'FAILED for resource:'
        def failedLineIndex = -1
        consoleLog.eachWithIndex { line, index ->
            if (line.startsWith('FAILED for resource:')) {
                failedLineIndex = index
            }
        }

        // If a 'FAILED for resource:' line is found, grab the line starting with 'Guide:'
        if (failedLineIndex != -1 && failedLineIndex + 1 < consoleLog.size()) {
            def guideLine = consoleLog[failedLineIndex + 1]
            body += "\n\nConsole Log (Line starting with 'Guide:'):\n$guideLine"
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
