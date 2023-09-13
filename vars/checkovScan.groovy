def call(Map params) {
   def projectDirectory = params.projectDirectory
   def planFileJson = params.planFileJson
   def customPolicy = params.customPolicy

      def checkovPolicy = libraryResource 'checkov_policy/SecurityGroupInboundCIDR.py'
      writeFile file: '/home/ubuntu/custom_checks/SecurityGroupInboundCIDR.py', text: checkovPolicy
      def checkovInit = libraryResource 'checkov_policy/__init__.py'
      writeFile file: '/home/ubuntu/custom_checks/__init__.py', text: checkovInit
   
   def checkovScanCommand = "checkov -f $project_dir/$plan_file_json --external-checks-dir $external_check_dir --check '/home/ubuntu/custom_checks' --hard-fail-on $custom_policy"
   sh checkovScanCommand   
}


