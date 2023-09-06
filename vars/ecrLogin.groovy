def call(Map Params) {
  def ecrRepository = Params.ecrRepository
  def awsRegion = Params.awsRegion

  dockerLogin(ecrRepository, awsRegion)  
}

def dockerLogin(repository, region) {
  def ecrLoginCommand = "aws ecr-public get-login-password --region $region | docker login --username AWS --password-stdin $repository"
  sh ecrLoginCommand
}
