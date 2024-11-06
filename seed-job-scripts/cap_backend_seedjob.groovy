def githubUrl = 'https://github.com/collinsefe/spring-boot-react-example.git'
def gitCreds = "prodigital-collinsefe"


def environments = [
    demo: [
        name: "Demo",
        branch: "demo"
    ],
    test: [
        name: "Testing",
        branch: "test"
    ],
]

environments.each { env, config ->
    job("COLL-BACKEND-${env.toUpperCase()}-JOB") {
        description("Job to deploy the application to the ${config.name} environment.")
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

