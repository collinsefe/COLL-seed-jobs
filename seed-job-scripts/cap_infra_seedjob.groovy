def githubUrl = "https://github.com/collinsefe/aws-infra.git"
def gitCreds = 'prodigital-collinsefe'


def environments = [
    dev: [
        name: 'Development',
        branch: 'dev'
    ],
    destroy: [
        name: 'Destroy',
        branch: 'test'
    ],
]

environments.each { env, config ->
    pipelineJob("CAP-INFRA-${env.toUpperCase()}-Job") {
        description('This Job is used to create the Node Server and is versioned. Changes should be made through the repo.')
        keepDependencies(false)

        definition {
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
        }
        triggers {
            githubPush()
        }

        properties {
            disableConcurrentBuilds()
        }
    }
}
