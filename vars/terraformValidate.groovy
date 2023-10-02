def call(Map params) {
   def projectDirectory = params.projectDirectory

   terraformValidate(projectDirectory)
}

def terraformValidate(directory) {
   dir(directory) {
      def terraformValidateCommand = "terraform validate"
      sh terraformValidateCommand 
   }   
}