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
pipelineJob("COLL-BACKEND-${env.toUpperCase()}-Job") {
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
