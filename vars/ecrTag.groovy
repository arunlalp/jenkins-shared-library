def call(Map params) {
  def ecrRepository = params.ecrRepository 
  def imageName = params.imageName 
  def imageTag = params.imageTag 
  
  ecrTag(imageName, ecrRepository, imageTag,)
}

def ecrTag(imageName, repository, imageTag) {
  def sourceImage = "$imageName:$imageTag"
  def targetImage = "$repository/$imageName:$imageTag"
  
  def ecrTagCommand = "docker tag $sourceImage $targetImage"  
  sh ecrTagCommand
}
