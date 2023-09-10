def call(Map params) {
   def projectDirectory = params.projectDirectory

   terraformInit(projectDirectory)
}

def terraformInit(directory) {
   def terraformInitCommand = "terraform init $directory --reconfigure"
   sh terraformInitCommand 
}
