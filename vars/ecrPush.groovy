def call(Map pushParms) {
    def ecrLoginCmd = "aws ecr-public get-login-password --region ${pushParms.awsRegion} | docker login --username AWS --password-stdin ${pushParms.ecrUrl}"
    ecrLoginCmd.execute()

    def imageTagCmd = "docker tag ${pushParms.dockerimageTag} ${pushParms.ecrimageTag}"
    imageTagCmd.execute()

    def ecrPushCmd = "docker push ${pushParms.ecrimageTag}"
    ecrPushCmd.execute()
}
