def githubUrl = 'https://github.com/collinsefe/spring-boot-react-app.git'
def gitCreds = 'prodigital-collinsefe'

def environments = [
    dev: [
        name: 'Development',
        branch: 'dev'
    ],
    test: [
        name: 'Testing',
        branch: 'test'
    ],
         prod: [
        name: 'Production',
        branch: 'main'
    ],
]

environments.each { env, config ->
    pipelineJob("MEDICAL-APP-BACKEND-${env.toUpperCase()}-Job") {
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
