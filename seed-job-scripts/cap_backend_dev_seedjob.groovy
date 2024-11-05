String env = 'dev'
def githubUrl = 'https://gitlab.com/blue-harvest-assignments/cloud-assignment.git'
def gitCreds = "prodigital-collinsefe"

pipelineJob("CAP-FRONTEND-${env.toUpperCase()}-Job") {
    description("This Job is used to create the Node Server and is versioned. Changes should be made through the repo.")
    keepDependencies(false)

     definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url(githubUrl)
                        credentials(gitCreds)
                    }
                    branch('*/demo')
                }
            }
            scriptPath("resources/${env.toUpperCase()}/Jenkinsfile")
        }
    }

    disabled(false)
    concurrentBuild(false)

       steps {
        // Load and execute the Jenkinsfile as a shell script
        // scriptPath("resources/${env.toUpperCase()}/Jenkinsfile")
        shell(readFileFromWorkspace("resources/${env.toUpperCase()}/run_backend.sh"))
    }

    
}
