def call(Map params) {
   def projectDirectory = params.projectDirectory

   terraformInit(projectDirectory)
}

def terraformInit(project_dir) {
   dir(project_dir) {
      def terraformInitCommand = """
        terraform init \\
          -backend-config="key=dev/jenkins_agent.tfstate" \\
          -backend-config="bucket=dcube-terraform-state" \\
          -backend-config="region=us-west-2" \\
          -backend-config="dynamodb_table=terraform-state-lock" \\
          -var-file=../../vars/infra/dev/jenkins-agent.tfvars
      """

      sh terraformInitCommand
   }
}
