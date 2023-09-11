def call(Map params) {
   def projectDirectory = params.projectDirectory

   tfLint(projectDirectory)
}

def tfLint(project_dir) {
   dir(project_dir) {
      def tflintConfig = resources/tflint/tflint.hcl
      writeFile file: '.tflint.hcl', text: tflintConfig

      def tfInitCommand = 'tflint --init'
      def tfLintCommand = 'tflint --disable-rule=terraform_required_version --disable-rule=terraform_required_providers'
      sh tfInitCommand
      sh tfLintCommand
   }   
}
