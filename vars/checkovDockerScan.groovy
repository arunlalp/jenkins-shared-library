def call(Map params) {
    def customPolicy = params.customPolicy
    // Create the 'checkov_policy' directory in the Jenkins workspace
    def checkovPolicyDir = "${env.WORKSPACE}/custom_policy"
    sh "mkdir -p $checkovPolicyDir"
    // Load the Python policy files from the shared library resources
    def dockerPolicyContent = libraryResource('checkov_policy/DockerLatestTag.py').trim()
    def initPolicyContent = libraryResource('checkov_policy/__init__.py').trim()
    // Write the policy contents to files in your workspace
    writeFile file: "${checkovPolicyDir}/DockerLatestTag.py", text: dockerPolicyContent
    writeFile file: "${checkovPolicyDir}/__init__.py", text: initPolicyContent
    checkovScan(customPolicy, checkovPolicyDir)
}
def checkovScan(custom_policy, checkov_policy_dir) {
    def checkovScanCommand = "checkov -d ${WORKSPACE} --external-checks-dir $checkov_policy_dir --check $custom_policy --hard-fail-on $custom_policy"
    sh checkovScanCommand
}





