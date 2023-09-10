def call(String directory, String variableFile) {
    def tflintConfigFile = libraryResource('tflint.hcl')
    if (tflintConfigFile) {
        echo "Linting Terraform Code with TFLint"
        dir(directory) {
            // Copy the TFLint configuration file to the working directory
            sh "cp ${tflintConfigFile} .tflint.hcl"

            // Update the variable file path in the copied TFLint configuration
            sh "sed -i 's|varfile = .*|varfile = [\"${variableFile}\"]|' .tflint.hcl"
            
            // Run TFLint
            sh 'tflint --init'
            sh 'tflint --disable-rule=terraform_required_version --disable-rule=terraform_required_providers'
        }
    } else {
        error "TFLint configuration file not found in shared library resources"
    }
}
