def call(Map params) {
   def projectDirectory = params.projectDirectory
   def variableFile = params.variableFile

   terraformInit(projectDirectory, variableFile)
}

def terraformInit(project_dir, var_file) {
   dir(project_dir) {
      // Construct the terraform init command with backend and variable configurations
      def terraformInitCommand = """
        terraform init \\
          -backend-config="key=dev/jenkins_agent.tfstate" \\
          -backend-config="bucket=dcube-terraform-state" \\
          -backend-config="region=us-west-2" \\
          -backend-config="dynamodb_table=terraform-state-lock" \\
          -var-file=../../vars/infra/dev/jenkins-agent.tfvars
      """
      
      // Execute the terraform init command
      sh terraformInitCommand
   }
}
