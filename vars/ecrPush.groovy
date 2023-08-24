def call() {
  sh 'aws ecr-public get-login-password --region us-east-1 | docker login --username AWS --password-stdin public.ecr.aws/m1n8x9y9'
  sh "docker tag techiescamp-repo:latest public.ecr.aws/m1n8x9y9/techiescamp-repo:latest"
  sh "docker push public.ecr.aws/m1n8x9y9/techiescamp-repo:latest"
}
