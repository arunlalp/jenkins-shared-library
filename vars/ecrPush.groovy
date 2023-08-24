def call() {
  sh 'aws ecr-public get-login-password --region us-east-1 | docker login --username AWS --password-stdin public.ecr.aws/c1l9p0p2'
  sh "docker tag arun_custom_repo:latest public.ecr.aws/c1l9p0p2/arun_custom_repo:latest"
  sh "docker push public.ecr.aws/c1l9p0p2/arun_custom_repo:latest"
}
