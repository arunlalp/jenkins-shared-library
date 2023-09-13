def sendEmailNotification(String pipelineStatus, String recipientEmail) {
    def subject, body, consoleLog

    if (pipelineStatus == 'success') {
        subject = "Pipeline Success"
        body = "The pipeline has successfully completed."
    } else if (pipelineStatus == 'failure') {
        subject = "Pipeline Failed"
        body = "The pipeline has failed. Please investigate."

        // Capture the console output and search for a line starting with 'Check'
        script {
            consoleLog = currentBuild.rawBuild.getLog(1000)
        }
        
        def linesContainingFailed = consoleLog.findAll { line -> line.contains('FAILED for resource:') }
        if (linesContainingFailed.size() >= 3) {
            def thirdLine = linesContainingFailed[2] // Index 2 for the third line (0-based index)
            body += "\n\nConsole Log (Third line containing 'FAILED for resource:'):\n$thirdLine"
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
        attachLog: false // We won't attach the log here
    )

    // Save the console output to a file
    writeFile file: '/var/lib/jenkins/consolelog.txt', text: consoleLog
}
