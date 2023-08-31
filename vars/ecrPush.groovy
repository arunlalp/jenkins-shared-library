def call() {
  def ecrRepository = env.ECR_REPOSITORY
  def imageName = env.IMAGE_NAME
  def imageTag = env.IMAGE_TAG
  
  dockerLogin(ecrRepository)
  dockerTagAndPush(imageName, ecrRepository, imageTag)
}

def dockerLogin(repository) {
  def ecrLoginCommand = "aws ecr-public get-login-password --region us-east-1 | docker login --username AWS --password-stdin $repository"
  sh ecrLoginCommand
}

def dockerTagAndPush(imageName, repository, imageTag) {
  def sourceImage = "$imageName:$imageTag"
  def targetImage = "$repository/$imageName:$imageTag"
  
  sh "docker tag $sourceImage $targetImage"
  sh "docker push $targetImage"
}
