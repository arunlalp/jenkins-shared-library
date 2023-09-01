def call(Map params) {
  def ecrRepository = params.ecrRepository 
  def imageName = params.imageName 
  def imageTag = params.imageTag 
  def awsRegion = params.awsRegion
  
  dockerLogin(ecrRepository)
  dockerTagAndPush(imageName, ecrRepository, imageTag, awsRegion)
}

def dockerLogin(repository) {
  def ecrLoginCommand = "aws ecr-public get-login-password --region $awsRegion | docker login --username AWS --password-stdin $repository"
  sh ecrLoginCommand
}

def dockerTagAndPush(imageName, repository, imageTag) {
  def sourceImage = "$imageName:$imageTag"
  def targetImage = "$repository/$imageName:$imageTag"
  
  sh "docker tag $sourceImage $targetImage"
  sh "docker push $targetImage"
}
