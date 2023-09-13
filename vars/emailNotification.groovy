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

    def email = emailext(
        subject: "${PROJECT_NAME} - ${BUILD_NUMBER}",
        body:  """<html><body>
                    <p>Click <a href="${BUILD_URL}">here</a> to view the build details.</p>
                    <pre>
                    ${readFile(reportPath)}
                    </pre>
                </body></html>""",
        to: "${recipientEmail}",
    )
    
}
