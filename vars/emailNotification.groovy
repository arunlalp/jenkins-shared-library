def sendEmailNotification(String pipelineStatus, String recipientEmail) {
    def subject, body

    if (pipelineStatus == 'success') {
        subject = "Pipeline Success"
        body = "The pipeline has successfully completed."
    } else if (pipelineStatus == 'failure') {
        subject = "Pipeline Failed"
        body = "The pipeline has failed. Please investigate."
    } else {
        subject = "Pipeline Status: $pipelineStatus"
        body = "The pipeline is in an unknown status: $pipelineStatus"
    }

    def logFilePath = '/var/lib/jenkins/consolelog.txt'
    def logFile = new File(logFilePath)
    logFile.text = '' // Clear the log file if it exists

    // Redirect console output to both the console and the log file
    def consoleOutput = new StringWriter()
    System.setOut(new PrintStream(new TeeOutputStream(System.out, logFile.outputStream())))
    System.setErr(new PrintStream(new TeeOutputStream(System.err, logFile.outputStream())))

    emailext(
        subject: subject,
        body: body,
        recipientProviders: [[$class: 'DevelopersRecipientProvider']],
        to: recipientEmail,
        attachLog: false // Set this to false because we are manually redirecting the console output
    )
}

