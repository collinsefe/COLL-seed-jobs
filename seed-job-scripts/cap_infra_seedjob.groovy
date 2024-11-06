def githubUrl = "https://github.com/collinsefe/aws-infra.git"
def gitCreds = 'prodigital-collinsefe'
def env = "dev"

job("CAP-INFRA-${env.toUpperCase()}-Job"){
    description("This Job is used to create the Node Server and its versioned. Changes should be made through the repo")
    keepDependencies(false)

        // definition {
            cpsScm {
                scm {
                    git {
                        remote {
                            url(githubUrl)
                            credentials(gitCreds)
                        }
                        branch("*/${config.branch}")
                    }
                }
            }
        // }
        triggers {
            githubPush()
        }

        properties {
            disableConcurrentBuilds()
        }
    }
