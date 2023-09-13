def sendEmailNotification(String pipelineStatus, String recipientEmail) {
    def subject, body

    if (pipelineStatus == 'success') {
        subject = "Pipeline Success"
        body = """<html>
                    <head>
                        <style>
                            /* Add your CSS styling here */
                            body {
                                font-family: Arial, sans-serif;
                                background-color: #f0f0f0;
                            }
                            .header {
                                background-color: #007bff;
                                color: #ffffff;
                                padding: 10px;
                                text-align: center;
                            }
                            .content {
                                padding: 20px;
                            }
                            a {
                                color: #007bff;
                            }
                        </style>
                    </head>
                    <body>
                        <div class="header">
                            <h1>Pipeline Success</h1>
                        </div>
                        <div class="content">
                            <p>Your pipeline has successfully completed.</p>
                            <p>Click <a href="${BUILD_URL}">here</a> to view the build details.</p>
                        </div>
                    </body>
                </html>"""
    } else if (pipelineStatus == 'failure') {
        subject = "Pipeline Failed"
        body = """<html>
                    <head>
                        <style>
                            /* Add your CSS styling here */
                            body {
                                font-family: Arial, sans-serif;
                                background-color: #f0f0f0;
                            }
                            .header {
                                background-color: #ff0000;
                                color: #ffffff;
                                padding: 10px;
                                text-align: center;
                            }
                            .content {
                                padding: 20px;
                            }
                            a {
                                color: #007bff;
                            }
                        </style>
                    </head>
                    <body>
                        <div class="header">
                            <h1>Pipeline Failed</h1>
                        </div>
                        <div class="content">
                            <p>Your pipeline has failed. Please investigate.</p>
                            <p>Click <a href="${BUILD_URL}">here</a> to view the build details.</p>
                        </div>
                    </body>
                </html>"""
    } else {
        subject = "Pipeline Status: $pipelineStatus"
        body = """<html>
                    <head>
                        <style>
                            /* Add your CSS styling here */
                            body {
                                font-family: Arial, sans-serif;
                                background-color: #f0f0f0;
                            }
                            .header {
                                background-color: #333;
                                color: #ffffff;
                                padding: 10px;
                                text-align: center;
                            }
                            .content {
                                padding: 20px;
                            }
                            a {
                                color: #007bff;
                            }
                        </style>
                    </head>
                    <body>
                        <div class="header">
                            <h1>Pipeline Status: $pipelineStatus</h1>
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
