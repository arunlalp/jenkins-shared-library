def call(Map params) {
   def projectDirectory = params.projectDirectory
   def variableFile = params.variableFile

   terraformApply(projectDirectory, variableFile)
}

def terraformApply(project_dir, var_file) {
   dir(project_dir) {
      def terraformApplyCommand = "terraform apply -var-file=../../vars/infra/dev/$var_file --auto-approve"
      sh terraformApplyCommand 
   }   
}