def call(Map params) {
   def projectDirectory = params.projectDirectory

   tfLint(projectDirectory)
}

def tfLint(project_dir) {
   dir(project_dir) {
      def tflintConfig = """
        config {
            module = true
            force = false
            disabled_by_default = false
            varfile = ["../../../vars/dev/ec2.tfvars"]
        }

        plugin "aws" {
            enabled = true
            version = "0.26.0"
            source = "github.com/terraform-linters/tflint-ruleset-aws"
        }
      """
      writeFile file: '.tflint.hcl', text: tflintConfig

      def tfInitCommand = 'tflint --init'
      def tfLintCommand = 'tflint --disable-rule=terraform_required_version --disable-rule=terraform_required_providers'
      sh tfInitCommand
      sh tfLintCommand
   }   
}
