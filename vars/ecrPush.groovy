def call(Map pushParms) {
    def ecrLoginCmd = "aws ecr-public get-login-password --region ${pushParms.awsRegion} | docker login --username AWS --password-stdin ${pushParms.ecrUrl}"
    def ecrLoginOutput = ecrLoginCmd.execute()
    if (ecrLoginOutput.text) {
        println "ECR login successful."
    } else {
        println "ECR login failed."
    }

    def imageTagCmd = "docker tag ${pushParms.dockerimageTag} ${pushParms.ecrimageTag}"
    def imageTagOutput = imageTagCmd.execute()
    if (imageTagOutput.text) {
        println "Image tagging successful."
    } else {
        println "Image tagging failed."
    }

    def ecrPushCmd = "docker push ${pushParms.ecrimageTag}"
    def ecrPushOutput = ecrPushCmd.execute()
    if (ecrPushOutput.text) {
        println "Image push to ECR successful."
    } else {
        println "Image push to ECR failed."
    }
}
