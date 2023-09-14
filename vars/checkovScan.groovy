def call(Map params) {
    def projectDirectory = params.projectDirectory
    def planFileJson = params.planFileJson
    def customPolicy = params.customPolicy

    // Load the Python policy files from the shared library resources
    def securityGroupPolicyContent = libraryResource('checkov_policy/SecurityGroupInboundCIDR.py').trim()
    def initPolicyContent = libraryResource('checkov_policy/__init__.py').trim()

    // Write the policy contents to files in your workspace
    writeFile file: "${env.WORKSPACE}/checkov_policy/security_group_policy.py", text: securityGroupPolicyContent
    writeFile file: "${env.WORKSPACE}/chekcov_policy/__init__.py", text: initPolicyContent

    checkovScan(projectDirectory, planFileJson, customPolicy)
}

def checkovScan(project_dir, plan_file_json, custom_policy) {
    def checkovScanCommand = "checkov -f $project_dir/$plan_file_json --external-checks $env.WORKSPACE/checkov_policy --check $custom_policy --hard-fail-on $custom_policy"
    sh checkovScanCommand
}
