def call(Map params) {
   def tflintConfigFile = libraryResource('tflint.hcl')
   def variableFile = params.variableFile
   def directory = params.directory // Add this line to define the 'directory' parameter

   tfLint(tflintConfigFile, variableFile, directory)
}

def tfLint(directory, variableFile) {
   dir(directory) {
      // Copy the TFLint configuration file to the working directory
      sh "cp ${tflintConfigFile} .tflint.hcl"

      // Update the variable file path in the copied TFLint configuration
      sh "sed -i 's|varfile = .*|varfile = [\"${variableFile}\"]|' .tflint.hcl"

      // Run TFLint
      sh 'tflint --init'
      sh 'tflint --disable-rule=terraform_required_version --disable-rule=terraform_required_providers'
   }
}
