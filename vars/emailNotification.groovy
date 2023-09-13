def sendEmailNotification(String pipelineStatus, String recipientEmail) {
    def subject, body

    if (pipelineStatus == 'success') {
        subject = "Pipeline Success"
        body = """<html>
                    <body>
                        <p>Click <a href="${BUILD_URL}">here</a> to view the build details.</p>
                    </body>
                </html>"""
    } else if (pipelineStatus == 'failure') {
        subject = "Pipeline Failed"
        body = """<html>
                    <body>
                        <p>Click <a href="${BUILD_URL}">here</a> to view the build details.</p>
                    </body>
                </html>"""
    } else {
        subject = "Pipeline Status: $pipelineStatus"
        body = """<html>
                    <body>
                        <p>Click <a href="${BUILD_URL}">here</a> to view the build details.</p>
                    </body>
                </html>"""
    }

    emailext(
        subject: subject,
        body: body,
        recipientProviders: [[$class: 'DevelopersRecipientProvider']],
        to: recipientEmail,
        mimeType: 'text/html', // Set the content type to HTML
        attachLog: true
    )
}
