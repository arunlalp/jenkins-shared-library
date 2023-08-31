def pushToECR(imageTag, repositoryUrl, region) {
    def ecrLoginCommand = "aws ecr-public get-login-password --region $region"
    def dockerLoginCommand = "docker login --username AWS --password-stdin $repositoryUrl"
    def dockerTagCommand = "docker tag arun_custom_repo:$imageTag $repositoryUrl/arun_custom_repo:$imageTag"
    def dockerPushCommand = "docker push $repositoryUrl/arun_custom_repo:$imageTag"

    def process = "${ecrLoginCommand} | ${dockerLoginCommand}".execute()
    process.waitFor()

    if (process.exitValue() == 0) {
        // Successfully logged in to ECR
        def tagProcess = dockerTagCommand.execute()
        tagProcess.waitFor()

        if (tagProcess.exitValue() == 0) {
            // Image tagged successfully
            def pushProcess = dockerPushCommand.execute()
            pushProcess.waitFor()

            if (pushProcess.exitValue() == 0) {
                println "Image pushed to ECR successfully."
            } else {
                println "Failed to push image to ECR."
                println "Error: ${pushProcess.err.text}"
            }
        } else {
            println "Failed to tag the Docker image."
            println "Error: ${tagProcess.err.text}"
        }
    } else {
        println "Failed to login to ECR."
        println "Error: ${process.err.text}"
    }
}


