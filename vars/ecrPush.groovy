def call(Map params) {
  def ecrRepository = params.ecrRepository 
  def imageName = params.imageName 
  def imageTag = params.imageTag 
  def awsRegion = params.awsRegion
  
  ecrLogin(ecrRepository, awsRegion)
  dockerTagAndPush(imageName, ecrRepository, imageTag,)
}

def ecrLogin(repository, region) {
  def ecrLoginCommand = "aws ecr-public get-login-password --region $region | docker login --username AWS --password-stdin $repository"
  sh ecrLoginCommand
}

def dockerTagAndPush(imageName, repository, imageTag) {
  def sourceImage = "$imageName:$imageTag"
  def targetImage = "$repository/$imageName:$imageTag"

  def dockerTagCommand = "docker tag $sourceImage $targetImage"
  def dockerPushCommand = "docker push $targetImage"
  
  sh dockerTagCommand
  sh dockerPushCommand
}
