def call(String imageNameAndTag) {
    script {
                    def tplContent = libraryResource 'trivy/html.tpl'
                    writeFile file: '/home/ubuntu/html.tpl', text: tplContent
                }

    def command = "trivy image --format template --template '@/home/ubuntu/html.tpl' -o ${WORKSPACE}/trivy-report.html ${imageNameAndTag}"
    def trivyOutput = sh(script: command, returnStdout: true).trim()

    echo "Trivy Scan Results:"
    echo trivyOutput
}