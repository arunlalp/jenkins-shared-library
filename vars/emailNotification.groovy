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
        
        def checkLine = consoleLog.find { line -> line.startsWith('FAILED for resource:') }
        if (checkLine) {
            body += "\n\nConsole Log 'FAILED for resource:'):\n$checkLine"
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
