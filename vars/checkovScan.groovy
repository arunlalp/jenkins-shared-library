def call(Map params) {
   def projectDirectory = params.projectDirectory
   def planFileJson = params.planFileJson
   def customPolicy = params.customPolicy

   // Load Checkov policies directory from library resource
   def externalCheckDir = libraryResource("checkov_policy")

   // Modify the external policies directory path to include the full Jenkins workspace path
   def modifiedExternalCheckDir = "${workspace}/$externalCheckDir"

   checkovScan(projectDirectory, planFileJson, modifiedExternalCheckDir, customPolicy)
}

def checkovScan(project_dir, plan_file_json, external_check_dir, custom_policy) {
   // Specify the path to the modified external Checkov policies directory and custom policy in the command
   def checkovScanCommand = "checkov -f $project_dir/$plan_file_json --external-checks-dir $external_check_dir --check $custom_policy"
   sh checkovScanCommand   
}
