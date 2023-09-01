def call(Map params) {
  def ecrRepository = params.ecrRepository 
  def imageName = params.imageName 
  def imageTag = params.imageTag 
  def awsRegion = params.awsRegion
  
  dockerLogin(ecrRepository, awsRegion)
  dockerTagAndPush(imageName, ecrRepository, imageTag,)
}

def dockerLogin(repository, region) {
  def ecrLoginCommand = "aws ecr-public get-login-password --region $region | docker login --username AWS --password-stdin $repository"
  sh ecrLoginCommand
}

def dockerTagAndPush(imageName, repository, imageTag) {
  def sourceImage = "$imageName:$imageTag"
  def targetImage = "$repository/$imageName:$imageTag"
  
  sh "docker tag $sourceImage $targetImage"
  sh "docker push $targetImage"
}
