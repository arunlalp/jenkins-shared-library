def call(Map params) {
    def projectDirectory = params.projectDirectory
    def planFileJson = params.planFileJson
    def customPolicy = params.customPolicy

    // Create the 'checkov_policy' directory in the Jenkins workspace
    def checkovPolicyDir = "${env.WORKSPACE}/custom_policy"
    sh "mkdir -p $checkovPolicyDir"

    // Load the Python policy files from the shared library resources
    def securityGroupPolicyContent = libraryResource('checkov_policy/SecurityGroupInboundCIDR.py').trim()
    def initPolicyContent = libraryResource('checkov_policy/__init__.py').trim()

    // Write the policy contents to files in your workspace
    writeFile file: "${checkovPolicyDir}/security_group_policy.py", text: securityGroupPolicyContent
    writeFile file: "${checkovPolicyDir}/__init__.py", text: initPolicyContent

    checkovScan(projectDirectory, planFileJson, customPolicy, checkovPolicyDir)
}

def checkovScan(project_dir, plan_file_json, custom_policy, checkov_policy_dir) {
    def checkovScanCommand = "checkov -f $project_dir/$plan_file_json --external-checks-dir $checkov_policy_dir --check $custom_policy --output json > checkov-report.json"
    sh checkovScanCommand

    // Read the JSON report
    def jsonReport = readFile(file: 'checkov-report.json')
    
    // Convert JSON to a Groovy map
    def reportMap = new groovy.json.JsonSlurper().parseText(jsonReport)

    // Generate an HTML table from the report
    def htmlTable = "<table border='1'><tr><th>Check</th><th>File</th><th>Resource</th><th>Line</th><th>Message</th></tr>"
    
    reportMap.each { issue ->
        htmlTable += "<tr><td>${issue.check_name}</td><td>${issue.file_path}</td><td>${issue.resource}</td><td>${issue.line}</td><td>${issue.check_name}</td></tr>"
    }
    
    htmlTable += "</table>"

    // Write the HTML table to a file
    writeFile file: 'checkov-report.html', text: htmlTable
}


