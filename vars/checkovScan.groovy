def call(Map params) {
    def projectDirectory = params.projectDirectory
    def planFileJson = params.planFileJson
    def customPolicy = params.customPolicy

    // Create the 'checkov_policy' directory in the Jenkins workspace
    def checkovPolicyDir = "${env.WORKSPACE}/custom_policy"
    sh "mkdir -p $checkovPolicyDir"

    // Load the Python policy files from the shared library resources
    def securityGroupPolicyContent = libraryResource('checkov_policy/SecurityGroupInboundCIDR.py').trim()
    def awsResourceCustomTags = libraryResource('checkov_policy/AWSResourceTags.py').trim()
    def initPolicyContent = libraryResource('checkov_policy/__init__.py').trim()

    // Write the policy contents to files in your workspace
    writeFile file: "${checkovPolicyDir}/security_group_policy.py", text: securityGroupPolicyContent
    writeFile file: "${checkovPolicyDir}/aws_resource_tags.py", text: awsResourceCustomTags
    writeFile file: "${checkovPolicyDir}/__init__.py", text: initPolicyContent

    checkovScan(projectDirectory, planFileJson, customPolicy, checkovPolicyDir)
}

def checkovScan(project_dir, plan_file_json, custom_policy, checkov_policy_dir) {
    def checkovScanCommand = "checkov -f $project_dir/$plan_file_json --external-checks-dir $checkov_policy_dir --check $custom_policy"
    sh checkovScanCommand
}