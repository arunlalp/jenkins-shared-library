def call(Map params) {
  def imageName = params.imageName
  def ecrRepository = params.ecrRepository
  def imageTag = params.imageTag

  ecrPush(imageName, ecrRepository, imageTag)
}

def ecrPush(imageName, repository, imageTag) {
  def targetImage = "$repository/$imageName:$imageTag"

  def ecrPushCommand = "docker push $targetImage"  
  sh ecrPushCommand
}
