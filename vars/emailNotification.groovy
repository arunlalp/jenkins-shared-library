def sendEmailNotification(String pipelineStatus, String recipientEmail) {
    def subject, body

    if (pipelineStatus == 'success') {
        subject = "[$JOB_NAME] Build #${BUILD_NUMBER} - Pipeline Success"
        body = """<html>
                    <head>
                        <!-- CSS styling as before -->
                    </head>
                    <body>
                        <div class="header">
                            <h1>[$JOB_NAME] Build #${BUILD_NUMBER} - Pipeline Success</h1>
                        </div>
                        <div class="content">
                            <p>Your pipeline has successfully completed.</p>
                            <p>Click <a href="${BUILD_URL}">here</a> to view the build details.</p>
                        </div>
                    </body>
                </html>"""
    } else if (pipelineStatus == 'failure') {
        subject = "[$JOB_NAME] Build #${BUILD_NUMBER} - Pipeline Failed"
        body = """<html>
                    <head>
                        <!-- CSS styling as before -->
                    </head>
                    <body>
                        <div class="header">
                            <h1>[$JOB_NAME] Build #${BUILD_NUMBER} - Pipeline Failed</h1>
                        </div>
                        <div class="content">
                            <p>Your pipeline has failed. Please investigate.</p>
                            <p>Click <a href="${BUILD_URL}">here</a> to view the build details.</p>
                        </div>
                    </body>
                </html>"""
    } else {
        subject = "[$JOB_NAME] Build #${BUILD_NUMBER} - Pipeline Status: $pipelineStatus"
        body = """<html>
                    <head>
                        <!-- CSS styling as before -->
                    </head>
                    <body>
                        <div class="header">
                            <h1>[$JOB_NAME] Build #${BUILD_NUMBER} - Pipeline Status: $pipelineStatus</h1>
                        </div>
                        <div class="content">
                            <p>Your pipeline is in an unknown status: $pipelineStatus</p>
                            <p>Click <a href="${BUILD_URL}">here</a> to view the build details.</p>
                        </div>
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
