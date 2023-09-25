def call(Map params) {
   def projectDirectory = params.projectDirectory
   def variableFile = params.variableFile
   def backendKey = params.backendKey

   terraformInit(projectDirectory, variableFile, backendKey)
}

def terraformInit(project_dir, var_file, backend_key) {
   dir(project_dir, var_file, backend_key) {
      // Construct the terraform init command with backend and variable configurations
      def terraformInitCommand = """
        terraform init \\
          -backend-config="key=dev/$backend_key.tfstate" \\
          -backend-config="bucket=dcube-terraform-state" \\
          -backend-config="region=us-west-2" \\
          -backend-config="dynamodb_table=terraform-state-lock" \\
          -var-file=../../vars/infra/dev/$var_file
      """
      
      // Execute the terraform init command
      sh terraformInitCommand
   }
}
