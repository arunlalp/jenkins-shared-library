def call(Map params) {
   def projectDirectory = params.projectDirectory
   def variableFile = params.variableFile

   terraformDestroy(projectDirectory, variableFile)
}

def terraformDestroy(project_dir, var_file) {
   dir(project_dir) {
      def terraformDestroyCommand = "terraform destroy -var-file=../../vars/infra/dev/${var_file} --auto-approve"
      sh terraformDestroyCommand
   }
}





