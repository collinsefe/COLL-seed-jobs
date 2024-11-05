String env = 'dev'
def githubUrl = 'https://github.com/collinsefe/dotnet-app-image.git'
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
                    branch('*/dev')
                }
            }
            // scriptPath("resources/${env.toUpperCase()}/Jenkinsfile")
        }
    }

    properties {
        // Disable concurrent builds for this pipeline job
        disableConcurrentBuilds()
    }
}
