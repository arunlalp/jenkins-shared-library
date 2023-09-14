def call(Map params) {
   def projectDirectory = params.projectDirectory
   def planFileJson = params.planFileJson
   def customPolicy = params.customPolicy

   // Load Checkov policies directory from library resource
   def externalCheckDir = libraryResource("checkov_policy")
   
   // Create a temporary directory in the workspace
   def tempCheckovDir = checkout([$class: 'WorkspaceCleanup', deleteDirs: ['checkov_temp']])
   
   // Copy the policies from the library to the temporary directory
   sh "cp -r $externalCheckDir/* $tempCheckovDir/"
   
   checkovScan(projectDirectory, planFileJson, tempCheckovDir, customPolicy)
}

def checkovScan(project_dir, plan_file_json, external_check_dir, custom_policy) {
   // Specify the path to the temporary Checkov policies directory and custom policy in the command
   def checkovScanCommand = "checkov -f $project_dir/$plan_file_json --external-checks-dir $external_check_dir --check $custom_policy"
   sh checkovScanCommand   
}
