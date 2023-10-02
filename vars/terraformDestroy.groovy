def call(Map params) {
   def projectDirectory = params.projectDirectory
   def tfstateFile = params.tfstateFile
   def variableFile = params.variableFile

   terraformDestroy(projectDirectory, tfstateFile, variableFile)
}

def terraformDestroy(project_dir, tfstateFile, var_file) {
   dir(project_dir) {
      def terraformDestroyCommand = """
        terraform destroy \\
          -backend-config="key=dev/${tfstateFile}" \\
          -backend-config="bucket=dcube-terraform-state" \\
          -backend-config="region=us-west-2" \\
          -backend-config="dynamodb_table=terraform-state-lock" \\
          -var-file=../../vars/infra/dev/${var_file} --auto-approve
      """

      sh terraformDestroyCommand
   }
}