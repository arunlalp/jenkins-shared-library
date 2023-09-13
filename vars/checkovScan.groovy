def call(Map params) {
   def projectDirectory = params.projectDirectory
   def planFileJson = params.planFileJson
   def checkovPolicy = libraryResource("checkov_policy")
   def customPolicy = params.customPolicy

   checkovScan(projectDirectory, planFileJson, checkovPolicy, customPolicy)
}

def checkovScan(project_dir, plan_file_json, checkovPolicy, custom_policy) {
   def checkovScanCommand = "checkov -f $project_dir/$plan_file_json --external-checks-dir ${checkovPolicy} --check $custom_policy --hard-fail-on $custom_policy"
   sh checkovScanCommand   
}
