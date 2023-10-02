def call(Map params) {
   def projectDirectory = params.projectDirectory
   def tflintConfig = libraryResource("tflint/tflint.hcl")
   
   // Set the value of tfvarsFile using the parameter
   def tfvarsFile = params.tfvarsFile

   tfLint(projectDirectory, tflintConfig, tfvarsFile)
}

def tfLint(project_dir, tflintConfig, tfvarsFile) {
   dir(project_dir) {
      // Create a modified .tflint.hcl file with the variable
      writeFile file: '.tflint.hcl', text: tflintConfig.replace('var.tfvars_file', '"' + tfvarsFile + '"')

      def tfInitCommand = 'tflint --init'
      def tfLintCommand = 'tflint'
      sh tfInitCommand
      sh tfLintCommand
   }   
}