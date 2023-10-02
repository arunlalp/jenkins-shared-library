def call(Map params) {
  def ecrRepository = params.ecrRepository 
  def imageName = params.imageName 
  def versionTag = params.versionTag 
  def awsRegion = params.awsRegion
  
  ecrLogin(ecrRepository, awsRegion)
  dockerTagAndPush(imageName, ecrRepository, versionTag)
}

def ecrLogin(ecrRepository, awsRegion) {
  def ecrLoginCommand = "aws ecr get-login-password --region $awsRegion | docker login --username AWS --password-stdin $ecrRepository"
  sh ecrLoginCommand
}

def dockerTagAndPush(imageName, ecrRepository, versionTag) {
  def sourceImage = "$imageName:$versionTag"
  def targetImage = "$ecrRepository:$imageName-$versionTag"

  def dockerTagCommand = "docker tag $sourceImage $targetImage"
  def dockerPushCommand = "docker push $targetImage"
  
  sh dockerTagCommand
  sh dockerPushCommand
}