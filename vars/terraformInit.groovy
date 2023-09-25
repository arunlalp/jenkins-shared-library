def call(Map params) {
   def projectDirectory = params.projectDirectory
   def variableFile = params.variableFile

   terraformInit(projectDirectory, variableFile,)
}

def terraformInit(project_dir, var_file) {
   dir(project_dir, var_file) {
      def terraformInitCommand = """
        terraform init \\
          -backend-config="key=dev/jenkins_agent.tfstate" \\
          -backend-config="bucket=dcube-terraform-state" \\
          -backend-config="region=us-west-2" \\
          -backend-config="dynamodb_table=terraform-state-lock" \\
          -var-file=../../vars/infra/dev/$var_file
      """

      sh terraformInitCommand
   }
}
