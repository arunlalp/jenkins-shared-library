// Create a StringWriter to capture console output
def sw = new StringWriter()
def pw = new PrintWriter(sw)

// Redirect console output to the StringWriter
System.setOut(pw)

// Your existing sendEmailNotification function
def sendEmailNotification(String pipelineStatus, String recipientEmail) {
    def subject, body, logContent

    // Capture console output
    def consoleOutput = sw.toString()

    if (pipelineStatus == 'success') {
        subject = "Pipeline Success"
        body = "The pipeline has successfully completed."
    } else if (pipelineStatus == 'failure') {
        subject = "Pipeline Failed"
        body = "The pipeline has failed. Please investigate."

        // Extract lines starting with 'FAILED for resource:'
        def failedLines = consoleOutput.split('\n').findAll { it.startsWith('FAILED for resource:') }
        logContent = failedLines.join('\n') // Join the failed lines into a single string
    } else {
        subject = "Pipeline Status: $pipelineStatus"
        body = "The pipeline is in an unknown status: $pipelineStatus"
    }

    emailext(
        subject: subject,
        body: body,
        recipientProviders: [[$class: 'DevelopersRecipientProvider']],
        to: recipientEmail,
        attachLog: true,
        attachmentsPattern: "scanmail.txt", // Attach the console output file
        msg: logContent // Attach the failed lines to the email
    )
}

// Your pipeline logic here
def pipelineStatus = 'failure' // Set the pipeline status
def recipientEmail = 'recipient@example.com' // Set the recipient email

// Call the sendEmailNotification function
sendEmailNotification(pipelineStatus, recipientEmail)
