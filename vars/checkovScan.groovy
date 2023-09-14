def call(Map params) {
    def projectDirectory = params.projectDirectory
    def planFileJson = params.planFileJson
    def customPolicy = params.customPolicy

    // Define a temporary directory to copy the checkov policies
    def tempCheckovDir = "${env.WORKSPACE}/temp_checkov_policies"

    // Copy the checkov policies from the shared library resources to the temporary directory
    sh "mkdir -p $tempCheckovDir"
    libraryResource('checkov_policy').copyTo(tempCheckovDir)

    // Run the checkovScan with the temporary checkov policies directory
    checkovScan(projectDirectory, planFileJson, customPolicy, tempCheckovDir)
}

def checkovScan(project_dir, plan_file_json, custom_policy, checkov_policies_dir) {
    // Use the absolute path to the temporary checkov policies directory
    def checkovScanCommand = "checkov -f $project_dir/$plan_file_json --external-checks-dir $checkov_policies_dir --check $custom_policy --hard-fail-on $custom_policy"
    sh checkovScanCommand
}
