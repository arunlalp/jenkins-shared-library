def call(Map params) {
   def projectDirectory = params.projectDirectory
   def planFileJson = params.planFileJson
   def externalCheckDir = params.externalCheckDir
   def customPolicy = params.customPolicy

   checkovScan(projectDirectory, planFileJson, externalCheckDir, customPolicy)
}

def checkovScan(project_dir, plan_file_json, external_check_dir, custom_policy) {
   dir(project_dir) {
      def checkovScanCommand = "checkov -f $plan_file_json --external-checks-dir $external_check_dir --check $custom_policy --hard-fail-on $custom_policy"
      sh checkovScanCommand
   }   
}
