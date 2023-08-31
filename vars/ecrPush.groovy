def call() {
  def ecrRepository = 'public.ecr.aws/c1l9p0p2'
  def imageName = 'arun_custom_repo'
  def imageTag = 'latest'
  
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

