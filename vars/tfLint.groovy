def call(Map params) {
   def projectDirectory = params.projectDirectory
   def tflintConfig = libraryResource("tflint/tflint.hcl")

   tfLint(projectDirectory, tflintConfig)
}

def tfLint(project_dir, tflintConfig) {
   dir(project_dir) {
      writeFile file: '.tflint.hcl', text: tflintConfig

      def tfInitCommand = 'tflint --init'
      def tfLintCommand = 'tflint --disable-rule=terraform_required_version --disable-rule=terraform_required_providers'
      sh tfInitCommand
      sh tfLintCommand
   }   
}
