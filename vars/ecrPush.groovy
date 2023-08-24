def call() {
  sh 'aws ecr get-login-password --region us-west-2 | docker login --username AWS --password-stdin 814200988517.dkr.ecr.us-west-2.amazonaws.com'
  sh "docker tag my-app:1.0 814200988517.dkr.ecr.us-west-2.amazonaws.com/docker-images:latest"
  sh "docker push 814200988517.dkr.ecr.us-west-2.amazonaws.com/docker-images:latest"
}
